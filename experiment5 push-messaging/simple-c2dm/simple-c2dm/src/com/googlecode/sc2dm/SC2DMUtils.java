package com.googlecode.sc2dm;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 2:30 PM - 11/18/11
 */
public enum SC2DMUtils {
    ;

    private static final String NOTIFICATION_MSG = "notificationMsg";

    public static List<String> getRegisteredGoogleAccounts(Context context) {
        List<String> googleAccounts = new ArrayList<String>();
        Account[] accounts = AccountManager.get(context).getAccounts();

        for (Account account : accounts) {
            if (account.type.equals("com.google"))
                googleAccounts.add(account.name);
        }

        return googleAccounts;
    }

    public static void createNotification(Context context, int icon, String tickerText, String message, Class<?> intentClass) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context.getApplicationContext(), intentClass);
        notificationIntent.putExtra(NOTIFICATION_MSG, message);
        notificationIntent.setAction(intentClass.getSimpleName() + System.currentTimeMillis());

        PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, notificationIntent, 0);

        Notification notification = new Notification(icon, tickerText, System.currentTimeMillis());
        notification.setLatestEventInfo(context.getApplicationContext(), message, message, pendingIntent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, notification);
    }

    public static String getNotificationMessage(Intent intent) {
        String message = intent.getStringExtra(NOTIFICATION_MSG);

        return (message == null) ? "" : message;
    }
}
