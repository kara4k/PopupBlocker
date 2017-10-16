package com.kara4k.popupblocker.view;


import com.kara4k.popupblocker.model.Package;

import java.util.List;

public interface IMainView {

    void setList(List<Package> packages);

    void onShowDialog(String message);

    void onHideDialog();
}
