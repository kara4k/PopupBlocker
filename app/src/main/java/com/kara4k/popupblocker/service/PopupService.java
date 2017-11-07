package com.kara4k.popupblocker.service;


import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.support.annotation.NonNull;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.kara4k.popupblocker.other.App;
import com.kara4k.popupblocker.model.DaoSession;
import com.kara4k.popupblocker.model.Package;
import com.kara4k.popupblocker.model.Rule;
import com.kara4k.popupblocker.model.RuleDao;

import java.util.List;

import javax.inject.Inject;

public class PopupService extends AccessibilityService {

    @Inject
    DaoSession mDaoSession;


    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        injectDependencies();
        setupServiceInfo();
    }

    /**
     * Setup service info with packages to track
     */
    private void setupServiceInfo() {
        AccessibilityServiceInfo info = getServiceInfo();
        info.packageNames = getRegisteredPackages();
        setServiceInfo(info);
    }

    /**
     * Get array of package names from database to track
     *
     * @return array of package names
     */
    @NonNull
    private String[] getRegisteredPackages() {
        List<Package> packages = mDaoSession.getPackageDao().queryBuilder().build().list();
        String[] packagesArray = new String[packages.size()];
        for (int i = 0; i < packages.size(); i++) {
            packagesArray[i] = packages.get(i).getPackageName();
        }
        return packagesArray;
    }


    private void injectDependencies() {
        ((App) getApplication()).getAppComponent().injectService(this);
    }

    /**
     * Events trigger
     *
     * @param event current event
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (isDialog(event)) {
            checkRules(event);
        }
    }

    /**
     * Check for event equals rule and apply actions
     *
     * @param event current event
     */
    private void checkRules(AccessibilityEvent event) {
        String dialogText = event.getText().toString();
        String packageName = event.getPackageName().toString();
        List<Rule> rules = getRules(packageName);

        for (Rule rule : rules) {
            String searchText = rule.getSearchText();

            if (searchText == null || searchText.trim().equals("")) break;

            if (dialogText.contains(searchText)) {
                applyRule(event, rule);
                break;
            }
        }
    }

    /**
     * Apply rule for event
     *
     * @param event current event
     * @param rule  rule to apply
     */
    private void applyRule(AccessibilityEvent event, Rule rule) {
        if (rule.getSendBackPress()) {
            performGlobalAction(GLOBAL_ACTION_BACK);
        } else if (rule.getPressText() != null && !rule.getPressText().equals("")) {
            AccessibilityNodeInfo source = event.getSource();
            tryToPerformClick(source, rule.getPressText());
        }
    }

    /**
     * Search for inner views recursively and perform action click,
     * if view text is equals to rule press text
     *
     * @param info parent node info
     * @param text text used for equals checks
     */
    private void tryToPerformClick(AccessibilityNodeInfo info, String text) {
        if (info != null) {
            int childCount = info.getChildCount();
            for (int i = 0; i < childCount; i++) {
                AccessibilityNodeInfo child = info.getChild(i);
                if (child != null) {
                    if (child.getText() != null && child.getText().toString().toLowerCase().trim()
                            .equals(text.toLowerCase().trim())) {
                        child.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        return;
                    }
                    tryToPerformClick(child, text);
                }
            }
        }
    }


    /**
     * Get rules for specified package from database
     *
     * @param packageName name of package
     * @return list of rules for specified package
     */
    private List<Rule> getRules(String packageName) {
        List<Rule> rules = mDaoSession.getRuleDao().queryBuilder()
                .where(RuleDao.Properties.PackageName.eq(packageName))
                .build().list();
        return rules;
    }

    /**
     * Check if triggered event view is dialog, by classname contains "dialog"
     *
     * @param event current event
     * @return is current view a dialog view
     */
    private boolean isDialog(AccessibilityEvent event) {
        return event.getClassName().toString().toLowerCase().contains("dialog");
    }

    @Override
    public void onInterrupt() {

    }
}
