/*
 * Created on Oct 27, 2003
 */
package net.cyklotron.cms.periodicals.internal;

import org.jcontainer.dna.Logger;
import org.objectledge.i18n.DateFormatter;
import org.objectledge.mail.MailSystem;
import org.objectledge.templating.Templating;

import net.cyklotron.cms.category.query.CategoryQueryService;
import net.cyklotron.cms.files.FilesService;
import net.cyklotron.cms.integration.IntegrationService;
import net.cyklotron.cms.periodicals.PeriodicalsService;
import net.cyklotron.cms.periodicals.PeriodicalsTemplatingService;
import net.cyklotron.cms.site.SiteService;


/**
 * HTML Document renderer for periodicals.
 *
 * @author <a href="mailto:dgajda@caltha.pl">Damian Gajda</a>
 * @version $Id: HTMLNotificationRenderer.java,v 1.3 2006-05-08 08:28:19 rafal Exp $
 */
public class HTMLNotificationRenderer extends HTMLRenderer
{
    /** renderer name */
    public static final String RENDERER_NAME = "html_notification";    
    
    public HTMLNotificationRenderer(Logger log, Templating templating, MailSystem mailSystem,
        CategoryQueryService categoryQueryService, PeriodicalsService periodicalsService,
        PeriodicalsTemplatingService periodicalsTemplatingService, FilesService cmsFilesService,
        DateFormatter dateFormatter, IntegrationService integrationService, SiteService siteService)
    {
        super(log, templating, mailSystem, categoryQueryService, periodicalsService,
                        periodicalsTemplatingService, cmsFilesService, dateFormatter,
                        integrationService, siteService);
    }
    
    // inherit doc
    public String getName()
    {
        return RENDERER_NAME;
    }
    
    // inherit doc
    protected boolean isNotification()
    {
        return true;
    }
}
