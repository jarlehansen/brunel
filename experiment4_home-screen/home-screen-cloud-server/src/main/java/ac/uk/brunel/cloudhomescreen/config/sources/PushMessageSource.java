package ac.uk.brunel.cloudhomescreen.config.sources;

import java.util.Arrays;
import java.util.List;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 5:01:57 PM - Nov 20, 2010
 */
public enum PushMessageSource {
    CONTINUOUS_INTEGRATION(1, "Continuous integration", "ci");

    private static final List<PushMessageSource> sources;

    static {
        sources = Arrays.asList(PushMessageSource.values());
    }

    private final int key;
    private final String source;
    private final String shortName;

    private PushMessageSource(final int key, final String source, final String shortName) {
        this.key = key;
        this.source = source;
        this.shortName = shortName;
    }

    public int getKey() {
        return key;
    }

    public String getSource() {
        return source;
    }

    public String getShortName() {
        return shortName;
    }

    public String createSource(String... values) {
        StringBuilder builder = new StringBuilder();
        builder.append(source).append("=");

        for (int i = 0; i < values.length; i++) {
            String value = values[i];
            builder.append(value);

            if (i != (values.length - 1)) {
                builder.append(",");
            }
        }

        return builder.toString();
    }

    public static List<PushMessageSource> getAllSources() {
        return sources;
    }

    public static int getConfigurationNumber(final String conf) {
        int confId = -1;

        for (PushMessageSource configuration : getAllSources()) {
            if (conf.equals(configuration.getSource())) {
                confId = configuration.getKey();
                break;
            }
        }

        return confId;
    }
}
