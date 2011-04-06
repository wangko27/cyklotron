package net.cyklotron.cms.catalogue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.queryParser.QueryParser.Operator;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.objectledge.coral.datatypes.ResourceList;
import org.objectledge.coral.entity.EntityDoesNotExistException;
import org.objectledge.coral.security.Subject;
import org.objectledge.coral.session.CoralSession;
import org.objectledge.coral.store.InvalidResourceNameException;
import org.objectledge.coral.store.Resource;
import org.objectledge.coral.table.comparator.NameComparator;

import net.cyklotron.cms.category.CategoryException;
import net.cyklotron.cms.category.CategoryResource;
import net.cyklotron.cms.category.CategoryService;
import net.cyklotron.cms.documents.DocumentNodeResource;
import net.cyklotron.cms.files.FileResource;
import net.cyklotron.cms.related.RelatedService;
import net.cyklotron.cms.search.PoolResource;
import net.cyklotron.cms.search.SearchConstants;
import net.cyklotron.cms.search.SearchException;
import net.cyklotron.cms.search.SearchService;
import net.cyklotron.cms.site.SiteResource;

public class CatalogueService
{
    private final SearchService searchService;

    private final RelatedService relatedService;

    private final CategoryService categoryService;

    private static final String[] SEARCH_FIELDS = { "index_title", "index_abbreviation",
                    "index_content", "keywords", "authors", "sources" };

    /**
     * No-arg constructor for mocking.
     */
    protected CatalogueService()
    {
        this.searchService = null;
        this.relatedService = null;
        this.categoryService = null;
    }

    /**
     * Create CatalogueService instance.
     * 
     * @param searchService site service.
     * @param relatedService related resources service.
     * @param categoryService category service.
     */
    public CatalogueService(SearchService searchService, RelatedService relatedService,
        CategoryService categoryService)
    {
        this.searchService = searchService;
        this.relatedService = relatedService;
        this.categoryService = categoryService;
    }

    /**
     * Return configuration node for catalogue application in a given site.
     * 
     * @param site the site.
     * @param coralSession coral session.
     * @return configuration node.
     */
    public CatalogueConfigResource getConfig(SiteResource site, CoralSession coralSession)
    {
        Resource[] res;
        res = coralSession.getStore().getResourceByPath(site, "applications/catalogue");
        if(res.length > 1)
        {
            throw new IllegalStateException("multiple catalogue nodes exist under site "
                + site.getName());
        }
        else if(res.length == 1)
        {
            return (CatalogueConfigResource)res[0];
        }
        else
        {
            res = coralSession.getStore().getResourceByPath(site, "applications");
            if(res.length != 1)
            {
                throw new IllegalStateException("applications node under site " + site.getName()
                    + " missing or duplicated");
            }
            CatalogueConfigResource config;
            try
            {
                config = CatalogueConfigResourceImpl.createCatalogueConfigResource(coralSession,
                    "catalogue", res[0]);
            }
            catch(InvalidResourceNameException e)
            {
                throw new RuntimeException("internal error", e);
            }
            return config;
        }
    }

    /**
     * Validate a resource as candidate for an index item.
     * 
     * @param res the resource, either {@link DocumentNodeResource} or {@link FileResource}.
     * @param site catalogue location
     * @param coralSession coral session
     * @return set of problems, hopefully empty.
     * @throws NotConfiguredException when configuration for catalogue in given site is missing.
     */
    public Set<Problem> validateIndexCardCandidate(Resource res, SiteResource site,
        CoralSession coralSession)
        throws NotConfiguredException
    {
        Set<Problem> problems = new HashSet<Problem>();
        if(res instanceof DocumentNodeResource)
        {
            return validateDocumentIndexCardCandidate((DocumentNodeResource)res, site, coralSession);
        }
        if(res instanceof FileResource)
        {
            return validateFileIndexCardCandidate((FileResource)res, site, coralSession);
        }
        problems.add(Problem.INVALID_CLASS);
        return problems;
    }

