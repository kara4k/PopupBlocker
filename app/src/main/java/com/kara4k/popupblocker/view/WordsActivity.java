package com.kara4k.popupblocker.view;


import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.kara4k.popupblocker.R;
import com.kara4k.popupblocker.di.DaggerWordsComponent;
import com.kara4k.popupblocker.di.modules.WordsModule;
import com.kara4k.popupblocker.model.Word;
import com.kara4k.popupblocker.presenter.WordsPresenter;
import com.kara4k.popupblocker.view.adapters.WordsAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class WordsActivity extends BaseActivity implements IWordsView, View.OnClickListener {

    @BindView(R.id.add_button)
    ImageButton mAddButton;
    @BindView(R.id.word_edit_text)
    EditText mEditText;

    @Inject
    WordsPresenter mPresenter;

    WordsAdapter mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_words;
    }

    @Override
    protected void onViewReady() {
        super.onViewReady();
        setupBackArrow();
        mAddButton.setOnClickListener(this);
        mRecyclerView.setAdapter(mAdapter = new WordsAdapter(mPresenter));
        mPresenter.onStart();
    }

    @Override
    protected void injectDependencies() {
        DaggerWordsComponent.builder()
                .appComponent(getAppComponent())
                .wordsModule(new WordsModule(this))
                .build().injectWordsActivity(this);
    }

    @Override
    public void setList(List<Word> words) {
        mAdapter.setList(words);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, WordsActivity.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_words_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_clear:
                mEditText.setText("");
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_button:
                mPresenter.addWord(mEditText.getText().toString());
                mEditText.setText("");
                break;
        }
    }
}
