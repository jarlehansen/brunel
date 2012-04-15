package com.googlecode.sc2dm.encrypt;

import com.googlecode.sc2dm.server.authentication.Authentication;
import com.googlecode.sc2dm.server.authentication.AuthenticationData;
import com.googlecode.sc2dm.server.authentication.GoogleAuthentication;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 10:41 AM - 10/7/11
 */
public class GoogleAuthTokenMain {

    public static void main(String[] args) {
        JLabel emailLbl = new JLabel("E-mail:");
        JTextField emailField = new JTextField(20);

        JLabel passwordLbl = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);

        int result = JOptionPane.showConfirmDialog(null, new Object[]{emailLbl, emailField, passwordLbl, passwordField}, "Google Authentication",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            AuthenticationData data = new AuthenticationData(emailField.getText(), String.copyValueOf(passwordField.getPassword()), "ping-servlet");
            Authentication auth = new GoogleAuthentication(data);

            JTextField token = new JTextField(40);
            String authToken = auth.getAuthToken();

            if (authToken != null && !"".equals(authToken)) {
                StringSelection selection = new StringSelection(authToken);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, selection);

                token.setText(authToken);
                JOptionPane.showConfirmDialog(null, token, "Google Authentication token (automatically added to your clipboard)", JOptionPane.CLOSED_OPTION);
            }
        }
    }
}
