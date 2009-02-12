package net.cyklotron.cms.modules.views.category.query;

import java.util.Map;
import java.util.Set;

import org.jcontainer.dna.Logger;
import org.objectledge.cache.CacheFactory;
import org.objectledge.context.Context;
import org.objectledge.coral.session.CoralSession;
import org.objectledge.coral.store.Resource;
import org.objectledge.parameters.Parameters;
import org.objectledge.parameters.RequestParameters;
import org.objectledge.pipeline.ProcessingException;
import org.objectledge.table.TableState;
import org.objectledge.table.TableStateManager;
import org.objectledge.table.TableTool;
import org.objectledge.templating.TemplatingContext;
import org.objectledge.web.mvc.finders.MVCFinder;

import net.cyklotron.cms.CmsData;
import net.cyklotron.cms.CmsDataFactory;
import net.cyklotron.cms.category.components.BaseResourceList;
import net.cyklotron.cms.category.components.BaseResourceListConfiguration;
import net.cyklotron.cms.category.query.CategoryQueryException;
import net.cyklotron.cms.category.query.CategoryQueryResource;
import net.cyklotron.cms.category.query.CategoryQueryResultsConfiguration;
import net.cyklotron.cms.category.query.CategoryQueryService;
import net.cyklotron.cms.category.query.CategoryQueryUtil;
import net.cyklotron.cms.category.query.screens.CategoryQueryResultsResourceList;
import net.cyklotron.cms.integration.IntegrationService;
import net.cyklotron.cms.modules.views.BaseSkinableScreen;
import net.cyklotron.cms.preferences.PreferencesService;
import net.cyklotron.cms.site.SiteService;
import net.cyklotron.cms.skins.SkinService;
import net.cyklotron.cms.structure.StructureService;
import net.cyklotron.cms.style.StyleService;

/**
 * Category Query Resutls screen.
 * 
 * @author <a href="mailto:dgajda@caltha.pl">Damian Gajda</a>
 * @version $Id: CategoryQueryResults.java,v 1.7 2008-10-30 17:54:28 rafal Exp $ 
 */
public class CategoryQueryResults 
    extends BaseSkinableScreen
{
	/** category query service */
	protected CategoryQueryService categoryQueryService;

    protected SiteService siteService;
    
    protected IntegrationService integrationService;
    
    protected CacheFactory cacheFactory;
    
    public CategoryQueryResults(org.objectledge.context.Context context, Logger logger,
        PreferencesService preferencesService, CmsDataFactory cmsDataFactory,
        StructureService structureService, StyleService styleService, SkinService skinService,
        MVCFinder mvcFinder, TableStateManager tableStateManager,
        CategoryQueryService categoryQueryService, SiteService siteService,
        IntegrationService integrationService, CacheFactory cacheFactory)
    {
        super(context, logger, preferencesService, cmsDataFactory, structureService, styleService,
                        skinService, mvcFinder, tableStateManager);
        this.categoryQueryService = categoryQueryService;
        this.siteService = siteService;
        this.integrationService = integrationService;
        this.cacheFactory = cacheFactory;
	}
	
	public void prepareDefault(Context context)
		throws ProcessingException
	{
		CmsData cmsData = cmsDataFactory.getCmsData(context);
        CoralSession coralSession = (CoralSession)context.getAttribute(CoralSession.class);
        Parameters parameters = RequestParameters.getRequestParameters(context);
        TemplatingContext templatingContext = TemplatingContext.getTemplatingContext(context);
		// get query object
		CategoryQueryResource categoryQuery;        
		try
		{
			if(parameters.isDefined(CategoryQueryUtil.QUERY_PARAM))
			{
                categoryQuery = CategoryQueryUtil.getQuery(coralSession, parameters);
			}
			else
			{
                categoryQuery = categoryQueryService.getDefaultQuery(coralSession, cmsData.getSite());
			}
            
            if(categoryQuery == null)
            {
                screenError(cmsData.getNode(), context, "default category query not configured");
                return;
            }
		}
		catch (CategoryQueryException e1)
		{
			screenError(cmsData.getNode(), context, "cannot get catgory query root for site "+
                cmsData.getSite().getName());
			return;
		}    
		templatingContext.put("category_query", categoryQuery);    

		// get config
		CategoryQueryResultsConfiguration config = 
			new CategoryQueryResultsConfiguration(getScreenConfig(), categoryQuery);

        CategoryQueryResultsResourceList resList = new CategoryQueryResultsResourceList(context, integrationService,
            cmsDataFactory, categoryQueryService, siteService,
                categoryQuery, config);

        // get resources based on category query
        Resource[] resources = null;
        try
        {
            resources = getResources(coralSession, resList, config);
        }
        catch(Exception e)
        {
            screenError(cmsData.getNode(), context, "Cannot execute category query");
            return;
        }

        // setup table tool
        TableState state = tableStateManager.getState(context, resList.getTableStateName());
        TableTool tool = resList.getTableTool(coralSession, context, config, state, resources);
        templatingContext.put("table", tool);
    }
    
    private Resource[] getResources(CoralSession coralSession, BaseResourceList resList,
        CategoryQueryResultsConfiguration config)
        throws Exception
    {
        long cacheInterval = (long) config.getCacheInterval();
        if(cacheInterval > 0L)
        {
            // get cache instance
            Map cache = null;
            Map keyCache = null;
            try
            {
                cache = cacheFactory.getInstance("categoryquery", "categoryquery");
                keyCache = cacheFactory.getInstance("categoryquerykey", "categoryquerykey");
            }
            catch(Exception e)
            {
                throw new ProcessingException(e);
            }
            CmsData cmsData = cmsDataFactory.getCmsData(context); 
            String key = cmsData.getNode().getIdString() + "."
                + config.getCategoryQuery().getIdString();
            String synchronizedKey = null;
            synchronized(keyCache)
            {
                synchronizedKey = (String)keyCache.get(key);
                if(synchronizedKey == null)
                {
                    synchronizedKey = key;
                    keyCache.put(key, key);
                }
            }
            synchronized(synchronizedKey)
            {
                // get cached resource list together with creation time
                CacheEntry entry = (CacheEntry) cache.get(key);
                // check entry validity
                if(entry == null ||
                System.currentTimeMillis() - entry.timeStamp > cacheInterval*1000L)
                {
                    Resource[] ress = getResources2(coralSession, resList, config);
                    entry = new CacheEntry(ress, System.currentTimeMillis());
                    synchronized (cache)
                    {
                        cache.put(key, entry);
                    }
                }
                return entry.list;
            }            
        }
        else
        {
            CmsData cmsData = cmsDataFactory.getCmsData(context);
            logger.warn("non-cachable category query results screen nodeId="
                + cmsData.getNode().getIdString());
            return getResources2(coralSession, resList, config);
        }
    }
    
    private Resource[] getResources2(CoralSession coralSession, BaseResourceList resList,
        BaseResourceListConfiguration config)
        throws Exception
    {
        String query = resList.getQuery(coralSession, config);
        Set idSet = resList.getIdSet(coralSession, config);
        if(idSet != null)
        {
            return categoryQueryService.forwardQuery(coralSession, query, idSet);
        }
        else
        {
            return categoryQueryService.forwardQuery(coralSession, query);
        }        
    }
    
    private class CacheEntry
    {
        final private Resource[] list;
        final private long timeStamp;

        public CacheEntry(Resource[] list, long timeStamp)
        {
            this.list = list;
            this.timeStamp = timeStamp;
        }
    }    
}