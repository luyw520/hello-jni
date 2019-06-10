package com.example.lu;

import android.os.Environment;
import android.util.Log;

import com.alipay.euler.andfix.AndFix;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Enumeration;

import dalvik.system.DexFile;

/**
 * Created by Administrator on 2018/4/8 0008.
 */

public class AndFixUtil {
    public void fixBug(ClassLoader classLoader,String dexName) {


        try {
            File file = Environment.getExternalStorageDirectory();
            String dexPath = null;
            //找到要修补的dex文件
            for (File f : file.listFiles()) {
                if (f.getName().equals(dexName)) {
                    Log.d(getClass().getSimpleName(), "找到dex文件");
                    dexPath = f.getAbsolutePath();
                    break;
                }
            }
            if (dexPath == null) {
                Log.d(getClass().getSimpleName(), "没有找到dex文件");
                return;
            }
            //加载dex文件
            DexFile dexFile = new DexFile(dexPath);
            Enumeration<String> entries = dexFile.entries();
            while (entries.hasMoreElements()) {
                String s = entries.nextElement();
                Log.d(getClass().getSimpleName(), "s:" + s);
                //加载解决bug的类
                Class clazz = dexFile.loadClass(s, classLoader);
                Method[] methods = clazz.getMethods();
                for (Method m : methods) {
                    //通过注解找到要解决BUG的方法
                    Replace replace = m.getAnnotation(Replace.class);
                    if (replace != null) {
                        String oldClass = replace.clazz();
                        String bugMethodName = replace.methoName();
                        Class bugClass = Class.forName(oldClass);
                        Method bugMethod = bugClass.getMethod(bugMethodName, m.getParameterTypes());
                        Log.d(getClass().getSimpleName(), "找到了要替换的方法:" + oldClass + "->" + bugMethodName);
                        //调用AndFix替换方法
                        AndFix.addReplaceMethod(bugMethod, m);
                        Log.d(getClass().getSimpleName(), "替换成功");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
