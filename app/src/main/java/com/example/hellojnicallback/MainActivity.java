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
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import dalvik.system.DexFile;

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
                        break;
                    case R.id.rb2:
                        newFragment=fragment2;
                        break;
                    case R.id.rb3:
                        newFragment=fragment3;
                        break;
                    case R.id.rb4:
                        newFragment=fragment4;
                        break;
                }
                fixBug();
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
                ((TextView)v).setText(String.valueOf(new BugTest().testBug()));
                Log.d(MainActivity.class.getSimpleName(),"解决bug后"+String.valueOf(new BugTest().testBug()));
            }
        });
      ;
        Log.d(MainActivity.class.getSimpleName()," "+ getClassLoader());
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},12);
        AndFix.setup();
    }


    private void fixBug() {
        try {
            File file=Environment.getExternalStorageDirectory();
            String dexPath=null;
            //找到要修补的dex文件
            for (File f:file.listFiles()){
                if (f.getName().equals("classes2.dex")){
                    Log.d(getClass().getSimpleName(),"找到dex文件");
                    dexPath=f.getAbsolutePath();
                    break;
                }
            }
            if (dexPath==null){
                Log.d(getClass().getSimpleName(),"没有找到dex文件");
                return;
            }
            //加载dex文件
            DexFile dexFile=new DexFile(dexPath);
            Enumeration<String> entries=dexFile.entries();
            while(entries.hasMoreElements()){
                String s=entries.nextElement();
                Log.d(getClass().getSimpleName(),"s:"+s);
                          //加载解决bug的类
            Class clazz=dexFile.loadClass(s,getClassLoader());
            Method[] methods=clazz.getMethods();
            for (Method m:methods){
                //通过注解找到要解决BUG的方法
               Replace replace=m.getAnnotation(Replace.class);
                if (replace!=null){
                    String oldClass=replace.clazz();
                    String bugMethodName=replace.methoName();
                    Class bugClass=Class.forName(oldClass);
                    Method bugMethod=bugClass.getMethod(bugMethodName,m.getParameterTypes());
                    Log.d(getClass().getSimpleName(),"找到了要替换的方法:"+oldClass+"->"+bugMethodName);
                    //调用AndFix替换方法
                    AndFix.addReplaceMethod(bugMethod,m);
                    Log.d(getClass().getSimpleName(),"替换成功");
                }
            }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
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
