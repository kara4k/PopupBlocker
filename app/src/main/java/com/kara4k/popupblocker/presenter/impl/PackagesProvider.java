package com.kara4k.popupblocker.presenter.impl;


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

    /**
     * Method receives all installed packages on device, convert them to view objects,
     * define isSelected variable, sort by application name and return as observable.
     *
     * @return Observable from mapped, sorted view objects of packages
     */
    public Observable<Package> getPackageObservable() {
        List<PackageInfo> packageInfo = mPackageManager.getInstalledPackages(0);

        Observable<Package> packageObservable = Observable.fromIterable(packageInfo)
                .map(this::mapPackage)
                .map(this::setSelected)
                .sorted(this::compare);

        return packageObservable;
    }

    /**
     * Compare packages by selection in DESC way, then by application name in ASC way
     *
     * @param o1 one package
     * @param o2 another package
     * @return compared value
     */
    private int compare(Package o1, Package o2) {
        int bySelection = Boolean.compare(o2.isSelected(), o1.isSelected());

        if (bySelection == 0) {
            int byName = o1.getAppName().compareToIgnoreCase(o2.getAppName());
            return byName;
        }

        return bySelection;
    }

    /**
     * Map system package object to view object
     *
     * @param packageInfo package to map
     * @return mapped view object
     */
    private Package mapPackage(PackageInfo packageInfo) {
        String appName = mPackageManager
                .getApplicationLabel(packageInfo.applicationInfo).toString();
        String pName = packageInfo.packageName;
        int system = packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM;
        Drawable icon = packageInfo.applicationInfo.loadIcon(mPackageManager);

        return new Package(appName, pName, system, icon);
    }

    /**
     * Set Package variable isSelected, depends on if a package stored in database
     *
     * @param aPackage package to check
     * @return Package with defined variable isSelected
     */
    private Package setSelected(Package aPackage) {
        for (Package p : mDbPackages) {
            if (p.getPackageName().equals(aPackage.getPackageName())) {
                aPackage.setSelected(true);
                break;
            }
            aPackage.setSelected(false);
        }
        return aPackage;
    }

    /**
     * Update database depends on if a package is selected
     *
     * @param aPackage package to update
     * @param selected used to determine insert or delete package
     */
    public void updateDb(Package aPackage, boolean selected) {
        if (selected) {
            mPackageDao.insertOrReplace(aPackage);
        } else {
            mPackageDao.delete(aPackage);
        }
    }
}
