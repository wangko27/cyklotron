package org.cyklotron.components;
// generated by MaxQ [ com.bitmechanic.maxq.generator.TemplateCodeGenerator ]

import org.objectledge.web.test.LedgeWebTestCase;

public class LinkCollectionTest extends LedgeWebTestCase {

    public void testScenario1() throws Exception {
        beginAt("/");
        assertActualView("BROWSING:/home_page");

        clickLink("components");
        assertActualView("BROWSING:/home_page/components");

        clickLink("links");
        assertActualView("BROWSING:/home_page/components/links");

        clickLink("linkCollection");
        assertActualView("BROWSING:/home_page/components/links/linkCollection");
        assertLinkPresentWithText("ledge_link");
        assertLinkPresentWithText("caltha.pl");
        // ^^^ insert new recordings here (do not remove) ^^^
    }
    
    public void testRecommnedLink() throws Exception {
        beginAt("/"); 
        assertActualView("BROWSING:/home_page");

        clickLink("components");
        assertActualView("BROWSING:/home_page/components");

        clickLink("links");
        assertActualView("BROWSING:/home_page/components/links");

        clickLink("recommendLink");
        assertActualView("BROWSING:/home_page/components/links/recommendLink");

        setWorkingForm("propose_link");
        setFormElement("title", "abc");
        setFormElement("ext_target", "a.b.c");
        setFormElement("src_type", "external");
        setFormElement("description", "xyz");
        setFormElement("end_time_enabled", "true");
        clickLinkWithText("Zaproponuj");
        assertActualView("BROWSING:/home_page/components/links/recommendLink");
        assertActionResult("added_successfully");

        clickLinkWithText("Sprobuj ponownie");
        assertActualView("BROWSING:/home_page/components/links/recommendLink");

        setWorkingForm("propose_link");
        setFormElement("title", "");
        setFormElement("ext_target", "");
        setFormElement("src_type", "external");
        setFormElement("description", "");
        setFormElement("end_time_enabled", "true");
        clickLinkWithText("Zaproponuj");
        assertActualView("BROWSING:/home_page/components/links/recommendLink");
        assertActionResult("invalid_title");

        // ^^^ insert new recordings here (do not remove) ^^^
    }
}