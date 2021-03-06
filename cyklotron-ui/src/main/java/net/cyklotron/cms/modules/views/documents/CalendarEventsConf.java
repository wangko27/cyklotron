package net.cyklotron.cms.modules.views.documents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jcontainer.dna.Logger;
import org.objectledge.context.Context;
import org.objectledge.coral.session.CoralSession;
import org.objectledge.coral.store.Resource;
import org.objectledge.coral.table.comparator.NameComparator;
import org.objectledge.i18n.I18nContext;
import org.objectledge.parameters.Parameters;
import org.objectledge.pipeline.ProcessingException;
import org.objectledge.table.TableStateManager;
import org.objectledge.templating.TemplatingContext;
import org.objectledge.web.HttpContext;
import org.objectledge.web.mvc.MVCContext;

import net.cyklotron.cms.CmsData;
import net.cyklotron.cms.CmsDataFactory;
import net.cyklotron.cms.category.query.CategoryQueryService;
import net.cyklotron.cms.modules.views.BaseCMSScreen;
import net.cyklotron.cms.preferences.PreferencesService;
import net.cyklotron.cms.site.SiteResource;

public class CalendarEventsConf
	extends BaseCMSScreen
{
    private CategoryQueryService categoryQueryService;
    

    public CalendarEventsConf(org.objectledge.context.Context context, Logger logger,
        PreferencesService preferencesService, CmsDataFactory cmsDataFactory,
        CategoryQueryService categoryQueryService,
        TableStateManager tableStateManager)
    {
        super(context, logger, preferencesService, cmsDataFactory, tableStateManager);
        this.categoryQueryService = categoryQueryService;
    }
    
    public void process(Parameters parameters, MVCContext mvcContext,
        TemplatingContext templatingContext, HttpContext httpContext, I18nContext i18nContext,
        CoralSession coralSession)
        throws ProcessingException
    {
        // get config
        Parameters componentConfig = prepareComponentConfig(parameters, templatingContext);
        templatingContext.put("config", componentConfig);

        try
        {
            SiteResource site = getSite();
            Resource root = categoryQueryService.getCategoryQueryRoot(coralSession, site);
            Resource[] queries = coralSession.getStore().getResource(root);
            Arrays.sort(queries, new NameComparator(i18nContext.getLocale()));
            List temp = new ArrayList(queries.length);
            for(int i = 0; i < queries.length; i++)
            {
                Resource query = queries[i];
                List item = new ArrayList();
                item.add(query.getName());
                item.add(query.getName());
                temp.add(item);
            }
            templatingContext.put("queries", temp);

            long index = componentConfig.getLong("index_id", -1);
            if(index != -1)
            {
                templatingContext.put("index", coralSession.getStore().getResource(index));
            }
        }
        catch(Exception e)
        {
            throw new ProcessingException("Exception occurred", e);
        }
    }
    
    public boolean checkAccessRights(Context context)
        throws ProcessingException
    {
        CoralSession coralSession = (CoralSession)context.getAttribute(CoralSession.class);
        CmsData cmsData = getCmsData();
        if(cmsData.getNode() != null)
        {
            return cmsData.getNode().canModify(coralSession, coralSession.getUserSubject());
        }
        else
        {
            return checkAdministrator(coralSession);
        }
    }
}
