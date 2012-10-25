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
 
package net.cyklotron.bazy.organizations;

import org.objectledge.coral.BackendException;
import org.objectledge.coral.entity.EntityDoesNotExistException;
import org.objectledge.coral.schema.AttributeDefinition;
import org.objectledge.coral.schema.ResourceClass;
import org.objectledge.coral.session.CoralSession;
import org.objectledge.coral.store.InvalidResourceNameException;
import org.objectledge.coral.store.ModificationNotPermitedException;
import org.objectledge.coral.store.Resource;
import org.objectledge.coral.store.ValueRequiredException;

import net.cyklotron.cms.CmsNodeResourceImpl;

/**
 * An implementation of <code>bazy.organizations.organization</code> Coral resource class.
 *
 * @author Coral Maven plugin
 */
public class OrganizationResourceImpl
    extends CmsNodeResourceImpl
    implements OrganizationResource
{
    // class variables /////////////////////////////////////////////////////////

    /** Class variables initialization status. */
	@SuppressWarnings("unused")
    private static boolean definitionsInitialized;
	
    /** The AttributeDefinition object for the <code>data</code> attribute. */
	private static AttributeDefinition<String> dataDef;

    /** The AttributeDefinition object for the <code>organizationName</code> attribute. */
	private static AttributeDefinition<String> organizationNameDef;

    // initialization /////////////////////////////////////////////////////////

    /**
     * Creates a blank <code>bazy.organizations.organization</code> resource wrapper.
     *
     * <p>This constructor should be used by the handler class only. Use 
     * <code>load()</code> and <code>create()</code> methods to create
     * instances of the wrapper in your application code.</p>
     *
     */
    public OrganizationResourceImpl()
    {
    }

    // static methods ////////////////////////////////////////////////////////

    /**
     * Retrieves a <code>bazy.organizations.organization</code> resource instance from the store.
     *
     * <p>This is a simple wrapper of StoreService.getResource() method plus
     * the typecast.</p>
     *
     * @param session the CoralSession
     * @param id the id of the object to be retrieved
     * @return a resource instance.
     * @throws EntityDoesNotExistException if the resource with the given id does not exist.
     */
    public static OrganizationResource getOrganizationResource(CoralSession session, long id)
        throws EntityDoesNotExistException
    {
        Resource res = session.getStore().getResource(id);
        if(!(res instanceof OrganizationResource))
        {
            throw new IllegalArgumentException("resource #"+id+" is "+
                                               res.getResourceClass().getName()+
                                               " not bazy.organizations.organization");
        }
        return (OrganizationResource)res;
    }

    /**
     * Creates a new <code>bazy.organizations.organization</code> resource instance.
     *
     * @param session the CoralSession
     * @param name the name of the new resource
     * @param parent the parent resource.
     * @return a new OrganizationResource instance.
     * @throws InvalidResourceNameException if the name argument contains illegal characters.
     */
    public static OrganizationResource createOrganizationResource(CoralSession session, String
        name, Resource parent)
        throws InvalidResourceNameException
    {
        try
        {
            ResourceClass<OrganizationResource> rc = session.getSchema().getResourceClass("bazy.organizations.organization", OrganizationResource.class);
		    Resource res = session.getStore().createResource(name, parent, rc,
                java.util.Collections.<AttributeDefinition<?>, Object> emptyMap());			
            if(!(res instanceof OrganizationResource))
            {
                throw new BackendException("incosistent schema: created object is "+
                                           res.getClass().getName());
            }
            return (OrganizationResource)res;
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
     * Returns the value of the <code>data</code> attribute.
     *
     * @return the value of the <code>data</code> attribute.
     */
    public String getData()
    {
        return get(dataDef);
    }
    
    /**
     * Returns the value of the <code>data</code> attribute.
     *
     * @param defaultValue the value to return if the attribute is undefined.
     * @return the value of the <code>data</code> attribute.
     */
    public String getData(String defaultValue)
    {
        return get(dataDef, defaultValue);
    }    

    /**
     * Sets the value of the <code>data</code> attribute.
     *
     * @param value the value of the <code>data</code> attribute,
     *        or <code>null</code> to remove value.
     */
    public void setData(String value)
    {
        try
        {
            if(value != null)
            {
                set(dataDef, value);
            }
            else
            {
                unset(dataDef);
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
	 * Checks if the value of the <code>data</code> attribute is defined.
	 *
	 * @return <code>true</code> if the value of the <code>data</code> attribute is defined.
	 */
    public boolean isDataDefined()
	{
	    return isDefined(dataDef);
	}
 
    /**
     * Returns the value of the <code>organizationName</code> attribute.
     *
     * @return the value of the <code>organizationName</code> attribute.
     */
    public String getOrganizationName()
    {
        return get(organizationNameDef);
    }
    
    /**
     * Returns the value of the <code>organizationName</code> attribute.
     *
     * @param defaultValue the value to return if the attribute is undefined.
     * @return the value of the <code>organizationName</code> attribute.
     */
    public String getOrganizationName(String defaultValue)
    {
        return get(organizationNameDef, defaultValue);
    }    

    /**
     * Sets the value of the <code>organizationName</code> attribute.
     *
     * @param value the value of the <code>organizationName</code> attribute,
     *        or <code>null</code> to remove value.
     */
    public void setOrganizationName(String value)
    {
        try
        {
            if(value != null)
            {
                set(organizationNameDef, value);
            }
            else
            {
                unset(organizationNameDef);
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
	 * Checks if the value of the <code>organizationName</code> attribute is defined.
	 *
	 * @return <code>true</code> if the value of the <code>organizationName</code> attribute is defined.
	 */
    public boolean isOrganizationNameDefined()
	{
	    return isDefined(organizationNameDef);
	}
  
    // @custom methods ///////////////////////////////////////////////////////
}