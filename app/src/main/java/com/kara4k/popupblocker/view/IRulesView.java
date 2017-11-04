package com.kara4k.popupblocker.view;


import com.kara4k.popupblocker.model.Rule;

import java.util.List;

public interface IRulesView extends IListView<List<Rule>> {

    void onRemoveRule(int position);

    void onEditRule(Rule rule);
}
