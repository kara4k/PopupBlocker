package com.kara4k.popupblocker.view.Adapters;


import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.kara4k.popupblocker.R;
import com.kara4k.popupblocker.presenter.MainPresenter;
import com.kara4k.popupblocker.model.Package;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

    MainPresenter mPresenter;

    private List<Package> mList;

    public Adapter(MainPresenter mainPresenter) {
        mPresenter = mainPresenter;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.package_item, parent, false);
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

    public void setList(List<Package> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public void setSelected(int position) {
        notifyItemChanged(position);
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.app_name)
        TextView mAppNameTextView;
        @BindView(R.id.package_name)
        TextView mPackageNameTextView;
        @BindView(R.id.icon)
        ImageView mIconImageView;
        @BindView(R.id.checkbox)
        CheckBox mCheckbox;

        private Package mPackage;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void onBind(Package aPackage) {
            mPackage = aPackage;

            mAppNameTextView.setText(aPackage.getAppName());
            mPackageNameTextView.setText(aPackage.getPackageName());
            mIconImageView.setImageDrawable(aPackage.getIcon());
            mCheckbox.setChecked(aPackage.isSelected());
            setTextColor(aPackage.getSystem());
        }

        private void setTextColor(int system) {
            int textColor = system == 0 ? Color.BLACK : Color.RED;

            mAppNameTextView.setTextColor(textColor);
            mPackageNameTextView.setTextColor(textColor);
        }

        @Override
        public void onClick(View v) {
            boolean isSelected = mList.get(getAdapterPosition()).isSelected();

            mPresenter.setSelected(mPackage, getAdapterPosition(), !isSelected);
        }
    }

}
