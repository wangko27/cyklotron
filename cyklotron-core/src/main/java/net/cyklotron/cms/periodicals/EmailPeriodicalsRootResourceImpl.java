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
 
package net.cyklotron.cms.periodicals;

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

import net.cyklotron.cms.structure.NavigationNodeResource;

/**
 * An implementation of <code>cms.periodicals.email.root</code> Coral resource class.
 *
 * @author Coral Maven plugin
 */
public class EmailPeriodicalsRootResourceImpl
    extends PeriodicalsNodeResourceImpl
    implements EmailPeriodicalsRootResource
{
    // class variables /////////////////////////////////////////////////////////

    /** Class variables initialization status. */
    private static boolean definitionsInitialized;
	
    /** The AttributeDefinition object for the <code>previewRecipient</code> attribute. */
    private static AttributeDefinition previewRecipientDef;

    /** The AttributeDefinition object for the <code>subscriptionNode</code> attribute. */
    private static AttributeDefinition subscriptionNodeDef;

    // initialization /////////////////////////////////////////////////////////

    /**
     * Creates a blank <code>cms.periodicals.email.root</code> resource wrapper.
     *
     * <p>This constructor should be used by the handler class only. Use 
     * <code>load()</code> and <code>create()</code> methods to create
     * instances of the wrapper in your application code.</p>
     *
     */
    public EmailPeriodicalsRootResourceImpl()
    {
    }

    // static methods ////////////////////////////////////////////////////////

    /**
     * Retrieves a <code>cms.periodicals.email.root</code> resource instance from the store.
     *
     * <p>This is a simple wrapper of StoreService.getResource() method plus
     * the typecast.</p>
     *
     * @param session the CoralSession
     * @param id the id of the object to be retrieved
     * @return a resource instance.
     * @throws EntityDoesNotExistException if the resource with the given id does not exist.
     */
    public static EmailPeriodicalsRootResource getEmailPeriodicalsRootResource(CoralSession session, long id)
        throws EntityDoesNotExistException
    {
        Resource res = session.getStore().getResource(id);
        if(!(res instanceof EmailPeriodicalsRootResource))
        {
            throw new IllegalArgumentException("resource #"+id+" is "+
                                               res.getResourceClass().getName()+
                                               " not cms.periodicals.email.root");
        }
        return (EmailPeriodicalsRootResource)res;
    }

    /**
     * Creates a new <code>cms.periodicals.email.root</code> resource instance.
     *
     * @param session the CoralSession
     * @param name the name of the new resource
     * @param parent the parent resource.
     * @return a new EmailPeriodicalsRootResource instance.
     * @throws InvalidResourceNameException if the name argument contains illegal characters.
     */
    public static EmailPeriodicalsRootResource createEmailPeriodicalsRootResource(CoralSession
        session, String name, Resource parent)
        throws InvalidResourceNameException
    {
        try
        {
            ResourceClass rc = session.getSchema().getResourceClass("cms.periodicals.email.root");
            Map attrs = new HashMap();
            Resource res = session.getStore().createResource(name, parent, rc, attrs);
            if(!(res instanceof EmailPeriodicalsRootResource))
            {
                throw new BackendException("incosistent schema: created object is "+
                                           res.getClass().getName());
            }
            return (EmailPeriodicalsRootResource)res;
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
     * Returns the value of the <code>previewRecipient</code> attribute.
     *
     * @return the value of the <code>previewRecipient</code> attribute.
     */
    public String getPreviewRecipient()
    {
        return (String)getInternal(previewRecipientDef, null);
    }
    
    /**
     * Returns the value of the <code>previewRecipient</code> attribute.
     *
     * @param defaultValue the value to return if the attribute is undefined.
     * @return the value of the <code>previewRecipient</code> attribute.
     */
    public String getPreviewRecipient(String defaultValue)
    {
        return (String)getInternal(previewRecipientDef, defaultValue);
    }    

    /**
     * Sets the value of the <code>previewRecipient</code> attribute.
     *
     * @param value the value of the <code>previewRecipient</code> attribute,
     *        or <code>null</code> to remove value.
     */
    public void setPreviewRecipient(String value)
    {
        try
        {
            if(value != null)
            {
                set(previewRecipientDef, value);
            }
            else
            {
                unset(previewRecipientDef);
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
	 * Checks if the value of the <code>previewRecipient</code> attribute is defined.
	 *
	 * @return <code>true</code> if the value of the <code>previewRecipient</code> attribute is defined.
	 */
    public boolean isPreviewRecipientDefined()
	{
	    return isDefined(previewRecipientDef);
	}
 
    /**
     * Returns the value of the <code>subscriptionNode</code> attribute.
     *
     * @return the value of the <code>subscriptionNode</code> attribute.
     */
    public NavigationNodeResource getSubscriptionNode()
    {
        return (NavigationNodeResource)getInternal(subscriptionNodeDef, null);
    }
    
    /**
     * Returns the value of the <code>subscriptionNode</code> attribute.
     *
     * @param defaultValue the value to return if the attribute is undefined.
     * @return the value of the <code>subscriptionNode</code> attribute.
     */
    public NavigationNodeResource getSubscriptionNode(NavigationNodeResource defaultValue)
    {
        return (NavigationNodeResource)getInternal(subscriptionNodeDef, defaultValue);
    }    

    /**
     * Sets the value of the <code>subscriptionNode</code> attribute.
     *
     * @param value the value of the <code>subscriptionNode</code> attribute,
     *        or <code>null</code> to remove value.
     */
    public void setSubscriptionNode(NavigationNodeResource value)
    {
        try
        {
            if(value != null)
            {
                set(subscriptionNodeDef, value);
            }
            else
            {
                unset(subscriptionNodeDef);
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
	 * Checks if the value of the <code>subscriptionNode</code> attribute is defined.
	 *
	 * @return <code>true</code> if the value of the <code>subscriptionNode</code> attribute is defined.
	 */
    public boolean isSubscriptionNodeDefined()
	{
	    return isDefined(subscriptionNodeDef);
	}
  
    // @custom methods ///////////////////////////////////////////////////////
}