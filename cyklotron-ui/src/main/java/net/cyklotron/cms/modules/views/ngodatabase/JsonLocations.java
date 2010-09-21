package net.cyklotron.cms.modules.views.ngodatabase;

import java.util.HashSet;

import java.util.Set;

import org.jcontainer.dna.Logger;
import org.objectledge.context.Context;
import org.objectledge.coral.session.CoralSession;
import org.objectledge.parameters.Parameters;
import org.objectledge.parameters.RequestParameters;
import org.objectledge.pipeline.ProcessingException;
import org.objectledge.table.TableStateManager;
import org.objectledge.templating.Template;
import org.objectledge.web.mvc.builders.AbstractBuilder;
import org.objectledge.web.mvc.security.SecurityChecking;

import net.cyklotron.cms.CmsDataFactory;
import net.cyklotron.cms.ngodatabase.Location;
import net.cyklotron.cms.ngodatabase.LocationDatabaseService;
import net.cyklotron.cms.preferences.PreferencesService;
import net.sf.json.JSONObject;

/**
 * The screen for serving files.
 *
 * @author <a href="mailto:pablo@caltha.pl">Pawel Potempski</a>
 * @version $Id: Download.java,v 1.6 2006-01-02 11:42:17 rafal Exp $
 */
public class JsonLocations
    extends AbstractBuilder
    implements SecurityChecking
{    
    /** location types **/
    public static final String LOCATION_TYPE_CITY = "city";
    
    public static final String LOCATION_TYPE_POSTCODE = "postcode";
    
    public static final String LOCATION_TYPE_PROVINCE = "province";
    
    /** The logging service. */
    Logger logger;
    
    /** The Location service. */
    LocationDatabaseService locationDatabaseService;
 
    public JsonLocations(Context context, Logger logger, PreferencesService preferencesService,
        CmsDataFactory cmsDataFactory, TableStateManager tableStateManager,
        LocationDatabaseService locationDatabaseService)
    {
        super(context);
        this.logger = logger;
        this.locationDatabaseService = locationDatabaseService;
    }
 
    public String build(Template template, String embeddedBuildResults, Context context)
    {
        JSONObject jsonObject = new JSONObject();
        try 
        {
            Set<Location> locations = getRequestedLocations(context);
            jsonObject = LocationsToJson(locations);
        }
        catch(Exception e)
        {
            logger.error("exception occured", e);
        }
        return jsonObject.toString();
    }

    /**
     * {@inheritDoc}
     */
    public boolean requiresAuthenticatedUser(Context context)
        throws Exception
    {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public boolean requiresSecureChannel(Context context)
        throws Exception
    {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public boolean checkAccessRights(Context context)
        throws ProcessingException
    {
        return true;
    }
    
    
    private Set<Location> getRequestedLocations(Context context)
        throws ProcessingException
    {
        CoralSession coralSession = (CoralSession)context.getAttribute(CoralSession.class);
        Parameters parameters = RequestParameters.getRequestParameters(context);
        String location = parameters.get("location", "");
        String locationType = parameters.get("ltype", "");

        if(location.equals("") || locationType.equals(""))
        {
            throw new ProcessingException("one of parameters is missing");
        }
        if(LOCATION_TYPE_POSTCODE.equals(locationType))
        {
            return locationDatabaseService.getLocationsByPostCode(location);
        }
        else if(LOCATION_TYPE_CITY.equals(locationType))
        {
            return locationDatabaseService.getLocationsByCity(location);
        }
        else if(LOCATION_TYPE_PROVINCE.equals(locationType))
        {
            return locationDatabaseService.getLocationsByProvince(location);
        }
        else
        {
            return new HashSet<Location>();
        }
    }
    
    private JSONObject LocationsToJson(Set<Location> locations)
    {
        JSONObject jsonObject = JSONObject.fromObject(locations);
        return jsonObject;
    }
    
}
