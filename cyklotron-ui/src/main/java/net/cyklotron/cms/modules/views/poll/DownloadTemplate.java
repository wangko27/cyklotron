/*
 * Created on Oct 29, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package net.cyklotron.cms.modules.views.poll;

import java.io.ByteArrayInputStream;
import java.util.Date;

import org.jcontainer.dna.Logger;
import org.objectledge.context.Context;
import org.objectledge.coral.session.CoralSession;
import org.objectledge.i18n.I18nContext;
import org.objectledge.parameters.Parameters;
import org.objectledge.pipeline.ProcessingException;
import org.objectledge.table.TableStateManager;
import org.objectledge.templating.TemplatingContext;
import org.objectledge.upload.FileDownload;
import org.objectledge.utils.StringUtils;
import org.objectledge.web.HttpContext;
import org.objectledge.web.mvc.MVCContext;

import net.cyklotron.cms.CmsDataFactory;
import net.cyklotron.cms.poll.PollService;
import net.cyklotron.cms.poll.VoteResource;
import net.cyklotron.cms.poll.VoteResourceImpl;
import net.cyklotron.cms.preferences.PreferencesService;

/**
 * @author fil
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DownloadTemplate
extends BasePollScreen
{
    protected FileDownload fileDownload;
    private final PollService pollService;
    
    public DownloadTemplate(Context context, Logger logger, PreferencesService preferencesService,
        CmsDataFactory cmsDataFactory, TableStateManager tableStateManager, FileDownload fileDownload, PollService pollService)
    {
        super(context, logger, preferencesService, cmsDataFactory, tableStateManager, pollService);
        this.pollService = pollService;
        this.fileDownload = fileDownload;
    }
    

    /**
     * {@inheritDoc}
     */
    public void process(Parameters parameters, MVCContext mvcContext, 
        TemplatingContext templatingContext, HttpContext httpContext,
        I18nContext i18nContext, CoralSession coralSession)
        throws ProcessingException
    {
        boolean asText =
            parameters.get("type","binary").equals("text");
        boolean asXML =
            parameters.get("type","binary").equals("xml");
        
        int vid = parameters.getInt("vid", -1);
        if(vid == -1)
        {
            throw new ProcessingException("Vote id not found");
        } 

        try
        {
            String contentType;
            VoteResource vote = VoteResourceImpl.getVoteResource(coralSession, vid);        
            String content =  pollService.getVoteConfiramationTicketContents(vote, i18nContext.getLocale());
            if (asText)
            {
                contentType = "text/plain";
            }
            else if(asXML)
            {
                contentType = "text/xml";
                content = 
                    "<?xml version=\"1.0\" encoding=\""+httpContext.getEncoding()+"\"?>\n"+
                    "<contents>\n"+
                    "  <![CDATA["+content+"]]>\n"+
                    "</contents>\n";
            }
            else
            {
                contentType = "application/octet-stream";
            }
            httpContext.disableCache();
            httpContext.setResponseLength(StringUtils.getByteCount(content, httpContext
                .getEncoding()));
            fileDownload.dumpData(new ByteArrayInputStream(content.getBytes(httpContext
                .getEncoding())), contentType, (new Date()).getTime());
        }
        catch (Exception e)
        {
            throw new ProcessingException("failed to send file", e);
        }
    }
}
