package com.kara4k.popupblocker.di.modules;

import com.kara4k.popupblocker.di.scopes.PerActivity;
import com.kara4k.popupblocker.model.DaoMaster;
import com.kara4k.popupblocker.model.DaoSession;
import com.kara4k.popupblocker.view.IMainView;

import org.greenrobot.greendao.database.Database;

import dagger.Module;
import dagger.Provides;

@Module
public class PackagesModule {

    IMainView mMainView;

    public PackagesModule(IMainView mainView) {
        mMainView = mainView;
    }

    @PerActivity
    @Provides
    IMainView provideView(){
        return mMainView;
    }

    @PerActivity
    @Provides
    DaoSession provideDaoSession(Database database) {
       return new DaoMaster(database).newSession();
    }
}
