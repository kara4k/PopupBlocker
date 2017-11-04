package com.kara4k.popupblocker.presenter;


import io.reactivex.SingleObserver;

public interface IPresenter<T> extends SingleObserver<T>{

    void onDestroy();
}
