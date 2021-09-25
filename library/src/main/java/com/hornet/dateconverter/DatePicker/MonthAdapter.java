package com.hornet.dateconverter.DatePicker;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.AbsListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hornet.dateconverter.Model;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Hornet on 5/2/2016.
 */
public abstract class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.MonthViewHolder> implements MonthView.OnDayClickListener {

    private static final String TAG = "SimpleMonthAdapter";

    protected final DatePickerController mController;

    private CalendarDay mSelectedDay;

    protected static int WEEK_7_OVERHANG_HEIGHT = 7;
    protected static final int MONTHS_IN_YEAR = 12;

    /**
     * A convenience class to represent a specific date.
     */
    public static class CalendarDay {
        private Calendar calendar;
        int year;
        int month;
        int day;
        int dayOfWeek;

        public CalendarDay() {
            setTime(System.currentTimeMillis());
        }

        public CalendarDay(TimeZone timeZone) {
            //mTimeZone=timeZone;
            setTime(System.currentTimeMillis());
        }

        public CalendarDay(Model calendar) {
            year = calendar.getYear();
            month = calendar.getMonth();
            day = calendar.getDay();
            dayOfWeek = calendar.getDayOfWeek();
        }

        public CalendarDay(int year, int month, int day) {
            setDay(year, month, day);
        }

        public void set(CalendarDay date) {
            year = date.year;
            month = date.month;
            day = date.day;
        }

        public void setDay(int year, int month, int day) {
            this.year = year;
            this.month = month;
            this.day = day;
        }

        public void setDay(int year, int month, int day, int dayOfWeek) {
            this.year = year;
            this.month = month;
            this.day = day;
            this.dayOfWeek = dayOfWeek;
        }

        private void setTime(long timeInMillis) {
            if (calendar == null) {
                //  calendar = Calendar.getInstance();
            }
            //calendar.setTimeInMillis(timeInMillis);
            //month = calendar.get(Calendar.MONTH);
            //year = calendar.get(Calendar.YEAR);
            //day = calendar.get(Calendar.DAY_OF_MONTH);
        }

        public int getYear() {
            return year;
        }

        public int getMonth() {
            return month;
        }

        public int getDayOfWeek() {
            return dayOfWeek;
        }

        public void setDayOfWeek(int dayOfWeek) {
            this.dayOfWeek = dayOfWeek;
        }

        public int getDay() {
            return day;
        }
    }


    public MonthAdapter(
            DatePickerController controller) {
        mController = controller;
        init();
        setSelectedDay(mController.getSelectedDay());
        setHasStableIds(true);
    }

    /**
     * Updates the selected day and related parameters.
     *
     * @param day The day to highlight
     */
    public void setSelectedDay(CalendarDay day) {
        mSelectedDay = day;
        notifyDataSetChanged();
    }

    @SuppressWarnings("unused")
    public CalendarDay getSelectedDay() {
        return mSelectedDay;
    }

    /**
     * Set up the gesture detector and selected time
     */
    protected void init() {
        mSelectedDay = new CalendarDay();
    }

    //todo check this commented method
      /*  @Override
        public int getCount() {
            Calendar endDate = mController.getEndDate();
            Calendar startDate = mController.getStartDate();
            int endMonth = endDate.get(Calendar.YEAR) * MONTHS_IN_YEAR + endDate.get(Calendar.MONTH);
            int startMonth = startDate.get(Calendar.YEAR) * MONTHS_IN_YEAR + startDate.get(Calendar.MONTH);
            return endMonth - startMonth + 1;
            //return ((mController.getMaxYear() - mController.getMinYear()) + 1) * MONTHS_IN_YEAR;
        }
*/
        /*@Override
        public long getItem(int position) {
            return position;
        }
*/

