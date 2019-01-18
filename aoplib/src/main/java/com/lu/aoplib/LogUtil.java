package com.lu.aoplib;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 */

public class LogUtil {
    private static String className;
    private static String methodName;
    private static int lineNumber;
    private static final int JSON_INDENT = 4;

    /**
     * 只是在控制台打印日志，不会保存到文件
     * d级别的日志有些手机在logcat里面显示不出来，
     * 故打印的日志改成i级别
     */
    public static void d(String message) {
        getMethodNames(new Throwable().getStackTrace(),1);
        Log.i(className, createLog(message));
    }
    /**
     * 只是在控制台打印日志，不会保存到文件
     * d级别的日志有些手机在logcat里面显示不出来，
     * 故打印的日志改成i级别
     */
    public static void d2(String message) {
        getMethodNames(new Throwable().getStackTrace(),2);
        Log.i(className, createLog(message));
    }
    /**
     * 只是在控制台打印日志，不会保存到文件
     * d级别的日志有些手机在logcat里面显示不出来，
     * 故打印的日志改成i级别
     */
    public static void w(String message) {
        getMethodNames(new Throwable().getStackTrace(),1);
        Log.w(className, createLog(message));
    }
    public static void d(Object o) {
        String message=null;
       if (o==null){
           message="message is null";
       }else{
           message=o.toString();
       }
        getMethodNames(new Throwable().getStackTrace(),1);
        Log.i(className, createLog(message));
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
        buffer.append(printIfJson(log));
        return buffer.toString();
    }
    public static String printIfJson(String msg) {

        String message;
        StringBuffer stringBuffer=new StringBuffer();
        try {
            int index=-1;
            if(msg.contains("{")){
                index=msg.indexOf("{");
            }else if (msg.contains("[")){
                index=msg.indexOf("{");
            }

            if (index!=-1){
                stringBuffer.append(msg.substring(0,index));
                msg=msg.substring(index);
            }

            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(JSON_INDENT);
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(JSON_INDENT);
            } else {
                message = msg;
            }
        } catch (Exception e) {
            message = msg;
        }
        stringBuffer.append(message);
        return stringBuffer.toString();
    }
    private static void getMethodNames(StackTraceElement[] sElements, int index) {
        className = sElements[index].getFileName();
        methodName = sElements[index].getMethodName();
        lineNumber = sElements[index].getLineNumber();
    }
    /**
     * 既打印在控制台，又保存到文件
     */
    public static void p(String tag, String text){
        Log.i(tag, text);
    }

    /**
     * 既打印在控制台，又保存到文件
     */
    public static void e(String tag, String text){
        Log.e(tag, text);
    }

}
