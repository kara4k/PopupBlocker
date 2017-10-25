package com.kara4k.popupblocker.service;


import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class PopupService extends AccessibilityService {

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.e("PopupService", "onServiceConnected: " + "started!");
            super.onServiceConnected();
            AccessibilityServiceInfo info = getServiceInfo();
            info.packageNames = null;
//        new String[] {"com.example.android.myFirstApp", "com.example.android.mySecondApp"};
        setServiceInfo(info);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        final int eventType = event.getEventType();
        Log.e("PopupService", "onAccessibilityEvent: " + event.getText().toString());
        Log.e("PopupService", "onAccessibilityEvent: " + event.toString());
        AccessibilityNodeInfo source = event.getSource();
        if (source != null) {
            Log.e("PopupService", "onAccessibilityEvent: " + source.getText());
        }

        if (event.getClassName().toString().contains("AlertDialog") && event.getText().toString().toLowerCase().contains("sort")) {
            performGlobalAction(GLOBAL_ACTION_BACK);
            Log.e("PopupService", "onAccessibilityEvent: " + "done");
        }
        String eventText = null;
        switch(eventType) {
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                eventText = "Focused: ";
                break;
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                eventText = "Focused: ";
                break;

        }

        eventText = eventText + event.getContentDescription();
        Log.e("PopupService", "onAccessibilityEvent: " + eventText);
    }

    @Override
    public void onInterrupt() {

    }
}
