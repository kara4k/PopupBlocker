package com.kara4k.popupblocker.presenter.controllers;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Inject;


public class Preferences {

    SharedPreferences mSp;

    @Inject
    public Preferences(Context context) {
        mSp = PreferenceManager.getDefaultSharedPreferences(context);
    }

}
