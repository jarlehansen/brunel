package ac.uk.brunel.server.contextaware.integration.calendar;

import ac.uk.brunel.server.contextaware.properties.PropertiesReader;
import ac.uk.brunel.server.contextaware.properties.TagConstants;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 29, 2010
 * Time: 10:10:46 PM
 */
class DescriptionInfo {
    private final String fullDescription;

    private String description = "";
    private String presentationLink = "";

    public DescriptionInfo(final String fullDescription) {
        if (fullDescription == null) {
            this.fullDescription = "";
        } else {
            this.fullDescription = fullDescription;
        }

        populateDescriptionAndPresentationLink();
    }

    private void populateDescriptionAndPresentationLink() {
        final String tag = PropertiesReader.DOC_TAGS.get(TagConstants.MEETING_SHARED_DOC_TAG);

        final String startTag = "<" + tag + ">";
        final String endTag = "</" + tag + ">";
        final int startIndex = fullDescription.indexOf(startTag);
        final int endIndex = fullDescription.indexOf(endTag);

        if (startIndex > -1 && endIndex > -1) {
            presentationLink = fullDescription.substring(startIndex + startTag.length(), endIndex);
            description = fullDescription.substring(endIndex + endTag.length());
        } else {
            description = fullDescription;
        }
    }

    public String getDescription() {
        return description;
    }

    public String getPresentationLink() {
        return presentationLink;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("DescriptionInfo");
        sb.append("{fullDescription='").append(fullDescription).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", presentationLink='").append(presentationLink).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
