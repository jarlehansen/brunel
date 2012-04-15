package ac.uk.brunel.cloudhomescreen.config.sources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 7:05:02 PM - Nov 23, 2010
 */
public enum ApplicationSources {
    CALENDAR(1, "calendar"),
    EMAIL(2, "email"),
    SETTINGS(3, "settings"),
    CAMERA(4, "camera"),
    BROWSER(5, "browser"),
    CONTACTS(6, "contacts"),
    CALC(7, "calculator"),
    MAPS(8, "maps");

    private static final List<ApplicationSources> sources;

    static {
        sources = Arrays.asList(ApplicationSources.values());
    }

    private final int key;
    private final String source;


    private ApplicationSources(final int key, final String source) {
        this.key = key;
        this.source = source;
    }

    public int getKey() {
        return key;
    }

    public String getSource() {
        return source;
    }

    public static List<ApplicationSources> getAllSources() {
        return sources;
    }

    public static int getApplicationNumber(final String app) {
        int appId = -1;

        for(ApplicationSources application : getAllSources()) {
            if(app.equals(application.getSource())) {
                appId = application.getKey();
                break;
            }
        }

        return appId;
    }
}
