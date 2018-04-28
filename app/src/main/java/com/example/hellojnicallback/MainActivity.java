/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.hellojnicallback;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.euler.andfix.AndFix;
import com.example.hellojnicallback.log.DebugLog;
import com.example.hellojnicallback.log.KLog;
import com.example.hellojnicallback.log.LogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.hellojnicallback.MergeDexUtil.FIX_DEX_PATH;

public class MainActivity extends AppCompatActivity {

    int hour = 0;
    int minute = 0;
    int second = 0;
    TextView tickView;

    MyFragment fragment1;
    MyFragment fragment2;
    MyFragment fragment3;
    MyFragment fragment4;
    MyFragment lastFragment;
    List<MyFragment> myFragmentList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);LinearLayout linearLayout;
        tickView = (TextView) findViewById(R.id.tickView);
        fragment1=MyFragment.newInstance("1");
        fragment2=MyFragment.newInstance("2");
        fragment3=MyFragment.newInstance("3");
        fragment4=MyFragment.newInstance("4");
        myFragmentList.add(fragment1);
        myFragmentList.add(fragment2);
        myFragmentList.add(fragment3);
        myFragmentList.add(fragment4);
        ((RadioGroup)findViewById(R.id.rg)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                MyFragment newFragment=null;

                switch (checkedId){
                    case R.id.rb1:
                        newFragment=fragment1;
                        fixBug();
                        Toast.makeText(MainActivity.this,"AndFix方式修复成功",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.rb2:
                        fix2Bug();
                        Toast.makeText(MainActivity.this,"Dex插入前面方式修复成功",Toast.LENGTH_LONG).show();
                        newFragment=fragment2;
                        break;
                    case R.id.rb3:
                        newFragment=fragment3;
//                        try {
//                            new HookUtil().hookSystem();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                        startActivity(new Intent(MainActivity.this,TestAidl2Activity.class));
                        break;
                    case R.id.rb4:
                        startActivity(new Intent(MainActivity.this,Main3dActivity.class));
                        newFragment=fragment4;
                        break;
                }

                checkFragment(lastFragment,newFragment,false);
                lastFragment=newFragment;
            }
        });
        lastFragment=fragment1;
        checkFragment(lastFragment,fragment1,true);


        viewpager= (ViewPager) findViewById(R.id.viewpager);

        fragmentPagerAdapter=new MyFragmentPagerAdapter(getSupportFragmentManager());
        FragmentStatePagerAdapter fragmentStatePagerAdapter=new MyFragmentStatePagerAdapter(getSupportFragmentManager());
//        viewpager.setAdapter(fragmentPagerAdapter);
        viewpager.setAdapter(fragmentStatePagerAdapter);


        findViewById(R.id.hellojniMsg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ((TextView)v).setText(String.valueOf(new BugTest().testBug()));
                }catch (Exception e){
                    Log.d(MainActivity.class.getSimpleName(),e.toString());
                    ((TextView)v).setText(String.valueOf(e.toString()));
                }

//                Log.d(MainActivity.class.getSimpleName(),"解决bug后"+String.valueOf(new BugTest().testBug()));
//                Toast.makeText(MainActivity.this,"toast弹框",Toast.LENGTH_SHORT).show();
            }
        });
      ;
        Log.d(MainActivity.class.getSimpleName()," "+ getClassLoader());
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},12);
        AndFix.setup();

        File fixDir = getDir(FIX_DEX_PATH, Context.MODE_PRIVATE);
        Log.d(MainActivity.class.getSimpleName(),fixDir.getAbsolutePath());
       ClassLoader classLoader= getClassLoader();
        Log.d(MainActivity.class.getSimpleName(),classLoader.toString());
     while((classLoader=classLoader.getParent())!=null){
         Log.d(MainActivity.class.getSimpleName(),classLoader.toString());
        }

        LogUtil.d("onCreate");
        KLog.d("onCreate");
        DebugLog.d("onCreate");

       String log="aaa[" +
               "  {" +
               "    \"appName\": \"string\"," +
               "    \"appVersion\": \"string\"," +
               "    \"createTime\": \"string\"," +
               "    \"deviceId\": \"string\"," +
               "    \"ip\": \"string\",\n" +
               "    \"mobileBrand\": \"string\"," +
               "    \"os\": 0,\n" +
               "    \"osVersion\": \"string\"," +
               "    \"userId\": \"string\"" +
               "  }" +
               "]";
        LogUtil.d(log);
        DebugLog.d(log);
        KLog.json(log);
        try {
            new HookUtil(this).hookSystem();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        Observable.create(new Observable.OnSubscribe<String>() {
//        })
//        HashMap hashMap;hashMap.put()+


        createObserable();



    }
    //创建一个观察者方法
    public static void createObserable(){
        //定义被观察者
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override public void call(Subscriber<? super String> subscriber) {
                DebugLog.d("call:"+Thread.currentThread().getName()+"");
                if (!subscriber.isUnsubscribed()){
                    subscriber.onNext("hello");// 发送事件
                    subscriber.onNext("world");// 发送事件
                    subscriber.onNext("dddd");// 发送事件
                    subscriber.onCompleted();// 完成事件
                }
            }
        });
        //订阅者
        Subscriber<String> showSub = new Subscriber<String>() {
            @Override public void onCompleted() {
                Log.i("adu",Thread.currentThread().getName()+",onCompleted");
            }
            @Override public void onError(Throwable e) {
                Log.i("adu","onError");
            }
            @Override public void onNext(String s) {
                Log.i("adu","onNext:"+s);
            }
        };
        observable.subscribeOn(Schedulers.io()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i("adu1",Thread.currentThread().getName()+"onCompletedonCompleted");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i("adu1","onNextonNext:"+s);
            }
        });
