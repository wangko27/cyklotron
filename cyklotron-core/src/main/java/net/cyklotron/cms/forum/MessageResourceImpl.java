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
 
package net.cyklotron.cms.forum;

import java.util.HashMap;
import java.util.Map;

import org.objectledge.coral.BackendException;
import org.objectledge.coral.entity.EntityDoesNotExistException;
import org.objectledge.coral.schema.AttributeDefinition;
import org.objectledge.coral.schema.CoralSchema;
import org.objectledge.coral.schema.ResourceClass;
import org.objectledge.coral.session.CoralSession;
import org.objectledge.coral.store.ModificationNotPermitedException;
import org.objectledge.coral.store.Resource;
import org.objectledge.coral.store.ValueRequiredException;
import org.objectledge.database.Database;

import net.cyklotron.cms.workflow.StateResource;
import net.labeo.services.resource.Permission;
import org.jcontainer.dna.Logger;

/**
 * An implementation of <code>cms.forum.message</code> Coral resource class.
 *
 * @author Coral Maven plugin
 */
public class MessageResourceImpl
    extends ForumNodeResourceImpl
    implements MessageResource
{
    // instance variables ////////////////////////////////////////////////////

    /** The AttributeDefinition object for the <code>title</code> attribute. */
    private AttributeDefinition titleDef;

    /** The AttributeDefinition object for the <code>content</code> attribute. */
    private AttributeDefinition contentDef;

    /** The AttributeDefinition object for the <code>priority</code> attribute. */
    private AttributeDefinition priorityDef;

    /** The AttributeDefinition object for the <code>characterEncoding</code> attribute. */
    private AttributeDefinition characterEncodingDef;

    /** The AttributeDefinition object for the <code>discussion</code> attribute. */
    private AttributeDefinition discussionDef;

    /** The AttributeDefinition object for the <code>author</code> attribute. */
    private AttributeDefinition authorDef;

    /** The AttributeDefinition object for the <code>email</code> attribute. */
    private AttributeDefinition emailDef;

    /** The AttributeDefinition object for the <code>messageId</code> attribute. */
    private AttributeDefinition messageIdDef;

    /** The AttributeDefinition object for the <code>moderationCookie</code> attribute. */
    private AttributeDefinition moderationCookieDef;

    /** The AttributeDefinition object for the <code>state</code> attribute. */
    private AttributeDefinition stateDef;

    // initialization /////////////////////////////////////////////////////////

    /**
     * Creates a blank <code>cms.forum.message</code> resource wrapper.
     *
     * <p>This constructor should be used by the handler class only. Use 
     * <code>load()</code> and <code>create()</code> methods to create
     * instances of the wrapper in your application code.</p>
     *
     * @param schema the CoralSchema.
     * @param database the Database.
     * @param logger the Logger.
     */
    public MessageResourceImpl(CoralSchema schema, Database database, Logger logger)
    {
        super(schema, database, logger);
        try
        {
            ResourceClass rc = schema.getResourceClass("cms.forum.message");
            titleDef = rc.getAttribute("title");
            contentDef = rc.getAttribute("content");
            priorityDef = rc.getAttribute("priority");
            characterEncodingDef = rc.getAttribute("characterEncoding");
            discussionDef = rc.getAttribute("discussion");
            authorDef = rc.getAttribute("author");
            emailDef = rc.getAttribute("email");
            messageIdDef = rc.getAttribute("messageId");
            moderationCookieDef = rc.getAttribute("moderationCookie");
            stateDef = rc.getAttribute("state");
        }
        catch(EntityDoesNotExistException e)
        {
            throw new BackendException("incompatible schema change", e);
        }
    }

    // static methods ////////////////////////////////////////////////////////

    /**
     * Retrieves a <code>cms.forum.message</code> resource instance from the store.
     *
     * <p>This is a simple wrapper of StoreService.getResource() method plus
     * the typecast.</p>
     *
     * @param session the CoralSession
     * @param id the id of the object to be retrieved
     * @return a resource instance.
     * @throws EntityDoesNotExistException if the resource with the given id does not exist.
     */
    public static MessageResource getMessageResource(CoralSession session, long id)
        throws EntityDoesNotExistException
    {
        Resource res = session.getStore().getResource(id);
        if(!(res instanceof MessageResource))
        {
            throw new IllegalArgumentException("resource #"+id+" is "+
                                               res.getResourceClass().getName()+
                                               " not cms.forum.message");
        }
        return (MessageResource)res;
    }

    /**
     * Creates a new <code>cms.forum.message</code> resource instance.
     *
     * @param session the CoralSession
     * @param name the name of the new resource
     * @param parent the parent resource.
     * @param title the title attribute
     * @param content the content attribute
     * @param priority the priority attribute
     * @param characterEncoding the characterEncoding attribute
     * @param discussion the discussion attribute
     * @return a new MessageResource instance.
     * @throws ValueRequiredException if one of the required attribues is undefined.
     */
    public static MessageResource createMessageResource(CoralSession session, String name,
        Resource parent, String title, String content, int priority, String characterEncoding,
        DiscussionResource discussion)
        throws ValueRequiredException
    {
        try
        {
            ResourceClass rc = session.getSchema().getResourceClass("cms.forum.message");
            Map attrs = new HashMap();
            attrs.put(rc.getAttribute("title"), title);
            attrs.put(rc.getAttribute("content"), content);
            attrs.put(rc.getAttribute("priority"), new Integer(priority));
            attrs.put(rc.getAttribute("characterEncoding"), characterEncoding);
            attrs.put(rc.getAttribute("discussion"), discussion);
            Resource res = session.getStore().createResource(name, parent, rc, attrs);
            if(!(res instanceof MessageResource))
            {
                throw new BackendException("incosistent schema: created object is "+
                                           res.getClass().getName());
            }
            return (MessageResource)res;
        }
        catch(EntityDoesNotExistException e)
        {
            throw new BackendException("incompatible schema change", e);
        }
    }

    // public interface //////////////////////////////////////////////////////
 
    /**
     * Returns the value of the <code>title</code> attribute.
     *
     * @return the value of the <code>title</code> attribute.
     */
    public String getTitle()
    {
        return (String)get(titleDef);
    }
 
    /**
     * Sets the value of the <code>title</code> attribute.
     *
     * @param value the value of the <code>title</code> attribute.
     * @throws ValueRequiredException if you attempt to set a <code>null</code> 
     *         value.
     */
    public void setTitle(String value)
        throws ValueRequiredException
    {
        try
        {
            if(value != null)
            {
                set(titleDef, value);
            }
            else
            {
                throw new ValueRequiredException("attribute title "+
                                                 "is declared as REQUIRED");
            }
        }
        catch(ModificationNotPermitedException e)
        {
            throw new BackendException("incompatible schema change",e);
        }
    }
    
    /**
     * Returns the value of the <code>content</code> attribute.
     *
     * @return the value of the <code>content</code> attribute.
     */
    public String getContent()
    {
        return (String)get(contentDef);
    }
 
    /**
     * Sets the value of the <code>content</code> attribute.
     *
     * @param value the value of the <code>content</code> attribute.
     * @throws ValueRequiredException if you attempt to set a <code>null</code> 
     *         value.
     */
    public void setContent(String value)
        throws ValueRequiredException
    {
        try
        {
            if(value != null)
            {
                set(contentDef, value);
            }
            else
            {
                throw new ValueRequiredException("attribute content "+
                                                 "is declared as REQUIRED");
            }
        }
        catch(ModificationNotPermitedException e)
        {
            throw new BackendException("incompatible schema change",e);
        }
    }
    
    /**
     * Returns the value of the <code>priority</code> attribute.
     *
     * @return the value of the <code>priority</code> attribute.
     */
    public int getPriority()
    {
        if(isDefined(priorityDef))
        {
            return ((Integer)get(priorityDef)).intValue();
        }
        else
        {
            throw new BackendException("incompatible schema change");
        }
    }    

    /**
     * Sets the value of the <code>priority</code> attribute.
     *
     * @param value the value of the <code>priority</code> attribute.
     */
    public void setPriority(int value)
    {
        try
        {
            set(priorityDef, new Integer(value));
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
     * Returns the value of the <code>characterEncoding</code> attribute.
     *
     * @return the value of the <code>characterEncoding</code> attribute.
     */
    public String getCharacterEncoding()
    {
        return (String)get(characterEncodingDef);
    }
 
    /**
     * Sets the value of the <code>characterEncoding</code> attribute.
     *
     * @param value the value of the <code>characterEncoding</code> attribute.
     * @throws ValueRequiredException if you attempt to set a <code>null</code> 
     *         value.
     */
    public void setCharacterEncoding(String value)
        throws ValueRequiredException
    {
        try
        {
            if(value != null)
            {
                set(characterEncodingDef, value);
            }
            else
            {
                throw new ValueRequiredException("attribute characterEncoding "+
                                                 "is declared as REQUIRED");
            }
        }
        catch(ModificationNotPermitedException e)
        {
            throw new BackendException("incompatible schema change",e);
        }
    }
    
    /**
     * Returns the value of the <code>discussion</code> attribute.
     *
     * @return the value of the <code>discussion</code> attribute.
     */
    public DiscussionResource getDiscussion()
    {
        return (DiscussionResource)get(discussionDef);
    }
 
    /**
     * Sets the value of the <code>discussion</code> attribute.
     *
     * @param value the value of the <code>discussion</code> attribute.
     * @throws ValueRequiredException if you attempt to set a <code>null</code> 
     *         value.
     */
    public void setDiscussion(DiscussionResource value)
        throws ValueRequiredException
    {
        try
        {
            if(value != null)
            {
                set(discussionDef, value);
            }
            else
            {
                throw new ValueRequiredException("attribute discussion "+
                                                 "is declared as REQUIRED");
            }
        }
        catch(ModificationNotPermitedException e)
        {
            throw new BackendException("incompatible schema change",e);
        }
    }
    
    /**
     * Returns the value of the <code>author</code> attribute.
     *
     * @return the value of the <code>author</code> attribute.
     */
    public String getAuthor()
    {
        return (String)get(authorDef);
    }
    
    /**
     * Returns the value of the <code>author</code> attribute.
     *
     * @param defaultValue the value to return if the attribute is undefined.
     * @return the value of the <code>author</code> attribute.
     */
    public String getAuthor(String defaultValue)
    {
        if(isDefined(authorDef))
        {
            return (String)get(authorDef);
        }
        else
        {
            return defaultValue;
        }
    }    

    /**
     * Sets the value of the <code>author</code> attribute.
     *
     * @param value the value of the <code>author</code> attribute,
     *        or <code>null</code> to remove value.
     */
    public void setAuthor(String value)
    {
        try
        {
            if(value != null)
            {
                set(authorDef, value);
            }
            else
            {
                unset(authorDef);
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
	 * Checks if the value of the <code>author</code> attribute is defined.
	 *
	 * @return <code>true</code> if the value of the <code>author</code> attribute is defined.
	 */
    public boolean isAuthorDefined()
	{
	    return isDefined(authorDef);
	}
 
    /**
     * Returns the value of the <code>email</code> attribute.
     *
     * @return the value of the <code>email</code> attribute.
     */
    public String getEmail()
    {
        return (String)get(emailDef);
    }
    
    /**
     * Returns the value of the <code>email</code> attribute.
     *
     * @param defaultValue the value to return if the attribute is undefined.
     * @return the value of the <code>email</code> attribute.
     */
    public String getEmail(String defaultValue)
    {
        if(isDefined(emailDef))
        {
            return (String)get(emailDef);
        }
        else
        {
            return defaultValue;
        }
    }    

    /**
     * Sets the value of the <code>email</code> attribute.
     *
     * @param value the value of the <code>email</code> attribute,
     *        or <code>null</code> to remove value.
     */
    public void setEmail(String value)
    {
        try
        {
            if(value != null)
            {
                set(emailDef, value);
            }
            else
            {
                unset(emailDef);
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
	 * Checks if the value of the <code>email</code> attribute is defined.
	 *
	 * @return <code>true</code> if the value of the <code>email</code> attribute is defined.
	 */
    public boolean isEmailDefined()
	{
	    return isDefined(emailDef);
	}
 
    /**
     * Returns the value of the <code>messageId</code> attribute.
     *
     * @return the value of the <code>messageId</code> attribute.
     */
    public String getMessageId()
    {
        return (String)get(messageIdDef);
    }
    
    /**
     * Returns the value of the <code>messageId</code> attribute.
     *
     * @param defaultValue the value to return if the attribute is undefined.
     * @return the value of the <code>messageId</code> attribute.
     */
    public String getMessageId(String defaultValue)
    {
        if(isDefined(messageIdDef))
        {
            return (String)get(messageIdDef);
        }
        else
        {
            return defaultValue;
        }
    }    

    /**
     * Sets the value of the <code>messageId</code> attribute.
     *
     * @param value the value of the <code>messageId</code> attribute,
     *        or <code>null</code> to remove value.
     */
    public void setMessageId(String value)
    {
        try
        {
            if(value != null)
            {
                set(messageIdDef, value);
            }
            else
            {
                unset(messageIdDef);
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
	 * Checks if the value of the <code>messageId</code> attribute is defined.
	 *
	 * @return <code>true</code> if the value of the <code>messageId</code> attribute is defined.
	 */
    public boolean isMessageIdDefined()
	{
	    return isDefined(messageIdDef);
	}
 
    /**
     * Returns the value of the <code>moderationCookie</code> attribute.
     *
     * @return the value of the <code>moderationCookie</code> attribute.
     */
    public String getModerationCookie()
    {
        return (String)get(moderationCookieDef);
    }
    
    /**
     * Returns the value of the <code>moderationCookie</code> attribute.
     *
     * @param defaultValue the value to return if the attribute is undefined.
     * @return the value of the <code>moderationCookie</code> attribute.
     */
    public String getModerationCookie(String defaultValue)
    {
        if(isDefined(moderationCookieDef))
        {
            return (String)get(moderationCookieDef);
        }
        else
        {
            return defaultValue;
        }
    }    

    /**
     * Sets the value of the <code>moderationCookie</code> attribute.
     *
     * @param value the value of the <code>moderationCookie</code> attribute,
     *        or <code>null</code> to remove value.
     */
    public void setModerationCookie(String value)
    {
        try
        {
            if(value != null)
            {
                set(moderationCookieDef, value);
            }
            else
            {
                unset(moderationCookieDef);
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
	 * Checks if the value of the <code>moderationCookie</code> attribute is defined.
	 *
	 * @return <code>true</code> if the value of the <code>moderationCookie</code> attribute is defined.
	 */
    public boolean isModerationCookieDefined()
	{
	    return isDefined(moderationCookieDef);
	}
 
    /**
     * Returns the value of the <code>state</code> attribute.
     *
     * @return the value of the <code>state</code> attribute.
     */
    public StateResource getState()
    {
        return (StateResource)get(stateDef);
    }
    
    /**
     * Returns the value of the <code>state</code> attribute.
     *
     * @param defaultValue the value to return if the attribute is undefined.
     * @return the value of the <code>state</code> attribute.
     */
    public StateResource getState(StateResource defaultValue)
    {
        if(isDefined(stateDef))
        {
            return (StateResource)get(stateDef);
        }
        else
        {
            return defaultValue;
        }
    }    

    /**
     * Sets the value of the <code>state</code> attribute.
     *
     * @param value the value of the <code>state</code> attribute,
     *        or <code>null</code> to remove value.
     */
    public void setState(StateResource value)
    {
        try
        {
            if(value != null)
            {
                set(stateDef, value);
            }
            else
            {
                unset(stateDef);
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
	 * Checks if the value of the <code>state</code> attribute is defined.
	 *
	 * @return <code>true</code> if the value of the <code>state</code> attribute is defined.
	 */
    public boolean isStateDefined()
	{
	    return isDefined(stateDef);
	}
  
    // @custom methods ///////////////////////////////////////////////////////
    // @import net.labeo.services.resource.Permission

    /**
     * Checks if this resource can be viewed at the given time.
     */
    public boolean isValid(Date time)
    {
        return true;
    }
    
    /** the message view permission */
    private Permission viewPermission;
    
	/** the message moderate permission */
	private Permission moderatePermission;
    
    
    /**
     * Checks if a given subject can view this resource.
     */
    public boolean canView(Subject subject)
    {
    	// all permission are granted on discussion so for better performace 
    	// we won't provoke build permission container for every message. 
    	if(!getDiscussion().canView(subject))
    	{
    		return false;
    	}
    	if(getState().getName().equals("visible"))
		{
			return true;
		}
		return false;		
    }
    
    /**
     * Checks if the specified subject can view this resource at the given time.
     */
    public boolean canView(Subject subject, Date time)
    {
        return canView(subject);
    }

    // @extends cms.forum.node
    // @order title, content, priority, characterEncoding, discussion


    // indexable resource methods //////////////////////////////////////////////////////////////////
    
    public String getIndexAbbreviation()
    {
       
        String abbrev = getContent();
        if(abbrev != null)
        {
            int endindex = abbrev.length() > 255 ? 255 : abbrev.length();
            return abbrev.substring(0,endindex);
        }
        else
        {
            return null;
        }
    }
    
    public String getIndexContent()
    {
        String content = getContent();
        if(content != null)
        {
            // TODO: Add HTML parsing for future formatting
            return content;
        }
        else
        {
            return null;
        }
    }
    
    public String getIndexTitle()
    {
        return getTitle();
    }
    
    public Object getFieldValue(String fieldName)
    {
        return null;
    }
    
}
