package net.cyklotron.cms.modules.views.library;

import java.util.List;
import java.util.Locale;

import org.jcontainer.dna.Logger;
import org.objectledge.context.Context;
import org.objectledge.coral.session.CoralSession;
import org.objectledge.i18n.I18nContext;
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
import net.cyklotron.cms.library.IndexCard;
import net.cyklotron.cms.library.IndexCardTableModel;
import net.cyklotron.cms.library.LibraryConfigResource;
import net.cyklotron.cms.library.LibraryService;
import net.cyklotron.cms.modules.views.BaseSkinableScreen;
import net.cyklotron.cms.preferences.PreferencesService;
import net.cyklotron.cms.site.SiteResource;
import net.cyklotron.cms.skins.SkinService;
import net.cyklotron.cms.structure.StructureService;
import net.cyklotron.cms.style.StyleService;

public class Index
    extends BaseSkinableScreen
{
    private final LibraryService libraryService;

    public Index(Context context, Logger logger, PreferencesService preferencesService,
        CmsDataFactory cmsDataFactory, StructureService structureService,
        StyleService styleService, SkinService skinService, MVCFinder mvcFinder,
        TableStateManager tableStateManager, LibraryService libraryService)
    {
        super(context, logger, preferencesService, cmsDataFactory, structureService, styleService,
                        skinService, mvcFinder, tableStateManager);
        this.libraryService = libraryService;
    }

    public void prepareDefault(Context context)
        throws ProcessingException
    {
        TemplatingContext templatingContext = context.getAttribute(TemplatingContext.class);
        CoralSession coralSession = context.getAttribute(CoralSession.class);
        I18nContext i18nContext = context.getAttribute(I18nContext.class);
        CmsData cmsData = getCmsData();
        SiteResource site = cmsData.getSite();
        LibraryConfigResource config = libraryService.getConfig(site, coralSession);
        Parameters screenConfig = cmsData.getEmbeddedScreenConfig();
        Parameters parameters = context.getAttribute(RequestParameters.class);
        if(config.isCategoryDefined() && config.isSearchPoolDefined())
        {
            templatingContext.put("applicationConfigured", "true");
            Locale locale = i18nContext.getLocale();
            try
            {
                List<IndexCard> index;
                String query;
                if(parameters.isDefined("query") && (query = parameters.get("query")).length() > 0)
                {
                    templatingContext.put("query", query);
                    index = libraryService.searchLibraryItems(query, site, coralSession, locale);
                }
                else
                {
                    index = libraryService.getAllLibraryItems(site, coralSession, locale);
                }
                IndexCardTableModel tableModel = new IndexCardTableModel(index, locale);
                TableState tableState = tableStateManager.getState(context, "screen:library.Index:"
                    + cmsData.getNode().getIdString());
                if(tableState.isNew())
                {
                    tableState.setTreeView(false);
                    tableState.setSortColumnName(screenConfig.get("sortColumn", "title"));
                    tableState.setAscSort(screenConfig.getBoolean("sortAsc", true));
                    tableState.setPageSize(screenConfig.getInt("pageSize", 20));
                }
                TableTool<IndexCard> tableTool = new TableTool<IndexCard>(tableState, null,
                    tableModel);
                templatingContext.put("table", tableTool);
            }
            catch(Exception e)
            {
                throw new ProcessingException("internal error", e);
            }
        }
    }
}
