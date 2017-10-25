package com.kara4k.popupblocker.di.modules;

import android.content.Context;

import com.kara4k.popupblocker.model.DaoMaster;
import com.kara4k.popupblocker.model.DaoSession;

import org.greenrobot.greendao.database.Database;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    Context mContext;

    public AppModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return mContext;
    }

    @Provides
    @Singleton
    DaoSession provideDaoSession(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "database");
        Database db = helper.getWritableDb();
        return new DaoMaster(db).newSession();
    }

}