    /**
     * Create an index card for a given resource.
     * 
     * @param res the resource, either {@link DocumentNodeResource} or {@link FileResource}. It is
     *        expected to be problem-free accoding to
     *        {@link #validateIndexCardCandidate(Resource, SiteResource, CoralSession)}.
     * @param site catalogue location
     * @param coralSession coral session
     * @param locale locale used for sorting downloads by name, when manual ordering is not used.
     * @return an IndexCard
     * @throws IllegalArgumentException when resource has problems.
     * @throws NotConfiguredException when configuration for catalogue in given site is missing.
     */
    public IndexCard getIndexCard(Resource res, SiteResource site, CoralSession coralSession,
        Locale locale)
        throws IllegalArgumentException, NotConfiguredException
    {
        Set<Problem> problems = validateIndexCardCandidate(res, site, coralSession);
        if(!problems.isEmpty())
        {
            throw new IllegalArgumentException("resource " + res.getIdString() + " has problems "
                + problems.toString());
        }
        DocumentNodeResource doc;
        FileResource file;
        List<FileResource> downloads;
        if(res instanceof DocumentNodeResource)
        {
            doc = (DocumentNodeResource)res;
            downloads = findDownloads(doc, site, coralSession, locale);
            return new IndexCard(doc, downloads);
        }
        if(res instanceof FileResource)
        {
            file = (FileResource)res;
            List<DocumentNodeResource> docCandidates = findDescriptionDocs(file, site, coralSession);
            if(docCandidates.size() == 1)
            {
                doc = docCandidates.get(0);
            }
            else
            {
                throw new IllegalArgumentException("multiple description documents for file "
                    + file.toString());
            }
            downloads = findDownloads(doc, site, coralSession, locale);
            return new IndexCard(doc, downloads);
        }
        throw new IllegalArgumentException("resource " + res.getIdString() + " has invalid class");
    }

    /**
     * Create a problem report for catalogue in a given site.
     * 
     * @param site the site.
     * @param coralSession coral session.
     * @param locale locale used for sorting downloads by name, when manual ordering is not used.
     * @return report contents.
     * @throws NotConfiguredException when configuration for catalogue in given site is missing.
     * @throws CategoryException when category service error occurs.
     */
    public List<ProblemReportItem> getProblemReport(SiteResource site, CoralSession coralSession,
        Locale locale)
        throws NotConfiguredException, CategoryException
    {
        List<ProblemReportItem> report = new ArrayList<ProblemReportItem>();
        CategoryResource markerCategory = getConfig(site, coralSession).getCategory();
        if(markerCategory == null)
        {
            throw new NotConfiguredException("marker category is not set");
        }
        Resource[] all = categoryService.getResources(coralSession, markerCategory, false);
        for(Resource res : all)
        {
            Set<Problem> problems = validateIndexCardCandidate(res, site, coralSession);
            if(!problems.isEmpty())
            {
                List<DocumentNodeResource> descriptionDocCandidates = null;
                List<FileResource> downloads = null;
                if(res instanceof DocumentNodeResource)
                {
                    downloads = findDownloads((DocumentNodeResource)res, site, coralSession, locale);
                }
                if(res instanceof FileResource)
                {
                    descriptionDocCandidates = findDescriptionDocs((FileResource)res, site,
                        coralSession);
                }
                report
                    .add(new ProblemReportItem(res, descriptionDocCandidates, downloads, problems));
            }
        }
        return report;
    }

    /**
     * Filter a problem report so that only items that have one or more problems from a predefined
     * set are included.
     * 
     * @param in a list of problem report items.
     * @param problemTypes a set of problems that should be included in the report.
     * @return a filtered list of problem report items.
     */
    public List<ProblemReportItem> filterProblemReport(List<ProblemReportItem> in,
        Set<Problem> problemTypes)
    {
        List<ProblemReportItem> out = new ArrayList<ProblemReportItem>(in.size());
        itemLoop: for(ProblemReportItem item : in)
        {
            for(Problem problem : item.getProblems())
            {
                if(problemTypes.contains(problem))
                {
                    out.add(item);
                    continue itemLoop;
                }
            }
        }
        return out;
    }

    /**
     * Retrieve all catalogue index items for a given site.
     * 
     * @param site the site.
     * @param coralSession coral session.
     * @param locale locale used for sorting downloads by name, when manual ordering is not used.
     * @return unordered list of index items, intended to be used with {@link IndexCardTableModel}.
     * @throws NotConfiguredException when configuration for catalogue in given site is missing.
     * @throws CategoryException when category service error occurs.
     */
    public List<IndexCard> getAllItems(SiteResource site, CoralSession coralSession,
        Locale locale)
        throws NotConfiguredException, CategoryException
    {
        CategoryResource markerCategory = getConfig(site, coralSession).getCategory();
        if(markerCategory == null)
        {
            throw new NotConfiguredException("marker category is not set");
        }
        Resource[] all = categoryService.getResources(coralSession, markerCategory, false);
        // a set is used to make sure we have a single index card per description document
        Set<IndexCard> indexCards = new HashSet<IndexCard>();
        for(Resource res : all)
        {
            Set<Problem> problems = validateIndexCardCandidate(res, site, coralSession);
            if(problems.isEmpty())
            {
                indexCards.add(getIndexCard(res, site, coralSession, locale));
            }
        }
        return new ArrayList<IndexCard>(indexCards);
    }

