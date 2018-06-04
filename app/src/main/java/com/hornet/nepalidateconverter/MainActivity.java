package com.hornet.nepalidateconverter;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hornet.dateconverter.DateConverter;
import com.hornet.dateconverter.DatePicker.DatePickerDialog;
import com.hornet.dateconverter.Model;
import com.hornet.dateconverter.TimePicker.TimePickerDialog;
import com.hornet.dateconverter.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    EditText year, month, day;
    Button adToBs, bsToAd, datePicker, timePicker;
    TextView outputConversion, outputDatePicker, outputTimePicker;
    DateConverter dateConverter;
    private CheckBox modeDarkDate;
    private CheckBox modeCustomAccentDate;
    private CheckBox dismissDate;
    private CheckBox titleDate;
    private CheckBox showYearFirst;
    private CheckBox disablePastDays;
    private CheckBox limitSelectableDays;
    private CheckBox disableSelectedDays;
    private CheckBox tryNewVersion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dateConverter = new DateConverter();
        year = findViewById(R.id.convertEditTextYear);
        month = findViewById(R.id.convertEditTextMonth);
        day = findViewById(R.id.convertEditTextDay);
        adToBs = findViewById(R.id.adToBsConvertButton);
        bsToAd = findViewById(R.id.bsToAdConvertButton);
        datePicker = findViewById(R.id.materialDatePickerButton);
        timePicker = findViewById(R.id.materialTimePickerButton);
        outputConversion = findViewById(R.id.outputConvertTextView);
        outputDatePicker = findViewById(R.id.outputDatePickerTextView);
        outputTimePicker = findViewById(R.id.outputTimePickerTextView);

        adToBs.setOnClickListener(this);
        bsToAd.setOnClickListener(this);
        datePicker.setOnClickListener(this);
        timePicker.setOnClickListener(this);

        modeCustomAccentDate = findViewById(R.id.mode_custom_accent_date);
        disablePastDays = findViewById(R.id.limit_past_date);
        modeDarkDate = findViewById(R.id.mode_dark_date);
        titleDate = findViewById(R.id.title_date);
        dismissDate = findViewById(R.id.dismiss_date);
        showYearFirst = findViewById(R.id.show_year_first);
        tryNewVersion = findViewById(R.id.dialog_version);
        limitSelectableDays = findViewById(R.id.limit_selectable_date);
        disableSelectedDays = findViewById(R.id.disable_selected_dates);


        modeDarkDate.setChecked(Utils.isDarkTheme(this, modeDarkDate.isChecked()));

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.materialDatePickerButton) {
            Calendar now = Calendar.getInstance();

            DatePickerDialog dpd = DatePickerDialog.newInstance(MainActivity.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH));


            if (tryNewVersion.isChecked()) {
                dpd.setVersion(DatePickerDialog.Version.VERSION_2);
            } else {
                dpd.setVersion(DatePickerDialog.Version.VERSION_1);
            }

            dpd.setThemeDark(modeDarkDate.isChecked());

            dpd.dismissOnPause(dismissDate.isChecked());

            dpd.showYearPickerFirst(showYearFirst.isChecked());

            if (modeCustomAccentDate.isChecked()) {
                dpd.setAccentColor(Color.parseColor("#9C27B0"));
            }
            if (titleDate.isChecked()) {
                dpd.setTitle("DatePicker Title");
            }

            DateConverter dcm = new DateConverter();
            Model todayDate = dcm.getNepaliDate(Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

            //this is a perfectly working line of code
            if (disablePastDays.isChecked())
                dpd.setMaxDate(todayDate);

            if (limitSelectableDays.isChecked()) {
                List<Model> myList = new ArrayList<>();

                for (int i = 0; i < 15; i++) {
                    myList.add(new Model(2075, 2, i + 2));
                    myList.add(new Model(2075, 1, i + 2));
                    myList.add(new Model(2075, 3, i + 2));
                }

                dpd.setSelectableDays(myList);
            }
            if (disableSelectedDays.isChecked()) {
                List<Model> myList = new ArrayList<>();

                for (int i = 0; i < 15; i++) {
                    myList.add(new Model(2075, 2, i + 2));
                    myList.add(new Model(2075, 1, i + 2));
                    myList.add(new Model(2075, 3, i + 2));
                }
                dpd.setDisabledDays(myList);
            }

            dpd.show(getSupportFragmentManager(), "DatePicker");
            return;
        }
        if (v.getId() == R.id.materialTimePickerButton) {
            Calendar now = Calendar.getInstance();
            TimePickerDialog dpd = TimePickerDialog.newInstance(MainActivity.this,
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    false);

            if (tryNewVersion.isChecked()) {
                dpd.setVersion(TimePickerDialog.Version.VERSION_2);
            } else {
                dpd.setVersion(TimePickerDialog.Version.VERSION_1);
            }

            dpd.setThemeDark(modeDarkDate.isChecked());
            dpd.dismissOnPause(dismissDate.isChecked());
            if (modeCustomAccentDate.isChecked()) {
                dpd.setAccentColor(Color.parseColor("#9C27B0"));
            }
            if (titleDate.isChecked()) {
                dpd.setTitle("DatePicker Title");
            }


            dpd.show(getFragmentManager(), "TimePicker");
            return;
        }

        if (checkEditTextParameter()) {
            int yy = Integer.parseInt(year.getText().toString());
            int mm = Integer.parseInt(month.getText().toString());
            int dd = Integer.parseInt(day.getText().toString());

            switch (v.getId()) {
                case R.id.adToBsConvertButton:
                    try {
                        Model nepDate = dateConverter.getNepaliDate(yy, mm, dd);
                        outputConversion.setText("" + nepDate.getYear() + " " + getResources().getString(DateConverter.getNepaliMonth(nepDate.getMonth())) + " " +
                                nepDate.getDay() + " " + getDayOfWeek(nepDate.getDayOfWeek()));
                    } catch (IllegalArgumentException e) {
                        Toast.makeText(MainActivity.this, "Date out of Range", Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.bsToAdConvertButton:
                    try {
                        Model engDate = dateConverter.getEnglishDate(yy, mm, dd);
                        outputConversion.setText("" + engDate.getYear() + " " + getEnglishMonth(engDate.getMonth()) + " " +
                                engDate.getDay() + " " + getDayOfWeek(engDate.getDayOfWeek()));
                    } catch (IllegalArgumentException e) {
                        Toast.makeText(MainActivity.this, "Date out of Range", Toast.LENGTH_LONG).show();
                    }
                    break;

            }
        }
    }

    /*
    get english month
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

    /*
    get day of the week
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

    public boolean checkEditTextParameter() {
        if (year.getText().toString().length() == 0) {
            year.setError("Empty Field");
        } else {
            if (month.getText().toString().length() == 0) {
                month.setError("Empty Field");
            } else {
                if (day.getText().toString().length() == 0) {
                    day.setError("Empty Field");
                } else {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "You picked the following date: " + dayOfMonth + " " + getResources().getString(DateConverter.getNepaliMonth(monthOfYear)) + " " + year;
        outputDatePicker.setText(date);
    }


    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String time = "You picked the following time:- " + hourOfDay + ":" + minute + ":" + second;
        outputTimePicker.setText(time);

    }
}
