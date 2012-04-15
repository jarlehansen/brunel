package ac.uk.brunel.server.contextaware.transport.servlet;

import ac.uk.brunel.server.contextaware.service.MeetingService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Jan 21, 2010
 * Time: 11:05:37 PM
 */
@Singleton
public class MeetingScheduleRefresher extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(MeetingScheduleRefresher.class);
    private final MeetingService meetingService;

    @Inject
    public MeetingScheduleRefresher(final MeetingService meetingService) {
        this.meetingService = meetingService;

        // Populate database with meetings when the servlet is initialized
        this.meetingService.refreshMeetingList();
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) {
        refreshMeetingSchedule(request, response);
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) {
        refreshMeetingSchedule(request, response);
    }

    private void refreshMeetingSchedule(final HttpServletRequest request, final HttpServletResponse response) {
        PrintWriter writer = null;

        try {
            meetingService.refreshMeetingList();

            writer = response.getWriter();
            if (writer != null) {
                writer.print("<html><head><title>Meeting list updated</title></head><body><h2>Meeting list updated</h2>" +
                        "<a href=\"javascript:history.go(-1)\">Go Back</a></body></html>");
            }
        } catch (IOException io) {
            if (logger.isErrorEnabled()) {
                logger.error("IOException thrown when trying to call response.getWriter()", io);
            }
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
