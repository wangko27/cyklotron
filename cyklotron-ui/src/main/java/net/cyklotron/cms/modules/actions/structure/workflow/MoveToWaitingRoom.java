package net.cyklotron.cms.modules.actions.structure.workflow;

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
import org.objectledge.web.HttpContext;
import org.objectledge.web.mvc.MVCContext;

import net.cyklotron.cms.CmsDataFactory;
import net.cyklotron.cms.structure.NavigationNodeResource;
import net.cyklotron.cms.structure.NavigationNodeResourceImpl;
import net.cyklotron.cms.structure.StructureService;
import net.cyklotron.cms.style.StyleService;
import net.cyklotron.cms.workflow.WorkflowService;

/**
 * Assign to transition action.
 * 
 * @author <a href="mailo:pablo@caltha.pl">Pawel Potempski</a>
 * @version $Id: MoveToWaitingRoom.java,v 1.3 2005-01-25 08:24:45 pablo Exp $
 */
public class MoveToWaitingRoom extends BaseWorkflowAction
{
    public MoveToWaitingRoom(Logger logger, StructureService structureService,
        CmsDataFactory cmsDataFactory, StyleService styleService, WorkflowService workflowService)
    {
        super(logger, structureService, cmsDataFactory, styleService, workflowService);
        // TODO Auto-generated constructor stub
    }
    public static final String WAITING_ROOM_NAME = "waiting_room";
    /**
     * Performs the action.
     */
    public void execute(Context context, Parameters parameters, MVCContext mvcContext, TemplatingContext templatingContext, HttpContext httpContext, CoralSession coralSession) throws ProcessingException
    {
        Subject subject = coralSession.getUserSubject();
        
        long nodeId = parameters.getLong("node_id", -1);
        if (nodeId == -1)
        {
            templatingContext.put("result", "parameter_not_found");
            return;
        }
        try
        {
            NavigationNodeResource node = NavigationNodeResourceImpl.getNavigationNodeResource(coralSession, nodeId);
            Resource homePage = getHomePage(context);
            // hack!!! but who cares if whole the action is one big hack!
            Resource[] parents = coralSession.getStore().getResource(homePage,WAITING_ROOM_NAME);
            Resource parent = null;
            if(parents.length == 0)
            {
                parent = structureService.addDocumentNode(coralSession,
                    WAITING_ROOM_NAME, WAITING_ROOM_NAME, homePage, subject);
            }
            else
            {
                parent = parents[0];
            }
            coralSession.getStore().setParent(node, parent);
        }
        catch (Exception e)
        {
            templatingContext.put("result", "exception");
            templatingContext.put("trace", new StackTrace(e));
            logger.error("ResourceException: ", e);
            return;
        }
        templatingContext.put("result", "changed_successfully");
    }
    
	public boolean checkAccessRights(Context context) throws ProcessingException
	{
        CoralSession coralSession = (CoralSession)context.getAttribute(CoralSession.class);
        Parameters parameters = RequestParameters.getRequestParameters(context);
		try
		{
			long nodeId = parameters.getLong("node_id", -1);
			NavigationNodeResource node = NavigationNodeResourceImpl.getNavigationNodeResource(coralSession, nodeId);
			Permission permission = coralSession.getSecurity().getUniquePermission("cms.structure.modify");
			return coralSession.getUserSubject().hasPermission(node, permission);
		}
		catch(Exception e)
		{
			throw new ProcessingException("Exception occured during access rights checking",e);
		}
	}
}
