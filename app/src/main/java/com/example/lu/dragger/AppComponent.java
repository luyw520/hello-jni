package com.example.lu.dragger;

import com.example.lu.MyAppcation;

import dagger.android.support.AndroidSupportInjectionModule;
import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MainModule.class,AndroidSupportInjectionModule.class})
public interface AppComponent  {

   void inject(MyAppcation appcation);

}
