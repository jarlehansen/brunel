package ac.uk.brunel.cloudhomescreen.config.sources;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 7:48 PM - 1/9/11
 */
public enum MessageType {
    APPLICATION(1),
    PUSH_MESSAGE(2);

    private final int type;

    private MessageType(final int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
