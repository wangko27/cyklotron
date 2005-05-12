package org.cyklotron.components;
// generated by MaxQ [ com.bitmechanic.maxq.generator.TemplateCodeGenerator ]

import org.objectledge.web.test.LedgeWebTestCase;

import com.meterware.httpunit.WebForm;

public class DocumentsTest extends LedgeWebTestCase 
{
	
    public void testPrint() throws Exception 
    {
        beginAt("/"); 
        assertActualView("BROWSING:/home_page");

        clickLink("components");
        assertActualView("BROWSING:/home_page/components");

        clickLink("documents");
        assertActualView("BROWSING:/home_page/components/documents");

        clickLink("printDocument");
        assertActualView("BROWSING:/home_page/components/documents/printDocument");

        beginAt("/app/cms/x/709?print_doc_id=685"); // TODO select correct link
        assertActualView("BROWSING:/home_page/screens/documents/printDocument");
        assertTextPresent("window.print()");

        clickLink("home_page");
        assertActualView("BROWSING:/home_page");

        clickLink("screens");
        assertActualView("BROWSING:/home_page/screens");

        clickLink("documents");
        assertActualView("BROWSING:/home_page/screens/documents");

        clickLink("printDocument");
        assertActualView("BROWSING:/home_page/screens/documents/printDocument");
        assertTextPresent("no 'print_doc_id' parameter defined");

        // ^^^ insert new recordings here (do not remove) ^^^
    }
    
    public void testDocumentView() throws Exception 
    {
        beginAt("/"); 
        assertActualView("BROWSING:/home_page");
    
        clickLink("components");
        assertActualView("BROWSING:/home_page/components");
    
        clickLink("documents");
        assertActualView("BROWSING:/home_page/components/documents");
    
        clickLink("documentView");
        assertActualView("BROWSING:/home_page/components/documents/documentView");
        assertTextPresent("<h2>Document view</h2>");
    }    
    
    
    public void testProposeDocument() throws Exception
    {
        beginAt("/"); 
        assertActualView("BROWSING:/home_page");

        clickLink("components");
        assertActualView("BROWSING:/home_page/components");

        clickLink("documents");
        assertActualView("BROWSING:/home_page/components/documents");

        clickLink("documentProposal");
        assertActualView("BROWSING:/home_page/components/documents/documentProposal");

        clickLinkWithText("Zaproponuj dokument");
		
		
		beginAt("/app/cms/x/710?parent_node_id=686");
        assertActualView("BROWSING:/home_page/screens/documents/documentProposal");
		WebForm[] forms = getDialog().getResponse().getForms();
		//setFormElement("event_start", "1115817515678");
        setFormElement("title", "a");
        setFormElement("organized_address", "f");
        setFormElement("event_place", "d");
        setFormElement("validity_end_enabled", "true");
        setFormElement("organized_www", "j");
        setFormElement("organized_by", "e");
        setFormElement("event_start_enabled", "true");
        setFormElement("validity_start_enabled", "true");
        setFormElement("content", "c");
        setFormElement("proposer_credentials", "l");
        setFormElement("organized_phone", "g");
        setFormElement("organized_email", "i");
        setFormElement("proposer_email", "m");
        setFormElement("event_end_hour", "11");
        setFormElement("abstract", "b");
        setFormElement("description", "n");
        setFormElement("source", "k");
        setFormElement("organized_fax", "h");
        setFormElement("parent", "686");
        setFormElement("event_end_enabled", "true");
        clickLinkWithText("Dodaj dokument");
        assertActualView("BROWSING:/home_page/screens/documents/documentProposal");
        
        assertActionResult("added_successfully");

        clickLink("documentProposal");
        // clickLink("documentProposal");
        assertActualView("BROWSING:/home_page/screens/documents/documentProposal");

        setFormElement("title", "");
        setFormElement("organized_address", "");
        setFormElement("event_place", "");
        setFormElement("validity_end_enabled", "true");
        setFormElement("organized_www", "");
        setFormElement("organized_by", "");
        setFormElement("event_start_enabled", "true");
        setFormElement("validity_start_enabled", "true");
        setFormElement("content", "");
        setFormElement("proposer_credentials", "");
        setFormElement("organized_phone", "");
        setFormElement("organized_email", "");
        setFormElement("proposer_email", "");
        setFormElement("abstract", "");
        setFormElement("description", "");
        setFormElement("source", "");
        setFormElement("organized_fax", "");
        setFormElement("parent", "555");
        setFormElement("event_end_enabled", "true");
        clickLinkWithText("Dodaj dokument");
        assertActualView("BROWSING:/home_page/screens/documents/documentProposal");
        assertActionResult("navi_title_empty");

        setFormElement("title", "a2");
        setFormElement("organized_address", "");
        setFormElement("event_place", "");
        setFormElement("validity_end_enabled", "true");
        setFormElement("organized_www", "");
        setFormElement("organized_by", "");
        setFormElement("event_start_enabled", "true");
        setFormElement("validity_start_enabled", "true");
        setFormElement("content", "");
        setFormElement("proposer_credentials", "");
        setFormElement("organized_phone", "");
        setFormElement("organized_email", "");
        setFormElement("proposer_email", "");
        setFormElement("event_end_hour", "11");
        setFormElement("abstract", "");
        setFormElement("description", "");
        setFormElement("source", "");
        setFormElement("organized_fax", "");
        setFormElement("parent", "555");
        setFormElement("event_end_enabled", "true");
        clickLinkWithText("Dodaj dokument");
        assertActualView("BROWSING:/home_page/screens/documents/documentProposal");
        assertActionResult("proposer_credentials_empty");


        setFormElement("title", "a2");
        setFormElement("organized_address", "");
        setFormElement("event_place", "");
        setFormElement("validity_end_enabled", "true");
        setFormElement("organized_www", "");
        setFormElement("organized_by", "");
        setFormElement("event_start_enabled", "true");
        setFormElement("validity_start_enabled", "true");
        setFormElement("content", "");
        setFormElement("proposer_credentials", "x2");
        setFormElement("organized_phone", "");
        setFormElement("organized_email", "");
        setFormElement("proposer_email", "");
        setFormElement("abstract", "");
        setFormElement("description", "");
        setFormElement("source", "");
        setFormElement("organized_fax", "");
        setFormElement("parent", "555");
        setFormElement("event_end_enabled", "true");
        clickLinkWithText("Dodaj dokument");
        assertActualView("BROWSING:/home_page/screens/documents/documentProposal");
        assertActionResult("added_successfully");

    }


