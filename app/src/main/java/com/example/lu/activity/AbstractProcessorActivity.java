package com.example.lu.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.lu.R;
import com.lu.javalib.TestRunTime;
import com.lu.javalib.VInjector;

@VInjector(id=1,name="ProcessorDemo",text="这是动态生成的哦")
public class AbstractProcessorActivity extends AppCompatActivity {

    @TestRunTime(test = "gaga")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abstract_processor);
    }

}
