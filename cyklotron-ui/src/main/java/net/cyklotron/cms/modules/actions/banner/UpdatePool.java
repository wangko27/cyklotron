package net.cyklotron.cms.modules.actions.banner;

import org.jcontainer.dna.Logger;
import org.objectledge.context.Context;
import org.objectledge.coral.entity.EntityDoesNotExistException;
import org.objectledge.coral.security.Subject;
import org.objectledge.coral.session.CoralSession;
import org.objectledge.coral.store.InvalidResourceNameException;
import org.objectledge.parameters.Parameters;
import org.objectledge.pipeline.ProcessingException;
import org.objectledge.templating.TemplatingContext;
import org.objectledge.utils.StackTrace;
import org.objectledge.web.HttpContext;
import org.objectledge.web.mvc.MVCContext;

import net.cyklotron.cms.CmsDataFactory;
import net.cyklotron.cms.banner.BannerService;
import net.cyklotron.cms.banner.PoolResource;
import net.cyklotron.cms.banner.PoolResourceImpl;
import net.cyklotron.cms.structure.StructureService;
import net.cyklotron.cms.workflow.WorkflowService;


/**
 *
 * @author <a href="mailo:pablo@ngo.pl">Pawel Potempski</a>
 * @version $Id: UpdatePool.java,v 1.5 2005-06-13 11:08:32 rafal Exp $
 */
public class UpdatePool
    extends BaseBannerAction
{
    
    

    public UpdatePool(Logger logger, StructureService structureService,
        CmsDataFactory cmsDataFactory, BannerService bannerService, WorkflowService workflowService)
    {
        super(logger, structureService, cmsDataFactory, bannerService, workflowService);
        
    }
    /**
     * Performs the action.
     */
    public void execute(Context context, Parameters parameters, MVCContext mvcContext, TemplatingContext templatingContext, HttpContext httpContext, CoralSession coralSession)
        throws ProcessingException
    {
        Subject subject = coralSession.getUserSubject();

        String title = parameters.get("title","");
        String description = parameters.get("description","");
        if(title.length() < 1 || title.length() > 32)
        {
            templatingContext.put("result","invalid_title");
            return;
        }
        if(description.length() > 255)
        {
            templatingContext.put("result","invalid_description");
            return;
        }

        int pid = parameters.getInt("pid", -1);
        if(pid == -1)
        {
            throw new ProcessingException("Poll id not found");
        }
        try
        {
            PoolResource poolResource = PoolResourceImpl.getPoolResource(coralSession, pid);
            if(!poolResource.getName().equals(title))
            {
                coralSession.getStore().setName(poolResource, title);
            }
            if(!poolResource.getDescription().equals(description))
            {
                poolResource.setDescription(description);
            }
            poolResource.update();
        }
        catch(EntityDoesNotExistException e)
        {
            templatingContext.put("result","exception");
            templatingContext.put("trace",new StackTrace(e));
            log.error("PollException: ",e);
            return;
        }
        catch(InvalidResourceNameException e)
        {
            templatingContext.put("result","invalid_name");
            return;
        }
        templatingContext.put("result","updated_successfully");
    }
}


