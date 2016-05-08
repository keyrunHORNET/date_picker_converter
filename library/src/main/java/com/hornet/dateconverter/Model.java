package com.hornet.dateconverter;

import java.util.GregorianCalendar;

/**
 * Created by Hornet on 4/29/2016.
 */
public class Model {

    private int day;
    private int year;
    private int month;
    private int dayOfWeek;

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Model() {
        GregorianCalendar date = new GregorianCalendar();
        day = date.get(GregorianCalendar.DAY_OF_MONTH);
        month = date.get(GregorianCalendar.MONTH) + 1;
        year = date.get(GregorianCalendar.YEAR);
    }


    public Model(int year, int month, int day) {
        this.year=year;
        this.month=month;
        this.day=day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

}
