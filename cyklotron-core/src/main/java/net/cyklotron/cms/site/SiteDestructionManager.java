package net.cyklotron.cms.site;

import java.util.ArrayList;
import java.util.List;

import org.jcontainer.dna.Logger;
import org.objectledge.coral.session.CoralSession;
import org.objectledge.coral.session.CoralSessionFactory;
import org.objectledge.coral.store.Resource;
import org.objectledge.event.EventWhiteboard;
import org.picocontainer.Startable;

import net.cyklotron.cms.security.SecurityService;
import net.cyklotron.cms.site.BaseSiteListener;
import net.cyklotron.cms.site.SiteDestructionListener;
import net.cyklotron.cms.site.SiteResource;
import net.cyklotron.cms.site.SiteService;

public class SiteDestructionManager
    extends BaseSiteListener
    implements SiteDestructionListener, Startable
{
    private List<SiteDestructionValve> valves; 
    
    public SiteDestructionManager(Logger logger, 
        CoralSessionFactory sessionFactory,
        SecurityService cmsSecurityService, EventWhiteboard eventWhiteboard,
        SiteDestructionValve[] valves)
    {
        super(logger, sessionFactory, cmsSecurityService, eventWhiteboard);
        
        this.valves = new ArrayList<SiteDestructionValve>();
        for(SiteDestructionValve valve:valves)
        {
            this.valves.add(valve);
        }
        eventWhiteboard.addListener(SiteDestructionListener.class,this,null);
    }

    /**
     * {@inheritDoc}
     */
    public void start()
    {
    }
    
    /**
     * {@inheritDoc}
     */
    public void stop()
    {
    }
    
    /**
     * {@inheritDoc}
     */
    public void destroySite(SiteService siteService, SiteResource site)
    {
        CoralSession coralSession = sessionFactory.getRootSession();
        try
        {
            for(SiteDestructionValve valve:valves)
            {
                valve.clearSecurity(coralSession, siteService, site);
            }
            for(SiteDestructionValve valve:valves)
            {
                valve.clearApplication(coralSession, siteService, site);
            }
            Resource[] res = coralSession.getStore().getResource(site, "applications");
            if(res.length > 0)
            {
                deleteSiteNode(coralSession, res[0]);
            }
        }
        catch(Exception e)
        {
            log.error("Listener Exception",e);
        }
        finally
        {
            coralSession.close();
        }
    }
}
