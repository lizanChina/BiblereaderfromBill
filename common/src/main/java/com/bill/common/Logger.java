package com.bill.common;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Logger {

    private static Printer inst;

    public static synchronized void init(boolean isShowLog, int logLev, String tag) {
        if (inst == null) {
            boolean mIsShowLog = isShowLog;
            int mLogLevel = logLev;
            String mTagName = tag;
            if (mLogLevel < Log.VERBOSE) {
                mLogLevel = Log.VERBOSE;
            }
            if (mLogLevel > Log.ASSERT) {
                mLogLevel = Log.ASSERT;
            }
            if ((mTagName == null) || (mTagName.equals(""))) {
                mTagName = "Bill";
            }
            inst = new Printer(mIsShowLog, mLogLevel, mTagName);
        }
    }

    /**
     * log.i
     */
    public static void i(String format, Object... args) {
        if (inst != null) {
            inst.i(format, args);
        }
    }

    public static void i(String tag, String format, Object... args) {
        if (inst != null) {
            inst.i(tag, format, args);
        }
    }

    /**
     * log.v
     */
    public static void v(String format, Object... args) {
        if (inst != null) {
            inst.v(format, args);
        }
    }

    public static void v(String tag, String format, Object... args) {
        if (inst != null) {
            inst.v(tag, format, args);
        }
    }

    /**
     * log.d
     */
    public static void d(String format, Object... args) {
        if (inst != null) {
            inst.d(format, args);
        }
    }

    public static void d(String tag, String format, Object... args) {
        if (inst != null) {
            inst.d(tag, format, args);
        }
    }

    /**
     * log.e
     */
    public static void e(String format, Object... args) {
        if (inst != null) {
            inst.e(format, args);
        }
    }

    public static void e(String tag, String format, Object... args) {
        if (inst != null) {
            inst.e(tag, format, args);
        }
    }

    /**
     * log.error
     */
    public static void error(Exception e) {
        if (inst != null) {
            inst.error(e);
        }
    }

    /**
     * log.w
     */
    public static void w(String format, Object... args) {
        if (inst != null) {
            inst.w(format, args);
        }
    }

    public static void w(String tag, String format, Object... args) {
        if (inst != null) {
            inst.w(tag, format, args);
        }
    }

    /**
     * log.json
     */
    public static void json(String jsonFormat) {
        if (inst != null) {
            inst.json(jsonFormat);
        }
    }

    public static void json(String tag, String jsonFormat) {
        if (inst != null) {
            inst.json(tag, jsonFormat);
        }
    }

    /**
     * Printer
     */
    static class Printer {

        // 打印开关 与 打印级别 设置，从文件中读取
        private boolean mIsShowLog;
        //private int logLevel = Log.ERROR;
        private int mLogLevel;

        //tag name
        private String mTagName;

        public static final String LINE_SEPARATOR = System.getProperty("line.separator");

        public static final int JSON_INDENT = 4;

        private Lock lock;

        public Printer(boolean isShowLog, int logLevel, String tagName) {
            mIsShowLog = isShowLog;
            mLogLevel = logLevel;
            mTagName = tagName;
            lock = new ReentrantLock();
        }

        /**
         * set log level
         */
        public void setLevel(int l) {
            lock.lock();
            try {
                mLogLevel = l;
            } finally {
                lock.unlock();
            }
        }

        private String getFunctionName() {
            StackTraceElement[] sts = Thread.currentThread().getStackTrace();

            if (sts == null) {
                return null;
            }

            int index = 6;
            return "[" + sts[index].getFileName() + ":" + sts[index].getLineNumber() + "]";
        }

        private String createMessage(String msg) {
            String functionName = getFunctionName();
            long threadId = Thread.currentThread().getId();
            //String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date());
            String message = (functionName == null ? msg : (functionName + " - "
                    + String.valueOf(threadId) + " - " + msg));
            //String finalRes = currentTime + " - " + message;
            return message;
        }

        private String getInputString(String format, Object... args) {
            if (format == null) {
                return "null log format";
            }
            if (args == null || args.length == 0) {
                return format;
            }

            return String.format(format, args);
        }

        /**
         * log.i
         */
        public void i(String format, Object... args) {
            if (!mIsShowLog) {
                return;
            }

            if (mLogLevel <= Log.INFO) {
                lock.lock();
                try {
                    String message = createMessage(getInputString(format, args));
                    Log.i(mTagName, message);
                } finally {
                    lock.unlock();
                }
            }
        }

        public void i(String tag, String format, Object... args) {
            if (!mIsShowLog) {
                return;
            }

            if (mLogLevel <= Log.INFO) {
                lock.lock();
                try {
                    String message = createMessage(getInputString(format, args));
                    Log.i(mTagName + "--" + tag, message);
                } finally {
                    lock.unlock();
                }
            }
        }

        /**
         * log.v
         */
        public void v(String format, Object... args) {
            if (!mIsShowLog) {
                return;
            }

            if (mLogLevel <= Log.VERBOSE) {
                lock.lock();
                try {
                    String message = createMessage(getInputString(format, args));
                    Log.v(mTagName, message);
                } finally {
                    lock.unlock();
                }
            }
        }

        public void v(String tag, String format, Object... args) {
            if (!mIsShowLog) {
                return;
            }

            if (mLogLevel <= Log.VERBOSE) {
                lock.lock();
                try {
                    String message = createMessage(getInputString(format, args));
                    Log.v(mTagName + "--" + tag, message);
                } finally {
                    lock.unlock();
                }
            }
        }

        /**
         * log.d
         */
        public void d(String format, Object... args) {
            if (!mIsShowLog) {
                return;
            }

            if (mLogLevel <= Log.DEBUG) {
                lock.lock();
                try {
                    String message = createMessage(getInputString(format, args));
                    Log.d(mTagName, message);
                } finally {
                    lock.unlock();
                }
            }
        }

        public void d(String tag, String format, Object... args) {
            if (!mIsShowLog) {
                return;
            }

            if (mLogLevel <= Log.DEBUG) {
                lock.lock();
                try {
                    String message = createMessage(getInputString(format, args));
                    Log.d(mTagName + "--" + tag, message);
                } finally {
                    lock.unlock();
                }
            }
        }

        /**
         * log.e
         */
        public void e(String format, Object... args) {
            if (!mIsShowLog) {
                return;
            }

            if (mLogLevel <= Log.ERROR) {
                lock.lock();
                try {
                    String message = createMessage(getInputString(format, args));
                    Log.e(mTagName, message);
                } finally {
                    lock.unlock();
                }
            }
        }

        public void e(String tag, String format, Object... args) {
            if (!mIsShowLog) {
                return;
            }

            if (mLogLevel <= Log.ERROR) {
                lock.lock();
                try {
                    String message = createMessage(getInputString(format, args));
                    Log.e(mTagName + "--" + tag, message);
                } finally {
                    lock.unlock();
                }
            }
        }

        /**
         * log.error
         */
        public void error(Exception e) {
            if (!mIsShowLog) {
                return;
            }

            if (mLogLevel <= Log.ERROR) {
                StringBuffer sb = new StringBuffer();
                lock.lock();
                try {
                    String name = getFunctionName();
                    StackTraceElement[] sts = e.getStackTrace();

                    if (name != null) {
                        sb.append(name + " - " + e + "\r\n");
                    } else {
                        sb.append(e + "\r\n");
                    }
                    if (sts != null && sts.length > 0) {
                        for (StackTraceElement st : sts) {
                            if (st != null) {
                                sb.append("[ " + st.getFileName() + ":"
                                        + st.getLineNumber() + " ]\r\n");
                            }
                        }
                    }
                    Log.e(mTagName, sb.toString());
                } finally {
                    lock.unlock();
                }
            }
        }

        /**
         * log.w
         */
        public void w(String format, Object... args) {
            if (!mIsShowLog) {
                return;
            }

            if (mLogLevel <= Log.WARN) {
                lock.lock();
                try {
                    String message = createMessage(getInputString(format, args));
                    Log.w(mTagName, message);
                } finally {
                    lock.unlock();
                }
            }
        }

        public void w(String tag, String format, Object... args) {
            if (!mIsShowLog) {
                return;
            }

            if (mLogLevel <= Log.WARN) {
                lock.lock();
                try {
                    String message = createMessage(getInputString(format, args));
                    Log.w(mTagName + "--" + tag, message);
                } finally {
                    lock.unlock();
                }
            }
        }

        /**
         * log.json
         */

        public static final String NULL_TIPS = "Log with null object";
        public static final String PARAM = "Param";
        public static final String NULL = "null";

        public void json(String jsonFormat) {

            json(null, jsonFormat);
        }

        public void json(String tag, String jsonFormat) {
            if (!mIsShowLog) {
                return;
            }

            lock.lock();
            try {
                String[] contents = wrapperContent(tag, jsonFormat);
                String tagStr = contents[0];
                String msg = contents[1];
                String headString = contents[2];
                printJson(tagStr, msg, headString);
            } finally {
                lock.unlock();
            }
        }

        private String[] wrapperContent(String tagStr, Object... objects) {

            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            int index = 6;
            String className = stackTrace[index].getFileName();
            String methodName = stackTrace[index].getMethodName();
            int lineNumber = stackTrace[index].getLineNumber();
            String methodNameShort = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[ (").append(className).append(":").append(lineNumber).append(")#").append(methodNameShort).append(" ] ");

            String tag = (tagStr == null ? className : tagStr);
            String msg = (objects == null) ? NULL_TIPS : getObjectsString(objects);
            String headString = stringBuilder.toString();

            return new String[]{tag, msg, headString};
        }

        private String getObjectsString(Object... objects) {

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

        private void printLine(String tag, boolean isTop) {
            if (isTop) {
                Log.d(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════");
            } else {
                Log.d(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
            }
        }

        private void printJson(String tag, String msg, String headString) {

            String message;

            try {
                if (msg.startsWith("{")) {
                    JSONObject jsonObject = new JSONObject(msg);
                    message = jsonObject.toString(JSON_INDENT);
                } else if (msg.startsWith("[")) {
                    JSONArray jsonArray = new JSONArray(msg);
                    message = jsonArray.toString(JSON_INDENT);
                } else {
                    message = msg;
                }
            } catch (JSONException e) {
                message = msg;
            }

            printLine(tag, true);
            message = headString + LINE_SEPARATOR + message;
            String[] lines = message.split(LINE_SEPARATOR);
            for (String line : lines) {
                Log.d(tag, "║ " + line);
            }
            printLine(tag, false);
        }
    }

}

