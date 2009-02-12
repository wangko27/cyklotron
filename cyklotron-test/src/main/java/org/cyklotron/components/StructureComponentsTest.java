package org.cyklotron.components;
// generated by MaxQ [ com.bitmechanic.maxq.generator.TemplateCodeGenerator ]

import org.objectledge.web.test.LedgeWebTestCase;

public class StructureComponentsTest extends LedgeWebTestCase 
{

    public void testBreadcrumbNavigation() throws Exception 
    {
        beginAt("/"); 
        assertActualView("BROWSING:/home_page");

        clickLink("components");
        assertActualView("BROWSING:/home_page/components");

        clickLink("structure");
        assertActualView("BROWSING:/home_page/components/structure");

        clickLink("breadCrumbNavigation");
        assertActualView("BROWSING:/home_page/components/structure/breadCrumbNavigation");
        
        assertLinkPresentWithText("Home", 1);
        assertLinkNotPresentWithText("Home", 2);
        assertLinkPresentWithText("Components", 1);
        assertLinkNotPresentWithText("Components", 2);
        assertLinkPresentWithText("Structure", 1);
        assertLinkNotPresentWithText("Structure", 2);
        assertLinkPresentWithText("Bread crumb navigation", 2);
        assertLinkNotPresentWithText("Bread crumb navigation", 3);
        assertLinkNotPresentWithText("Dynamic navigation");
        assertLinkNotPresentWithText("List navigation");
        assertLinkNotPresentWithText("Site map");
        assertLinkNotPresentWithText("Tree navigation");
    }

    public void testDynamicNavigation() throws Exception 
    {
        beginAt("/");
        assertActualView("BROWSING:/home_page");

        clickLink("components");
        assertActualView("BROWSING:/home_page/components");

        clickLink("structure");
        assertActualView("BROWSING:/home_page/components/structure");

        clickLink("dynamicNavigation");
        assertActualView("BROWSING:/home_page/components/structure/dynamicNavigation");
        
        assertLinkPresentWithText("Home", 1);
        assertLinkNotPresentWithText("Home", 2);
        assertLinkPresentWithText("Components", 1);
        assertLinkNotPresentWithText("Components", 2);
        assertLinkPresentWithText("Structure", 1);
        assertLinkNotPresentWithText("Structure", 2);
        assertLinkPresentWithText("Bread crumb navigation", 0);
        assertLinkNotPresentWithText("Bread crumb navigation", 1);
        assertLinkPresentWithText("Dynamic navigation", 2);
        assertLinkNotPresentWithText("Dynamic navigation", 3);
        assertLinkPresentWithText("List navigation");
        assertLinkPresentWithText("Site map");
        assertLinkPresentWithText("Tree navigation");
        assertLinkPresentWithImage("Tplus.gif");
        assertLinkPresentWithImage("Tminus.gif");
        assertEquals(1, countImagesWithSource("Lminus.gif"));
        assertEquals(13, countImagesWithSource("Tplus.gif"));
        assertEquals(1, countImagesWithSource("Tminus.gif"));
    }

    public void testListNavigation() throws Exception 
    {
        beginAt("/");
        assertActualView("BROWSING:/home_page");

        clickLink("components");
        assertActualView("BROWSING:/home_page/components");

        clickLink("structure");
        assertActualView("BROWSING:/home_page/components/structure");

        clickLink("listNavigation");
        assertActualView("BROWSING:/home_page/components/structure/listNavigation");

		int linksCounter = countLinksWithString("więcej");
		int aCounter = countString("<h3>a</h3>");
        assertEquals(64, linksCounter - aCounter);
    }

    public void testSiteMap() throws Exception 
    {
        beginAt("/");
        assertActualView("BROWSING:/home_page");

        clickLink("components");
        assertActualView("BROWSING:/home_page/components");

        clickLink("structure");
        assertActualView("BROWSING:/home_page/components/structure");

        clickLink("siteMap");
        assertActualView("BROWSING:/home_page/components/structure/siteMap");

		int aCounter = countLinksWithExactString("a");
		int liCounter = countElements("li");
        assertEquals(64, liCounter - aCounter);
		int expectedUl = 24;
		if(aCounter > 0)
		{
			expectedUl++;
		}
        assertEquals(25, countElements("ul"));
    }

    public void testTreeNavigation() throws Exception 
    {
        beginAt("/");
        assertActualView("BROWSING:/home_page");

        clickLink("components");
        assertActualView("BROWSING:/home_page/components");

        clickLink("structure");
        assertActualView("BROWSING:/home_page/components/structure");

        clickLink("treeNavigation");
        assertActualView("BROWSING:/home_page/components/structure/treeNavigation");
        assertEquals(22, countElements("li"));
        assertEquals(4, countElements("ul"));
        // ^^^ insert new recordings here (do not remove) ^^^
    }
}