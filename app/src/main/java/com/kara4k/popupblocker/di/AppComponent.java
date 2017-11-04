package com.kara4k.popupblocker.di;

import android.content.Context;

import com.kara4k.popupblocker.di.modules.AppModule;
import com.kara4k.popupblocker.model.DaoSession;
import com.kara4k.popupblocker.service.PopupService;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    Context shareContext();

    DaoSession shareDaoSession();

    void injectService(PopupService service);
}
