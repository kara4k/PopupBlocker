package com.kara4k.popupblocker.view;


import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.kara4k.popupblocker.R;
import com.kara4k.popupblocker.di.DaggerPackagesComponent;
import com.kara4k.popupblocker.di.modules.PackagesModule;
import com.kara4k.popupblocker.model.Package;
import com.kara4k.popupblocker.presenter.PackagesPresenter;
import com.kara4k.popupblocker.view.adapters.PackagesAdapter;

import java.util.List;

import javax.inject.Inject;

public class SettingsActivity extends BaseActivity implements IPackagesView, SearchView.OnQueryTextListener {

    @Inject
    PackagesPresenter mPresenter;

    PackagesAdapter mPackagesAdapter;

    private SearchView mSearchView;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onViewReady() {
        super.onViewReady();
        mRecyclerView.setAdapter(mPackagesAdapter = new PackagesAdapter(mPresenter));
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
        mPackagesAdapter.setList(packages);
    }

    @Override
    public void onShowDialog(String message) {
        showDialog(message);
    }

    @Override
    public void onHideDialog() {
        hideDialog();
    }

    @Override
    public void updatePackageView(int position) {
        mPackagesAdapter.setSelected(position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings_activity, menu);
        MenuItem mSearchItem = menu.findItem(R.id.menu_item_search);
        mSearchView = (SearchView) mSearchItem.getActionView();
        mSearchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_toggle_system:
                mPresenter.toggleSysVisibility();
                break;
            case R.id.menu_item_edit_words:
                startActivity(WordsActivity.newIntent(this));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mPresenter.onSearch(newText);
        return false;
    }
}
