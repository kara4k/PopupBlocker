package com.kara4k.popupblocker.model;


import android.graphics.drawable.Drawable;

public class Package {

    public Package(String appName, String packageName, int system, Drawable icon) {
        this.appName = appName;
        this.packageName = packageName;
        this.system = system;
        this.icon = icon;
    }

    private String appName;
    private String packageName;
    private int system;
    private Drawable icon;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getSystem() {
        return system;
    }

    public void setSystem(int system) {
        this.system = system;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

}
