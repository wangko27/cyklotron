package net.cyklotron.cms.modules.components.files;

import java.util.ArrayList;
import java.util.Arrays;

import org.jcontainer.dna.Logger;
import org.objectledge.coral.session.CoralSession;
import org.objectledge.coral.store.Resource;
import org.objectledge.i18n.I18nContext;
import org.objectledge.parameters.Parameters;
import org.objectledge.pipeline.ProcessingException;
import org.objectledge.table.TableColumn;
import org.objectledge.table.TableModel;
import org.objectledge.table.TableState;
import org.objectledge.table.TableStateManager;
import org.objectledge.table.TableTool;
import org.objectledge.table.generic.ListTableModel;
import org.objectledge.templating.Templating;
import org.objectledge.templating.TemplatingContext;
import org.objectledge.web.HttpContext;
import org.objectledge.web.mvc.MVCContext;
import org.objectledge.web.mvc.finders.MVCFinder;

import net.cyklotron.cms.CmsDataFactory;
import net.cyklotron.cms.files.FilesService;
import net.cyklotron.cms.modules.components.SkinableCMSComponent;
import net.cyklotron.cms.skins.SkinService;
import net.cyklotron.cms.util.ProtectedViewFilter;

/**
 * Files component.
 *
 * @author <a href="mailto:pablo@caltha.pl">Pawel Potempski</a>
 * @version $Id: Files.java,v 1.2 2005-01-25 11:24:14 pablo Exp $
 */

public class Files
    extends SkinableCMSComponent
{
    private FilesService filesService;

    private TableStateManager tableStateManager;

    public Files(org.objectledge.context.Context context, Logger logger, Templating templating,
        CmsDataFactory cmsDataFactory, SkinService skinService, MVCFinder mvcFinder,
        FilesService fileService, TableStateManager tableStateManager)
    {
        super(context, logger, templating, cmsDataFactory, skinService, mvcFinder);
        this.filesService = fileService;
        this.tableStateManager = tableStateManager;
    }
    
    public void process(Parameters parameters, MVCContext mvcContext, TemplatingContext templatingContext, HttpContext httpContext, I18nContext i18nContext, CoralSession coralSession) throws ProcessingException
    {
        try
        {
            if(getSite(context) != null)
            {
                Resource filesRoot = filesService.getFilesRoot(coralSession, getSite(context));
                Parameters componentConfig = getConfiguration();
                Resource directory = null;
                long dir = parameters.getLong("dir_id", -1L);
                if(dir == -1L)
                {
                    dir = componentConfig.getLong("dir",-1L);
                }
                if(dir == -1L)
                {
                    directory = filesRoot;
                    dir = directory.getId();
                }
                else
                {
                    directory = coralSession.getStore().getResource(dir);
                }
                Resource[] files = coralSession.getStore().getResource(directory);
                TableColumn[] columns = new TableColumn[0];
                TableState state = tableStateManager.getState(context, "cms:components:files,Files");
                if(state.isNew())
                {
                    state.setTreeView(false);
                    state.setTreeView(false);
                    state.setPageSize(10);
                }
                TableModel model = new ListTableModel(Arrays.asList(files), columns);
                ArrayList filters = new ArrayList();
                filters.add(new ProtectedViewFilter(context, coralSession.getUserSubject()));
                TableTool helper = new TableTool(state, filters, model);
                
                templatingContext.put("table", helper);
                templatingContext.put("current_directory", directory);
            }
            else
            {
                componentError(context, "No site selected");
            }
        }
        catch(Exception e)
        {
            componentError(context, "Exception", e);
        }
    }
}
