package net.cyklotron.cms.modules.actions.forum;

import org.jcontainer.dna.Logger;
import org.objectledge.context.Context;
import org.objectledge.coral.entity.EntityDoesNotExistException;
import org.objectledge.coral.security.Permission;
import org.objectledge.coral.security.Subject;
import org.objectledge.coral.session.CoralSession;
import org.objectledge.parameters.Parameters;
import org.objectledge.parameters.RequestParameters;
import org.objectledge.pipeline.ProcessingException;
import org.objectledge.templating.TemplatingContext;
import org.objectledge.utils.StackTrace;
import org.objectledge.web.HttpContext;
import org.objectledge.web.mvc.MVCContext;

import net.cyklotron.cms.CmsDataFactory;
import net.cyklotron.cms.forum.DiscussionResource;
import net.cyklotron.cms.forum.ForumResource;
import net.cyklotron.cms.forum.ForumResourceImpl;
import net.cyklotron.cms.forum.ForumService;
import net.cyklotron.cms.structure.StructureService;
import net.cyklotron.cms.workflow.WorkflowService;

/**
 *
 * @author <a href="mailo:pablo@caltha.pl">Pawel Potempski</a>
 * @version $Id: AddDiscussion.java,v 1.4 2005-03-08 10:52:12 pablo Exp $
 */
public class AddDiscussion
    extends BaseForumAction
{
    
    public AddDiscussion(Logger logger, StructureService structureService,
        CmsDataFactory cmsDataFactory, ForumService forumService, WorkflowService workflowService)
    {
        super(logger, structureService, cmsDataFactory, forumService, workflowService);
        
    }
    
    /**
     * Performs the action.
     */
    public void execute(Context context, Parameters parameters, MVCContext mvcContext, TemplatingContext templatingContext, HttpContext httpContext, CoralSession coralSession)
        throws ProcessingException
    {
        Subject subject = coralSession.getUserSubject();
        String name = parameters.get("name","");
        if(name.equals(""))
        {
            templatingContext.put("result","illegal_discussion_name");
            return;
        }
        long forumId = parameters.getLong("fid", -1);
        if (forumId == -1)
        {
            templatingContext.put("result","parameter_not_found");
            return;
        }
        String description = parameters.get("description","");
        String adminName = parameters.get("admin",subject.getName());
        String replyTo = parameters.get("reply_to","");
        String state = parameters.get("state","");
        Subject admin;
        try
        {
            admin = coralSession.getSecurity().getSubject(adminName);
        }
        catch(EntityDoesNotExistException e)
        {
            templatingContext.put("result","admin_subject_not_found");
            return;
        }
        try
        {
            ForumResource forum = ForumResourceImpl.getForumResource(coralSession,forumId);
            name = "discussions/"+name;
            DiscussionResource discussion = forumService.createDiscussion(coralSession, 
                forum, name);
            discussion.setDescription(description);
            discussion.setReplyTo(replyTo);
            discussion.update();
            if(state.equals("moderated"))
            {
				workflowService.performTransition(coralSession, discussion, "show.moderated", subject);            	            	
            }
			if(state.equals("open"))
			{
				workflowService.performTransition(coralSession, discussion, "show.open", subject);
			}
        }
        catch(Exception e)
        {
            templatingContext.put("result","exception");
            templatingContext.put("trace",new StackTrace(e));
            logger.error("failed to create discussion",e);
            return;
        }
        templatingContext.put("result","added_successfully");
    }

    public boolean checkAccessRights(Context context)
    {
        CoralSession coralSession = (CoralSession)context.getAttribute(CoralSession.class);
        Parameters parameters = RequestParameters.getRequestParameters(context);
        long forumId = parameters.getLong("fid", -1);
        if(forumId == -1)
        {
            logger.error("Couldn't find forum id");
            return false;
        }
        try
        {
            ForumResource forum = ForumResourceImpl.getForumResource(coralSession,forumId);
            Permission forumAdd = coralSession.getSecurity().getUniquePermission("cms.forum.add");
            return coralSession.getUserSubject().hasPermission(forum, forumAdd);
        }
        catch(Exception e)
        {
            logger.error("Subject has no rights to add discussion in this forum" , e);
            return false;
        }    
    }
    
}