    public List<IndexCard> search(String queryString, SiteResource site,
        CoralSession coralSession, Locale locale)
        throws NotConfiguredException, SearchException
    {
        PoolResource searchPool = getConfig(site, coralSession).getSearchPool();
        if(searchPool == null)
        {
            throw new NotConfiguredException("searchPool is not set");
        }
        Analyzer analyzer = searchService.getAnalyzer(locale);
        QueryParser parser = new MultiFieldQueryParser(Version.LUCENE_30, SEARCH_FIELDS, analyzer);
        parser.setDefaultOperator(Operator.AND);
        Set<Long> uniqueIds = new HashSet<Long>();
        Searcher searcher = null;
        try
        {
            Query query = parser.parse(queryString);
            searcher = searchService.getSearchingFacility().getSearcher(
                new PoolResource[] { searchPool }, coralSession.getUserSubject());
            int numHits = searcher.maxDoc() > 0 ? searcher.maxDoc() : 1;
            TopDocs hits = searcher.search(query, null, numHits);
            for(ScoreDoc hit : hits.scoreDocs)
            {
                org.apache.lucene.document.Document doc = searcher.doc(hit.doc);
                uniqueIds.add(Long.parseLong(doc.get(SearchConstants.FIELD_ID)));
            }
        }
        catch(Exception e)
        {
            throw new SearchException("full text search failed", e);
        }
        finally
        {
            if(searcher != null)
            {
                searchService.getSearchingFacility().returnSearcher(searcher);
            }
        }
        Set<IndexCard> indexCards = new HashSet<IndexCard>();
        for(long resId : uniqueIds)
        {
            try
            {
                Resource res = coralSession.getStore().getResource(resId);
                Set<Problem> problems = validateIndexCardCandidate(res, site, coralSession);
                if(problems.isEmpty())
                {
                    indexCards.add(getIndexCard(res, site, coralSession, locale));
                }
            }
            catch(EntityDoesNotExistException e)
            {
                // id of deleted resource in stale index, most probably
            }
        }
        return new ArrayList<IndexCard>(indexCards);
    }

    /**
     * Validate a document as candidate for an index item.
     * 
     * @param doc the document.
     * @param site catalogue location
     * @param coralSession coral session
     * @return set of problems, hopefully empty.
     * @throws NotConfiguredException when configuration for catalogue in given site is missing.
     */
    private Set<Problem> validateDocumentIndexCardCandidate(DocumentNodeResource doc,
        SiteResource site, CoralSession coralSession)
        throws NotConfiguredException
    {
        Set<Problem> problems = new HashSet<Problem>();
        CategoryResource markerCategory = getConfig(site, coralSession).getCategory();
        if(markerCategory == null)
        {
            throw new NotConfiguredException("marker category is not set");
        }
        List<CategoryResource> categories = Arrays.asList(categoryService.getCategories(
            coralSession, doc, true));
        if(!categories.contains(markerCategory))
        {
            problems.add(Problem.REQUIRED_CATEGORY_MISSING);
        }
        try
        {
            Subject anonymous = coralSession.getSecurity().getSubject(Subject.ANONYMOUS);
            if(!doc.canView(coralSession, anonymous, new Date()))
            {
                problems.add(Problem.DOCUMENT_NOT_ACCESSIBLE);
            }
        }
        catch(EntityDoesNotExistException e)
        {
            throw new RuntimeException("internal error", e);
        }
        
        Document metaDOM = null;
        if(doc.getMeta() == null || doc.getMeta().trim().length() == 0)
        {
            // new documents start with empty metadata, we report it as author has not been set
            problems.add(Problem.MISSING_AUTHOR);
        }
        else
        {
            try
            {
                metaDOM = DocumentHelper.parseText(doc.getMeta());
            }
            catch(org.dom4j.DocumentException e)
            {
                problems.add(Problem.INVALID_METADATA);
            }
        }

        if(metaDOM != null)
        {
            @SuppressWarnings("unchecked")
            List<Element> authorNames = metaDOM.selectNodes("/meta/authors/author/name");
            if(authorNames.size() == 0)
            {
                problems.add(Problem.MISSING_AUTHOR);
            }
            else
            {
                StringBuilder buff = new StringBuilder();
                for(Element authorName : authorNames)
                {
                    buff.append(authorName.getTextTrim());
                }
                if(buff.toString().trim().length() == 0)
                {
                    problems.add(Problem.MISSING_AUTHOR);
                }
            }
        }

        if(!doc.isValidityStartDefined())
        {
            problems.add(Problem.VALIDITY_START_UNSET);
        }
        return problems;
    }

