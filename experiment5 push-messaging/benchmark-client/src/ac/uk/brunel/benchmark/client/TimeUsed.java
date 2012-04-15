package ac.uk.brunel.benchmark.client;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 9:51 PM - 11/22/11
 */
public class TimeUsed {
    private final long start;

    public TimeUsed() {
        start = System.currentTimeMillis();
    }

    public long stop() {
        return System.currentTimeMillis() - start;
    }
}