//        observable.subscribeOn(Schedulers.io()).subscribe(new Subscriber<String>() {
//            @Override
//            public void onCompleted() {
//                Log.i("adu2",Thread.currentThread().getName()+"onCompletedonCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(String s) {
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                Log.i("adu2","onNextonNext:"+s);
//            }
//        });
//        observable.subscribeOn(Schedulers.io()).subscribe(new Subscriber<String>() {
//            @Override
//            public void onCompleted() {
//                Log.i("adu3",Thread.currentThread().getName()+"onCompletedonCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(String s) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                Log.i("adu3","onNextonNext:"+s);
//            }
//        });
       observable.subscribeOn(AndroidSchedulers.mainThread()).subscribe(showSub);//关联被观察者
    }

    public String dexName="fix_dex.dex";
    private void fixBug() {
      new AndFixUtil().fixBug(getClassLoader(),dexName);
    }
    private void fix2Bug() {
      MergeDexUtil.copyDexFileToAppAndFix(this,dexName,true);
    }

    ViewPager viewpager;
    MyFragmentPagerAdapter fragmentPagerAdapter;
    class MyFragmentPagerAdapter extends FragmentPagerAdapter{

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return myFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return myFragmentList.size();
        }
    }
    class MyFragmentStatePagerAdapter extends FragmentStatePagerAdapter{

        public MyFragmentStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return myFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return myFragmentList.size();
        }
    };
    private void checkFragment(Fragment oldFragment,Fragment newFragment,boolean isReplace){

        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
      if (isReplace){
          transaction.replace(R.id.content,newFragment);
      }else{
          if (oldFragment==newFragment){
              return;
          }

          transaction.hide(fragment1);
          transaction.hide(fragment2);
          transaction.hide(fragment3);
          transaction.hide(fragment4);
          if (!newFragment.isAdded()){
              transaction.add(R.id.content,newFragment);
          }

//          if (newFragment.isDetached()){
//              transaction.attach(newFragment);
//          }
          transaction.show(newFragment);
      }
      TextView textView;
        transaction.commit();
        Log.d("getFragments","getFragments().size:"+manager.getFragments().size());

    }
    @Override
    public void onResume() {
        super.onResume();
        hour = minute = second = 0;
//        ((TextView)findViewById(R.id.hellojniMsg)).setText(stringFromJNI());
//        startTicks();
        Log.d("getFragments","getChildCount:"+viewpager.getChildCount());
    }

    @Override
    protected void onRestart() {
        Log.d("getFragments","onRestart:");
        super.onRestart();
    }

    @Override
    public void onPause () {
        super.onPause();
//        StopTicks();
    }
    public static class MyFragment extends Fragment{
        public  String msg;
//        public static MyFragment getMyFragment(String m){
//            msg=m;
//            MyFragment myFragment=new MyFragment();
//            return myFragment;
//        }
        public static MyFragment newInstance(String args) {
            MyFragment fragment = new MyFragment();
            if (fragment != null) {
                fragment.setMsg(args);
            }
            return fragment;
        }
        public void setMsg(String m){
            this.msg=m;
        }
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//            TextView textView=new TextView(getContext());
//            textView.setText(msg);
////            ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
////            container.addView(textView,params);
//            Log.d("MyFragment"+msg,"onCreateView()");
//            textView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(getActivity(),Main2Activity.class));
//                }
//            });
            int layoutId=0;
            if (msg.equals("1")){
                layoutId=R.layout.fragment_1;
            }else if (msg.equals("2")){
                layoutId=R.layout.fragment_2;
            }else if (msg.equals("3")){
                layoutId=R.layout.fragment_3;
            }else if (msg.equals("4")){
                layoutId=R.layout.fragment_4;
            }
            View view=inflater.inflate(layoutId,container,false);
            view.findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getActivity(),Main2Activity.class));
                }
            });
            return view;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            Log.d("MyFragment"+msg,"onCreate()");
            super.onCreate(savedInstanceState);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            Log.d("MyFragment"+msg,"onViewCreated()");
            super.onViewCreated(view, savedInstanceState);
        }

        @Override
        public void onResume() {
            Log.d("MyFragment"+msg,"onResume()");
            super.onResume();
        }

        @Override
        public void onStop() {
            Log.d("MyFragment"+msg,"onStop()");
            super.onStop();
        }

        @Override
        public void onAttach(Activity activity) {
            Log.d("MyFragment"+msg,"onAttach()");
            super.onAttach(activity);
        }

        @Override
        public void onDetach() {
            Log.d("MyFragment"+msg,"onDetach()");
            super.onDetach();
        }

        @Override
        public void onDestroy() {
            Log.d("MyFragment"+msg,"onDestroy()");
            super.onDestroy();
        }

        @Override
        public void onHiddenChanged(boolean hidden) {
            Log.d("MyFragment"+msg,"onHiddenChanged hidden:"+hidden);
            super.onHiddenChanged(hidden);
        }

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            Log.d("MyFragment"+msg,"setUserVisibleHint isVisibleToUser:"+isVisibleToUser);
            super.setUserVisibleHint(isVisibleToUser);
        }
    }
    /*
     * A function calling from JNI to update current timer
     */
    @Keep
    private void updateTimer() {
        ++second;
        if(second >= 60) {
            ++minute;
            second -= 60;
            if(minute >= 60) {
                ++hour;
                minute -= 60;
            }
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String ticks = "" + MainActivity.this.hour + ":" +
                        MainActivity.this.minute + ":" +
                        MainActivity.this.second;
                MainActivity.this.tickView.setText(ticks);
            }
        });
    }
    static {
//        System.loadLibrary("hello-jnicallback");
    }
    public native  String stringFromJNI();
    public native void startTicks();
    public native void StopTicks();
}
