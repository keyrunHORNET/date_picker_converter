package com.hornet.nepalidateconverter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.hornet.dateconverter.DateConverter;
import com.hornet.dateconverter.DatePicker.DatePickerDialog;
import com.hornet.dateconverter.Model;
import com.hornet.dateconverter.TimePicker.TimePickerDialog;
import com.hornet.dateconverter.Utils;
import com.hornet.nepalidateconverter.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, com.hornet.dateconverter.CalendarView.Calendar.OnDateSetListener {

    DateConverter dateConverter;
    ActivityMainBinding mainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        dateConverter = new DateConverter();
        setTitle("Nepali Date Picker Converter");

        mainBinding.adToBsConvertButton.setOnClickListener(this);
        mainBinding.bsToAdConvertButton.setOnClickListener(this);
        mainBinding.materialDatePickerButton.setOnClickListener(this);
        mainBinding.materialTimePickerButton.setOnClickListener(this);
        mainBinding.btnCalendar.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CalendarActivity.class)));
//        mainBinding.calendar.setOnDateSetListener(this);
//        mainBinding.calendar.setHighlightedDays(getSampleModelList());

        mainBinding.modeDarkDate.setChecked(Utils.isDarkTheme(this, mainBinding.modeDarkDate.isChecked()));

    }

    @Override
    public void onClick(View v) {
        Calendar now = Calendar.getInstance();

        if (v.getId() == R.id.materialDatePickerButton) {

            DatePickerDialog dpd = DatePickerDialog.newInstance(this,dateConverter.getNepaliDate(now));

            if (mainBinding.dialogVersion.isChecked()) {
                dpd.setVersion(DatePickerDialog.Version.VERSION_2);
            } else {
                dpd.setVersion(DatePickerDialog.Version.VERSION_1);
            }

            dpd.setThemeDark(mainBinding.modeDarkDate.isChecked());
            dpd.dismissOnPause(mainBinding.dismissDate.isChecked());
            dpd.showYearPickerFirst(mainBinding.showYearFirst.isChecked());
            if (mainBinding.dialogSwitchOrientation.isChecked()) {
                if (dpd.getVersion() == DatePickerDialog.Version.VERSION_1) {
                    dpd.setScrollOrientation(DatePickerDialog.ScrollOrientation.HORIZONTAL);
                } else {
                    dpd.setScrollOrientation(DatePickerDialog.ScrollOrientation.VERTICAL);
                }
            }
            if (mainBinding.modeCustomAccentDate.isChecked()) {
                dpd.setAccentColor(Color.parseColor("#9C27B0"));
            }
            if (mainBinding.titleDate.isChecked()) {
                dpd.setTitle("DatePicker Title");
            }
            if (mainBinding.limitPastDate.isChecked())
                dpd.setMinDate(dateConverter.getTodayNepaliDate());
            if (mainBinding.dialogHighlightDays.isChecked()) {
                dpd.setHighlightedDays(getSampleModelList());
            }
            if (mainBinding.limitSelectableDate.isChecked()) {
                dpd.setSelectableDays(getSampleModelList());
            }
            if (mainBinding.disableSelectedDates.isChecked()) {
                dpd.setDisabledDays(getSampleModelList());
            }
            dpd.show(getSupportFragmentManager(), "DatePicker");
            return;
        }
        if (v.getId() == R.id.materialTimePickerButton) {
            TimePickerDialog tpd = TimePickerDialog.newInstance(MainActivity.this,
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    false);

            if (mainBinding.dialogVersion.isChecked()) {
                tpd.setVersion(TimePickerDialog.Version.VERSION_2);
            } else {
                tpd.setVersion(TimePickerDialog.Version.VERSION_1);
            }

            tpd.setThemeDark(mainBinding.modeDarkDate.isChecked());
            tpd.dismissOnPause(mainBinding.dismissDate.isChecked());

            if (mainBinding.modeCustomAccentDate.isChecked()) {
                tpd.setAccentColor(Color.parseColor("#9C27B0"));
            }
            if (mainBinding.titleDate.isChecked()) {
                tpd.setTitle("TimePicker Title");
            }
            tpd.show(getFragmentManager(), "TimePicker");
            return;
        }

        if (checkEditTextParameter()) {
            int yy = Integer.parseInt(mainBinding.convertEditTextYear.getText().toString());
            int mm = Integer.parseInt(mainBinding.convertEditTextMonth.getText().toString());
            int dd = Integer.parseInt(mainBinding.convertEditTextDay.getText().toString());

            switch (v.getId()) {
                case R.id.adToBsConvertButton:
                    try {
                        Model nepDate = dateConverter.getNepaliDate(yy, mm, dd);
                        String date = "" + nepDate.getYear() + " " + getResources().getString(DateConverter.getNepaliMonthString(nepDate.getMonth())) + " " +
                                nepDate.getDay() + " " + getDayOfWeek(nepDate.getDayOfWeek());
                        mainBinding.outputConvertTextView.setText(date);
                    } catch (IllegalArgumentException e) {
                        Toast.makeText(MainActivity.this, "Date out of Range", Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.bsToAdConvertButton:
                    try {
                        Model engDate = dateConverter.getEnglishDate(yy, mm, dd);
                        String date = "" + engDate.getYear() + " " + getEnglishMonth(engDate.getMonth()) + " " +
                                engDate.getDay() + " " + getDayOfWeek(engDate.getDayOfWeek());
                        mainBinding.outputConvertTextView.setText(date);
                    } catch (IllegalArgumentException e) {
                        Toast.makeText(MainActivity.this, "Date out of Range", Toast.LENGTH_LONG).show();
                    }
                    break;

            }
        }
    }


    /**
     * get List of sample model of date
     *
     * @return ArrayList<Model>
     */
    public List<Model> getSampleModelList() {
        List<Model> myList = new ArrayList<>();
        for (int i = 2; i < 15; i++) {
            myList.add(new Model(dateConverter.getTodayNepaliDate().getYear(), dateConverter.getTodayNepaliDate().getMonth(), (i + 2)));
        }
        return myList;
    }


    /**
     * get english month
     *
     * @return String
     */
    public String getEnglishMonth(int month) {
        switch (month) {
            case 0:
                return "January";
            case 1:
                return "February";
            case 2:
                return "March";
            case 3:
                return "April";
            case 4:
                return "May";
            case 5:
                return "June";
            case 6:
                return "July";
            case 7:
                return "August";
            case 8:
                return "September";
            case 9:
                return "October";
            case 10:
                return "November";
            case 11:
                return "December";
        }
        return "";
    }

    /**
     * get day of the week
     *
     * @return String
     */

    public String getDayOfWeek(int week) {

        switch (week) {
            case 1:
                return "Sunday";
            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";
            case 7:
                return "Saturday";
        }
        return "";
    }

    /**
     * check if the edit text has valid input
     *
     * @return boolean
     */

    public boolean checkEditTextParameter() {
        if (mainBinding.convertEditTextYear.getText().toString().length() == 0) {
            mainBinding.convertEditTextYear.setError("Empty Field");
        } else {
            if (mainBinding.convertEditTextMonth.getText().toString().length() == 0) {
                mainBinding.convertEditTextMonth.setError("Empty Field");
            } else {
                if (mainBinding.convertEditTextDay.getText().toString().length() == 0) {
                    mainBinding.convertEditTextDay.setError("Empty Field");
                } else {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "You picked the following date: " + dayOfMonth + " " + getResources().getString(DateConverter.getNepaliMonthString(monthOfYear)) + " " + year;
        mainBinding.outputDatePickerTextView.setText(date);
    }


    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String time = "You picked the following time:- " + hourOfDay + ":" + minute + ":" + second;
        mainBinding.outputTimePickerTextView.setText(time);

    }

    @Override
    public void onDateClick(View calendar, int year, int month, int day) {
        Toast.makeText(this, "year :: " + year + "  month :: " + month + " day :: " + day, Toast.LENGTH_SHORT).show();
    }
}
