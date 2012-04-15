package com.googlecode.sc2dm.log;

import android.util.Log;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 2:04 PM - 9/18/11
 */
public enum SC2DMLogger {
    ;

    private static final String TAG = "simple-c2dm";

    private static final boolean V_ENABLED = Log.isLoggable(TAG, Log.VERBOSE);
    private static final boolean D_ENABLED = Log.isLoggable(TAG, Log.DEBUG);
    private static final boolean I_ENABLED = Log.isLoggable(TAG, Log.INFO);
    private static final boolean W_ENABLED = Log.isLoggable(TAG, Log.WARN);
    private static final boolean E_ENABLED = Log.isLoggable(TAG, Log.ERROR);

    private SC2DMLogger() {
    }

    static String mergeMessages(String... msgs) {
        StringBuilder sb = new StringBuilder();
        for (String msg : msgs) {
            sb.append(msg);
        }

        return sb.toString();
    }

    public static void v(String msg) {
        if (V_ENABLED)
            Log.v(TAG, msg);
    }

    public static void v(String msg, Throwable tr) {
        if (V_ENABLED)
            Log.v(TAG, msg, tr);
    }

    public static void d(String msg) {
        if (D_ENABLED)
            Log.d(TAG, msg);
    }

    public static void d(String... msgs) {
        if (D_ENABLED)
            Log.d(TAG, mergeMessages(msgs));
    }

    public static void d(String msg, Throwable tr) {
        if (D_ENABLED)
            Log.d(TAG, msg, tr);
    }

    public static void i(String msg) {
        if (I_ENABLED)
            Log.i(TAG, msg);
    }

    public static void i(String... msgs) {
        if (I_ENABLED)
            Log.i(TAG, mergeMessages(msgs));
    }

    public static void i(String msg, Throwable tr) {
        if (I_ENABLED)
            Log.i(TAG, msg, tr);
    }

    public static void w(String msg) {
        if (W_ENABLED)
            Log.w(TAG, msg);
    }

    public static void w(String... msgs) {
        if (W_ENABLED)
            Log.w(TAG, mergeMessages(msgs));
    }

    public static void w(String msg, Throwable tr) {
        if (W_ENABLED)
            Log.w(TAG, msg, tr);
    }

    public static void w(Throwable tr) {
        if (W_ENABLED)
            Log.w(TAG, tr);
    }

    public static void e(String msg) {
        if (E_ENABLED)
            Log.e(TAG, msg);
    }

    public static void e(String... msgs) {
        if (E_ENABLED)
            Log.e(TAG, mergeMessages(msgs));
    }

    public static void e(String msg, Throwable tr) {
        if (E_ENABLED)
            Log.e(TAG, msg, tr);
    }

    public static void wtf(String msg) {
        Log.wtf(TAG, msg);
    }

    public static void wtf(Throwable tr) {
        Log.wtf(TAG, tr);
    }

    public static void wtf(String msg, Throwable tr) {
        Log.wtf(TAG, msg, tr);
    }

    public static void printActiveLogLevels() {
        if (V_ENABLED)
            Log.v(TAG, "Verbose is enabled");
        if (D_ENABLED)
            Log.d(TAG, "Debug is enabled");
        if (I_ENABLED)
            Log.i(TAG, "Info is enabled");
        if (W_ENABLED)
            Log.w(TAG, "Warn is enabled");
        if (E_ENABLED)
            Log.e(TAG, "Error is enabled");
    }
}
