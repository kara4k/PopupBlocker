package com.kara4k.popupblocker.di;


import com.kara4k.popupblocker.di.modules.WordsModule;
import com.kara4k.popupblocker.di.scopes.PerActivity;
import com.kara4k.popupblocker.view.WordsActivity;

import dagger.Component;

@PerActivity
@Component(modules = WordsModule.class, dependencies = AppComponent.class)
public interface WordsComponent {

    void injectWordsActivity(WordsActivity activity);
}
