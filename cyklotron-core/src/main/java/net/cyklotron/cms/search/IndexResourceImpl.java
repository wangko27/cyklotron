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
 
package net.cyklotron.cms.search;

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

import net.cyklotron.cms.CmsNodeResourceImpl;

/**
 * An implementation of <code>search.index</code> Coral resource class.
 *
 * @author Coral Maven plugin
 */
public class IndexResourceImpl
    extends CmsNodeResourceImpl
    implements IndexResource
{
    // class variables /////////////////////////////////////////////////////////

    /** Class variables initialization status. */
	@SuppressWarnings("unused")
    private static boolean definitionsInitialized;
	
    /** The AttributeDefinition object for the <code>filesLocation</code> attribute. */
	private static AttributeDefinition<String> filesLocationDef;

    /** The AttributeDefinition object for the <code>optimise</code> attribute. */
    private static AttributeDefinition<Boolean> optimiseDef;

    /** The AttributeDefinition object for the <code>optionalCategoryIdentifiers</code> attribute. */
	private static AttributeDefinition<String> optionalCategoryIdentifiersDef;

    /** The AttributeDefinition object for the <code>public</code> attribute. */
    private static AttributeDefinition<Boolean> publicDef;

    /** The AttributeDefinition object for the <code>requiredCategoryIdentifiers</code> attribute. */
	private static AttributeDefinition<String> requiredCategoryIdentifiersDef;

    // initialization /////////////////////////////////////////////////////////

    /**
     * Creates a blank <code>search.index</code> resource wrapper.
     *
     * <p>This constructor should be used by the handler class only. Use 
     * <code>load()</code> and <code>create()</code> methods to create
     * instances of the wrapper in your application code.</p>
     *
     */
    public IndexResourceImpl()
    {
    }

    // static methods ////////////////////////////////////////////////////////

    /**
     * Retrieves a <code>search.index</code> resource instance from the store.
     *
     * <p>This is a simple wrapper of StoreService.getResource() method plus
     * the typecast.</p>
     *
     * @param session the CoralSession
     * @param id the id of the object to be retrieved
     * @return a resource instance.
     * @throws EntityDoesNotExistException if the resource with the given id does not exist.
     */
    public static IndexResource getIndexResource(CoralSession session, long id)
        throws EntityDoesNotExistException
    {
        Resource res = session.getStore().getResource(id);
        if(!(res instanceof IndexResource))
        {
            throw new IllegalArgumentException("resource #"+id+" is "+
                                               res.getResourceClass().getName()+
                                               " not search.index");
        }
        return (IndexResource)res;
    }

    /**
     * Creates a new <code>search.index</code> resource instance.
     *
     * @param session the CoralSession
     * @param name the name of the new resource
     * @param parent the parent resource.
     * @param filesLocation the filesLocation attribute
     * @return a new IndexResource instance.
     * @throws ValueRequiredException if one of the required attribues is undefined.
     * @throws InvalidResourceNameException if the name argument contains illegal characters.
     */
    public static IndexResource createIndexResource(CoralSession session, String name, Resource
        parent, String filesLocation)
        throws ValueRequiredException, InvalidResourceNameException
    {
        try
        {
            ResourceClass<IndexResource> rc = session.getSchema().getResourceClass("search.index", IndexResource.class);
			Map<AttributeDefinition<?>, Object> attrs = new HashMap<AttributeDefinition<?>, Object>();
            attrs.put(rc.getAttribute("filesLocation"), filesLocation);
            Resource res = session.getStore().createResource(name, parent, rc, attrs);
            if(!(res instanceof IndexResource))
            {
                throw new BackendException("incosistent schema: created object is "+
                                           res.getClass().getName());
            }
            return (IndexResource)res;
        }
        catch(EntityDoesNotExistException e)
        {
            throw new BackendException("incompatible schema change", e);
        }
    }

    // public interface //////////////////////////////////////////////////////
 
    /**
     * Returns the value of the <code>filesLocation</code> attribute.
     *
     * @return the value of the <code>filesLocation</code> attribute.
     */
    public String getFilesLocation()
    {
        return get(filesLocationDef);
    }
 
    /**
     * Sets the value of the <code>filesLocation</code> attribute.
     *
     * @param value the value of the <code>filesLocation</code> attribute.
     * @throws ValueRequiredException if you attempt to set a <code>null</code> 
     *         value.
     */
    public void setFilesLocation(String value)
        throws ValueRequiredException
    {
        try
        {
            if(value != null)
            {
                set(filesLocationDef, value);
            }
            else
            {
                throw new ValueRequiredException("attribute filesLocation "+
                                                 "is declared as REQUIRED");
            }
        }
        catch(ModificationNotPermitedException e)
        {
            throw new BackendException("incompatible schema change",e);
        }
    }
   
    /**
     * Returns the value of the <code>optimise</code> attribute.
     *
     * @return the value of the <code>optimise</code> attribute.
     * @throws IllegalStateException if the value of the attribute is 
     *         undefined.
     */
    public boolean getOptimise()
        throws IllegalStateException
    {
	    Boolean value = get(optimiseDef);
        if(value != null)
        {
            return value.booleanValue();
        }
        else
        {
            throw new IllegalStateException("value of attribute optimise is undefined"+
			    " for resource #"+getId());
        }
    }

    /**
     * Returns the value of the <code>optimise</code> attribute.
     *
     * @param defaultValue the value to return if the attribute is undefined.
     * @return the value of the <code>optimise</code> attribute.
     */
    public boolean getOptimise(boolean defaultValue)
    {
		return get(optimiseDef, Boolean.valueOf(defaultValue)).booleanValue();
	}

    /**
     * Sets the value of the <code>optimise</code> attribute.
     *
     * @param value the value of the <code>optimise</code> attribute.
     */
    public void setOptimise(boolean value)
    {
        try
        {
            set(optimiseDef, Boolean.valueOf(value));
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
     * Removes the value of the <code>optimise</code> attribute.
     */
    public void unsetOptimise()
    {
        try
        {
            unset(optimiseDef);
        }
        catch(ValueRequiredException e)
        {
            throw new BackendException("incompatible schema change",e);
        }     
    } 
   
	/**
	 * Checks if the value of the <code>optimise</code> attribute is defined.
	 *
	 * @return <code>true</code> if the value of the <code>optimise</code> attribute is defined.
	 */
    public boolean isOptimiseDefined()
	{
	    return isDefined(optimiseDef);
	}
 
    /**
     * Returns the value of the <code>optionalCategoryIdentifiers</code> attribute.
     *
     * @return the value of the <code>optionalCategoryIdentifiers</code> attribute.
     */
    public String getOptionalCategoryIdentifiers()
    {
        return get(optionalCategoryIdentifiersDef);
    }
    
    /**
     * Returns the value of the <code>optionalCategoryIdentifiers</code> attribute.
     *
     * @param defaultValue the value to return if the attribute is undefined.
     * @return the value of the <code>optionalCategoryIdentifiers</code> attribute.
     */
    public String getOptionalCategoryIdentifiers(String defaultValue)
    {
        return get(optionalCategoryIdentifiersDef, defaultValue);
    }    

    /**
     * Sets the value of the <code>optionalCategoryIdentifiers</code> attribute.
     *
     * @param value the value of the <code>optionalCategoryIdentifiers</code> attribute,
     *        or <code>null</code> to remove value.
     */
    public void setOptionalCategoryIdentifiers(String value)
    {
        try
        {
            if(value != null)
            {
                set(optionalCategoryIdentifiersDef, value);
            }
            else
            {
                unset(optionalCategoryIdentifiersDef);
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
	 * Checks if the value of the <code>optionalCategoryIdentifiers</code> attribute is defined.
	 *
	 * @return <code>true</code> if the value of the <code>optionalCategoryIdentifiers</code> attribute is defined.
	 */
    public boolean isOptionalCategoryIdentifiersDefined()
	{
	    return isDefined(optionalCategoryIdentifiersDef);
	}

    /**
     * Returns the value of the <code>public</code> attribute.
     *
     * @return the value of the <code>public</code> attribute.
     * @throws IllegalStateException if the value of the attribute is 
     *         undefined.
     */
    public boolean getPublic()
        throws IllegalStateException
    {
	    Boolean value = get(publicDef);
        if(value != null)
        {
            return value.booleanValue();
        }
        else
        {
            throw new IllegalStateException("value of attribute public is undefined"+
			    " for resource #"+getId());
        }
    }

    /**
     * Returns the value of the <code>public</code> attribute.
     *
     * @param defaultValue the value to return if the attribute is undefined.
     * @return the value of the <code>public</code> attribute.
     */
    public boolean getPublic(boolean defaultValue)
    {
		return get(publicDef, Boolean.valueOf(defaultValue)).booleanValue();
	}

    /**
     * Sets the value of the <code>public</code> attribute.
     *
     * @param value the value of the <code>public</code> attribute.
     */
    public void setPublic(boolean value)
    {
        try
        {
            set(publicDef, Boolean.valueOf(value));
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
     * Removes the value of the <code>public</code> attribute.
     */
    public void unsetPublic()
    {
        try
        {
            unset(publicDef);
        }
        catch(ValueRequiredException e)
        {
            throw new BackendException("incompatible schema change",e);
        }     
    } 
   
	/**
	 * Checks if the value of the <code>public</code> attribute is defined.
	 *
	 * @return <code>true</code> if the value of the <code>public</code> attribute is defined.
	 */
    public boolean isPublicDefined()
	{
	    return isDefined(publicDef);
	}
 
    /**
     * Returns the value of the <code>requiredCategoryIdentifiers</code> attribute.
     *
     * @return the value of the <code>requiredCategoryIdentifiers</code> attribute.
     */
    public String getRequiredCategoryIdentifiers()
    {
        return get(requiredCategoryIdentifiersDef);
    }
    
    /**
     * Returns the value of the <code>requiredCategoryIdentifiers</code> attribute.
     *
     * @param defaultValue the value to return if the attribute is undefined.
     * @return the value of the <code>requiredCategoryIdentifiers</code> attribute.
     */
    public String getRequiredCategoryIdentifiers(String defaultValue)
    {
        return get(requiredCategoryIdentifiersDef, defaultValue);
    }    

    /**
     * Sets the value of the <code>requiredCategoryIdentifiers</code> attribute.
     *
     * @param value the value of the <code>requiredCategoryIdentifiers</code> attribute,
     *        or <code>null</code> to remove value.
     */
    public void setRequiredCategoryIdentifiers(String value)
    {
        try
        {
            if(value != null)
            {
                set(requiredCategoryIdentifiersDef, value);
            }
            else
            {
                unset(requiredCategoryIdentifiersDef);
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
	 * Checks if the value of the <code>requiredCategoryIdentifiers</code> attribute is defined.
	 *
	 * @return <code>true</code> if the value of the <code>requiredCategoryIdentifiers</code> attribute is defined.
	 */
    public boolean isRequiredCategoryIdentifiersDefined()
	{
	    return isDefined(requiredCategoryIdentifiersDef);
	}
  
    // @custom methods ///////////////////////////////////////////////////////
    // @order filesLocation
}
