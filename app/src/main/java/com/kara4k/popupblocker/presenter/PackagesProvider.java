package com.kara4k.popupblocker.presenter;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.kara4k.popupblocker.model.DaoSession;
import com.kara4k.popupblocker.model.Package;
import com.kara4k.popupblocker.model.PackageDao;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class PackagesProvider {

    private Context mContext;

    private PackageManager mPackageManager;

    private PackageDao mPackageDao;

    private List<Package> mDbPackages;

    @Inject
    public PackagesProvider(Context context, DaoSession daoSession) {
        mContext = context;
        mPackageManager = mContext.getPackageManager();
        mPackageDao = daoSession.getPackageDao();
        mDbPackages = mPackageDao.loadAll();
    }

    public Observable<Package> getPackageObservable() {
        List<PackageInfo> packageInfo = mPackageManager.getInstalledPackages(0);

        Observable<Package> packageObservable = Observable.fromIterable(packageInfo)
                .map(pInfo -> mapPackage(pInfo))
                .map(aPackage -> setChecked(aPackage))
                .sorted(((o1, o2) -> o1.getAppName().compareToIgnoreCase(o2.getAppName())));

        return packageObservable;
    }

    private Package mapPackage(PackageInfo packageInfo) {
        String appName = mPackageManager
                .getApplicationLabel(packageInfo.applicationInfo).toString();
        String pName = packageInfo.packageName;
        int system = packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM;
        Drawable icon = packageInfo.applicationInfo.loadIcon(mPackageManager);

        return new Package(appName, pName, system, icon);
    }

    private Package setChecked(Package aPackage) {
        for (Package p : mDbPackages) {
            if (p.getPackageName().equals(aPackage.getPackageName())) {
                aPackage.setSelected(true);
                aPackage.setId(p.getId());
                break;
            }
            aPackage.setSelected(false);
        }
        return aPackage;
    }

    public void updateDb(Package aPackage, boolean selected) {
        if (selected) {
            mPackageDao.insertOrReplace(aPackage);
        } else {
            mPackageDao.delete(aPackage);
        }
    }
}
