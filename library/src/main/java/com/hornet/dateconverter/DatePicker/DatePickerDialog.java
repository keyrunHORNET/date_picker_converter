package com.hornet.dateconverter.DatePicker;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.hornet.dateconverter.DateConverter;
import com.hornet.dateconverter.Model;
import com.hornet.dateconverter.R;
import com.hornet.dateconverter.TypefaceHelper;
import com.hornet.dateconverter.Utils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Hornet on 4/30/2016.
 */
public class DatePickerDialog extends DialogFragment implements
        View.OnClickListener, DatePickerController {

    private static final String KEY_DATERANGELIMITER = "datechangelimiter";

    public enum Version {
        VERSION_1,
        VERSION_2
    }

    public enum ScrollOrientation {
        HORIZONTAL,
        VERTICAL
    }

    private static final String TAG = DatePickerDialog.class.getSimpleName();

    private static final int UNINITIALIZED = -1;
    private static final int MONTH_AND_DAY_VIEW = 0;
    private static final int YEAR_VIEW = 1;

    private static final String KEY_SELECTED_YEAR = "year";
    private static final String KEY_SELECTED_MONTH = "month";
    private static final String KEY_SELECTED_DAY = "day";
    private static final String KEY_LIST_POSITION = "list_position";
    private static final String KEY_WEEK_START = "week_start";
    private static final String KEY_YEAR_START = "year_start";
    private static final String KEY_YEAR_END = "year_end";
    private static final String KEY_CURRENT_VIEW = "current_view";
    private static final String KEY_LIST_POSITION_OFFSET = "list_position_offset";
    private static final String KEY_HIGHLIGHTED_DAYS = "highlighted_days";
    private static final String KEY_MIN_DATE = "min_date";
    private static final String KEY_MAX_DATE = "max_date";
    private static final String KEY_SELECTABLE_DAYS = "selectable_days";
    private static final String KEY_THEME_DARK = "theme_dark";
    private static final String KEY_THEME_DARK_CHANGED = "theme_dark_changed";
    private static final String KEY_ACCENT = "accent";
    private static final String KEY_VIBRATE = "vibrate";
    private static final String KEY_DISMISS = "dismiss";
    private static final String KEY_AUTO_DISMISS = "auto_dismiss";
    private static final String KEY_DEFAULT_VIEW = "default_view";
    private static final String KEY_TITLE = "title";
    private static final String KEY_OK_RESID = "ok_resid";
    private static final String KEY_OK_STRING = "ok_string";
    private static final String KEY_CANCEL_RESID = "cancel_resid";
    private static final String KEY_CANCEL_STRING = "cancel_string";

    private static final String KEY_TIMEZONE = "timezone";

    /*
     *
     * starting and ending of an english year
     */
    //private static final int DEFAULT_END_YEAR = 2100;
    //private static final int DEFAULT_START_YEAR = 1900;


    /*
     * starting and ending of nepali year
     * */
    //private static final int DEFAULT_END_YEAR = 2090;
    //private static final int DEFAULT_START_YEAR = 2000;

    private static final int ANIMATION_DURATION = 300;
    private static final int ANIMATION_DELAY = 500;

    private static SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy", Locale.getDefault());
    private static SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("dd", Locale.getDefault());

    private static final String KEY_OK_COLOR = "ok_color";
    private static final String KEY_CANCEL_COLOR = "cancel_color";
    private static final String KEY_VERSION = "version";
    private static final String KEY_SCROLL_ORIENTATION = "scrollorientation";
    private static final String KEY_LOCALE = "locale";


    private static SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat("MMM", Locale.getDefault());
    private static SimpleDateFormat VERSION_2_FORMAT;


    private DefaultDateRangeLimiter mDefaultLimiter = new DefaultDateRangeLimiter();

    private Model mCalendar = new Model();
    //private Calendar mCalendar = Calendar.getInstance();
    //private final Calendar mCalendar = new DateConverter().getNepaliDate(Calendar.getInstance());
    private OnDateSetListener mCallBack;
    private HashSet<OnDateChangedListener> mListeners = new HashSet<>();
    private DialogInterface.OnCancelListener mOnCancelListener;
    private DialogInterface.OnDismissListener mOnDismissListener;
    private DateRangeLimiter mDateRangeLimiter = mDefaultLimiter;

    private AccessibleDateAnimator mAnimator;

    //private TextView mDayOfWeekView;
    private TextView mDatePickerHeaderView;
    private LinearLayout mMonthAndDayView;
    private TextView mSelectedMonthTextView;
    private TextView mSelectedDayTextView;
    private TextView mYearView;
    private DayPickerGroup mDayPickerView;
    private YearPickerView mYearPickerView;

    private int mCurrentView = UNINITIALIZED;

    //private int mWeekStart = mCalendar.getFirstDayOfWeek();
    private int mWeekStart = 1;//week start is sunday by default
    private String mTitle;
    private Calendar mMinDate;
    private Calendar mMaxDate;
    private HashSet<Calendar> highlightedDays = new HashSet<>();
    private Calendar[] selectableDays;
    private boolean mThemeDark = false;
    private boolean mThemeDarkChanged = false;
    private int mAccentColor = -1;
    private boolean mVibrate = true;
    private boolean mDismissOnPause = false;
    private boolean mAutoDismiss = false;
    private int mDefaultView = MONTH_AND_DAY_VIEW;
    private int mOkResid = R.string.mdtp_ok;
    private Version mVersion;
    private ScrollOrientation mScrollOrientation;
    private String mOkString;
    private int mCancelResid = R.string.mdtp_cancel;
    private String mCancelString;
    private TimeZone mTimezone;
    private int mOkColor = -1;
    private int mCancelColor = -1;
    private Locale mLocale = Locale.getDefault();

    //private HapticFeedbackController mHapticFeedbackController;

    private boolean mDelayAnimation = true;

    // Accessibility strings.
    private String mDayPickerDescription;
    private String mYearPickerDescription;
    private String mSelectDay;
    private String mSelectYear;

    //private Model currentNepaliDate;
    DateConverter dc;

    /**
     * The callback used to indicate the user is done filling in the date.
     */
    public interface OnDateSetListener {

        /**
         * @param view        The view associated with this listener.
         * @param year        The year that was set.
         * @param monthOfYear The month that was set (0-11) for compatibility
         *                    with {@link java.util.Calendar}.
         * @param dayOfMonth  The day of the month that was set.
         */
        void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth);
    }

    /**
     * The callback used to notify other date picker components of a change in selected date.
     */
    public interface OnDateChangedListener {

        void onDateChanged();
    }


    public DatePickerDialog() {
        // Empty constructor required for dialog fragment.
    }

    /**
     * @param callBack    How the parent is notified that the date is set.
     * @param year        The initial year of the dialog.
     * @param monthOfYear The initial month of the dialog.
     * @param dayOfMonth  The initial day of the dialog.
     */
    public static DatePickerDialog newInstance(OnDateSetListener callBack, int year,
                                               int monthOfYear,
                                               int dayOfMonth) {
        DatePickerDialog ret = new DatePickerDialog();
        ret.initialize(callBack, year, monthOfYear, dayOfMonth);
        return ret;
    }

    @SuppressWarnings("unused")
    public static DatePickerDialog newInstance(OnDateSetListener callback) {
        DateConverter dc = new DateConverter();
        return DatePickerDialog.newInstance(callback, dc.getTodayNepaliDate());
    }

    @SuppressWarnings("unused")
    public static DatePickerDialog newInstance(OnDateSetListener callback, Model initialSelection) {
        DatePickerDialog ret = new DatePickerDialog();
        ret.initialize(callback, initialSelection);
        return ret;
    }

    /*private void initialize(OnDateSetListener callBack,Calendar initialSelection){
        mCallBack=callBack;
        mCalendar=Utils.trimToMidnight((Calendar) initialSelection.clone());

    }
    */

    public void initialize(OnDateSetListener callBack, Model initialSelection) {
        mCallBack = callBack;
        mCalendar.setYear(initialSelection.getYear());
        mCalendar.setMonth(initialSelection.getMonth());
        mCalendar.setDay(initialSelection.getDay());
        mScrollOrientation = null;

        mVersion = Build.VERSION.SDK_INT < Build.VERSION_CODES.M ? Version.VERSION_1 : Version.VERSION_2;
    }

    public void initialize(OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
        this.initialize(callBack, new Model(year, monthOfYear, dayOfMonth));
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Activity activity = getActivity();
        activity.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mCurrentView = UNINITIALIZED;
        if (savedInstanceState != null) {

            mCalendar.setYear(savedInstanceState.getInt(KEY_SELECTED_YEAR));
            mCalendar.setMonth(savedInstanceState.getInt(KEY_SELECTED_MONTH));
            mCalendar.setDay(savedInstanceState.getInt(KEY_SELECTED_DAY));
            mDefaultView = savedInstanceState.getInt(KEY_DEFAULT_VIEW);
        }

        if (Build.VERSION.SDK_INT < 18) {
            VERSION_2_FORMAT = new SimpleDateFormat(activity.getResources().getString(R.string.mdtp_date_v2_daymonthyear), mLocale);
        } else {
            VERSION_2_FORMAT = new SimpleDateFormat(DateFormat.getBestDateTimePattern(mLocale, "EEEMMMdd"), mLocale);
        }
        VERSION_2_FORMAT.setTimeZone(getTimeZone());


        dc = new DateConverter();
        if (mCalendar == null) {
            mCalendar = dc.getTodayNepaliDate();
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SELECTED_YEAR, mCalendar.getYear());
        outState.putInt(KEY_SELECTED_MONTH, mCalendar.getMonth());
        outState.putInt(KEY_SELECTED_DAY, mCalendar.getDay());
        outState.putInt(KEY_WEEK_START, mWeekStart);
        outState.putInt(KEY_CURRENT_VIEW, mCurrentView);
        int listPosition = -1;
        if (mCurrentView == MONTH_AND_DAY_VIEW) {
            listPosition = mDayPickerView.getMostVisiblePosition();
        } else if (mCurrentView == YEAR_VIEW) {
            listPosition = mYearPickerView.getFirstVisiblePosition();
            outState.putInt(KEY_LIST_POSITION_OFFSET, mYearPickerView.getFirstPositionOffset());
        }
        outState.putInt(KEY_LIST_POSITION, listPosition);
        outState.putSerializable(KEY_HIGHLIGHTED_DAYS, highlightedDays);
        outState.putBoolean(KEY_THEME_DARK, mThemeDark);
        outState.putBoolean(KEY_THEME_DARK_CHANGED, mThemeDarkChanged);
        outState.putInt(KEY_ACCENT, mAccentColor);
        outState.putBoolean(KEY_VIBRATE, mVibrate);
        outState.putBoolean(KEY_DISMISS, mDismissOnPause);
        outState.putBoolean(KEY_AUTO_DISMISS, mAutoDismiss);
        outState.putInt(KEY_DEFAULT_VIEW, mDefaultView);
        outState.putString(KEY_TITLE, mTitle);
        outState.putInt(KEY_OK_RESID, mOkResid);
        outState.putString(KEY_OK_STRING, mOkString);
        outState.putInt(KEY_OK_COLOR, mOkColor);
        outState.putInt(KEY_CANCEL_RESID, mCancelResid);
        outState.putString(KEY_CANCEL_STRING, mCancelString);
        outState.putInt(KEY_CANCEL_COLOR, mCancelColor);
        outState.putSerializable(KEY_VERSION, mVersion);
        outState.putSerializable(KEY_SCROLL_ORIENTATION, mScrollOrientation);
        outState.putSerializable(KEY_TIMEZONE, mTimezone);
        outState.putParcelable(KEY_DATERANGELIMITER, mDateRangeLimiter);
        outState.putSerializable(KEY_LOCALE, mLocale);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int listPosition = -1;
        int listPositionOffset = 0;
        // All options have been set at this point: round the initial selection if necessary
        //setToNearestDate(mCalendar);
        if (mScrollOrientation == null) {
            mScrollOrientation = mVersion == Version.VERSION_1
                    ? ScrollOrientation.VERTICAL
                    : ScrollOrientation.HORIZONTAL;
        }

        int viewRes = mVersion == Version.VERSION_1 ? R.layout.mdtp_date_picker_dialog : R.layout.mdtp_date_picker_dialog_v2;
        View view = inflater.inflate(viewRes, container, false);

        //mDayOfWeekView = view.findViewById(R.id.mdtp_date_picker_header);

        int currentView = mDefaultView;
        if (savedInstanceState != null) {
            mWeekStart = savedInstanceState.getInt(KEY_WEEK_START);
            currentView = savedInstanceState.getInt(KEY_CURRENT_VIEW);
            listPosition = savedInstanceState.getInt(KEY_LIST_POSITION);
            listPositionOffset = savedInstanceState.getInt(KEY_LIST_POSITION_OFFSET);
            mMinDate = (Calendar) savedInstanceState.getSerializable(KEY_MIN_DATE);
            mMaxDate = (Calendar) savedInstanceState.getSerializable(KEY_MAX_DATE);
            highlightedDays = (HashSet<Calendar>) savedInstanceState.getSerializable(KEY_HIGHLIGHTED_DAYS);
            mThemeDark = savedInstanceState.getBoolean(KEY_THEME_DARK);
            mThemeDarkChanged = savedInstanceState.getBoolean(KEY_THEME_DARK_CHANGED);
            mAccentColor = savedInstanceState.getInt(KEY_ACCENT);
            //mVibrate = savedInstanceState.getBoolean(KEY_VIBRATE);
            mDismissOnPause = savedInstanceState.getBoolean(KEY_DISMISS);
            mAutoDismiss = savedInstanceState.getBoolean(KEY_AUTO_DISMISS);
            mTitle = savedInstanceState.getString(KEY_TITLE);
            mOkResid = savedInstanceState.getInt(KEY_OK_RESID);
            mOkString = savedInstanceState.getString(KEY_OK_STRING);
            mTimezone = (TimeZone) savedInstanceState.getSerializable(KEY_TIMEZONE);

            mCancelResid = savedInstanceState.getInt(KEY_CANCEL_RESID);
            mCancelString = savedInstanceState.getString(KEY_CANCEL_STRING);
            mDateRangeLimiter = savedInstanceState.getParcelable(KEY_DATERANGELIMITER);
        }

        if (mDateRangeLimiter instanceof DefaultDateRangeLimiter) {
            mDefaultLimiter = (DefaultDateRangeLimiter) mDateRangeLimiter;
        } else {
            mDefaultLimiter = new DefaultDateRangeLimiter();
        }

        mDefaultLimiter.setController(this);

        mDatePickerHeaderView = view.findViewById(R.id.mdtp_date_picker_header);
        mMonthAndDayView = view.findViewById(R.id.mdtp_date_picker_month_and_day);
        mMonthAndDayView.setOnClickListener(this);
        mSelectedMonthTextView = view.findViewById(R.id.mdtp_date_picker_month);
        mSelectedDayTextView = view.findViewById(R.id.mdtp_date_picker_day);
        mYearView = view.findViewById(R.id.mdtp_date_picker_year);
        mYearView.setOnClickListener(this);


        final Activity activity = getActivity();
        mDayPickerView = new DayPickerGroup(activity, this);
        mYearPickerView = new YearPickerView(activity, this);

        // if theme mode has not been set by java code, check if it is specified in Style.xml
        if (!mThemeDarkChanged) {
            mThemeDark = Utils.isDarkTheme(activity, mThemeDark);
        }

        Resources res = getResources();
        mDayPickerDescription = res.getString(R.string.mdtp_day_picker_description);
        mSelectDay = res.getString(R.string.mdtp_select_day);
        mYearPickerDescription = res.getString(R.string.mdtp_year_picker_description);
        mSelectYear = res.getString(R.string.mdtp_select_year);

        int bgColorResource = mThemeDark ? R.color.mdtp_date_picker_view_animator_dark_theme : R.color.mdtp_date_picker_view_animator;
        view.setBackgroundColor(ContextCompat.getColor(activity, bgColorResource));

        mAnimator = view.findViewById(R.id.mdtp_animator);
        mAnimator.addView(mDayPickerView);
        mAnimator.addView(mYearPickerView);
        mAnimator.setDateMillis(Calendar.getInstance().getTimeInMillis());
        // TODO: Replace with animation decided upon by the design team.
        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(ANIMATION_DURATION);
        mAnimator.setInAnimation(animation);
        // TODO: Replace with animation decided upon by the design team.
        Animation animation2 = new AlphaAnimation(1.0f, 0.0f);
        animation2.setDuration(ANIMATION_DURATION);
        mAnimator.setOutAnimation(animation2);

        Button okButton = (Button) view.findViewById(R.id.mdtp_ok);
        okButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // tryVibrate();
                notifyOnDateListener();
                dismiss();
            }
        });
        okButton.setTypeface(TypefaceHelper.get(activity, "Roboto-Medium"));
        if (mOkString != null) okButton.setText(mOkString);
        else okButton.setText(mOkResid);

        Button cancelButton = (Button) view.findViewById(R.id.mdtp_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tryVibrate();
                if (getDialog() != null) getDialog().cancel();
            }
        });
        cancelButton.setTypeface(TypefaceHelper.get(activity, "Roboto-Medium"));
        if (mCancelString != null) cancelButton.setText(mCancelString);
        else cancelButton.setText(mCancelResid);
        cancelButton.setVisibility(isCancelable() ? View.VISIBLE : View.GONE);

        // If an accent color has not been set manually, get it from the context
        if (mAccentColor == -1) {
            mAccentColor = Utils.getAccentColorFromThemeIfAvailable(getActivity());
        }
        if (mDatePickerHeaderView != null)
            mDatePickerHeaderView.setBackgroundColor(Utils.darkenColor(mAccentColor));
        view.findViewById(R.id.mdtp_day_picker_selected_date_layout).setBackgroundColor(mAccentColor);

        // Buttons can have a different color
        if (mOkColor != -1) okButton.setTextColor(mOkColor);
        else okButton.setTextColor(mAccentColor);
        if (mCancelColor != -1) cancelButton.setTextColor(mCancelColor);
        else cancelButton.setTextColor(mAccentColor);

        if (getDialog() == null) {
            view.findViewById(R.id.mdtp_done_background).setVisibility(View.GONE);
        }

        updateDisplay(false);
        setCurrentView(currentView);

        if (listPosition != -1) {
            if (currentView == MONTH_AND_DAY_VIEW) {
                mDayPickerView.postSetSelection(listPosition);
            } else if (currentView == YEAR_VIEW) {
                mYearPickerView.postSetSelectionFromTop(listPosition, listPositionOffset);
            }
        }

        //mHapticFeedbackController = new HapticFeedbackController(activity);
        return view;
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ViewGroup viewGroup = (ViewGroup) getView();
        if (viewGroup != null) {
            viewGroup.removeAllViewsInLayout();
            View view = onCreateView(getActivity().getLayoutInflater(), viewGroup, null);
            viewGroup.addView(view);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        //mHapticFeedbackController.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        //mHapticFeedbackController.stop();
        if (mDismissOnPause) dismiss();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (mOnCancelListener != null) mOnCancelListener.onCancel(dialog);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mOnDismissListener != null) mOnDismissListener.onDismiss(dialog);
    }

    private void setCurrentView(final int viewIndex) {
        long millis = Calendar.getInstance().getTimeInMillis();

        switch (viewIndex) {
            case MONTH_AND_DAY_VIEW:
                ObjectAnimator pulseAnimator = Utils.getPulseAnimator(mMonthAndDayView, 0.9f,
                        1.05f);
                if (mDelayAnimation) {
                    pulseAnimator.setStartDelay(ANIMATION_DELAY);
                    mDelayAnimation = false;
                }
                mDayPickerView.onDateChanged();
                if (mCurrentView != viewIndex) {
                    mMonthAndDayView.setSelected(true);
                    mYearView.setSelected(false);
                    mAnimator.setDisplayedChild(MONTH_AND_DAY_VIEW);
                    mCurrentView = viewIndex;
                }
                pulseAnimator.start();

                int flags = DateUtils.FORMAT_SHOW_DATE;
                String dayString = DateUtils.formatDateTime(getActivity(), millis, flags);
                mAnimator.setContentDescription(mDayPickerDescription + ": " + dayString);
                Utils.tryAccessibilityAnnounce(mAnimator, mSelectDay);
                break;
            case YEAR_VIEW:
                pulseAnimator = Utils.getPulseAnimator(mYearView, 0.85f, 1.1f);
                if (mDelayAnimation) {
                    pulseAnimator.setStartDelay(ANIMATION_DELAY);
                    mDelayAnimation = false;
                }
                mYearPickerView.onDateChanged();
                if (mCurrentView != viewIndex) {
                    mMonthAndDayView.setSelected(false);
                    mYearView.setSelected(true);
                    mAnimator.setDisplayedChild(YEAR_VIEW);
                    mCurrentView = viewIndex;
                }
                pulseAnimator.start();

                CharSequence yearString = YEAR_FORMAT.format(millis);
                mAnimator.setContentDescription(mYearPickerDescription + ": " + yearString);
                Utils.tryAccessibilityAnnounce(mAnimator, mSelectYear);
                break;
        }
    }

    private void updateDisplay(boolean announce) {
        mYearView.setText(String.valueOf(mCalendar.getYear()));

        if (mVersion == Version.VERSION_1) {
            if (mDatePickerHeaderView != null) {
                if (mTitle != null)
                    mDatePickerHeaderView.setText(mTitle.toUpperCase(mLocale));
                else {
                    mDatePickerHeaderView.setText(Utils.getDayOfWeek(mCalendar.getDayOfWeek()).toUpperCase());
                }
            }
            mSelectedMonthTextView.setText(getResources().getString(DateConverter.getNepaliMonthString(mCalendar.getMonth())));
            mSelectedDayTextView.setText(String.valueOf(mCalendar.getDay()));
        }

        if (mVersion == Version.VERSION_2) {
            //        mSelectedDayTextView.setText(VERSION_2_FORMAT.format(mCalendar.getTime()));

            String mText = (Utils.getDayOfWeek((dc.getWeekDay(mCalendar.getYear(), mCalendar.getMonth() + 1, mCalendar.getDay())))).substring(0, 3) + ", "
                    + getResources().getString(DateConverter.getNepaliMonthString(mCalendar.getMonth())) + " " + mCalendar.getDay();
            mSelectedDayTextView.setText(mText);

            if (mTitle != null)
                mDatePickerHeaderView.setText(mTitle.toUpperCase(mLocale));
            else
                mDatePickerHeaderView.setVisibility(View.GONE);
        }


         /*
         mSelectedMonthTextView.setText(mCalendar.getDisplayName(Calendar.MONTH, Calendar.SHORT,
                Locale.getDefault()).toUpperCase(Locale.getDefault()));
          */


        // Accessibility.
        long millis = Calendar.getInstance().getTimeInMillis();
        mAnimator.setDateMillis(millis);
        int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NO_YEAR;
        String monthAndDayText = DateUtils.formatDateTime(getActivity(), millis, flags);
        mMonthAndDayView.setContentDescription(monthAndDayText);

        if (announce) {
            flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR;
            String fullDateText = DateUtils.formatDateTime(getActivity(), millis, flags);
            Utils.tryAccessibilityAnnounce(mAnimator, fullDateText);
        }
    }

    /**
     * Set whether the device should vibrate when touching fields
     *
     * @param vibrate true if the device should vibrate when touching a field
     */
    public void vibrate(boolean vibrate) {
        mVibrate = vibrate;
    }

    /**
     * Set whether the picker should dismiss itself when being paused or whether it should try to survive an orientation change
     *
     * @param dismissOnPause true if the dialog should dismiss itself when it's pausing
     */
    public void dismissOnPause(boolean dismissOnPause) {
        mDismissOnPause = dismissOnPause;
    }

    /**
     * Set whether the picker should dismiss itself when a day is selected
     *
     * @param autoDismiss true if the dialog should dismiss itself when a day is selected
     */
    @SuppressWarnings("unused")
    public void autoDismiss(boolean autoDismiss) {
        mAutoDismiss = autoDismiss;
    }

    /**
     * Set whether the dark theme should be used
     *
     * @param themeDark true if the dark theme should be used, false if the default theme should be used
     */
    public void setThemeDark(boolean themeDark) {
        mThemeDark = themeDark;
        mThemeDarkChanged = true;
    }

    /**
     * Sets a list of days that are not selectable in the picker
     * Setting this value will take precedence over using setMinDate() and setMaxDate(), but stacks with setSelectableDays()
     *
     * @param myDisableList a ArrayList of Model Objects containing the disabled dates
     */
    @SuppressWarnings("unused")
    public void setDisabledDays(List<Model> myDisableList) {

        Calendar[] days = new Calendar[myDisableList.size()];
        for (int i = 0; i < myDisableList.size(); i++) {
            Calendar mDay = new GregorianCalendar(myDisableList.get(i).getYear(), myDisableList.get(i).getMonth(), myDisableList.get(i).getDay());
            days[i] = mDay;
        }

        mDefaultLimiter.setDisabledDays(days);
        if (mDayPickerView != null) mDayPickerView.onChange();
    }

    /**
     * @return an Array of Calendar objects containing the list of days that are not selectable. null if no restriction is set
     */
    @SuppressWarnings("unused")
    public Calendar[] getDisabledDays() {
        return mDefaultLimiter.getDisabledDays();
    }


    public void setDateRangeLimiter(DateRangeLimiter dateRangeLimiter) {
        mDateRangeLimiter = dateRangeLimiter;
    }


    /**
     * Returns true when the dark theme should be used
     *
     * @return true if the dark theme should be used, false if the default theme should be used
     */
    @Override
    public boolean isThemeDark() {
        return mThemeDark;
    }

    /**
     * Set the accent color of this dialog
     *
     * @param color the accent color you want
     */
    public void setAccentColor(String color) {
        try {
            mAccentColor = Color.parseColor(color);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    /**
     * Set the accent color of this dialog
     *
     * @param color the accent color you want
     */
    public void setAccentColor(@ColorInt int color) {
        mAccentColor = Color.argb(255, Color.red(color), Color.green(color), Color.blue(color));
        ;
    }

    /**
     * Get the accent color of this dialog
     *
     * @return accent color
     */
    @Override
    public int getAccentColor() {
        return mAccentColor;
    }

    /**
     * Set whether the year picker of the month and day picker is shown first
     *
     * @param yearPicker boolean
     */
    public void showYearPickerFirst(boolean yearPicker) {
        mDefaultView = yearPicker ? YEAR_VIEW : MONTH_AND_DAY_VIEW;
    }

    @SuppressWarnings("unused")
    public void setFirstDayOfWeek(int startOfWeek) {
        if (startOfWeek < Calendar.SUNDAY || startOfWeek > Calendar.SATURDAY) {
            throw new IllegalArgumentException("Value must be between Calendar.SUNDAY and " +
                    "Calendar.SATURDAY");
        }
        mWeekStart = startOfWeek;
        if (mDayPickerView != null) {
            mDayPickerView.onChange();
        }
    }

    @SuppressWarnings("unused")
    public void setYearRange(int startYear, int endYear) {
        if (endYear < startYear) {
            throw new IllegalArgumentException("Year end must be larger than or equal to year start");
        }
        mDefaultLimiter.setYearRange(startYear, endYear);
        //mMinYear = startYear;
        //mMaxYear = endYear;
        if (mDayPickerView != null) {
            mDayPickerView.onChange();
        }
    }

    /**
     * Sets the minimal date supported by this DatePicker. Dates before (but not including) the
     * specified date will be disallowed from being selected.
     */
    public void setMinDate(Model minDate) {
        //mMinDate = calendar;
        Calendar mCalendar = new GregorianCalendar(minDate.getYear(), minDate.getMonth(), minDate.getDay(), 0, 0, 0);
        /*mCalendar.setLenient(false);
        mCalendar.set(Calendar.MONTH, minDate.getMonth());
        mCalendar.set(Calendar.YEAR, minDate.getYear());
        mCalendar.set(Calendar.DAY_OF_MONTH, minDate.getDay());
        */
        mCalendar.set(Calendar.DAY_OF_WEEK, minDate.getDayOfWeek());
        mDefaultLimiter.setMinDate(mCalendar);
        Log.e("checking min date: ", "" +
                minDate.getDay());
        Log.e("Checking the min date:", mCalendar.get(Calendar.YEAR) +
                ":year -- month:" + mCalendar.get(Calendar.MONTH) +
                "-- DAY:" + mCalendar.get(Calendar.DAY_OF_MONTH));
        if (mDayPickerView != null) {
            mDayPickerView.onChange();
        }
    }

    /**
     * @return The minimal date supported by this DatePicker. Null if it has not been set.
     */
    @SuppressWarnings("unused")
    public Calendar getMinDate() {
        return mDefaultLimiter.getMinDate();
    }

    /**
     * Sets the minimal date supported by this DatePicker. Dates after (but not including) the
     * specified date will be disallowed from being selected.
     */
    @SuppressWarnings("unused")
    public void setMaxDate(Model maxDate) {
        //mMaxDate = calendar;
        Calendar calendar = new GregorianCalendar(maxDate.getYear(), maxDate.getMonth(), maxDate.getDay(), 0, 0, 0);
        /*calendar.set(Calendar.MONTH, maxDate.getMonth());
        calendar.set(Calendar.YEAR, maxDate.getYear());
        calendar.set(Calendar.DAY_OF_MONTH, maxDate.getDay());
        */
        mDefaultLimiter.setMaxDate(calendar);

        if (mDayPickerView != null) {
            mDayPickerView.onChange();
        }
    }

    /**
     * @return The maximal date supported by this DatePicker. Null if it has not been set.
     */
    @SuppressWarnings("unused")
    public Calendar getMaxDate() {
        return mDefaultLimiter.getMaxDate();
    }

    /**
     * Sets an array of dates which should be highlighted when the picker is drawn
     *
     * @param myHighlightedDays an Array of Calendar objects containing the dates to be highlighted
     */
    @SuppressWarnings("unused")
    public void setHighlightedDays(List<Model> myHighlightedDays) {

        Calendar[] days = new Calendar[myHighlightedDays.size()];
        for (int i = 0; i < myHighlightedDays.size(); i++) {
            Calendar mDay = new GregorianCalendar(myHighlightedDays.get(i).getYear(), myHighlightedDays.get(i).getMonth(), myHighlightedDays.get(i).getDay());
            days[i] = mDay;
        }


        for (Calendar highlightedDay : days) {
            this.highlightedDays.add(Utils.trimToMidnight((Calendar) highlightedDay.clone()));
        }
        // Sort the array to optimize searching over it later on
        ///Arrays.sort(highlightedDays);
        if (mDayPickerView != null) mDayPickerView.onChange();
    }

    /**
     * @return The list of dates, as Calendar Objects, which should be highlighted. null is no dates should be highlighted
     */

    public Calendar[] getHighlightedDays() {
        if (highlightedDays.isEmpty()) return null;
        Calendar[] output = highlightedDays.toArray(new Calendar[0]);
        Arrays.sort(output);
        return output;
    }

    /**
     * Set's a list of days which are the only valid selections.
     * Setting this value will take precedence over using setMinDate() and setMaxDate()
     *//*
    @SuppressWarnings("unused")
    public void setSelectableDays(Calendar[] selectableDays) {
        // Sort the array to optimize searching over it later on
        Arrays.sort(selectableDays);
        this.selectableDays = selectableDays;
    }
*/
    @Override
    public boolean isHighlighted(int year, int month, int day) {
        Calendar date = Calendar.getInstance(getTimeZone());
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month);
        date.set(Calendar.DAY_OF_MONTH, day);
        Utils.trimToMidnight(date);
        return highlightedDays.contains(date);
    }

    //modified by hornet...base code by wdullaer
    public void setSelectableDays(List<Model> myList) {
        Calendar[] days = new Calendar[myList.size()];
        for (int i = 0; i < myList.size(); i++) {
            Calendar mDay = new GregorianCalendar(myList.get(i).getYear(), myList.get(i).getMonth(), myList.get(i).getDay());
            days[i] = mDay;
        }
        selectableDays = days;
        mDefaultLimiter.setSelectableDays(selectableDays);
        if (mDayPickerView != null) mDayPickerView.onChange();
    }

    /**
     * @return an Array of Calendar objects containing the list with selectable items. null if no restriction is set
     */

    public Calendar[] getSelectableDays() {
        return mDefaultLimiter.getSelectableDays();
    }


    /**
     * Set a title to be displayed instead of the weekday
     *
     * @param title String - The title to be displayed
     */
    public void setTitle(String title) {
        mTitle = title;
    }

    /**
     * Set the label for the Ok button (max 12 characters)
     *
     * @param okString A literal String to be used as the Ok button label
     */
    @SuppressWarnings("unused")
    public void setOkText(String okString) {
        mOkString = okString;
    }

    /**
     * Set the label for the Ok button (max 12 characters)
     *
     * @param okResid A resource ID to be used as the Ok button label
     */
    @SuppressWarnings("unused")
    public void setOkText(@StringRes int okResid) {
        mOkString = null;
        mOkResid = okResid;
    }

    /**
     * Set the label for the Cancel button (max 12 characters)
     *
     * @param cancelString A literal String to be used as the Cancel button label
     */
    @SuppressWarnings("unused")
    public void setCancelText(String cancelString) {
        mCancelString = cancelString;
    }

    /**
     * Set the label for the Cancel button (max 12 characters)
     *
     * @param cancelResid A resource ID to be used as the Cancel button label
     */
    @SuppressWarnings("unused")
    public void setCancelText(@StringRes int cancelResid) {
        mCancelString = null;
        mCancelResid = cancelResid;
    }

    /**
     * Set which layout version the picker should use
     *
     * @param version The version to use
     */
    public void setVersion(Version version) {
        mVersion = version;
    }

    /**
     * Get the layout version the Dialog is using
     *
     * @return Version
     */
    public Version getVersion() {
        return mVersion;
    }


    /**
     * Set which way the user needs to swipe to switch months in the MonthView
     *
     * @param orientation The orientation to use
     */
    public void setScrollOrientation(ScrollOrientation orientation) {
        mScrollOrientation = orientation;
    }

    /**
     * Get which way the user needs to swipe to switch months in the MonthView
     *
     * @return SwipeOrientation
     */
    public ScrollOrientation getScrollOrientation() {
        return mScrollOrientation;
    }

    /**
     * Set which timezone the picker should use
     * <p>
     * This has been deprecated in favor of setting the TimeZone using the constructor that
     * takes a Calendar object
     *
     * @param timeZone The timezone to use
     */
    @SuppressWarnings("DeprecatedIsStillUsed")
    @Deprecated
    public void setTimeZone(TimeZone timeZone) {
        mTimezone = timeZone;
        YEAR_FORMAT.setTimeZone(timeZone);
        MONTH_FORMAT.setTimeZone(timeZone);
        DAY_FORMAT.setTimeZone(timeZone);
    }


    @SuppressWarnings("unused")
    public void setOnDateSetListener(OnDateSetListener listener) {
        mCallBack = listener;
    }

    @SuppressWarnings("unused")
    public void setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        mOnCancelListener = onCancelListener;
    }

    @SuppressWarnings("unused")
    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        mOnDismissListener = onDismissListener;
    }

    // If the newly selected month / year does not contain the currently selected day number,
    // change the selected day number to the last day of the selected month or year.
    //      e.g. Switching from Ashar 32 to Ashar of another year and if that consist of only 31 days then ashar 31 is selected
    //
    private void adjustDayInMonthIfNeeded(Model calendar) {
        int day = calendar.getDay();
        int daysInMonth = new DateConverter().noOfDaysInMonth(calendar.getYear(), calendar.getMonth() + 1);
        if (day > daysInMonth) {
            calendar.setDay(daysInMonth);

        }
        //  setToNearestDate(calendar);
    }

    @Override
    public void onClick(View v) {
        //tryVibrate();
        Log.e("DATEPICKERDIALOG", "just to checking if its working or not" + v.getId());
        if (v.getId() == R.id.mdtp_date_picker_year) {
            Log.e("DATEPICKERDIALOG", "just to checking if its working or not: YEAR_VIEW");
            setCurrentView(YEAR_VIEW);
        } else if (v.getId() == R.id.mdtp_date_picker_month_and_day) {
            Log.e("DATEPICKERDIALOG", "just to checking if its working or not: datePickerMonthAndDay");
            setCurrentView(MONTH_AND_DAY_VIEW);
        }
    }

    @Override
    public void onYearSelected(int year) {
        mCalendar.setYear(year);
        adjustDayInMonthIfNeeded(mCalendar);
        updatePickers();
        setCurrentView(MONTH_AND_DAY_VIEW);
        updateDisplay(true);
    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day, int dayOfWeek) {

        mCalendar.setDayOfWeek(dayOfWeek);
        mCalendar.setYear(year);
        mCalendar.setMonth(month);
        mCalendar.setDay(day);

        updatePickers();
        updateDisplay(true);
        if (mAutoDismiss) {
            notifyOnDateListener();
            dismiss();
        }
    }

    private void updatePickers() {

        for (OnDateChangedListener listener : mListeners) listener.onDateChanged();
    }


    @Override
    public MonthAdapter.CalendarDay getSelectedDay() {
        return new MonthAdapter.CalendarDay(mCalendar);
    }

    @Override
    public Calendar getStartDate() {
        return mDateRangeLimiter.getStartDate();
        /*if (selectableDays != null) return selectableDays[0];
        if (mMinDate != null) return mMinDate;
        Calendar output = Calendar.getInstance();
        output.set(Calendar.YEAR, mMinYear);
        output.set(Calendar.DAY_OF_MONTH, 1);
        output.set(Calendar.MONTH, Calendar.JANUARY);
        return output;
   */
    }

    @Override
    public Calendar getEndDate() {
        /*if (selectableDays != null) return selectableDays[selectableDays.length - 1];
        if (mMaxDate != null) return mMaxDate;
        Calendar output = Calendar.getInstance();
        output.set(Calendar.YEAR, mMaxYear);
        output.set(Calendar.DAY_OF_MONTH, dc.getFirstWeekDayMonth(mMaxYear, 12));
        output.set(Calendar.MONTH, Calendar.DECEMBER);
        return output;
    */

        return mDateRangeLimiter.getEndDate();
    }

    @Override
    public int getMinYear() {
        return mDateRangeLimiter.getMinYear();
        /*    if (selectableDays != null) return selectableDays[0].get(Calendar.YEAR);
        // Ensure no years can be selected outside of the given minimum date
        return mMinDate != null && mMinDate.get(Calendar.YEAR) > mMinYear ? mMinDate.get(Calendar.YEAR) : mMinYear;
    */
    }

    @Override
    public int getMaxYear() {
        return mDateRangeLimiter.getMaxYear();
       /* if (selectableDays != null)
            return selectableDays[selectableDays.length - 1].get(Calendar.YEAR);
        // Ensure no years can be selected outside of the given maximum date
        return mMaxDate != null && mMaxDate.get(Calendar.YEAR) < mMaxYear ? mMaxDate.get(Calendar.YEAR) : mMaxYear;
   */
    }

    /**
     * @return true if the specified year/month/day are within the selectable days or the range set by minDate and maxDate.
     * If one or either have not been set, they are considered as Integer.MIN_VALUE and
     * Integer.MAX_VALUE.
     */
    @Override
    public boolean isOutOfRange(int year, int month, int day) {

        return mDateRangeLimiter.isOutOfRange(year, month, day);
        /*if (selectableDays != null) {
            return !isSelectable(year, month, day);
        }

        if (isBeforeMin(year, month, day)) {
            return true;
        } else if (isAfterMax(year, month, day)) {
            return true;
        }

        return false;
    */
    }

    @Override
    public void tryVibrate() {
        //if (mVibrate) mHapticFeedbackController.tryVibrate();

    }

    //@Override
    public TimeZone getTimeZone() {
        return mTimezone == null ? TimeZone.getDefault() : mTimezone;

    }

    @Override
    public Locale getLocale() {
        return mLocale;
    }


    @SuppressWarnings("unused")
    public boolean isOutOfRange(Calendar calendar) {
        return isOutOfRange(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
    }

    private boolean isSelectable(int year, int month, int day) {
        for (Calendar c : selectableDays) {

            if (year < c.get(Calendar.YEAR)) break;
            if (year > c.get(Calendar.YEAR)) continue;
            if (month < c.get(Calendar.MONTH)) break;
            if (month > c.get(Calendar.MONTH)) continue;
            if (day < c.get(Calendar.DAY_OF_MONTH)) break;
            if (day > c.get(Calendar.DAY_OF_MONTH)) continue;
            return true;
        }
        return false;
    }

    private boolean isBeforeMin(int year, int month, int day) {
        if (mMinDate == null) {
            return false;
        }

        if (year < mMinDate.get(Calendar.YEAR)) {
            return true;
        } else if (year > mMinDate.get(Calendar.YEAR)) {
            return false;
        }

        if (month < mMinDate.get(Calendar.MONTH)) {
            return true;
        } else if (month > mMinDate.get(Calendar.MONTH)) {
            return false;
        }

        if (day < mMinDate.get(Calendar.DAY_OF_MONTH)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isBeforeMin(Calendar calendar) {
        return isBeforeMin(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
    }

    private boolean isAfterMax(int year, int month, int day) {
        if (mMaxDate == null) {
            return false;
        }

        if (year > mMaxDate.get(Calendar.YEAR)) {
            return true;
        } else if (year < mMaxDate.get(Calendar.YEAR)) {
            return false;
        }

        if (month > mMaxDate.get(Calendar.MONTH)) {
            return true;
        } else if (month < mMaxDate.get(Calendar.MONTH)) {
            return false;
        }

        if (day > mMaxDate.get(Calendar.DAY_OF_MONTH)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isAfterMax(Calendar calendar) {
        return isAfterMax(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
    }

    private void setToNearestDate(Calendar calendar) {
        if (selectableDays != null) {
            long distance = Long.MAX_VALUE;
            Calendar currentBest = calendar;
            for (Calendar c : selectableDays) {
                long newDistance = Math.abs(calendar.getTimeInMillis() - c.getTimeInMillis());
                if (newDistance < distance) {
                    distance = newDistance;
                    currentBest = c;
                } else break;
            }
            calendar.setTimeInMillis(currentBest.getTimeInMillis());
            return;
        }

        if (isBeforeMin(calendar)) {
            calendar.setTimeInMillis(mMinDate.getTimeInMillis());
            return;
        }

        if (isAfterMax(calendar)) {
            calendar.setTimeInMillis(mMaxDate.getTimeInMillis());
            return;
        }
    }

    @Override
    public int getFirstDayOfWeek() {
        return mWeekStart;
    }

    @Override
    public void registerOnDateChangedListener(OnDateChangedListener listener) {
        mListeners.add(listener);
    }

    @Override
    public void unregisterOnDateChangedListener(OnDateChangedListener listener) {
        mListeners.remove(listener);
    }

    /* @Override
     public void tryVibrate() {
         if (mVibrate) mHapticFeedbackController.tryVibrate();
     }
    */
    public void notifyOnDateListener() {
        if (mCallBack != null) {
            mCallBack.onDateSet(DatePickerDialog.this, mCalendar.getYear(),
                    mCalendar.getMonth(), mCalendar.getDay());
        }
    }
}
