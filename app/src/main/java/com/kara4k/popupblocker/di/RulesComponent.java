package com.kara4k.popupblocker.di;


import com.kara4k.popupblocker.di.modules.RulesModule;
import com.kara4k.popupblocker.di.scopes.PerActivity;
import com.kara4k.popupblocker.view.activities.RulesListActivity;

import dagger.Component;

@PerActivity
@Component(modules = RulesModule.class, dependencies = AppComponent.class)
public interface RulesComponent {

    void injectRulesActivity(RulesListActivity activity);
}
