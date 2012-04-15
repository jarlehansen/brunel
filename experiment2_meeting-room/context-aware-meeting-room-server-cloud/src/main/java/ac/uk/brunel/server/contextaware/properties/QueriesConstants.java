package ac.uk.brunel.server.contextaware.properties;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 25, 2010
 * Time: 10:28:29 PM
 */
public enum QueriesConstants {
    ;

    public static final String USERDAO_SELECTALL = "userdao.selectall";
    public static final String USERDAO_SELECTUSERALIAS = "userdao.selectuseralias";
    public static final String USERDAO_DELETEUSER = "userdao.deleteuser";

    public static final String MEETINGDAO_TODAY_SELECTALL = "meetingdao.today.selectall";
    public static final String MEETINGDAO_PRESENTERMEETINGS = "meetingdao.presentermeetings";
    public static final String MEETINGDAO_DELETEALLMEETINGS = "meetingdao.deleteallmeetings";

    public static final String PRESENTATIONDAO_SELECTALL = "presentationdao.selecall";
    public static final String PRESENTATIONDAO_DELETEALLMEETINGNOTES = "presentationdao.deleteallmeetingnotes";
}
