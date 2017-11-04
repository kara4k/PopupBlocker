package com.kara4k.popupblocker.view.adapters;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.kara4k.popupblocker.R;
import com.kara4k.popupblocker.model.Package;
import com.kara4k.popupblocker.presenter.impl.PackagesPresenter;
import com.kara4k.popupblocker.view.activities.RulesListActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PackagesAdapter extends RecyclerView.Adapter<PackagesAdapter.Holder> {

    PackagesPresenter mPresenter;

    private List<Package> mList;

    public PackagesAdapter(PackagesPresenter packagesPresenter) {
        mPresenter = packagesPresenter;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.package_holder, parent, false);
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

        @OnClick(R.id.checkbox)
        void onCheckBoxClick(CheckBox checkBox) {
            mPresenter.setSelected(mPackage, checkBox.isChecked());
        }

        @Override
        public void onClick(View v) {
            Context context = itemView.getContext();
            context.startActivity(RulesListActivity.newIntent(context, mPackage.getPackageName()));
        }
    }

}