      /*  @SuppressLint("NewApi")
        @SuppressWarnings("unchecked")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MonthView v;
            HashMap<String, Integer> drawingParams = null;
            if (convertView != null) {
                v = (MonthView) convertView;
                // We store the drawing parameters in the view so it can be recycled
                drawingParams = (HashMap<String, Integer>) v.getTag();
            } else {
                v = createMonthView(mContext);
                // Set up the new view
                AbsListView.LayoutParams params = new AbsListView.LayoutParams(
                        AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT);
                v.setLayoutParams(params);
                v.setClickable(true);
                v.setOnDayClickListener(this);
            }
            if (drawingParams == null) {
                drawingParams = new HashMap<>();
            }
            drawingParams.clear();

            final int month = (position + mController.getStartDate().get(Calendar.MONTH)) % MONTHS_IN_YEAR;
            final int year = (position + mController.getStartDate().get(Calendar.MONTH)) / MONTHS_IN_YEAR + mController.getMinYear();

            int selectedDay = -1;
            if (isSelectedDayInMonth(year, month)) {
                selectedDay = mSelectedDay.day;
            }

            // Invokes requestLayout() to ensure that the recycled view is set with the appropriate
            // height/number of weeks before being displayed.
            v.reuse();

            drawingParams.put(MonthView.VIEW_PARAMS_SELECTED_DAY, selectedDay);
            drawingParams.put(MonthView.VIEW_PARAMS_YEAR, year);
            drawingParams.put(MonthView.VIEW_PARAMS_MONTH, month);
            drawingParams.put(MonthView.VIEW_PARAMS_WEEK_START, mController.getFirstDayOfWeek());
            v.setMonthParams(drawingParams);
            v.invalidate();
            return v;
        }
*/

    @Override
    @NonNull
    public MonthViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        MonthView v = createMonthView(parent.getContext());
        // Set up the new view
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT);
        v.setLayoutParams(params);
        v.setClickable(true);
        v.setOnDayClickListener(this);

        return new MonthViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MonthViewHolder holder, int position) {
        holder.bind(position, mController, mSelectedDay);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        Calendar endDate = mController.getEndDate();
        Calendar startDate = mController.getStartDate();
        int endMonth = endDate.get(Calendar.YEAR) * MONTHS_IN_YEAR + endDate.get(Calendar.MONTH);
        int startMonth = startDate.get(Calendar.YEAR) * MONTHS_IN_YEAR + startDate.get(Calendar.MONTH);
        return endMonth - startMonth + 1;
    }

    public abstract MonthView createMonthView(Context context);

    @Override
    public void onDayClick(MonthView view, CalendarDay day) {
        if (day != null) {
            onDayTapped(day);
        }
    }

    /**
     * Maintains the same hour/min/sec but moves the day to the tapped day.
     *
     * @param day The day that was tapped
     */
    protected void onDayTapped(CalendarDay day) {
        mController.tryVibrate();
        mController.onDayOfMonthSelected(day.year, day.month, day.day, day.dayOfWeek);
        setSelectedDay(day);

    }

    //public abstract MonthView createMonthView(Context context);

    private boolean isSelectedDayInMonth(int year, int month) {
        return mSelectedDay.year == year && mSelectedDay.month == month;
    }


    /*    protected void onDayTapped(CalendarDay day) {
          //  mController.tryVibrate();
            mController.onDayOfMonthSelected(day.year, day.month, day.day);
            setSelectedDay(day);
        }
*/
    static class MonthViewHolder extends RecyclerView.ViewHolder {

        public MonthViewHolder(MonthView itemView) {
            super(itemView);

        }

        void bind(int position, DatePickerController mController, CalendarDay selectedCalendarDay) {
            final int month = (position + mController.getStartDate().get(Calendar.MONTH)) % MONTHS_IN_YEAR;
            //final int month = selectedCalendarDay.month;
            final int year = (position + mController.getStartDate().get(Calendar.MONTH)) / MONTHS_IN_YEAR + mController.getMinYear();
            //final int year = selectedCalendarDay.year;

            int selectedDay = -1;
            if (isSelectedDayInMonth(selectedCalendarDay, year, month)) {
                selectedDay = selectedCalendarDay.day;

            }

            ((MonthView) itemView).setMonthParams(selectedDay, year, month, mController.getFirstDayOfWeek());
            this.itemView.invalidate();
        }

        private boolean isSelectedDayInMonth(CalendarDay selectedDay, int year, int month) {
            return selectedDay.year == year && selectedDay.month == month;
        }
    }

}
