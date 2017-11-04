package com.kara4k.popupblocker.presenter.impl;


import android.content.Context;
import android.support.annotation.Nullable;

import com.kara4k.popupblocker.R;
import com.kara4k.popupblocker.model.Package;
import com.kara4k.popupblocker.presenter.ListPresenter;
import com.kara4k.popupblocker.view.IPackagesView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

public class PackagesPresenter extends ListPresenter<List<Package>, IPackagesView> {

    @Inject
    Context mContext;
    @Inject
    PackagesProvider mPackagesProvider;

    private List<Package> mList;
    private boolean mIsShowSystem;


    @Inject
    public PackagesPresenter() {
    }

    public void onStart() {
        getView().onShowDialog(mContext.getString(R.string.dialog_loading));
        mPackagesProvider.getPackageObservable()
                .toList()
                .subscribeOn(Schedulers.newThread())
                .subscribe(packages -> {
                    mList = packages;
                    showPackages(null, mIsShowSystem);
                });

    }

    /**
     * Show sorted list of packages on device
     * @param query Packages with text in application name or package name will be shown
     * @param isShowSystem Show or not system packages
     */
    private void showPackages(@Nullable String query, boolean isShowSystem) {
        int visibility = isShowSystem ? 2 : 1;

        Observable.fromIterable(mList)
                .filter(aPackage -> aPackage.getSystem() != visibility)
                .filter(aPackage -> filterQuery(aPackage, query))
                .toList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    /**
     * Show or hide system packages
     */
    public void toggleSysVisibility() {
        showPackages(null, mIsShowSystem = !mIsShowSystem);
    }

    public void setSelected(Package aPackage, boolean selected) {
        setPackageSelection(aPackage, selected);

        Completable completable = Completable.fromAction(() -> mPackagesProvider.updateDb(aPackage, selected));
        subscribe(completable, () -> {});
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
                packageName.contains(query.toLowerCase())) {
            return true;
        }
        return false;
    }

    @Override
    public void onSuccess(@NonNull List<Package> packages) {
        getView().onHideDialog();
        super.onSuccess(packages);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        getView().onHideDialog();
        super.onError(e);
    }
}
