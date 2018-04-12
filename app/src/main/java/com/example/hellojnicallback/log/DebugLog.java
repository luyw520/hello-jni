package com.example.hellojnicallback.log;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DebugLog {

    static String className;
    static String methodName;
    static int lineNumber;

    private DebugLog() {

    }

    public static boolean isDebuggable() {
        return true;
    }

    private static String createLog(String log) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("(");
        buffer.append(className);
        buffer.append(":");
        buffer.append(lineNumber);
        buffer.append(")");
        buffer.append("#");
        buffer.append(methodName);
        buffer.append("]");

        buffer.append(printJson(log));

        return buffer.toString();
    }
    public static void printLine(String tag, boolean isTop) {
        if (isTop) {
            Log.d(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════");
        } else {
            Log.d(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
        }
    }
    public static String printJson(String msg) {

        String message;
        boolean isJson=false;
        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(KLog.JSON_INDENT);
                isJson=true;
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(KLog.JSON_INDENT);
                isJson=true;
            } else {
                message = msg;
                isJson=false;
            }
        } catch (JSONException e) {
            message = msg;
            isJson=false;
        }

//        message = headString + KLog.LINE_SEPARATOR + message;
//        String[] lines = message.split(KLog.LINE_SEPARATOR);
//        for (String line : lines) {
//            Log.d(tag, "║ " + line);
//        }

//        KLogUtil.printLine(tag, true);
//        message = headString + KLog.LINE_SEPARATOR + message;
//        String[] lines = message.split(KLog.LINE_SEPARATOR);
//        for (String line : lines) {
//            Log.d(tag, "║ " + line);
//        }
//        KLogUtil.printLine(tag, false);
        return message;
    }
    private static void getMethodNames(StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }

    public static void e(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.d(className, createLog(message));
    }

    public static void i(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.i(className, createLog(message));
    }

    public static void d(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.i(className, createLog(message));
    }

    public static void v(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.v(className, createLog(message));
    }

    public static void w(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.w(className, createLog(message));
    }

    public static void wtf(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.wtf(className, createLog(message));
    }

}