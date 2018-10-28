package com.hornet.dateconverter.CalendarView;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hornet.dateconverter.DatePicker.DatePickerController;
import com.hornet.dateconverter.DatePicker.DatePickerDialog;
import com.hornet.dateconverter.DatePicker.DayPickerGroup;
import com.hornet.dateconverter.DatePicker.MonthAdapter;
import com.hornet.dateconverter.DatePicker.YearPickerView;
import com.hornet.dateconverter.R;

import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Kiran Gyawali on 10/28/2018.
 */
public class Calendar extends LinearLayout
        implements View.OnClickListener , DatePickerController {

    private TextView mDatePickerHeaderView;
    private LinearLayout mMonthAndDayView;
    private TextView mSelectedMonthTextView;
    private TextView mSelectedDayTextView;
    private TextView mYearView;
    private DayPickerGroup mDayPickerView;
    private YearPickerView mYearPickerView;


    public Calendar(Context context) {
        this(context, null);
    }


    public Calendar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Calendar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {
        setOrientation(LinearLayout.VERTICAL);
        Calendar view = (Calendar) LayoutInflater.from(getContext()).inflate(R.layout.mdtp_date_picker_dialog_v2, this, true);
        mDatePickerHeaderView = view.findViewById(R.id.mdtp_date_picker_header);
        mDatePickerHeaderView.setVisibility(GONE);
        mMonthAndDayView = view.findViewById(R.id.mdtp_date_picker_month_and_day);
        mMonthAndDayView.setOnClickListener(this);
        mSelectedMonthTextView = view.findViewById(R.id.mdtp_date_picker_month);
        mSelectedDayTextView = view.findViewById(R.id.mdtp_date_picker_day);
        mYearView = view.findViewById(R.id.mdtp_date_picker_year);
        mYearView.setOnClickListener(this);


        mDayPickerView = new DayPickerGroup(getContext(), this);
        mYearPickerView = new YearPickerView(getContext(), this);

    }


    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onYearSelected(int year) {

    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day, int dayOfMonth) {

    }

    @Override
    public void registerOnDateChangedListener(DatePickerDialog.OnDateChangedListener listener) {

    }

    @Override
    public void unregisterOnDateChangedListener(DatePickerDialog.OnDateChangedListener listener) {

    }

    @Override
    public MonthAdapter.CalendarDay getSelectedDay() {
        return null;
    }

    @Override
    public boolean isThemeDark() {
        return false;
    }

    @Override
    public int getAccentColor() {
        return 0;
    }

    @Override
    public boolean isHighlighted(int year, int month, int day) {
        return false;
    }

    @Override
    public int getFirstDayOfWeek() {
        return 0;
    }

    @Override
    public int getMinYear() {
        return 0;
    }

    @Override
    public int getMaxYear() {
        return 0;
    }

    @Override
    public java.util.Calendar getStartDate() {
        return null;
    }

    @Override
    public java.util.Calendar getEndDate() {
        return null;
    }

    @Override
    public boolean isOutOfRange(int year, int month, int day) {
        return false;
    }

    @Override
    public void tryVibrate() {

    }

    @Override
    public TimeZone getTimeZone() {
        return null;
    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public DatePickerDialog.Version getVersion() {
        return null;
    }

    @Override
    public DatePickerDialog.ScrollOrientation getScrollOrientation() {
        return null;
    }
}
