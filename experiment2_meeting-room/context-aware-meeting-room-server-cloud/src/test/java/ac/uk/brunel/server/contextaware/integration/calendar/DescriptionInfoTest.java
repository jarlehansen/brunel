package ac.uk.brunel.server.contextaware.integration.calendar;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 29, 2010
 * Time: 10:16:28 PM
 */
public class DescriptionInfoTest {
    private final String presentationLink = "http://www.google.com";
    private final String description = "This is the actual description";
    private final String fullDescription = "<shared-doc>" + presentationLink + "</shared-doc>" + description;

    @Test
    public void testPopulateDescriptionOnly() {
        final DescriptionInfo descriptionInfo = new DescriptionInfo(description);

        assertEquals(description, descriptionInfo.getDescription());
        assertEquals("", descriptionInfo.getPresentationLink());
    }

    @Test
    public void testPopulateDescriptionAndPresentationLink() {
        final DescriptionInfo descriptionInfo = new DescriptionInfo(fullDescription);

        System.out.println(descriptionInfo);

        assertEquals(description, descriptionInfo.getDescription());
        assertEquals(presentationLink, descriptionInfo.getPresentationLink());
    }
}
