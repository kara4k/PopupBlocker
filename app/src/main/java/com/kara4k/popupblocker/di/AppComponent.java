package com.kara4k.popupblocker.di;

import android.content.Context;
import android.content.SharedPreferences;

import com.kara4k.popupblocker.di.modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    Context shareContext();

    SharedPreferences sharePreferences();

}
