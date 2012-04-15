package ac.uk.brunel.benchmark.server.persistence;

import java.util.List;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 7:05 PM - 11/21/11
 */
public interface ResultDao {
    public void persist(String email, String type, String tech, String timeUsed);
    public List<Result> getResultsForEmail(String email);
    public List<Result> getAll();
    public void deleteAll();
}
