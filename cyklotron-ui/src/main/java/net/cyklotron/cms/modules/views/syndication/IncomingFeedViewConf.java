package net.cyklotron.cms.modules.views.syndication;

import org.jcontainer.dna.Logger;
import org.objectledge.context.Context;
import org.objectledge.coral.security.Subject;
import org.objectledge.coral.session.CoralSession;
import org.objectledge.i18n.I18nContext;
import org.objectledge.parameters.Parameters;
import org.objectledge.pipeline.ProcessingException;
import org.objectledge.table.TableStateManager;
import org.objectledge.templating.TemplatingContext;
import org.objectledge.web.HttpContext;
import org.objectledge.web.mvc.MVCContext;

import net.cyklotron.cms.CmsData;
import net.cyklotron.cms.CmsDataFactory;
import net.cyklotron.cms.preferences.PreferencesService;
import net.cyklotron.cms.syndication.IncomingFeedViewConfiguration;
import net.cyklotron.cms.syndication.SyndicationService;

/**
 * Screen for incoming feed view component configuration.
 *
 * @author <a href="mailto:dgajda@caltha.pl">Damian Gajda</a>
 * @version $Id: IncomingFeedViewConf.java,v 1.3 2007-11-18 21:24:50 rafal Exp $
 */
public class IncomingFeedViewConf extends IncomingFeedList
{
    public IncomingFeedViewConf(Context context, Logger logger, PreferencesService preferencesService,
        CmsDataFactory cmsDataFactory, TableStateManager tableStateManager,
        SyndicationService syndicationService)
    {
        super(context, logger, preferencesService, cmsDataFactory, tableStateManager,
                        syndicationService);
    }
    
    @Override
    public void process(Parameters parameters, MVCContext mvcContext,
        TemplatingContext templatingContext, HttpContext httpContext, I18nContext i18nContext,
        CoralSession coralSession) throws org.objectledge.pipeline.ProcessingException
    {
        super.process(parameters, mvcContext, templatingContext, httpContext, i18nContext,
            coralSession);
        
        Parameters componentConfig = prepareComponentConfig(parameters, templatingContext);
        IncomingFeedViewConfiguration feedConf = new IncomingFeedViewConfiguration(componentConfig);
        templatingContext.put("feedConf", feedConf);
    }

    public boolean checkAccessRights(Context context)
    throws ProcessingException
    {
        CmsData cmsData = cmsDataFactory.getCmsData(context);
        if(!cmsData.isApplicationEnabled("syndication"))
        {
            logger.debug("Application 'syndication' not enabled in site");
            return false;
        }
        CoralSession coralSession = getCoralSession(context); 
        Subject subject = coralSession.getUserSubject();
        if(cmsData.getNode() != null)
        { 
            return cmsData.getNode().canModify(coralSession, subject);
        }
        else
        {
            // check privileges necessary for configuring global components
            return checkAdministrator(coralSession);
        }
    }
}
