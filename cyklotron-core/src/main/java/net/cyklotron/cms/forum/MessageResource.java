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

import org.objectledge.coral.security.Subject;
import org.objectledge.coral.session.CoralSession;
import org.objectledge.coral.store.Resource;
import org.objectledge.coral.store.ValueRequiredException;

import net.cyklotron.cms.search.IndexableResource;
import net.cyklotron.cms.workflow.StatefulResource;

/**
 * Defines the accessor methods of <code>cms.forum.message</code> Coral resource class.
 *
 * @author Coral Maven plugin
 */
public interface MessageResource
    extends Resource, ForumNodeResource, IndexableResource, StatefulResource
{
    // constants /////////////////////////////////////////////////////////////

    /** The name of the Coral resource class. */    
    public static final String CLASS_NAME = "cms.forum.message";

    // public interface //////////////////////////////////////////////////////
 
    /**
     * Returns the value of the <code>author</code> attribute.
     *
     * @return the value of the the <code>author</code> attribute.
     */
    public String getAuthor();
    
    /**
     * Returns the value of the <code>author</code> attribute.
     *
     * @param defaultValue the value to return if the attribute is undefined.
     * @return the value of the <code>author</code> attribute.
     */
    public String getAuthor(String defaultValue);

    /**
     * Sets the value of the <code>author</code> attribute.
     *
     * @param value the value of the <code>author</code> attribute,
     *        or <code>null</code> to remove value.
     */
    public void setAuthor(String value);   
   
	/**
	 * Checks if the value of the <code>author</code> attribute is defined.
	 *
	 * @return <code>true</code> if the value of the <code>author</code> attribute is defined.
	 */
    public boolean isAuthorDefined();
 
    /**
     * Returns the value of the <code>characterEncoding</code> attribute.
     *
     * @return the value of the the <code>characterEncoding</code> attribute.
     */
    public String getCharacterEncoding();
 
    /**
     * Sets the value of the <code>characterEncoding</code> attribute.
     *
     * @param value the value of the <code>characterEncoding</code> attribute.
     * @throws ValueRequiredException if you attempt to set a <code>null</code> 
     *         value.
     */
    public void setCharacterEncoding(String value)
        throws ValueRequiredException;
    
    /**
     * Returns the value of the <code>content</code> attribute.
     *
     * @return the value of the the <code>content</code> attribute.
     */
    public String getContent();
 
    /**
     * Sets the value of the <code>content</code> attribute.
     *
     * @param value the value of the <code>content</code> attribute.
     * @throws ValueRequiredException if you attempt to set a <code>null</code> 
     *         value.
     */
    public void setContent(String value)
        throws ValueRequiredException;
    
    /**
     * Returns the value of the <code>discussion</code> attribute.
     *
     * @return the value of the the <code>discussion</code> attribute.
     */
    public DiscussionResource getDiscussion();
 
    /**
     * Sets the value of the <code>discussion</code> attribute.
     *
     * @param value the value of the <code>discussion</code> attribute.
     * @throws ValueRequiredException if you attempt to set a <code>null</code> 
     *         value.
     */
    public void setDiscussion(DiscussionResource value)
        throws ValueRequiredException;
    
    /**
     * Returns the value of the <code>email</code> attribute.
     *
     * @return the value of the the <code>email</code> attribute.
     */
    public String getEmail();
    
    /**
     * Returns the value of the <code>email</code> attribute.
     *
     * @param defaultValue the value to return if the attribute is undefined.
     * @return the value of the <code>email</code> attribute.
     */
    public String getEmail(String defaultValue);

    /**
     * Sets the value of the <code>email</code> attribute.
     *
     * @param value the value of the <code>email</code> attribute,
     *        or <code>null</code> to remove value.
     */
    public void setEmail(String value);   
   
	/**
	 * Checks if the value of the <code>email</code> attribute is defined.
	 *
	 * @return <code>true</code> if the value of the <code>email</code> attribute is defined.
	 */
    public boolean isEmailDefined();
 
    /**
     * Returns the value of the <code>messageId</code> attribute.
     *
     * @return the value of the the <code>messageId</code> attribute.
     */
    public String getMessageId();
    
    /**
     * Returns the value of the <code>messageId</code> attribute.
     *
     * @param defaultValue the value to return if the attribute is undefined.
     * @return the value of the <code>messageId</code> attribute.
     */
    public String getMessageId(String defaultValue);

    /**
     * Sets the value of the <code>messageId</code> attribute.
     *
     * @param value the value of the <code>messageId</code> attribute,
     *        or <code>null</code> to remove value.
     */
    public void setMessageId(String value);   
   
	/**
	 * Checks if the value of the <code>messageId</code> attribute is defined.
	 *
	 * @return <code>true</code> if the value of the <code>messageId</code> attribute is defined.
	 */
    public boolean isMessageIdDefined();
 
    /**
     * Returns the value of the <code>moderationCookie</code> attribute.
     *
     * @return the value of the the <code>moderationCookie</code> attribute.
     */
    public String getModerationCookie();
    
    /**
     * Returns the value of the <code>moderationCookie</code> attribute.
     *
     * @param defaultValue the value to return if the attribute is undefined.
     * @return the value of the <code>moderationCookie</code> attribute.
     */
    public String getModerationCookie(String defaultValue);

    /**
     * Sets the value of the <code>moderationCookie</code> attribute.
     *
     * @param value the value of the <code>moderationCookie</code> attribute,
     *        or <code>null</code> to remove value.
     */
    public void setModerationCookie(String value);   
   
	/**
	 * Checks if the value of the <code>moderationCookie</code> attribute is defined.
	 *
	 * @return <code>true</code> if the value of the <code>moderationCookie</code> attribute is defined.
	 */
    public boolean isModerationCookieDefined();
	
    /**
     * Returns the value of the <code>priority</code> attribute.
     *
     * @return the value of the the <code>priority</code> attribute.
     */
    public int getPriority();

    /**
     * Sets the value of the <code>priority</code> attribute.
     *
     * @param value the value of the <code>priority</code> attribute.
     */
    public void setPriority(int value);
    
    /**
     * Returns the value of the <code>remoteAddr</code> attribute.
     *
     * @return the value of the the <code>remoteAddr</code> attribute.
     */
    public String getRemoteAddr();
    
    /**
     * Returns the value of the <code>remoteAddr</code> attribute.
     *
     * @param defaultValue the value to return if the attribute is undefined.
     * @return the value of the <code>remoteAddr</code> attribute.
     */
    public String getRemoteAddr(String defaultValue);

    /**
     * Sets the value of the <code>remoteAddr</code> attribute.
     *
     * @param value the value of the <code>remoteAddr</code> attribute,
     *        or <code>null</code> to remove value.
     */
    public void setRemoteAddr(String value);   
   
	/**
	 * Checks if the value of the <code>remoteAddr</code> attribute is defined.
	 *
	 * @return <code>true</code> if the value of the <code>remoteAddr</code> attribute is defined.
	 */
    public boolean isRemoteAddrDefined();
	
    /**
     * Returns the value of the <code>sticky</code> attribute.
     *
     * @return the value of the the <code>sticky</code> attribute.
     * @throws IllegalStateException if the value of the attribute is 
     *         undefined.
     */
    public boolean getSticky()
		throws IllegalStateException;

	/**
     * Returns the value of the <code>sticky</code> attribute.
     *
     * @param defaultValue the value to return if the attribute is undefined.
     * @return the value of the <code>sticky</code> attribute.
     */
    public boolean getSticky(boolean defaultValue);

    /**
     * Sets the value of the <code>sticky</code> attribute.
     *
     * @param value the value of the <code>sticky</code> attribute.
     */
    public void setSticky(boolean value);

	/**
     * Removes the value of the <code>sticky</code> attribute.
     */
    public void unsetSticky();
   
	/**
	 * Checks if the value of the <code>sticky</code> attribute is defined.
	 *
	 * @return <code>true</code> if the value of the <code>sticky</code> attribute is defined.
	 */
    public boolean isStickyDefined();
 
    /**
     * Returns the value of the <code>title</code> attribute.
     *
     * @return the value of the the <code>title</code> attribute.
     */
    public String getTitle();
 
    /**
     * Sets the value of the <code>title</code> attribute.
     *
     * @param value the value of the <code>title</code> attribute.
     * @throws ValueRequiredException if you attempt to set a <code>null</code> 
     *         value.
     */
    public void setTitle(String value)
        throws ValueRequiredException;
     
    // @custom methods ///////////////////////////////////////////////////////
    // @import org.objectledge.coral.session.CoralSession
    // @import org.objectledge.coral.security.Subject

    /**
     * Returns the value of the <code>content</code> attribute.
     *
     * @return the value of the <code>content</code> attribute.
     */
    public String getPublicContent();

}