    public void testRecommendDocumentComponent() throws Exception {
        beginAt("/"); 
        assertActualView("BROWSING:/home_page");

        clickLink("components");
        assertActualView("BROWSING:/home_page/components");

        clickLink("documents");
        assertActualView("BROWSING:/home_page/components/documents");

        clickLink("recommendDocument");
        assertActualView("BROWSING:/home_page/components/documents/recommendDocument");

        beginAt("/app/cms/x/711?parent_node_id=687"); // TODO select correct link
        assertActualView("BROWSING:/home_page/screens/documents/recommendDocument");

        setFormElement("to", "");
        setFormElement("content", "");
        setFormElement("from", "");
        setFormElement("second_name", "");
        setFormElement("first_name", "");
        clickLinkWithText("Wyślij");
        assertActualView("BROWSING:/home_page/screens/documents/recommendDocument");
        assertActionResult("field_empty");

        setFormElement("to", "pablo@caltha.pl");
        setFormElement("content", "abc");
        setFormElement("from", "pablo@ngo.pl");
        setFormElement("second_name", "b");
        setFormElement("first_name", "a");
        clickLinkWithText("Wyślij");
        // popup
        assertActualView("");
        assertActionResult("");

        // ^^^ insert new recordings here (do not remove) ^^^
    }
    
    public void testRecommendDocumentScreen() throws Exception {
        beginAt("/"); 
        assertActualView("BROWSING:/home_page");

        clickLink("screens");
        assertActualView("BROWSING:/home_page/screens");

        clickLink("documents");
        assertActualView("BROWSING:/home_page/screens/documents");

        clickLink("recommendDocument");
        assertActualView("BROWSING:/home_page/screens/documents/recommendDocument");

        setFormElement("to", "");
        setFormElement("content", "");
        setFormElement("from", "");
        setFormElement("second_name", "");
        setFormElement("first_name", "");
        clickLinkWithText("Wyślij");
        assertActualView("BROWSING:/home_page/screens/documents/recommendDocument");
        assertActionResult("field_empty");

        setFormElement("to", "pablo@caltha.pl");
        setFormElement("content", "xyz");
        setFormElement("from", "pablo@ngo.pl");
        setFormElement("second_name", "b");
        setFormElement("first_name", "a");
        clickLinkWithText("Wyślij");
        assertActualView("");
        assertActionResult("");

        // ^^^ insert new recordings here (do not remove) ^^^
    }

}