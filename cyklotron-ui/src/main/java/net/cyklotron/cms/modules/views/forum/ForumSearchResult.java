package net.cyklotron.cms.modules.views.forum;

import org.jcontainer.dna.Logger;
import org.objectledge.context.Context;
import org.objectledge.coral.session.CoralSession;
import org.objectledge.i18n.I18nContext;
import org.objectledge.parameters.Parameters;
import org.objectledge.pipeline.ProcessingException;
import org.objectledge.table.TableStateManager;
import org.objectledge.templating.TemplatingContext;
import org.objectledge.web.HttpContext;
import org.objectledge.web.mvc.MVCContext;

import net.cyklotron.cms.CmsDataFactory;
import net.cyklotron.cms.forum.ForumService;
import net.cyklotron.cms.preferences.PreferencesService;
import net.cyklotron.cms.workflow.WorkflowService;

/**
 * The forum search result screen class.
 *
 * @author <a href="mailto:pablo@caltha.pl">Pawel Potempski</a>
 * @version $Id: ForumSearchResult.java,v 1.4 2005-03-08 11:02:49 pablo Exp $
 */
public class ForumSearchResult
    extends BaseForumScreen
{
    
    
    public ForumSearchResult(Context context, Logger logger, PreferencesService preferencesService,
        CmsDataFactory cmsDataFactory, TableStateManager tableStateManager,
        ForumService forumService, WorkflowService workflowService)
    {
        super(context, logger, preferencesService, cmsDataFactory, tableStateManager, forumService,
                        workflowService);
        
    }
    
    
    /* (non-Javadoc)
     * @see net.cyklotron.cms.modules.views.BaseCMSScreen#process(org.objectledge.parameters.Parameters, org.objectledge.web.mvc.MVCContext, org.objectledge.templating.TemplatingContext, org.objectledge.web.HttpContext, org.objectledge.i18n.I18nContext, org.objectledge.coral.session.CoralSession)
     */
    public void process(Parameters parameters, MVCContext mvcContext,
        TemplatingContext templatingContext, HttpContext httpContext, I18nContext i18nContext,
        CoralSession coralSession) throws ProcessingException
    {
    }
    /**
     * Builds the screen contents.
     *
     * <p>Redirecto to forum application</p>
     */
    //TODO LC what to do!???
    /**
    public String build(RunData data)
        throws ProcessingException
    {
        LinkTool link = data.getLinkTool();
        long rid = parameters.getLong("res_id", -1);
        if(rid == -1)
        {
            throw new ProcessingException("Resource id not found");
        }
        try
        {
            Resource resource = coralSession.getStore().getResource(rid);
            if(!(resource instanceof DiscussionResource) &&
               !(resource instanceof MessageResource))
            {
                throw new ProcessingException("Class of the resource '"+resource.getResourceClass().getName()+
                                              "' is does not belong to forum application");
            }
            if(resource instanceof DiscussionResource)
            {
                Resource node = ((DiscussionResource)resource).getForum().getForumNode();
                if(node == null)
                {
                    throw new ProcessingException("Section forum not configured in forum application");
                }
                link = link.set("x",node.getIdString()).set("did",rid).set("state","Messages").unset("view");
            }
            if(resource instanceof MessageResource)
            {
                Resource node = ((MessageResource)resource).getDiscussion().getForum().getForumNode();
                if(node == null)
                {
                    throw new ProcessingException("Section forum not configured in forum application");
                }
                link = link.set("x",node.getIdString()).set("mid",rid).set("state","Message").unset("view");
            }
            data.sendRedirect(link.toString());
        }
        catch(Exception e)
        {
            throw new ProcessingException("Exception occured during redirecting...",e);
        }
        return null;
    }
    */

    public boolean checkAccessRights(Context context)
        throws ProcessingException
    {
        return true;
    }
    
}
