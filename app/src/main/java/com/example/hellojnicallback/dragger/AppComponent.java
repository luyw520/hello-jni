package com.example.hellojnicallback.dragger;

import com.example.hellojnicallback.MyAppcation;

import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MainModule.class,AndroidSupportInjectionModule.class})
public interface AppComponent  {

   void inject(MyAppcation appcation);

}
