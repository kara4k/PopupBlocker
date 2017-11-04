package com.kara4k.popupblocker.view.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kara4k.popupblocker.R;
import com.kara4k.popupblocker.model.Rule;
import com.kara4k.popupblocker.presenter.impl.RulesPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RulesAdapter extends RecyclerView.Adapter<RulesAdapter.Holder> {

    private List<Rule> mList;
    private RulesPresenter mPresenter;

    public RulesAdapter(RulesPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rule_holder, parent, false);
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

    public void setList(List<Rule> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public void removeRule(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.contain_text_view)
        TextView mSearchTextView;
        @BindView(R.id.send_back_text_view)
        TextView mSendBackTextView;
        @BindView(R.id.press_text_view)
        TextView mPressTextView;
        @BindView(R.id.delete_image_view)
        ImageView mDeleteImageView;

        private Context mContext;
        private Rule mRule;

        public Holder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void onBind(Rule rule) {
            mRule = rule;
            String searchText = mContext.getString(R.string.rule_search, rule.getSearchText());
            String pressText = mContext.getString(R.string.rule_press, rule.getPressText());

            if (rule.getSendBackPress()) {
                mSendBackTextView.setVisibility(View.VISIBLE);
                mPressTextView.setVisibility(View.GONE);
            } else {
                mSendBackTextView.setVisibility(View.GONE);
                mPressTextView.setVisibility(View.VISIBLE);
                mPressTextView.setText(pressText);
            }

            mSearchTextView.setText(searchText);
        }

        @OnClick(R.id.delete_image_view)
        void onDeleteClick() {
            mPresenter.onDeleteRule(mRule, getAdapterPosition());
        }

        @Override
        public void onClick(View v) {
            mPresenter.onEditRule(mRule);
        }
    }
}
