package com.hornet.dateconverter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by Hornet on 4/30/2016.
 */
public class AccessibleTextView extends AppCompatTextView {
    public AccessibleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(Button.class.getName());
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(Button.class.getName());
    }

}
