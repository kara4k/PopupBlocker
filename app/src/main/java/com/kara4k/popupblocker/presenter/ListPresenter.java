package com.kara4k.popupblocker.presenter;


import android.support.annotation.CallSuper;

import com.kara4k.popupblocker.view.IListView;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public abstract class ListPresenter<T, V extends IListView<T>> implements IPresenter<T> {

    @Inject
    protected V mListView;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    protected void subscribe(Completable completable, Action onComplete) {
        Disposable disposable = completable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onComplete, this::onError);

        mCompositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        mCompositeDisposable.dispose();
    }

    @Override
    public void onSubscribe(Disposable d) {
        mCompositeDisposable.add(d);
    }

    @CallSuper
    @Override
    public void onSuccess(T t) {
        mListView.setList(t);
    }

    @CallSuper
    @Override
    public void onError(Throwable e) {
        mListView.showError(e.getMessage());
        e.printStackTrace();
    }

    public V getView() {
        return mListView;
    }
}
