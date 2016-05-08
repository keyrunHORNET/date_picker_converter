package com.hornet.dateconverter.DatePicker;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Hornet on 4/30/2016.
 */
public class SimpleDayPickerView extends DayPickerView {

    public SimpleDayPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleDayPickerView(Context context, DatePickerController controller) {
        super(context, controller);
    }
    @Override
    public MonthAdapter createMonthAdapter(Context context, DatePickerController controller) {
        return new SimpleMonthAdapter(context, controller);
    }

}
