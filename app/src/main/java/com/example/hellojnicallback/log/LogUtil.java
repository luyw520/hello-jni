package com.example.hellojnicallback.log;

import android.util.Log;

/**
 * Created by Administrator on 2018/4/12 0012.
 */

public class LogUtil {

    private static final int STACK_TRACE_INDEX_5 = 5;
    private static final String SUFFIX = ".java";
    public static final String NULL_TIPS = "Log with null object";
    private static String[] wrapperContent(int stackTraceIndex, String tagStr, Object... objects) {

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        StackTraceElement targetElement = stackTrace[stackTraceIndex];
        String className = targetElement.getClassName();
        String[] classNameInfo = className.split("\\.");
        if (classNameInfo.length > 0) {
            className = classNameInfo[classNameInfo.length - 1] + SUFFIX;
        }

        if (className.contains("$")) {
            className = className.split("\\$")[0] + SUFFIX;
        }

        String methodName = targetElement.getMethodName();
        int lineNumber = targetElement.getLineNumber();

        if (lineNumber < 0) {
            lineNumber = 0;
        }

        String tag = (tagStr == null ? className : tagStr);

//        if (mIsGlobalTagEmpty && TextUtils.isEmpty(tag)) {
//            tag = TAG_DEFAULT;
//        } else if (!mIsGlobalTagEmpty) {
//            tag = mGlobalTag;
//        }

//        String msg = (objects == null) ? NULL_TIPS : getObjectsString(objects);
        String msg =  getObjectsString(objects); ;
        String headString = "[(" + className + ":" + lineNumber + ")#" + methodName + " ] ";

        return new String[]{tag, msg, headString};
    }
    private static final String NULL = "null";
    private static final String PARAM = "Param";

    private static String getObjectsString(Object... objects) {

        if (objects.length > 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\n");
            for (int i = 0; i < objects.length; i++) {
                Object object = objects[i];
                if (object == null) {
                    stringBuilder.append(PARAM).append("[").append(i).append("]").append(" = ").append(NULL).append("\n");
                } else {
                    stringBuilder.append(PARAM).append("[").append(i).append("]").append(" = ").append(object.toString()).append("\n");
                }
            }
            return stringBuilder.toString();
        } else {
            Object object = objects[0];
            return object == null ? NULL : object.toString();
        }
    }
    public static void d(Object msg) {
        printLog(null, null, msg);
    }
    public static void printLog(String tagStr, Object... objects) {

//        if (!IS_SHOW_LOG) {
//            return;
//        }

        String[] contents = wrapperContent(STACK_TRACE_INDEX_5, tagStr, objects);
        String tag = contents[0];
        String msg = contents[1];
        String headString = contents[2];
        Log.i(tag,headString + msg);
//        switch (type) {
//
//            case V:
//            case D:
//            case I:
//            case W:
//            case E:
//            case A:
//                BaseLog.printDefault(type, tag, headString + msg);
//                break;
//            case JSON:
//                JsonLog.printJson(tag, msg, headString);
//                break;
//            case XML:
//                XmlLog.printXml(tag, msg, headString);
//                break;
//        }

    }
}
