package com.kara4k.popupblocker.presenter;


import com.kara4k.popupblocker.model.DaoSession;
import com.kara4k.popupblocker.model.Word;
import com.kara4k.popupblocker.model.WordDao;
import com.kara4k.popupblocker.view.IWordsView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WordsPresenter implements SingleObserver<List<Word>> {

    @Inject
    IWordsView mWordsView;

    private WordDao mWordDao;

    @Inject
    public WordsPresenter(DaoSession daoSession) {
        mWordDao = daoSession.getWordDao();
    }

    public void onStart() {
        showAllWords();
    }

    private void showAllWords() {
        Observable.fromIterable(mWordDao.loadAll())
                .toList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    public void addWord(String text) { // TODO: 25.10.2017 empty check
        mWordDao.insert(new Word(text));
        showAllWords();
    }

    public void onDeleteWord(Word word) {
        mWordDao.delete(word);
        showAllWords();
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onSuccess(@NonNull List<Word> words) {
        mWordsView.setList(words);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        e.printStackTrace();
    }
}
