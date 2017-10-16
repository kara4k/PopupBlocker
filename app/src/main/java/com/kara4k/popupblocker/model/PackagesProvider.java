package com.kara4k.popupblocker.model;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class PackagesProvider {

    private Context mContext;

    private PackageManager mPackageManager;

    @Inject
    public PackagesProvider(Context context) {
        mContext = context;
        mPackageManager = mContext.getPackageManager();
    }

    public Observable<PackageInfo> getPackages() {
        List<PackageInfo> packages = mPackageManager.getInstalledPackages(0);
        return Observable.fromIterable(packages);
    }

    public Package mapPackage(PackageInfo packageInfo) {
        String appName = mPackageManager
                .getApplicationLabel(packageInfo.applicationInfo).toString();
        String pName = packageInfo.packageName;
        int system = packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM;
        Drawable icon = packageInfo.applicationInfo.loadIcon(mPackageManager);

        return new Package(appName, pName, system, icon);
    }

}
