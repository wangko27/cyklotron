package net.cyklotron.cms.documents;

import org.dom4j.Document;

/** DocumentService is used to operate on CMS documents.
 *
 * @author <a href="mailto:zwierzem@ngo.pl">Damian Gajda</a>
 * @version $Id: HTMLService.java,v 1.3 2005-01-20 10:59:17 pablo Exp $
 */
public interface HTMLService
{
	public static final String SERVICE_NAME = "html";

	public static final String LOGGING_FACILITY = "html";

	public String encodeHTML(String html, String encodingName)
        throws Exception;

	public String htmlToText(String html)
	throws HTMLException;

    public Document parseHTML(String html)
	throws HTMLException;
    
    public String serializeHTML(Document dom4jDoc)
	throws HTMLException;
}
