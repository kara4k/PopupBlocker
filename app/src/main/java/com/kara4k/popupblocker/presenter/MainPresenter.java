package com.kara4k.popupblocker.presenter;


import android.content.Context;
import android.support.annotation.Nullable;

import com.kara4k.popupblocker.R;
import com.kara4k.popupblocker.model.Package;
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
    Context mContext;
    @Inject
    PackagesProvider mPackagesProvider;

    private List<Package> mList;
    private boolean mIsShowSystem = true;


    @Inject
    public MainPresenter() {

    }

    public void onStart() {
        mMainView.onShowDialog(mContext.getString(R.string.dialog_loading));
        mPackagesProvider.getPackageObservable()
                .toList()
                .subscribeOn(Schedulers.newThread())
                .subscribe(packages -> {
                    mList = packages;
                    showPackages(null, mIsShowSystem);
                });

    }

    public void showPackages(@Nullable String query, boolean isShowSystem) {
        int visibility = isShowSystem ? 2 : 1;

        Observable.fromIterable(mList)
                .filter(aPackage -> aPackage.getSystem() != visibility)
                .filter(aPackage -> filterQuery(aPackage, query))
                .toList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    public void toggleSysVisibility() {
        showPackages(null, mIsShowSystem = !mIsShowSystem);
    }

    public void setSelected(Package aPackage, int position, boolean selected) {
        setPackageSelection(aPackage, selected);
        mMainView.updatePackageView(position);
        mPackagesProvider.updateDb(aPackage, selected);
    }

    private void setPackageSelection(Package aPackage, boolean selected) {
        for (Package p : mList) {
            if (p.equals(aPackage)) {
                p.setSelected(selected);
                break;
            }
        }
    }

    public void onSearch(String query) {
        showPackages(query, mIsShowSystem);
    }

    public boolean filterQuery(Package aPackage, String query) {
        String appName = aPackage.getAppName().toLowerCase();
        String packageName = aPackage.getPackageName().toLowerCase();
        if (query == null || query == "" ||
                appName.contains(query.toLowerCase()) ||
                packageName.contains(query.toLowerCase())){
           return true;
        }
        return false;
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
