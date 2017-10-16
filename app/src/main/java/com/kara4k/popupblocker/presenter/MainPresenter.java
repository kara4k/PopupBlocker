package com.kara4k.popupblocker.presenter;


import android.content.pm.PackageInfo;

import com.kara4k.popupblocker.model.Package;
import com.kara4k.popupblocker.model.PackagesProvider;
import com.kara4k.popupblocker.view.IMainView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter implements SingleObserver<List<Package>> {

    @Inject
    IMainView mMainView;

    @Inject
    PackagesProvider mPackagesProvider;

    @Inject
    public MainPresenter() {
    }

    public void onStart() {
        mMainView.onShowDialog("Loading");
        Observable<PackageInfo> packagesInfo = mPackagesProvider.getPackages();
        packagesInfo.map(pInfo -> mPackagesProvider.mapPackage(pInfo))
                .toSortedList(((o1, o2) -> {
                    return o1.getAppName().compareToIgnoreCase(o2.getAppName());
                }))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }


    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onSuccess(@NonNull List<Package> packages) {
        mMainView.setList(packages);
        mMainView.onHideDialog();
    }

    @Override
    public void onError(@NonNull Throwable e) {
        mMainView.onHideDialog();
    }

}
