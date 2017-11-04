package com.kara4k.popupblocker.presenter.impl;


import com.kara4k.popupblocker.model.DaoSession;
import com.kara4k.popupblocker.model.Rule;
import com.kara4k.popupblocker.model.RuleDao;
import com.kara4k.popupblocker.presenter.ListPresenter;
import com.kara4k.popupblocker.view.IRulesView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.internal.operators.single.SingleFromCallable;
import io.reactivex.schedulers.Schedulers;

public class RulesPresenter extends ListPresenter<List<Rule>, IRulesView> {

    private RuleDao mRuleDao;
    private String mPackageName;

    @Inject
    public RulesPresenter(DaoSession daoSession) {
        mRuleDao = daoSession.getRuleDao();
    }

    /**
     * Get rules from database
     * @param packageName Name of package
     * @return List of rules belongs to package
     */
    private List<Rule> getRules(String packageName) {
        List<Rule> list = mRuleDao.queryBuilder()
                .where(RuleDao.Properties.PackageName.eq(packageName))
                .build().list();
        return list;
    }

    public void onStart(String packageName) {
        mPackageName = packageName;

        SingleFromCallable.fromCallable(() -> getRules(packageName))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    public void onDeleteRule(Rule rule, int position) {
        Completable completable = Completable.fromAction(() -> mRuleDao.delete(rule));
        subscribe(completable, () -> getView().onRemoveRule(position));
    }

    public void onEditRule(Rule rule) {
        getView().onEditRule(rule);
    }

    public void onUpdateRule(Rule rule) {
        Completable completable = Completable.fromAction(() -> mRuleDao.insertOrReplace(rule));
        subscribe(completable, () -> onStart(mPackageName));
    }

}
