package net.cyklotron.cms.modules.actions.forum;

import org.jcontainer.dna.Logger;
import org.objectledge.context.Context;
import org.objectledge.coral.security.Permission;
import org.objectledge.coral.security.Subject;
import org.objectledge.coral.session.CoralSession;
import org.objectledge.coral.store.Resource;
import org.objectledge.parameters.Parameters;
import org.objectledge.parameters.RequestParameters;
import org.objectledge.pipeline.ProcessingException;
import org.objectledge.templating.TemplatingContext;
import org.objectledge.utils.StackTrace;
import org.objectledge.utils.StringUtils;
import org.objectledge.web.HttpContext;
import org.objectledge.web.mvc.MVCContext;

import net.cyklotron.cms.CmsDataFactory;
import net.cyklotron.cms.CmsTool;
import net.cyklotron.cms.forum.DiscussionResource;
import net.cyklotron.cms.forum.DiscussionResourceImpl;
import net.cyklotron.cms.forum.ForumResource;
import net.cyklotron.cms.forum.ForumService;
import net.cyklotron.cms.forum.MessageResource;
import net.cyklotron.cms.forum.MessageResourceImpl;
import net.cyklotron.cms.site.SiteResource;
import net.cyklotron.cms.structure.StructureService;
import net.cyklotron.cms.workflow.WorkflowService;


/**
 *
 * @author <a href="mailo:pablo@ngo.pl">Pawel Potempski</a>
 * @version $Id: AddMessage.java,v 1.3 2005-01-25 03:21:37 pablo Exp $
 */
public class AddMessage
    extends BaseForumAction
{
    
    
    public AddMessage(Logger logger, StructureService structureService,
        CmsDataFactory cmsDataFactory, ForumService forumService, WorkflowService workflowService)
    {
        super(logger, structureService, cmsDataFactory, forumService, workflowService);
        // TODO Auto-generated constructor stub
    }
    /**
     * Performs the action.
     */
    public void execute(Context context, Parameters parameters, MVCContext mvcContext, TemplatingContext templatingContext, HttpContext httpContext, CoralSession coralSession)
        throws ProcessingException
    {
        try
        {
            Subject subject = coralSession.getUserSubject();
            String instanceName = parameters.get("ci","");
            templatingContext.put("result_scope", "forum_"+instanceName);
            String name = parameters.get("name","");
            if(name.equals(""))
            {
                templatingContext.put("result","illegal_message_name");
                return;
            }
            long parentId = parameters.getLong("parent", -1);
            long discussionId = parameters.getLong("did", -1);
            long resourceId = parameters.getLong("resid", -1);
            if((parentId == -1 || discussionId == -1) && resourceId == -1)
            {
                templatingContext.put("result","parameter_not_found");
                return;
            }

            DiscussionResource discussion = null;
            Resource parent = null;
            if(parentId != -1)
            {
                discussion = DiscussionResourceImpl.getDiscussionResource(coralSession,discussionId);
                parent = coralSession.getStore().getResource(parentId);
            }
            else
            {
                Resource res = coralSession.getStore().getResource(resourceId);
                SiteResource site = CmsTool.getSite(res);
                ForumResource forum = forumService.getForum(coralSession, site);
                discussion = forumService.createCommentary(coralSession, forum, "comments/"+Long.toString(resourceId), res);
                parent = discussion;
            }
            
            if(discussion.getState().getName().equals("hidden"))
            {
				templatingContext.put("result","hidden_discussion");
				return;
            }
            
            String content = parameters.get("content","");
            content = StringUtils.wrap(content, 78);
            int priority = parameters.getInt("priority", 0);

            MessageResource message = MessageResourceImpl.
                createMessageResource(coralSession, name, parent, httpContext.getEncoding(),
                                      content, discussion, priority, name);
            String author = parameters.get("author","");
            String email = parameters.get("email","");
            message.setAuthor(author);
            message.setEmail(email);
            message.update();
            // workflow
            if(discussion.getForum().getSite() != null)
            {
                workflowService.assignState(coralSession, discussion.getForum().getSite().getParent().getParent(), message);
            }
            else
            {
                workflowService.assignState(coralSession, null, message);
            }
			if(discussion.getState().getName().equals("open"))
			{
				workflowService.performTransition(coralSession, message, "accept", subject);		
                templatingContext.put("result","added_successfully");
			}
            else
            {
                templatingContext.put("result","added_for_moderation_successfully");
            }
        }
        catch(Exception e)
        {
            templatingContext.put("result","exception");
            templatingContext.put("trace",new StackTrace(e));
            logger.error("ForumException: ",e);
            return;
        }
    }


    public boolean checkAccessRights(Context context) 
        throws ProcessingException
    {
        CoralSession coralSession = (CoralSession)context.getAttribute(CoralSession.class);
        Parameters parameters = RequestParameters.getRequestParameters(context);
        Permission forumAdd = coralSession.getSecurity().getUniquePermission("cms.forum.add");
        long parentId = parameters.getLong("parent", -1);
        if(parentId != -1)
        {
            try
            {
                Resource parent = coralSession.getStore().getResource(parentId);
                return coralSession.getUserSubject().hasPermission(parent, forumAdd);
            }   
            catch(Exception e)
            {
                throw new ProcessingException("failed to check access rights", e);
            }    
        }
        else
        {
            long resId = parameters.getLong("resid", -1);
            if(resId == -1)
            {
                logger.error("forum,AddMessage action: parent nor resid undefined");
                return false; 
            }
            else
            {
                try
                {
                    Resource res = coralSession.getStore().getResource(resId);
                    SiteResource site = CmsTool.getSite(res);
                    ForumResource forum = forumService.getForum(coralSession, site);
                    Resource[] r = coralSession.getStore().getResource(forum, "discussions");
                    if(r.length == 0)
                    {
                        return coralSession.getUserSubject().hasPermission(forum, forumAdd);
                    }
                    else
                    {
                        return coralSession.getUserSubject().hasPermission(r[0], forumAdd);
                    }
                }
                catch (Exception e)
                {
                    throw new ProcessingException("failed to check access rights", e);
                }
            }
        }
    }
}


