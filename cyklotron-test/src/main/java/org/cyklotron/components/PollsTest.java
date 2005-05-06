package org.cyklotron.components;
// generated by MaxQ [ com.bitmechanic.maxq.generator.TemplateCodeGenerator ]

import org.objectledge.web.test.DOMTreeWalker;
import org.objectledge.web.test.LedgeWebTestCase;
import org.w3c.dom.Document;

public class PollsTest extends LedgeWebTestCase 
{
    public void testPools() throws Exception 
    {
        beginAt("/"); // TODO select correct link
        assertActualView("BROWSING:/home_page");

        clickLink("components");
        assertActualView("BROWSING:/home_page/components");

        clickLink("polls");
        assertActualView("BROWSING:/home_page/components/polls");

        clickLink("showPollPool");
        assertActualView("BROWSING:/home_page/components/polls/showPollPool");

        clickLinkWithText("zobacz ankiety");
        assertActualView("BROWSING:/home_page/screens/polls/pollsScreen");

        clickLinkWithText("wybierz");
        assertActualView("BROWSING:/home_page/screens/polls/pollsScreen");
        assertTextPresent("w zestawie pollPool0");
        assertTextPresent("poll0");
    }

    public void testShowPoll() throws Exception {
        beginAt("/"); // TODO select correct link
        assertActualView("BROWSING:/home_page");

        clickLink("components");
        assertActualView("BROWSING:/home_page/components");

        clickLink("polls");
        assertActualView("BROWSING:/home_page/components/polls");

        clickLink("displayPoll");
        assertActualView("BROWSING:/home_page/components/polls/displayPoll");

        clickLinkWithText("Obejrzyj wyniki");
        assertActualView("BROWSING:/home_page/components/polls/displayPoll");
        
        Document doc = getTester().getDialog().getResponse().getDOM();
        DOMTreeWalker walker = new DOMTreeWalker(doc.getDocumentElement());
        walker.findElementAfterText("Opcja 1","i");
        String result1Before = walker.getNextText(1);
        walker.findElementAfterText("Opcja 2","i");
        String result2Before = walker.getNextText(1);
        
        clickLink("displayPoll");
        assertActualView("BROWSING:/home_page/components/polls/displayPoll");

        setWorkingForm("form_component_under_test");
        setFormElement("question_0", "766");
        setFormElement("poll_instance", "component_under_test");
        setFormElement("pid", "763");
        clickLinkWithText("Zagłosuj");
        assertActualView("BROWSING:/home_page/components/polls/displayPoll");
        assertActionResult("responded_successfully");
        
        doc = getTester().getDialog().getResponse().getDOM();
        walker = new DOMTreeWalker(doc.getDocumentElement());
        walker.findElementAfterText("Opcja 1","i");
        String result1After = walker.getNextText(1);
        walker.findElementAfterText("Opcja 2","i");
        String result2After = walker.getNextText(1);
        assertEquals(result1Before, result1After);
        assertFalse(result2Before.equals(result2After));
        
        // ^^^ insert new recordings here (do not remove) ^^^
    }
}
