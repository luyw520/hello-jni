package com.example.hellojnicallback;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import static com.example.hellojnicallback.MainArcMenu.DURATIONMILLIS;

public class AcrMenuActivity extends AppCompatActivity implements View.OnClickListener{

    MainArcMenu mainArcMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acr_menu);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        mainArcMenu=new MainArcMenu();
        viewGroup= (ViewGroup) findViewById(R.id.rl_arc_menu_run);
        findViewById(R.id.rlView).setOnClickListener(this);
    }

    ViewGroup viewGroup;
    @Override
    public void onClick(View view) {
            switch (view.getId()){
                case R.id.rlView:
                    if (mainArcMenu.isShown()) {
                        mainArcMenu.closeMenuAnimation(viewGroup,DURATIONMILLIS);
                        mainArcMenu.clear();
                    }else{
                        if (!mainArcMenu.isShown()) {
                            mainArcMenu.openMenuAnimation(viewGroup,DURATIONMILLIS);
                        }
                    }
                    break;
            }
        }
}
