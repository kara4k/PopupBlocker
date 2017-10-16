package com.kara4k.popupblocker.di;

import com.kara4k.popupblocker.di.modules.PackagesModule;
import com.kara4k.popupblocker.di.scopes.PerActivity;
import com.kara4k.popupblocker.view.SettingsActivity;

import dagger.Component;

@PerActivity
@Component(modules = PackagesModule.class, dependencies = AppComponent.class)
public interface PackagesComponent {

    void injectSettingsActivity(SettingsActivity activity);
}
