package net.cyklotron.cms.category.components;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.objectledge.context.Context;
import org.objectledge.coral.datatypes.DateAttributeHandler;
import org.objectledge.coral.query.MalformedQueryException;
import org.objectledge.coral.query.QueryResults;
import org.objectledge.coral.query.QueryResults.Row;
import org.objectledge.coral.session.CoralSession;
import org.objectledge.pipeline.ProcessingException;

import net.cyklotron.cms.CmsData;
import net.cyklotron.cms.CmsDataFactory;
import net.cyklotron.cms.category.query.CategoryQueryResource;
import net.cyklotron.cms.category.query.CategoryQueryService;
import net.cyklotron.cms.documents.DocumentNodeResource;
import net.cyklotron.cms.integration.IntegrationService;
import net.cyklotron.cms.site.SiteService;

/**
 * This class contains logic of component which displays lists of documents assigned
 * to queried categories.
 *
 * @author <a href="mailto:dgajda@caltha.pl">Damian Gajda</a>
 * @version $Id: DocumentResourceList.java,v 1.8 2005-05-17 06:19:58 zwierzem Exp $
 */
public class DocumentResourceList
extends ResourceList
{
    public DocumentResourceList(Context context, IntegrationService integrationService,
        CmsDataFactory cmsDataFactory,  CategoryQueryService categoryQueryService,
        SiteService siteService)
	{
        super(context,integrationService, cmsDataFactory, categoryQueryService, siteService);
	}

    public BaseResourceListConfiguration createConfig()
        throws ProcessingException
    {
        return new DocumentResourceListConfiguration();
    }
    
    private CategoryQueryResource categoryQuery;
    private boolean categoryQuerySought = false;

    public String getTableStateName()
    {
        return "net.cyklotron.cms.category.document_resource_list";
    }

    private static String[] resourceClasses = { DocumentNodeResource.CLASS_NAME };

    protected String[] getResourceClasses(org.objectledge.coral.session.CoralSession coralSession, BaseResourceListConfiguration config)
	    throws ProcessingException
    {
        int offset = ((DocumentResourceListConfiguration) config).getPublicationTimeOffset();
        if(offset == -1)
        {
            return resourceClasses;
        }
        return null;
    }
    
    public Set getIdSet(CoralSession coralSession, BaseResourceListConfiguration config)
        throws ProcessingException
    {
        CmsData cmsData = cmsDataFactory.getCmsData(context);

        String date = null;
        int offset = ((DocumentResourceListConfiguration) config).getPublicationTimeOffset();
        if(offset != -1)
        {
            Date now = cmsData.getDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.add(Calendar.DAY_OF_MONTH, -offset);

            SimpleDateFormat df = 
                new SimpleDateFormat(DateAttributeHandler.DATE_TIME_FORMAT);

            date = df.format(calendar.getTime());

            try
            {
                QueryResults res = coralSession.getQuery().executeQuery(
                    "FIND RESOURCE FROM documents.document_node WHERE validityStart >= '"+date+"'");
                Set set = new HashSet(1024);
                for (Iterator iter = res.iterator(); iter.hasNext();)
                {
                    long[] ids = ((Row) iter.next()).getIdArray();
                    for (int i = 0; i < ids.length; i++)
                    {
                        set.add(new Long(ids[i]));
                    }
                }
                return set;
            }
            catch (MalformedQueryException e)
            {
                // should not happen
                return null;
            }
        }
        return null;
    }    
}