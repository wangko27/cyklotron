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
 
package net.cyklotron.cms.docimport;

import java.util.Date;

import org.objectledge.coral.store.Resource;
import org.objectledge.coral.store.ValueRequiredException;

import net.cyklotron.cms.CmsNodeResource;

/**
 * Defines the accessor methods of <code>docimport.trace</code> Coral resource class.
 *
 * @author Coral Maven plugin
 */
public interface ImportTraceResource
    extends Resource, CmsNodeResource
{
    // constants /////////////////////////////////////////////////////////////

    /** The name of the Coral resource class. */    
    public static final String CLASS_NAME = "docimport.trace";

    // public interface //////////////////////////////////////////////////////
 
    /**
     * Returns the value of the <code>navigationNode</code> attribute.
     *
     * @return the value of the the <code>navigationNode</code> attribute.
     */
    public Resource getNavigationNode();
 
    /**
     * Sets the value of the <code>navigationNode</code> attribute.
     *
     * @param value the value of the <code>navigationNode</code> attribute.
     * @throws ValueRequiredException if you attempt to set a <code>null</code> 
     *         value.
     */
    public void setNavigationNode(Resource value)
        throws ValueRequiredException;
    
    /**
     * Returns the value of the <code>originalURL</code> attribute.
     *
     * @return the value of the the <code>originalURL</code> attribute.
     */
    public String getOriginalURL();
 
    /**
     * Sets the value of the <code>originalURL</code> attribute.
     *
     * @param value the value of the <code>originalURL</code> attribute.
     * @throws ValueRequiredException if you attempt to set a <code>null</code> 
     *         value.
     */
    public void setOriginalURL(String value)
        throws ValueRequiredException;
    
    /**
     * Returns the value of the <code>sourceModificationTime</code> attribute.
     *
     * @return the value of the the <code>sourceModificationTime</code> attribute.
     */
    public Date getSourceModificationTime();
 
    /**
     * Sets the value of the <code>sourceModificationTime</code> attribute.
     *
     * @param value the value of the <code>sourceModificationTime</code> attribute.
     * @throws ValueRequiredException if you attempt to set a <code>null</code> 
     *         value.
     */
    public void setSourceModificationTime(Date value)
        throws ValueRequiredException;
    
    /**
     * Returns the value of the <code>targetUpdateTime</code> attribute.
     *
     * @return the value of the the <code>targetUpdateTime</code> attribute.
     */
    public Date getTargetUpdateTime();
 
    /**
     * Sets the value of the <code>targetUpdateTime</code> attribute.
     *
     * @param value the value of the <code>targetUpdateTime</code> attribute.
     * @throws ValueRequiredException if you attempt to set a <code>null</code> 
     *         value.
     */
    public void setTargetUpdateTime(Date value)
        throws ValueRequiredException;
     
    // @custom methods ///////////////////////////////////////////////////////
}
