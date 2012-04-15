package ac.uk.brunel.cloudhomescreen.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 7:18 PM - 1/17/11
 */
public enum DateStringUtil {

    ;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss,SSSS");

    public static String getDateString() {
        return sdf.format(new Date());
    }
}
