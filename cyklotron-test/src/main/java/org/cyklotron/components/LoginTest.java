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
		if(getDialog().hasFormParameterNamed("PASSWORD"))
		{
			assertFormElementPresent("LOGIN");
		}
		else
		{
			assertFormElementPresent("login");
		}
        assertLinkPresentWithText("Załóż konto!");
        // ^^^ insert new recordings here (do not remove) ^^^
		if(getDialog().hasFormParameterNamed("PASSWORD"))
		{
			setFormElement("PASSWORD", "Wie2feiw");
		}
		else
		{
			setFormElement("password", "Wie2feiw");
		}
		if(getDialog().hasFormParameterNamed("LOGIN"))
		{
			setFormElement("LOGIN", "root");
		}
		else
		{
			setFormElement("login", "root");
		}
        submit();
		System.out.println(getDialog().getResponseText());
        assertActualView("BROWSING:/home_page/components/security/login");
        assertFormElementNotPresent("LOGIN");
        assertFormElementNotPresent("PASSWORD");
        assertNoActionResult();

		if(getDialog().isLinkPresentWithText("wyloguj się"))
		{
			clickLinkWithText("wyloguj się",1);
		}
		else
		{
			clickLinkWithText("log out",1);
		}
        assertActualView("BROWSING:/home_page");
        assertNoActionResult();
    }
}



