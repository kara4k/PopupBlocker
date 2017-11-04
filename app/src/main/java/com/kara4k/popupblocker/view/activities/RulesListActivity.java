package com.kara4k.popupblocker.view.activities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;

import com.kara4k.popupblocker.R;
import com.kara4k.popupblocker.di.DaggerRulesComponent;
import com.kara4k.popupblocker.di.modules.RulesModule;
import com.kara4k.popupblocker.model.Rule;
import com.kara4k.popupblocker.presenter.impl.RulesPresenter;
import com.kara4k.popupblocker.view.IRulesView;
import com.kara4k.popupblocker.view.adapters.RulesAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class RulesListActivity extends BaseListActivity implements IRulesView {

    private static final String PACKAGE_NAME = "package";
    public static final String RULE = "rule";

    private static final int CREATE_RULE = 1;
    private static final int EDIT_RULE = 2;


    @BindView(R.id.fab)
    FloatingActionButton mFab;

    @Inject
    RulesPresenter mPresenter;

    private RulesAdapter mAdapter;
    private String mPackageName;


    @Override
    protected int getContentView() {
        return R.layout.activity_rules;
    }

    @OnClick(R.id.fab)
    void onFabClicked() {
        Intent intent = RuleCreatorActivity.newIntent(this, mPackageName);
        startActivityForResult(intent, CREATE_RULE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Rule rule = (Rule) data.getSerializableExtra(RULE);
            if (rule == null) return;

            mPresenter.onUpdateRule(rule);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onViewReady() {
        super.onViewReady();
        mRecyclerView.setAdapter(mAdapter = new RulesAdapter(mPresenter));

        if (getIntent() == null) return;
        mPackageName = getIntent().getStringExtra(PACKAGE_NAME);
        if (mPackageName == null) return;

        mPresenter.onStart(mPackageName);
    }

    @Override
    protected void injectDependencies() {
        DaggerRulesComponent.builder()
                .appComponent(getAppComponent())
                .rulesModule(new RulesModule(this))
                .build().injectRulesActivity(this);
    }

    @Override
    public void setList(List<Rule> rules) {
        mAdapter.setList(rules);
    }

    @Override
    public void showError(String message) {
        showToast(message);
    }

    @Override
    public void onRemoveRule(int position) {
        mAdapter.removeRule(position);
    }

    @Override
    public void onEditRule(Rule rule) {
        Intent intent = RuleCreatorActivity.newIntent(this, rule);
        startActivityForResult(intent, EDIT_RULE);
    }

    public static Intent newIntent(Context context, String packageName) {
        Intent intent = new Intent(context, RulesListActivity.class);
        intent.putExtra(PACKAGE_NAME, packageName);
        return intent;
    }
}
