package net.cyklotron.cms.modules.views.appearance.skin;

import java.io.StringReader;
import java.io.StringWriter;

import org.jcontainer.dna.Logger;
import org.objectledge.coral.session.CoralSession;
import org.objectledge.i18n.I18nContext;
import org.objectledge.parameters.Parameters;
import org.objectledge.pipeline.ProcessingException;
import org.objectledge.table.TableStateManager;
import org.objectledge.templating.MergingException;
import org.objectledge.templating.Templating;
import org.objectledge.templating.TemplatingContext;
import org.objectledge.utils.StackTrace;
import org.objectledge.web.HttpContext;
import org.objectledge.web.mvc.MVCContext;

import net.cyklotron.cms.CmsDataFactory;
import net.cyklotron.cms.integration.ApplicationResource;
import net.cyklotron.cms.integration.IntegrationService;
import net.cyklotron.cms.integration.ScreenResource;
import net.cyklotron.cms.modules.views.appearance.BaseAppearanceScreen;
import net.cyklotron.cms.preferences.PreferencesService;
import net.cyklotron.cms.site.SiteResource;
import net.cyklotron.cms.skins.ScreenVariantResource;
import net.cyklotron.cms.skins.SkinService;
import net.cyklotron.cms.style.StyleService;

/**
 * 
 * 
 * @author <a href="mailto:rafal@caltha.pl">Rafal Krzewski</a>
 * @version $Id: EditScreenTemplate.java,v 1.5 2005-03-08 10:57:43 pablo Exp $
 */
public class EditScreenTemplate extends BaseAppearanceScreen
{
    
    public EditScreenTemplate(org.objectledge.context.Context context, Logger logger,
        PreferencesService preferencesService, CmsDataFactory cmsDataFactory,
        TableStateManager tableStateManager, StyleService styleService, SkinService skinService,
        IntegrationService integrationService, Templating templating)
    {
        super(context, logger, preferencesService, cmsDataFactory, tableStateManager, styleService,
                        skinService, integrationService, templating);
        
    }
    public void process(Parameters parameters, MVCContext mvcContext, TemplatingContext templatingContext, HttpContext httpContext, I18nContext i18nContext, CoralSession coralSession)
        throws ProcessingException
    {
        try
        {
            SiteResource site = getSite();
            String skin = parameters.get("skin");
            String app = parameters.get("appName");
            String screen = parameters.get("screenName");
            String variant =
                parameters.get("variant","Default");
            String state =
                parameters.get("state","Default");
            templatingContext.put("skin", skin);
            templatingContext.put("appName", app);
            templatingContext.put("screenName", screen);
            templatingContext.put("variant", variant);
            if (parameters.isDefined("state"))
            {
                templatingContext.put("state", state);
            }
            ApplicationResource appRes = integrationService.getApplication(coralSession, app);
            ScreenResource screenRes =
                integrationService.getScreen(coralSession, appRes, screen);
            ScreenVariantResource variantRes =
                skinService.getScreenVariant(coralSession, 
                    site,
                    skin,
                    screenRes.getApplicationName(),
                    screenRes.getScreenName(),
                    variant);
            templatingContext.put("variant_description", variantRes.getDescription());
            String fileName =
                skinService.getTemplateFilename(
                    screenRes.getScreenName(),
                    variant,
                    state);
            templatingContext.put("filename", fileName);

            try
            {
                String contents =
                    skinService.getScreenTemplateContents(
                        site,
                        skin,
                        screenRes.getApplicationName(),
                        screenRes.getScreenName(),
                        variant,
                        state);
                templatingContext.put("contents", contents);
                if(!templatingContext.containsKey("result"))
                {
                    TemplatingContext blankContext = templating.createContext();
                    StringReader in = new StringReader(contents);
                    StringWriter out = new StringWriter();
                    try
                    {
                        templating.merge(
                            blankContext,
                            in,
                            out,
                            "<screen template>");
                    }
                    catch (MergingException e)
                    {
                        templatingContext.put("result", "template_parse_error");
                        templatingContext.put(
                            "parse_trace", new StackTrace(e));
                    }
                }
            }
            catch (Exception e)
            {
                throw new ProcessingException(
                    "failed to load file contents",
                    e);
            }
        }
        catch (Exception e)
        {
            throw new ProcessingException("failed to retrieve information", e);
        }
    }
}
