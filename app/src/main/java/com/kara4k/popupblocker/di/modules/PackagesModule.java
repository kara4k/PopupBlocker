package com.kara4k.popupblocker.di.modules;

import com.kara4k.popupblocker.di.scopes.PerActivity;
import com.kara4k.popupblocker.view.IPackagesView;

import dagger.Module;
import dagger.Provides;

@Module
public class PackagesModule {

    IPackagesView mMainView;

    public PackagesModule(IPackagesView mainView) {
        mMainView = mainView;
    }

    @PerActivity
    @Provides
    IPackagesView provideView(){
        return mMainView;
    }


}
