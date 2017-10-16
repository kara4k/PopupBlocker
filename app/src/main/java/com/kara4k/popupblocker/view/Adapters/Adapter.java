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
import com.kara4k.popupblocker.model.Package;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

    List<Package> mList;

    @Inject
    public Adapter() {
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

    class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.app_name) TextView mAppNameTV;
        @BindView(R.id.package_name) TextView mPackageNameTV;
        @BindView(R.id.icon) ImageView mIconIV;
        @BindView(R.id.checkbox)CheckBox mSelectedCB;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBind(Package aPackage) {
            mAppNameTV.setText(aPackage.getAppName());
            mPackageNameTV.setText(aPackage.getPackageName());
            mIconIV.setImageDrawable(aPackage.getIcon());

            setTextColor(aPackage.getSystem());
            // TODO: 16.10.2017 setChecked
        }

        private void setTextColor(int system) {
            int textColor = system == 0 ? Color.BLACK : Color.RED;
            mAppNameTV.setTextColor(textColor);
            mPackageNameTV.setTextColor(textColor);
        }
    }

}
