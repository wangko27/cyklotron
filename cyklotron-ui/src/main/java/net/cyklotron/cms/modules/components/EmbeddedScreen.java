package net.cyklotron.cms.modules.components;

import java.util.Iterator;
import java.util.List;

import org.jcontainer.dna.Logger;
import org.objectledge.context.Context;
import org.objectledge.coral.session.CoralSession;
import org.objectledge.i18n.I18nContext;
import org.objectledge.parameters.Parameters;
import org.objectledge.pipeline.ProcessingException;
import org.objectledge.templating.Template;
import org.objectledge.templating.Templating;
import org.objectledge.templating.TemplatingContext;
import org.objectledge.web.HttpContext;
import org.objectledge.web.mvc.MVCContext;
import org.objectledge.web.mvc.builders.BuildException;
import org.objectledge.web.mvc.builders.Builder;
import org.objectledge.web.mvc.finders.MVCFinder;
import org.objectledge.web.mvc.security.SecurityHelper;

import net.cyklotron.cms.CmsComponentData;
import net.cyklotron.cms.CmsData;
import net.cyklotron.cms.CmsDataFactory;
import net.cyklotron.cms.integration.ApplicationResource;
import net.cyklotron.cms.integration.IntegrationService;
import net.cyklotron.cms.integration.ScreenResource;
import net.cyklotron.cms.site.SiteResource;
import net.cyklotron.cms.skins.SkinService;

public class EmbeddedScreen extends SkinableCMSComponent
{
    private IntegrationService integrationService;
    
    private final SecurityHelper securityHelper;
    
    public EmbeddedScreen(Context context, Logger logger, Templating templating,
        CmsDataFactory cmsDataFactory, SkinService skinService, MVCFinder mvcFinder,
        IntegrationService integrationService, SecurityHelper securityHelper)
    {
        super(context, logger, templating, cmsDataFactory, skinService, mvcFinder);
        this.integrationService = integrationService;
        this.securityHelper = securityHelper;
    }
    public static String SCREEN_ERRORS_KEY = "screen_errors";
    
    public void process(Parameters parameters, MVCContext mvcContext,
        TemplatingContext templatingContext, HttpContext httpContext, I18nContext i18nContext,
        CoralSession coralSession)
        throws ProcessingException
    {
        CmsData cmsData = cmsDataFactory.getCmsData(context);
        Parameters conf = cmsData.getPreferences();
        
        String app = "cms";
        String screen = "CmsDefault";
        boolean found = false;

        String[] instances = conf.getChild("component.").getParameterNames();
        for(int i=0; i<instances.length; i++)
        {
            if(instances[i].endsWith(".class"))
            {
                if(CmsComponentData.getParameter(conf,"component."+instances[i],"").equals("EmbeddedScreen"))
                {
                    app = CmsComponentData.getParameter(conf,"screen.app",app);
                    screen = CmsComponentData.getParameter(conf,"screen.class",screen);
                    screen = screen.replace(",",".");
                    found = true;
                    break;
                }
            }
        }
        if(found)
        {
            SiteResource site = cmsData.getSite();
            ScreenResource screenRes = integrationService.getScreen(coralSession, app, screen);
            if(screenRes != null)
            {
                if(integrationService.isApplicationEnabled(coralSession, site,
                    (ApplicationResource)screenRes.getParent().getParent()))
                {
                    templatingContext.put("application_enabled", true);
                    Builder builder = finderService.findBuilder(screen).getBuilder();
                    securityHelper.checkSecurity(builder, context);
                    Template template = finderService.findBuilderTemplate(screen).getTemplate();
                    try
                    {
                        String screenContent = builder.build(template, "");
                        templatingContext.put("embeddedPlaceholder", screenContent);
                    }
                    catch(BuildException e)
                    {
                        componentError(context, "Failed to build embeded screen", e);
                    }
                }
                else
                {
                    templatingContext.put("application_enabled", false);
                }
            }
            else
            {
                componentError(context, "Failed to find screen integration resource");
            }
          
        }
        
        List<Exception> errors = (List<Exception>)(templatingContext.get(SCREEN_ERRORS_KEY));
        if(errors != null && errors.size() > 0)
        {
            for(Exception ex : errors)
            {
                componentError(context, ex.getMessage(), ex);
            }
        }
    }
}
