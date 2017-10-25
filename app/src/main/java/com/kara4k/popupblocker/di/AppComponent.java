package com.kara4k.popupblocker.di;

import android.content.Context;

import com.kara4k.popupblocker.di.modules.AppModule;

import org.greenrobot.greendao.database.Database;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    Context shareContext();

    Database shareDatabase();
}
