package com.example.hellojnicallback.dragger;


import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {


    @Provides
    DraggerTestPresenter providerP(){
        return new DraggerTestPresenter();
    }
}
