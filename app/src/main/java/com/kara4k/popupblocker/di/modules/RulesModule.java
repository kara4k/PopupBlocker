package com.kara4k.popupblocker.di.modules;


import com.kara4k.popupblocker.di.scopes.PerActivity;
import com.kara4k.popupblocker.view.IRulesView;

import dagger.Module;
import dagger.Provides;

@Module
public class RulesModule {

    IRulesView mRulesView;

    public RulesModule(IRulesView rulesView) {
        mRulesView = rulesView;
    }

    @Provides
    @PerActivity
    IRulesView provideView(){
        return mRulesView;
    }

}
