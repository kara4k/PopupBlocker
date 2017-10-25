package com.kara4k.popupblocker.view.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kara4k.popupblocker.R;
import com.kara4k.popupblocker.model.Word;
import com.kara4k.popupblocker.presenter.WordsPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.Holder> {

    private WordsPresenter mPresenter;

    private List<Word> mList;

    public WordsAdapter(WordsPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.word_holder, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.onBind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void setList(List<Word> list) {
        mList = list;
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.num_text_view)
        TextView mNumTextView;
        @BindView(R.id.word_text_view)
        TextView mWordTextView;
        @BindView(R.id.delete_image_view)
        ImageView mDeleteImageView;     // TODO: 25.10.2017 remake, ugly

        private Word mWord;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mDeleteImageView.setOnClickListener(this);
        }

        public void onBind(Word word) {
            mWord = word;

            mNumTextView.setText(String.valueOf(getAdapterPosition() + 1));
            mWordTextView.setText(word.getText());
        }

        @Override
        public void onClick(View v) {
            mPresenter.onDeleteWord(mWord);
        }
    }
}
