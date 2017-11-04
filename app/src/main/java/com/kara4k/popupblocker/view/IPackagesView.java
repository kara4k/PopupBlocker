package com.kara4k.popupblocker.view;


import com.kara4k.popupblocker.model.Package;

import java.util.List;

public interface IPackagesView extends IListView<List<Package>>{

    void onShowDialog(String message);

    void onHideDialog();

}
