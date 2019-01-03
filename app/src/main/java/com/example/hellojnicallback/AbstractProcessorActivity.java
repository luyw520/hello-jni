package com.example.hellojnicallback;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.autotestdemo.maomao.autotestdemo.auto.ProcessorDemo;
import com.smart.android.javalib.MyInjectPrecessor;
import com.smart.android.javalib.TestRunTime;
import com.smart.android.javalib.VInjector;

@VInjector(id=1,name="ProcessorDemo",text="这是动态生成的哦")
public class AbstractProcessorActivity extends AppCompatActivity {

    @TestRunTime(test = "gaga")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abstract_processor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                com.autotestdemo.maomao.autotestdemo.auto.ProcessorDemo demo=new ProcessorDemo();
                Snackbar.make(view, demo.getMessage(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

//                ProcessorDemoClass processorDemoClass;


                ;
            }
        });
        MyInjectPrecessor precessor;
    }

}
