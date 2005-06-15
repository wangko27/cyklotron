// 
// Copyright (c) 2004, Caltha - Gajda, Krzewski, Mach, Potempski Sp.J. 
// All rights reserved. 
// 
// Redistribution and use in source and binary forms, with or without modification,  
// are permitted provided that the following conditions are met: 
// 
// * Redistributions of source code must retain the above copyright notice,  
//       this list of conditions and the following disclaimer. 
// * Redistributions in binary form must reproduce the above copyright notice,  
//       this list of conditions and the following disclaimer in the documentation  
//       and/or other materials provided with the distribution. 
// * Neither the name of the Caltha - Gajda, Krzewski, Mach, Potempski Sp.J.  
//       nor the names of its contributors may be used to endorse or promote products  
//       derived from this software without specific prior written permission. 
// 
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"  
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED  
// WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
// IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,  
// INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,  
// BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, 
// OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,  
// WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)  
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE  
// POSSIBILITY OF SUCH DAMAGE. 
// 
 
package net.cyklotron.cms.link;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.objectledge.context.Context;
import org.objectledge.coral.BackendException;
import org.objectledge.coral.datatypes.ResourceList;
import org.objectledge.coral.entity.EntityDoesNotExistException;
import org.objectledge.coral.schema.AttributeDefinition;
import org.objectledge.coral.schema.CoralSchema;
import org.objectledge.coral.schema.ResourceClass;
import org.objectledge.coral.security.Subject;
import org.objectledge.coral.session.CoralSession;
import org.objectledge.coral.store.InvalidResourceNameException;
import org.objectledge.coral.store.ModificationNotPermitedException;
import org.objectledge.coral.store.Resource;
import org.objectledge.coral.store.ValueRequiredException;
import org.objectledge.database.Database;

import net.cyklotron.cms.CmsData;
import net.cyklotron.cms.CmsNodeResourceImpl;
import org.jcontainer.dna.Logger;

/**
 * An implementation of <code>cms.link.pool</code> Coral resource class.
 *
 * @author Coral Maven plugin
 */