    /**
     * Validate a file as candidate for an index item.
     * 
     * @param file the file.
     * @param site catalogue location
     * @param coralSession coral session
     * @return set of problems, hopefully empty.
     * @throws NotConfiguredException when configuration for catalogue in given site is missing.
     */
    private Set<Problem> validateFileIndexCardCandidate(FileResource file, SiteResource site,
        CoralSession coralSession)
        throws NotConfiguredException
    {
        Set<Problem> problems = new HashSet<Problem>();
        CategoryResource markerCategory = getConfig(site, coralSession).getCategory();
        if(markerCategory == null)
        {
            throw new NotConfiguredException("marker category is not set");
        }
        List<CategoryResource> categories = Arrays.asList(categoryService.getCategories(
            coralSession, file, true));
        if(!categories.contains(markerCategory))
        {
            problems.add(Problem.REQUIRED_CATEGORY_MISSING);
        }
        
        try
        {
            Subject anonymous = coralSession.getSecurity().getSubject(Subject.ANONYMOUS);
            if(!file.canView(coralSession, anonymous))
            {
                problems.add(Problem.FILE_NOT_ACCESSIBLE);
            }
        }
        catch(EntityDoesNotExistException e)
        {
            throw new RuntimeException("internal error", e);
        }        

        List<DocumentNodeResource> descriptionDocCandidates = findDescriptionDocs(file, site,
            coralSession);
        if(descriptionDocCandidates.size() < 1)
        {
            problems.add(Problem.MISSING_DESCRIPTION_DOC);
        }
        else if(descriptionDocCandidates.size() > 1)
        {
            problems.add(Problem.MULTIPLE_DESCRIPTION_DOCS);
        }
        else
        {
            problems.addAll(validateDocumentIndexCardCandidate(descriptionDocCandidates.get(0),
                site, coralSession));
        }
        return problems;
    }

    /**
     * Find description document for a file.
     * 
     * @param file the file
     * @param site catalogue location
     * @param coralSession coral session
     * @return a set of resources, may be empty.
     * @throws NotConfiguredException when configuration for catalogue in given site is missing.
     */
    private List<DocumentNodeResource> findDescriptionDocs(FileResource file, SiteResource site,
        CoralSession coralSession)
        throws NotConfiguredException
    {
        CategoryResource markerCategory = getConfig(site, coralSession).getCategory();
        if(markerCategory == null)
        {
            throw new NotConfiguredException("marker category is not set");
        }

        Resource relatedFrom[] = relatedService.getRelatedFrom(coralSession, file);

        List<DocumentNodeResource> descriptionDocCandidates = new ArrayList<DocumentNodeResource>();
        for(Resource res : relatedFrom)
        {
            if(res instanceof DocumentNodeResource)
            {
                List<CategoryResource> categories = Arrays.asList(categoryService.getCategories(
                    coralSession, res, true));
                if(categories.contains(markerCategory))
                {
                    descriptionDocCandidates.add((DocumentNodeResource)res);
                }
            }
        }
        return descriptionDocCandidates;
    }

    /**
     * Find downloadable files for a description document.
     * 
     * @param doc description document
     * @param site catalogue location
     * @param coralSession coral session
     * @return a set of files, may be empty
     * @throws NotConfiguredException when configuration for catalogue in given site is missing.
     */
    private List<FileResource> findDownloads(DocumentNodeResource doc, SiteResource site,
        CoralSession coralSession, Locale locale)
        throws NotConfiguredException
    {
        CategoryResource markerCategory = getConfig(site, coralSession).getCategory();
        if(markerCategory == null)
        {
            throw new NotConfiguredException("marker category is not set");
        }

        Comparator<Resource> autoSequence = null;
        if(locale != null)
        {
            autoSequence = new NameComparator<Resource>(locale);
        }
        @SuppressWarnings("unchecked")
        ResourceList<Resource> maualSequence = doc.getRelatedResourcesSequence();
        Resource relatedTo[] = relatedService.getRelatedTo(coralSession, doc, maualSequence,
            autoSequence);

        List<FileResource> downloads = new ArrayList<FileResource>();
        for(Resource res : relatedTo)
        {
            if(res instanceof FileResource)
            {
                List<CategoryResource> categories = Arrays.asList(categoryService.getCategories(
                    coralSession, res, true));
                if(categories.contains(markerCategory))
                {
                    downloads.add((FileResource)res);
                }
            }
        }
        return downloads;
    }
}