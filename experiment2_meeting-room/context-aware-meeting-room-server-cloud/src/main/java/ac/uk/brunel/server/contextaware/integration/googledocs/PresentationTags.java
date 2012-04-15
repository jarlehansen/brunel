package ac.uk.brunel.server.contextaware.integration.googledocs;

import ac.uk.brunel.server.contextaware.properties.PropertiesReader;
import ac.uk.brunel.server.contextaware.properties.TagConstants;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 30, 2010
 * Time: 8:24:26 PM
 */
public enum PresentationTags {
    ;

    private static final String TAG = PropertiesReader.DOC_TAGS.get(TagConstants.PRESENTATION_SHARED_NOTES_TAG);
    static final String START_TAG = "&lt;" + TAG + " id=";
    static final String END_TAG = "&lt;\\/" + TAG + "&gt;";

    static final String NOTE_REGEX = PropertiesReader.DOC_TAGS.get(TagConstants.PRESENTATION_SHARED_NOTES_REGEX);
    static final String ID_REGEX = PropertiesReader.DOC_TAGS.get(TagConstants.PRESENTATION_SHARED_NOTES_ID_REGEX);
}
