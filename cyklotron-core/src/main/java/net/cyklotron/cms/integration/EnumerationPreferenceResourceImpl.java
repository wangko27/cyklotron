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
 
package net.cyklotron.cms.integration;

import java.util.HashMap;
import java.util.Map;

import org.objectledge.coral.BackendException;
import org.objectledge.coral.entity.EntityDoesNotExistException;
import org.objectledge.coral.schema.AttributeDefinition;
import org.objectledge.coral.schema.ResourceClass;
import org.objectledge.coral.session.CoralSession;
import org.objectledge.coral.store.InvalidResourceNameException;
import org.objectledge.coral.store.ModificationNotPermitedException;
import org.objectledge.coral.store.Resource;
import org.objectledge.coral.store.ValueRequiredException;

/**
 * An implementation of <code>integration.enumeration_preference</code> Coral resource class.
 *
 * @author Coral Maven plugin
 */
public class EnumerationPreferenceResourceImpl
    extends PreferenceResourceImpl
    implements EnumerationPreferenceResource
{
    // class variables /////////////////////////////////////////////////////////

    /** Class variables initialization status. */
	@SuppressWarnings("unused")
    private static boolean definitionsInitialized;
	
    /** The AttributeDefinition object for the <code>multi</code> attribute. */
    private static AttributeDefinition<Boolean> multiDef;

    /** The AttributeDefinition object for the <code>values</code> attribute. */
	private static AttributeDefinition<String> valuesDef;

    // initialization /////////////////////////////////////////////////////////

    /**
     * Creates a blank <code>integration.enumeration_preference</code> resource wrapper.
     *
     * <p>This constructor should be used by the handler class only. Use 
     * <code>load()</code> and <code>create()</code> methods to create
     * instances of the wrapper in your application code.</p>
     *
     */
    public EnumerationPreferenceResourceImpl()
    {
    }

    // static methods ////////////////////////////////////////////////////////

    /**
     * Retrieves a <code>integration.enumeration_preference</code> resource instance from the store.
     *
     * <p>This is a simple wrapper of StoreService.getResource() method plus
     * the typecast.</p>
     *
     * @param session the CoralSession
     * @param id the id of the object to be retrieved
     * @return a resource instance.
     * @throws EntityDoesNotExistException if the resource with the given id does not exist.
     */
    public static EnumerationPreferenceResource getEnumerationPreferenceResource(CoralSession session, long id)
        throws EntityDoesNotExistException
    {
        Resource res = session.getStore().getResource(id);
        if(!(res instanceof EnumerationPreferenceResource))
        {
            throw new IllegalArgumentException("resource #"+id+" is "+
                                               res.getResourceClass().getName()+
                                               " not integration.enumeration_preference");
        }
        return (EnumerationPreferenceResource)res;
    }

    /**
     * Creates a new <code>integration.enumeration_preference</code> resource instance.
     *
     * @param session the CoralSession
     * @param name the name of the new resource
     * @param parent the parent resource.
     * @param multi the multi attribute
     * @param required the required attribute
     * @param scope the scope attribute
     * @param values the values attribute
     * @return a new EnumerationPreferenceResource instance.
     * @throws ValueRequiredException if one of the required attribues is undefined.
     * @throws InvalidResourceNameException if the name argument contains illegal characters.
     */
    public static EnumerationPreferenceResource createEnumerationPreferenceResource(CoralSession
        session, String name, Resource parent, boolean multi, boolean required, String scope, String
        values)
        throws ValueRequiredException, InvalidResourceNameException
    {
        try
        {
            ResourceClass<EnumerationPreferenceResource> rc = session.getSchema().getResourceClass("integration.enumeration_preference", EnumerationPreferenceResource.class);
			Map<AttributeDefinition<?>, Object> attrs = new HashMap<AttributeDefinition<?>, Object>();
            attrs.put(rc.getAttribute("multi"), Boolean.valueOf(multi));
            attrs.put(rc.getAttribute("required"), Boolean.valueOf(required));
            attrs.put(rc.getAttribute("scope"), scope);
            attrs.put(rc.getAttribute("values"), values);
            Resource res = session.getStore().createResource(name, parent, rc, attrs);
            if(!(res instanceof EnumerationPreferenceResource))
            {
                throw new BackendException("incosistent schema: created object is "+
                                           res.getClass().getName());
            }
            return (EnumerationPreferenceResource)res;
        }
        catch(EntityDoesNotExistException e)
        {
            throw new BackendException("incompatible schema change", e);
        }
    }

    // public interface //////////////////////////////////////////////////////
 
    /**
     * Returns the value of the <code>multi</code> attribute.
     *
     * @return the value of the <code>multi</code> attribute.
     */
    public boolean getMulti()
    {
		return get(multiDef).booleanValue();
    }    

    /**
     * Sets the value of the <code>multi</code> attribute.
     *
     * @param value the value of the <code>multi</code> attribute.
     */
    public void setMulti(boolean value)
    {
        try
        {
            set(multiDef, Boolean.valueOf(value));
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
     * Returns the value of the <code>values</code> attribute.
     *
     * @return the value of the <code>values</code> attribute.
     */
    public String getValues()
    {
        return get(valuesDef);
    }
 
    /**
     * Sets the value of the <code>values</code> attribute.
     *
     * @param value the value of the <code>values</code> attribute.
     * @throws ValueRequiredException if you attempt to set a <code>null</code> 
     *         value.
     */
    public void setValues(String value)
        throws ValueRequiredException
    {
        try
        {
            if(value != null)
            {
                set(valuesDef, value);
            }
            else
            {
                throw new ValueRequiredException("attribute values "+
                                                 "is declared as REQUIRED");
            }
        }
        catch(ModificationNotPermitedException e)
        {
            throw new BackendException("incompatible schema change",e);
        }
    }
     
    // @custom methods ///////////////////////////////////////////////////////
    // @extends integration.preference

}
