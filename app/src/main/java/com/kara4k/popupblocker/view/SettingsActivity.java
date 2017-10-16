package com.kara4k.popupblocker.view;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kara4k.popupblocker.R;
import com.kara4k.popupblocker.di.DaggerPackagesComponent;
import com.kara4k.popupblocker.di.modules.PackagesModule;
import com.kara4k.popupblocker.model.Package;
import com.kara4k.popupblocker.presenter.MainPresenter;
import com.kara4k.popupblocker.view.Adapters.Adapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class SettingsActivity extends BaseActivity implements IMainView {

    @Inject
    MainPresenter mPresenter;

    @Inject
    Adapter mAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;


    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onViewReady() {
        super.onViewReady();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter = new Adapter());
        mPresenter.onStart();
    }

    @Override
    protected void injectDependencies() {
        DaggerPackagesComponent.builder()
                .appComponent(getAppComponent())
                .packagesModule(new PackagesModule(this))
                .build().injectSettingsActivity(this);
    }

    @Override
    public void setList(List<Package> packages) {
        mAdapter.setList(packages);
    }

    @Override
    public void onShowDialog(String message) {
        showDialog(message);
    }

    @Override
    public void onHideDialog() {
        hideDialog();
    }


}
