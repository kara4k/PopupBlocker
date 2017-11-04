package com.kara4k.popupblocker.view.activities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.kara4k.popupblocker.R;
import com.kara4k.popupblocker.model.Rule;
import com.kara4k.popupblocker.view.custom.ItemView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RuleCreatorActivity extends AppCompatActivity {

    public static final String PACKAGE_NAME = "package_name";
    public static final String RULE = "rule";

    public static final String TYPE = "type";
    public static final int TYPE_NEW = 1;
    public static final int TYPE_EDIT = 2;

    @BindView(R.id.search_edit_text)
    EditText mSearchEditText;
    @BindView(R.id.press_edit_text)
    EditText mPressEditText;
    @BindView(R.id.back_press_item_view)
    ItemView mBackPessItemView;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.press_layout)
    LinearLayout mPressLayout;

    private Rule mRule;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_rule);
        ButterKnife.bind(this);
        onViewReady();
    }

    private void onViewReady() {
        if (getIntent() == null) return;
        int type = getIntent().getIntExtra(TYPE, TYPE_NEW);

        if (type == TYPE_EDIT) {
            mRule = (Rule) getIntent().getSerializableExtra(RULE);
            mSearchEditText.setText(mRule.getSearchText());
            mPressEditText.setText(mRule.getPressText());
            mBackPessItemView.setChecked(mRule.isSendBackPress());
        }

        if (type == TYPE_NEW) {
            mRule = new Rule();
            String packageName = getIntent().getStringExtra(PACKAGE_NAME);
            mRule.setPackageName(packageName);
        }
    }

    @OnClick(R.id.fab)
    void onFabClicked() {
        mRule.setSearchText(mSearchEditText.getText().toString());
        mRule.setPressText(mPressEditText.getText().toString());
        mRule.setSendBackPress(mBackPessItemView.isChecked());

        Intent intent = new Intent();
        intent.putExtra(RULE, mRule);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public static Intent newIntent(Context context, String packageName) {
        Intent intent = new Intent(context, RuleCreatorActivity.class);
        intent.putExtra(PACKAGE_NAME, packageName);
        intent.putExtra(TYPE, TYPE_NEW);
        return intent;
    }

    public static Intent newIntent(Context context, Rule rule) {
        Intent intent = new Intent(context, RuleCreatorActivity.class);
        intent.putExtra(RULE, rule);
        intent.putExtra(TYPE, TYPE_EDIT);
        return intent;
    }
}
