package com.hornet.dateconverter.CalendarView;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

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

import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Kiran Gyawali on 10/28/2018.
 */
public class Calendar extends LinearLayout
        implements View.OnClickListener, DatePickerController {

    private OnDateSetListener mCallback;
    private DefaultDateRangeLimiter mDefaultLimiter = new DefaultDateRangeLimiter();
    //    MdtpDatePickerViewAnimatorV2Binding mainBinding;
    private DayPickerGroup mDayPickerView;
    private AccessibleDateAnimator mAnimator;
    private Model mCalendar;
    private HashSet<java.util.Calendar> highlightedDays = new HashSet<>();
    private DateRangeLimiter mDateRangeLimiter = mDefaultLimiter;
    private boolean highlightSaturdays;

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

    public void setOnDateSetListener(OnDateSetListener listener) {
        this.mCallback = listener;
    }

    private void init(AttributeSet attrs, Context context) {
        /*LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mainBinding= MdtpDatePickerViewAnimatorV2Binding.inflate(inflater);
        */
        ((Activity) getContext())
                .getLayoutInflater()
                .inflate(R.layout.mdtp_date_picker_view_animator_v2, this, true);
        mCalendar = new DateConverter().getTodayNepaliDate();
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
    public void onDayOfMonthSelected(int year, int month, int day, int dayOfWeek) {
        mCalendar.setDayOfWeek(dayOfWeek);
        mCalendar.setYear(year);
        mCalendar.setMonth(month);
        mCalendar.setDay(day);
        notifyDateSet();
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
        java.util.Calendar date = java.util.Calendar.getInstance(getTimeZone());
        date.set(java.util.Calendar.YEAR, year);
        date.set(java.util.Calendar.MONTH, month);
        date.set(java.util.Calendar.DAY_OF_MONTH, day);
        Utils.trimToMidnight(date);
        return highlightedDays.contains(date);
    }

    @Override
    public int getFirstDayOfWeek() {
        return 1;//week start is sunday by default
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
        return DatePickerDialog.Version.VERSION_2; //default version 2
    }

    @Override
    public DatePickerDialog.ScrollOrientation getScrollOrientation() {
        return DatePickerDialog.ScrollOrientation.HORIZONTAL;
    }

    public void setHighlightedDays(List<Model> myHighlightedDays) {

        java.util.Calendar[] days = new java.util.Calendar[myHighlightedDays.size()];
        for (int i = 0; i < myHighlightedDays.size(); i++) {
            java.util.Calendar mDay = new GregorianCalendar(myHighlightedDays.get(i).getYear(), myHighlightedDays.get(i).getMonth(), myHighlightedDays.get(i).getDay());
            days[i] = mDay;
        }

        for (java.util.Calendar highlightedDay : days) {
            this.highlightedDays.add(Utils.trimToMidnight((java.util.Calendar) highlightedDay.clone()));
        }
        // Sort the array to optimize searching over it later on
        ///Arrays.sort(highlightedDays);
        if (mDayPickerView != null) mDayPickerView.onChange();
    }


    private void setSelectableDays(List<Model> myList) {
        java.util.Calendar[] days = new java.util.Calendar[myList.size()];
        for (int i = 0; i < myList.size(); i++) {
            java.util.Calendar mDay = new GregorianCalendar(myList.get(i).getYear(), myList.get(i).getMonth(), myList.get(i).getDay());
            days[i] = mDay;
        }
        mDefaultLimiter.setSelectableDays(days);
        if (mDayPickerView != null) mDayPickerView.onChange();
    }

    private void highlightAllSaturdays(boolean highlight) {
        highlightSaturdays = highlight;

        if (highlightSaturdays) {
            highlightedDays.addAll(getSaturdays());
        } else {
            if (highlightedDays.containsAll(getSaturdays())) {
                highlightedDays.removeAll(getSaturdays());
            }
        }
    }

    private HashSet<java.util.Calendar> getSaturdays() {
        HashSet<java.util.Calendar> saturdaysHashSet = new HashSet<>();
        List<Model> saturdays = DateConverter.getAllSaturdays();
        java.util.Calendar[] days = new java.util.Calendar[saturdays.size()];
        for (int i = 0; i < saturdays.size(); i++) {
            java.util.Calendar mDay = new GregorianCalendar(saturdays.get(i).getYear(), saturdays.get(i).getMonth(), saturdays.get(i).getDay());
            days[i] = mDay;
        }
        for (java.util.Calendar highlightedDay : days) {
            saturdaysHashSet.add(Utils.trimToMidnight((java.util.Calendar) highlightedDay.clone()));
        }
        return saturdaysHashSet;
    }


    public void notifyDateSet() {
        if (mCallback != null) {
            mCallback.onDateClick(Calendar.this,  mCalendar.getYear(),
                    mCalendar.getMonth(), mCalendar.getDay());
        }
    }


    public interface OnDateSetListener {


        void onDateClick(View calendar, int year, int month, int day);
    }

}
