package com.kara4k.popupblocker.view;


import com.kara4k.popupblocker.model.Package;

import java.util.List;

public interface IPackagesView {

    void setList(List<Package> packages);

    void onShowDialog(String message);

    void onHideDialog();

    void updatePackageView(int position);
}
