package ac.uk.brunel.benchmark.server.persistence;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 7:05 PM - 11/21/11
 */
public class ResultDaoImpl implements ResultDao {
    private static final Logger logger = LoggerFactory.getLogger(ResultDaoImpl.class);
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM-yyyy,HH:mm:ss:SSSS");

    static {
        ObjectifyService.register(Result.class);
    }

    public ResultDaoImpl() {
    }

    public void persist(String email, String type, String tech, String timeUsed) {
        Objectify objectify = ObjectifyService.begin();
        objectify.put(new Result(formatter.format(new Date()), email, type, tech, Long.parseLong(timeUsed)));
    }

    public List<Result> getResultsForEmail(String email) {
        Objectify objectify = ObjectifyService.begin();
        List<Result> results = new ArrayList<Result>();

        try {
            results = objectify.query(Result.class).filter("email", email).list();
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("Rows retrieved, {} = {}", email, results.size());
        return results;
    }

    public List<Result> getAll() {
        Objectify objectify = ObjectifyService.begin();
        List<Key<Result>> resultKeys = objectify.query(Result.class).listKeys();

        List<Result> results = new ArrayList<Result>();

        for (Key<Result> resultKey : resultKeys) {
            Result result = objectify.get(Result.class, resultKey.getName());
            results.add(result);
        }

        return results;
    }

    public void deleteAll() {
        Objectify objectify = ObjectifyService.begin();
        List<Key<Result>> resultKeys = objectify.query(Result.class).listKeys();

        objectify.delete(resultKeys);
    }
}