public class PoolResourceImpl
    extends CmsNodeResourceImpl
    implements PoolResource
{
    // instance variables ////////////////////////////////////////////////////

    /** The AttributeDefinition object for the <code>links</code> attribute. */
    private AttributeDefinition linksDef;

    // initialization /////////////////////////////////////////////////////////

    /**
     * Creates a blank <code>cms.link.pool</code> resource wrapper.
     *
     * <p>This constructor should be used by the handler class only. Use 
     * <code>load()</code> and <code>create()</code> methods to create
     * instances of the wrapper in your application code.</p>
     *
     * @param schema the CoralSchema.
     * @param database the Database.
     * @param logger the Logger.
     */
    public PoolResourceImpl(CoralSchema schema, Database database, Logger logger)
    {
        super(schema, database, logger);
        try
        {
            ResourceClass rc = schema.getResourceClass("cms.link.pool");
            linksDef = rc.getAttribute("links");
        }
        catch(EntityDoesNotExistException e)
        {
            throw new BackendException("incompatible schema change", e);
        }
    }

    // static methods ////////////////////////////////////////////////////////

    /**
     * Retrieves a <code>cms.link.pool</code> resource instance from the store.
     *
     * <p>This is a simple wrapper of StoreService.getResource() method plus
     * the typecast.</p>
     *
     * @param session the CoralSession
     * @param id the id of the object to be retrieved
     * @return a resource instance.
     * @throws EntityDoesNotExistException if the resource with the given id does not exist.
     */
    public static PoolResource getPoolResource(CoralSession session, long id)
        throws EntityDoesNotExistException
    {
        Resource res = session.getStore().getResource(id);
        if(!(res instanceof PoolResource))
        {
            throw new IllegalArgumentException("resource #"+id+" is "+
                                               res.getResourceClass().getName()+
                                               " not cms.link.pool");
        }
        return (PoolResource)res;
    }

    /**
     * Creates a new <code>cms.link.pool</code> resource instance.
     *
     * @param session the CoralSession
     * @param name the name of the new resource
     * @param parent the parent resource.
     * @return a new PoolResource instance.
     * @throws InvalidResourceNameException if the name argument contains illegal characters.
     */
    public static PoolResource createPoolResource(CoralSession session, String name, Resource
        parent)
        throws InvalidResourceNameException
    {
        try
        {
            ResourceClass rc = session.getSchema().getResourceClass("cms.link.pool");
            Map attrs = new HashMap();
            Resource res = session.getStore().createResource(name, parent, rc, attrs);
            if(!(res instanceof PoolResource))
            {
                throw new BackendException("incosistent schema: created object is "+
                                           res.getClass().getName());
            }
            return (PoolResource)res;
        }
        catch(EntityDoesNotExistException e)
        {
            throw new BackendException("incompatible schema change", e);
        }
        catch(ValueRequiredException e)
        {
            throw new BackendException("incompatible schema change", e);
        }
    }

    // public interface //////////////////////////////////////////////////////
 
    /**
     * Returns the value of the <code>links</code> attribute.
     *
     * @return the value of the <code>links</code> attribute.
     */
    public ResourceList getLinks()
    {
        return (ResourceList)get(linksDef);
    }
    
    /**
     * Returns the value of the <code>links</code> attribute.
     *
     * @param defaultValue the value to return if the attribute is undefined.
     * @return the value of the <code>links</code> attribute.
     */
    public ResourceList getLinks(ResourceList defaultValue)
    {
        if(isDefined(linksDef))
        {
            return (ResourceList)get(linksDef);
        }
        else
        {
            return defaultValue;
        }
    }    

    /**
     * Sets the value of the <code>links</code> attribute.
     *
     * @param value the value of the <code>links</code> attribute,
     *        or <code>null</code> to remove value.
     */
    public void setLinks(ResourceList value)
    {
        try
        {
            if(value != null)
            {
                set(linksDef, value);
            }
            else
            {
                unset(linksDef);
            }
        }
        catch(ModificationNotPermitedException e)
        {
            throw new BackendException("incompatible schema change",e);
        }
        catch(ValueRequiredException e)
        {
            throw new BackendException("incompatible schema change",e);
        }
    }
   
	/**
	 * Checks if the value of the <code>links</code> attribute is defined.
	 *
	 * @return <code>true</code> if the value of the <code>links</code> attribute is defined.
	 */
    public boolean isLinksDefined()
	{
	    return isDefined(linksDef);
	}
  
    // @custom methods ///////////////////////////////////////////////////////
	// @extends node
	// @import net.cyklotron.cms.CmsData
    // @import java.util.Date
    // @import org.objectledge.context.Context
    // @import org.objectledge.coral.security.Subject

	public boolean isValid(Date time)
	{
		return true;
	}
		
	/**
	 * Checks if a given subject can view this resource.
	 */
	public boolean canView(CoralSession coralSession, Subject subject)
	{	
		return true;
	}
    
	/**
	 * Checks if the specified subject can view this resource at the given time.
	 */
	public boolean canView(CoralSession coralSession, Subject subject, Date time)
	{
		return true;
	}


	/**
	 * Checks if the specified subject can modify this resource.
	 */
	public boolean canModify(CoralSession coralSession, Subject subject)
	{
		return true;     
	}

	/**
	 * Checks if the specified subject can remove this resource.
	 */
	public boolean canRemove(Context context, Subject subject)
	{
		return true;
	}
    
	/**
	 * Checks if the specified subject can add children to this resource.
	 */
	public boolean canAddChild(Context context, Subject subject)
	{
		return true;
	}

	/**
	 * Checks if the specified subject can view this resource
	 */
	public boolean canView(CoralSession coralSession, CmsData data, Subject subject)
	{
		return true;
	}

}
