package com.kara4k.popupblocker.di.modules;

import com.kara4k.popupblocker.di.scopes.PerActivity;
import com.kara4k.popupblocker.view.IWordsView;

import dagger.Module;
import dagger.Provides;

@Module
public class WordsModule {

    IWordsView mWordsView;

    public WordsModule(IWordsView wordsView) {
        mWordsView = wordsView;
    }

    @PerActivity
    @Provides
    IWordsView provideWordsView() {
        return mWordsView;
    }
}
