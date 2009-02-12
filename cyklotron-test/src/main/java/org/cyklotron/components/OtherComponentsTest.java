package org.cyklotron.components;
// generated by MaxQ [ com.bitmechanic.maxq.generator.TemplateCodeGenerator ]

import org.objectledge.web.test.LedgeWebTestCase;

public class OtherComponentsTest extends LedgeWebTestCase 
{
    public void testScenario1() throws Exception 
    {
        beginAt("/"); 
        assertActualView("BROWSING:/home_page");

        clickLink("components");
        assertActualView("BROWSING:/home_page/components");

        clickLink("otherComponents");
        assertActualView("BROWSING:/home_page/components/otherComponents");

        clickLink("emptyComponent");
        assertActualView("BROWSING:/home_page/components/otherComponents/emptyComponent");

        assertTextPresent("here goes empty");
        
        //                getDialog().getResponseText().
        
        clickLink("otherComponents");
        assertActualView("BROWSING:/home_page/components/otherComponents");

        clickLink("applicationScreen");
        assertActualView("BROWSING:/home_page/components/otherComponents/applicationScreen");
        assertTextPresent("To jest ekran domyslny");

        // ^^^ insert new recordings here (do not remove) ^^^
    }
}