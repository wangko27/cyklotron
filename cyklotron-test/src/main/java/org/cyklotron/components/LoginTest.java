package org.cyklotron.components;
// generated by MaxQ [ com.bitmechanic.maxq.generator.TemplateCodeGenerator ]

import org.objectledge.web.test.LedgeWebTestCase;

public class LoginTest extends LedgeWebTestCase {

    public void testScenario1() throws Exception {
        beginAt("/"); 
        assertActualView("BROWSING:/home_page");

        clickLink("components");
        assertActualView("BROWSING:/home_page/components");

        clickLink("security");
        assertActualView("BROWSING:/home_page/components/security");

        clickLink("login");
        assertActualView("BROWSING:/home_page/components/security/login");
        assertFormElementPresent("LOGIN");
        assertFormElementPresent("PASSWORD");
        assertLinkPresentWithText("Załóż konto!");
        // ^^^ insert new recordings here (do not remove) ^^^
    }
}
