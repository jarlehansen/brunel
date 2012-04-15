package com.googlecode.sc2dm.ping.servlet;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.googlecode.sc2dm.ping.servlet.datastore.UserInfoDao;
import com.googlecode.sc2dm.server.registration.PushDevice;
import com.googlecode.sc2dm.server.registration.RegIdReceiver;
import com.googlecode.sc2dm.server.sender.C2DMMessageSender;
import com.googlecode.sc2dm.server.sender.MessageData;
import com.googlecode.sc2dm.server.sender.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 5:20 PM - 10/6/11
 */
@Singleton
public class PingServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(PingServlet.class.getName());
    private static final String REQ_MANUAL_MSG = "manualMsg";

    private Provider<UserInfoDao> userInfoProvider;

    @Inject
    public PingServlet(Provider<UserInfoDao> userInfoProvider) {
        this.userInfoProvider = userInfoProvider;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserInfoDao userInfoDao = userInfoProvider.get();
        String token = userInfoDao.getAuthToken();

        PushDevice device = RegIdReceiver.createPushDevice(request);
        String manualMsg = request.getParameter(REQ_MANUAL_MSG);

        if (isAutomaticDevicePing(device, manualMsg)) {
            MessageSender messageSender = C2DMMessageSender.createMessageSender(token, userInfoDao);

            String message = getPushMessage(device, request, response);

            MessageData msg = new MessageData(message, device.getRegistrationId());
            boolean msgSent = messageSender.sendMessage(msg);

            if (msgSent)
                logger.info("Message was sent to: " + device.getEmail() + ", regId: " + device.getRegistrationId());
            else
                logger.info("Unable to send message");

            token = null;
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/MessageSender.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    private boolean isAutomaticDevicePing(PushDevice device, String manualMsg) {
        boolean automaticDevicePing = true;

        if (device.getEmail() == null || "".equals(device.getEmail()) ||
                device.getRegistrationId() == null || "".equals(device.getRegistrationId())) {
            automaticDevicePing = false;
        } else if (manualMsg != null && !"".equals(manualMsg)) {
            automaticDevicePing = false;
        }

        logger.info("Automatic device ping? " + automaticDevicePing);
        return automaticDevicePing;
    }

    private String getPushMessage(PushDevice device, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String message = request.getParameter("message");
        if (message == null || "".equals(message))
            message = "Ping!";
        else
            createMessageSentScreen(device, message, response);

        return message;
    }

    private void createMessageSentScreen(PushDevice device, String message, HttpServletResponse response) throws IOException {
        response.getWriter().println("<html><head><title>Simple-C2DM message sender</title></head><body><h1>Sending message: " +
                message + "</h1><br/><a href='" + buildBackLink(device, message) + "'><- Back</a></body></html>");
    }

    private String buildBackLink(PushDevice device, String message) {
        StringBuilder sb = new StringBuilder();
        sb.append("./?regId=").append(device.getRegistrationId());
        sb.append("&email=").append(device.getEmail());
        sb.append("&message=").append(message);
        sb.append("&").append(REQ_MANUAL_MSG).append("=true");

        return sb.toString();
    }
}
