package net.cyklotron.cms.modules.views.poll;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.labeo.services.resource.EntityDoesNotExistException;
import net.labeo.services.resource.Resource;
import net.labeo.services.resource.table.CreationTimeComparator;
import net.labeo.services.resource.table.CreatorNameComparator;
import net.labeo.services.resource.table.NameComparator;
import net.labeo.services.table.ListTableModel;
import net.labeo.services.table.TableColumn;
import net.labeo.services.table.TableConstants;
import net.labeo.services.table.TableException;
import net.labeo.services.table.TableModel;
import net.labeo.services.table.TableService;
import net.labeo.services.table.TableState;
import net.labeo.services.table.TableTool;
import net.labeo.services.templating.Context;
import net.labeo.webcore.ProcessingException;
import net.labeo.webcore.RunData;

import net.cyklotron.cms.poll.PollsResource;
import net.cyklotron.cms.poll.PollsResourceImpl;
import net.cyklotron.cms.poll.PoolResource;

/**
 *
 */
public class PoolList
    extends BasePollScreen
{
    TableService tableService = null;

    public PoolList()
    {
        tableService = (TableService)broker.getService(TableService.SERVICE_NAME);
    }

    public void process(Parameters parameters, MVCContext mvcContext, TemplatingContext templatingContext, HttpContext httpContext, I18nContext i18nContext, CoralSession coralSession)
        throws ProcessingException
    {
        if(parameters.get("reset").asBoolean(false))
        {
            data.getLocalContext().removeAttribute(FROM_COMPONENT);
            data.getLocalContext().removeAttribute(COMPONENT_INSTANCE);
            data.getLocalContext().removeAttribute(COMPONENT_NODE);
        }
        else
        {
            Boolean fromComponent = (Boolean)httpContext.getSessionAttribute(FROM_COMPONENT);
            if(fromComponent != null && fromComponent.booleanValue())
            {
                templatingContext.put("from_component",fromComponent);
            }
        }

        int psid = parameters.getInt("psid", -1);
        if(psid == -1)
        {
            throw new ProcessingException("Polls root id not found");
        }
        try
        {
            PollsResource pollsRoot = PollsResourceImpl.getPollsResource(coralSession, psid);
            templatingContext.put("pollsRoot",pollsRoot);
            Resource[] resources = coralSession.getStore().getResource(pollsRoot);
            List pools = new ArrayList();
            HashMap hasPolls = new HashMap();
            for(int i = 0; i < resources.length; i++)
            {
                if(resources[i] instanceof PoolResource)
                {
                    pools.add(resources[i]);
                    hasPolls.put(resources[i], new Boolean(pollsRoot.getBindings().get(resources[i]).length > 0));
                }
            }
            templatingContext.put("pools",pools);
            templatingContext.put("has_polls",hasPolls);
            TableColumn[] columns = new TableColumn[3];
            columns[0] = new TableColumn("name", new NameComparator(i18nContext.getLocale()()));
            columns[1] = new TableColumn("creator", new CreatorNameComparator(i18nContext.getLocale()()));
            columns[2] = new TableColumn("creation_time", new CreationTimeComparator());
            TableState state = tableService.getLocalState(data, "cms:screens:poll,PoolList");
            if(state.isNew())
            {
                state.setTreeView(false);
                state.setPageSize(10);
            }
            TableModel model = new ListTableModel(pools, columns);
            templatingContext.put("table", new TableTool(state, model, null));
        }
        catch(EntityDoesNotExistException e)
        {
            throw new ProcessingException("failed to lookup resource", e);
        }
        catch(TableException e)
        {
            throw new ProcessingException("failed to initialize column data", e);
        }
    }
}
