package com.example.hellojnicallback;

import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.example.hellojnicallback.log.DebugLog;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2018/4/16 0016.
 */

public class HookUtil {
    private Context context;
    public HookUtil(Context context){
this.context=context;
    }

    Class<?> activityThreadClass;
    Object activityThread;
    public void hookSystem() throws Exception{


        Class<?> activityManagerNative=Class.forName("android.app.ActivityManagerNative");
//        Field[] fields=activityManagerNative.getDeclaredFields();activityManagerNative.getDeclaredFields()
//        for (Field f:fields){
//            LogUtil.d(f.getName());
//            LogUtil.d(f.toString());
//            LogUtil.d("----------------------------");
//        }
        //获取gDefault属性
       Field field= getField(activityManagerNative,"gDefault");
        //获取一个对象。。。。Singleton
       Object o= field.get(null);

        DebugLog.d(o.toString());

        Class<?> singleton=Class.forName("android.util.Singleton");
        //获取mInstance 属性
        Field field2= getField(singleton,"mInstance");
        Object mInstance=field2.get(o);

        DebugLog.d(mInstance.toString());


        MyInvocationHandler myInvocationHandler=new MyInvocationHandler(mInstance);
        //生成代理类
        Object proxy= Proxy.newProxyInstance(mInstance.getClass().getClassLoader(),mInstance.getClass().getInterfaces(),myInvocationHandler);
//        DebugLog.d(proxy.toString());
//
//        //替换Singleton类里面的mInstance属性
        field2.set(o,proxy);



       activityThreadClass=Class.forName("android.app.ActivityThread");
        Field mHField=getField(activityThreadClass,"mH");
        Field sCurrentActivityThread=getField(activityThreadClass,"sCurrentActivityThread");

        //获取到ActivityThread对象
        activityThread=sCurrentActivityThread.get(null);
        //获取Handler对象
        Object mH= mHField.get(activityThread);

        Field field1=getField(Handler.class,"mCallback");
//        Object callback= field1.get(mH);

        //替换mH对面里面的callback
        field1.set(mH,myCallback);
    }
    Handler.Callback myCallback=new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what==100){
                DebugLog.d("handleMessage");
                handleStartActivity(msg);


            }
//            msg.getTarget().sendEmptyMessage(100);
            return false;
        };
    };
    boolean isReplaceOncreate=false;
    private void handleStartActivity(Message msg){
        Object activityClientRecord= msg.obj;
        try {

            isReplaceOncreate=false;
            Field field=getField(activityClientRecord.getClass(),"intent");

            //拿到intent对象。
            Intent intent= (Intent) field.get(activityClientRecord);
            ComponentName componentName=intent.getParcelableExtra("realComponentName");

           String className=componentName.getClassName();

            Class<?> clazz=Class.forName(className);
            if (clazz.newInstance() instanceof AppCompatActivity){
                DebugLog.d("AppCompatActivity............");
                isReplaceOncreate=true;
            }
            //重新替换过来
            intent.setComponent(componentName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void hookInstrumentation(){
        try {
            Field f =getField(activityThreadClass,"mInstrumentation");
            Instrumentation in= (Instrumentation) f.get(activityThread);
            MyInvocationHandler2 m=new MyInvocationHandler2(in);
            Proxy.newProxyInstance(in.getClass().getClassLoader(),Instrumentation.class.getInterfaces(),m);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ;
    }


    private Field getField(Class<?> clazz,String fieldName)throws Exception{
        Field field=null;
//        if (o!=null){
//            field= clazz.getFields(fieldName);
//        }
         field= clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field;
    }

    class MyInvocationHandler implements InvocationHandler {
        Object target;
        public MyInvocationHandler(Object o){
            this.target=o;
        }
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//
//            DebugLog.d("proxy:"+proxy);
            if (method.getName().equals("startActivity")){
                int index=-1;
                DebugLog.d("invoke:"+method.getName());
                for (Object o:args){
                    index++;
                    if (o instanceof Intent){
//                        intent.setComponent(componentName);
                        Intent oldIntent= (Intent) o;
                        //把原来的intent的跳转类信息保存起来
//                        Intent intent=new Intent();
                        DebugLog.d("原来要跳转的getAction:"+oldIntent.getAction());
                        DebugLog.d("原来要跳转的getPackage:"+oldIntent.getPackage());
                        DebugLog.d("原来要跳转的Component():"+(oldIntent.getComponent()==null?"null":oldIntent.getComponent().toString()));
                        //替换
                        ComponentName componentName=new ComponentName(context,TestAidlActivity.class);
                        oldIntent.putExtra("realComponentName",oldIntent.getComponent());
                        oldIntent.setComponent(componentName);
                        break;
                    }
                }
//                if (index!=-1){
//                    args[index]=
//                }
            }
            return method.invoke(target,args);
        }
    }
    class MyInvocationHandler2 implements InvocationHandler {
        Object target;
        public MyInvocationHandler2(Object o){
            this.target=o;
        }
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//
//            DebugLog.d("proxy:"+proxy);
            if (method.getName().equals("startActivity")){
                int index=-1;
                DebugLog.d("invoke:"+method.getName());
                for (Object o:args){
                    index++;
                    if (o instanceof Intent){
//                        intent.setComponent(componentName);
                        Intent oldIntent= (Intent) o;
                        //把原来的intent的跳转类信息保存起来
//                        Intent intent=new Intent();
                        DebugLog.d("原来要跳转的getAction:"+oldIntent.getAction());
                        DebugLog.d("原来要跳转的getPackage:"+oldIntent.getPackage());
                        DebugLog.d("原来要跳转的Component():"+(oldIntent.getComponent()==null?"null":oldIntent.getComponent().toString()));
                        //替换
                        ComponentName componentName=new ComponentName(context,TestAidlActivity.class);
                        oldIntent.putExtra("realComponentName",oldIntent.getComponent());
                        oldIntent.setComponent(componentName);
                        break;
                    }
                }
//                if (index!=-1){
//                    args[index]=
//                }
            }
            return method.invoke(target,args);
        }
    }

}
