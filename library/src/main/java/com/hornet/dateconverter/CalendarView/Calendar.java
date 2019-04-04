package com.hornet.dateconverter.CalendarView;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hornet.dateconverter.DateConverter;
import com.hornet.dateconverter.DatePicker.AccessibleDateAnimator;
import com.hornet.dateconverter.DatePicker.DatePickerController;
import com.hornet.dateconverter.DatePicker.DatePickerDialog;
import com.hornet.dateconverter.DatePicker.DateRangeLimiter;
import com.hornet.dateconverter.DatePicker.DayPickerGroup;
import com.hornet.dateconverter.DatePicker.DefaultDateRangeLimiter;
import com.hornet.dateconverter.DatePicker.MonthAdapter;
import com.hornet.dateconverter.Model;
import com.hornet.dateconverter.R;
import com.hornet.dateconverter.Utils;

import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Kiran Gyawali on 10/28/2018.
 */
public class Calendar extends LinearLayout
        implements View.OnClickListener, DatePickerController {

    private DefaultDateRangeLimiter mDefaultLimiter = new DefaultDateRangeLimiter();

    private DayPickerGroup mDayPickerView;
    private AccessibleDateAnimator mAnimator;
    private java.util.Calendar mCalendar;

    private DateRangeLimiter mDateRangeLimiter = mDefaultLimiter;

    public Calendar(Context context) {
        this(context, null);
    }


    public Calendar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Calendar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, context);

    }

    private void init(AttributeSet attrs, Context context) {
        ((Activity) getContext())
                .getLayoutInflater()
                .inflate(R.layout.mdtp_date_picker_view_animator_v2, this, true);
        Model today = new DateConverter().getTodayNepaliDate();
        mCalendar = DateConverter.convertModelToCalendar(today);
        mAnimator = findViewById(R.id.mdtp_animator);
        mDayPickerView = new DayPickerGroup(context, this);
        mDefaultLimiter.setController(this);
        mAnimator.addView(mDayPickerView);
        mAnimator.setDateMillis(java.util.Calendar.getInstance().getTimeInMillis());
        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(300);
        mAnimator.setInAnimation(animation);
        Animation animation2 = new AlphaAnimation(1.0f, 0.0f);
        animation2.setDuration(300);
        mAnimator.setOutAnimation(animation2);

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
        Toast.makeText(getContext(), "onClick " + day, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void registerOnDateChangedListener(DatePickerDialog.OnDateChangedListener listener) {

    }

    @Override
    public void unregisterOnDateChangedListener(DatePickerDialog.OnDateChangedListener listener) {

    }

    @Override
    public MonthAdapter.CalendarDay getSelectedDay() {
        return new MonthAdapter.CalendarDay(new DateConverter().getTodayNepaliDate());
    }

    @Override
    public boolean isThemeDark() {
        return false;
    }

    @Override
    public int getAccentColor() {
        return Utils.getAccentColorFromThemeIfAvailable(getContext());
    }

    @Override
    public boolean isHighlighted(int year, int month, int day) {
        return false;
    }

    @Override
    public int getFirstDayOfWeek() {
        return 7;
    }

    @Override
    public int getMinYear() {
        return mDateRangeLimiter.getMinYear();
    }

    @Override
    public int getMaxYear() {
        return mDateRangeLimiter.getMaxYear();
    }

    @Override
    public java.util.Calendar getStartDate() {
        return mDateRangeLimiter.getStartDate();
    }

    @Override
    public java.util.Calendar getEndDate() {
        return mDateRangeLimiter.getEndDate();
    }

    @Override
    public boolean isOutOfRange(int year, int month, int day) {
        return mDateRangeLimiter.isOutOfRange(year, month, day);
    }

    @Override
    public void tryVibrate() {

    }

    @Override
    public TimeZone getTimeZone() {
        return TimeZone.getDefault();
    }

    @Override
    public Locale getLocale() {
        return Locale.getDefault();
    }

    @Override
    public DatePickerDialog.Version getVersion() {
        return DatePickerDialog.Version.VERSION_2;
    }

    @Override
    public DatePickerDialog.ScrollOrientation getScrollOrientation() {
        return DatePickerDialog.ScrollOrientation.HORIZONTAL;
    }
}
