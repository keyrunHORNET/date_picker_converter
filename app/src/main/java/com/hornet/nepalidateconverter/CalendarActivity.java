package com.hornet.nepalidateconverter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hornet.dateconverter.CalendarView.Calendar;
import com.hornet.dateconverter.DateConverter;
import com.hornet.dateconverter.Model;

import java.util.ArrayList;
import java.util.List;

public class CalendarActivity extends AppCompatActivity implements com.hornet.dateconverter.CalendarView.Calendar.OnDateSetListener {
    Calendar mCalendar;
    DateConverter dateConverter;
    TextView textOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        dateConverter = new DateConverter();
        mCalendar = findViewById(R.id.calendar);
        textOutput = findViewById(R.id.textOutput);
        mCalendar.setOnDateSetListener(this);
        mCalendar.setHighlightedDays(DateConverter.getAllSaturdays());
        setTitle("Nepali Calendar");
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onDateClick(View calendar, int year, int month, int day) {
        textOutput.setText("year :: " + year + "  month :: " + (month + 1) + " day :: " + day);
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

}
