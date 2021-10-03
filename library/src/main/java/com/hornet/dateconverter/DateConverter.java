
/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.hornet.dateconverter;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import org.jetbrains.annotations.Contract;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author Kiran Gyawali
 * <p>
 * so, you are interested enough to try and find the logic behind conversion
 * or may be you want to know weather i did it by myself or .......[you know what i mean]
 * are you here to JUDGE ME ?........ are you?
 * I don't mind unless you are a metalHead
 * <p>
 * Created by Hornet on 4/29/2016.
 * Edited by Jeffrey Jongko on 7/2/2019.
 */
public class DateConverter {
    private static final String TAG = DateConverter.class.getSimpleName();


    public DateConverter() {
    }

    /**
     * convert nepali date into english date
     * <p>
     * I've got 99 problems, but you ain't one
     * --Jay Z
     *
     * @param nepYY {@code int} year of nepali date [1970-2090]
     * @param nepMM {@code int} month of nepali date [1-12]
     * @param nepDD {@code int} day of a nepali date [1-32]
     * @return {@link Model } object with the converted value from nepali to english
     */
    public Model getEnglishDate(@IntRange(from = 1970, to = 2090) int nepYY,
                                @IntRange(from = 1, to = 12) int nepMM,
                                @IntRange(from = 1, to = 32) int nepDD) {

        if (isNepDateInConversionRange(nepYY, nepMM, nepDD)) {

            int startingEngYear = 1913;
            int startingEngMonth = 4;
            int startingEngDay = 13;


            int startingDayOfWeek = Calendar.SUNDAY; // 1970/1/1 is Sunday /// based on www.ashesh.com.np/neplai-date-converter

            int startingNepYear = 1970;
            int startingNepMonth = 1;
            int startingNepDay = 1;

            Model tempModel = new Model();

            int engYY, engMM, engDD;
            long totalNepDaysCount = 0;

            //*count total no of days in nepali year from our starting range*//
            for (int i = startingNepYear; i < nepYY; i++) {
                for (int j = 1; j <= 12; j++) {
                    totalNepDaysCount = totalNepDaysCount + NepaliCalendarUtils.INSTANCE.getDaysInMonthMap().get(i)[j];
                }
            }
            /*count total days in terms of month*/
            for (int j = startingNepMonth; j < nepMM; j++) {
                totalNepDaysCount = totalNepDaysCount + NepaliCalendarUtils.INSTANCE.getDaysInMonthMap().get(nepYY)[j];
            }
            /*count total days in terms of date*/
            totalNepDaysCount += nepDD - startingNepDay;

            int[] daysInMonth = new int[]{0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            int[] daysInMonthOfLeapYear = new int[]{0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            engYY = startingEngYear;
            engMM = startingEngMonth;
            engDD = startingEngDay;
            int endDayOfMonth;
            int dayOfWeek = startingDayOfWeek;
            while (totalNepDaysCount != 0) {
                if (isEngLeapYear(engYY)) {
                    endDayOfMonth = daysInMonthOfLeapYear[engMM];
                } else {
                    endDayOfMonth = daysInMonth[engMM];
                }
                engDD++;
                dayOfWeek++;

                if (engDD > endDayOfMonth) {
                    engMM++;
                    engDD = 1;
                    if (engMM > 12) {
                        engYY++;
                        engMM = 1;
                    }
                }
                if (dayOfWeek > 7) {
                    dayOfWeek = 1;
                }
                totalNepDaysCount--;
            }
            tempModel.setDay(engDD);
            tempModel.setYear(engYY);
            tempModel.setMonth(engMM - 1);
            tempModel.setDayOfWeek(dayOfWeek);
            return tempModel;
        } else throw new IllegalArgumentException("Out of Range: Date is out of range to Convert");
    }

    /**
     * @param date {@link Calendar} Gregorian calendar
     * @return {@link Model} dateModel after conversion from the given Calendar
     */
    @SuppressWarnings("unused")
    public Calendar getEnglishDate(Model date) {
        Model tempModel = getEnglishDate(date.getYear(), date.getMonth(), date.getDay());
        return new GregorianCalendar(tempModel.getYear(), tempModel.getMonth(), tempModel.getDay(), 0, 0, 0);
    }

    /**
     * convert english date into nepali date
     * <p>
     * this is probably the method you are looking for
     * conversion logic
     * get into it
     * tweak it, make it, break it, shake it, share it
     * </p>
     *
     * @param engYY {@code int} year of nepali date [1944-2033]
     * @param engMM {@code int} month of nepali date [1-12]
     * @param engDD {@code int} day of a nepali date [1-31]
     * @return return nepali date as a {@link Model} object converted from english to nepali
     */
    public Model getNepaliDate(@IntRange(from = 1913 - 2033) int engYY,
                               @IntRange(from = 1, to = 12) int engMM,
                               @IntRange(from = 1, to = 31) int engDD) {

        if (isEngDateInConversionRange(engYY, engMM, engDD)) {

            int startingEngYear = 1913;
            int startingEngMonth = 4;
            int startingEngDay = 13;

            int startingDayOfWeek = Calendar.SUNDAY; // 1913/4/13 is a Sunday

            int startingNepYear = 1970;
            int startingNepMonth = 1;
            int startingNepDay = 1;


            int nepYY, nepMM, nepDD;
            int dayOfWeek = startingDayOfWeek;

            Model tempModel = new Model();

            /*
            Calendar currentEngDate = new GregorianCalendar();
            currentEngDate.set(engYY, engMM, engDD);
            Calendar baseEngDate = new GregorianCalendar();
            baseEngDate.set(startingEngYear, startingEngMonth, startingEngDay);
            long totalEngDaysCount = daysBetween(baseEngDate, currentEngDate);
            */

            /*calculate the days between two english date*/
            DateTime base = new DateTime(startingEngYear, startingEngMonth, startingEngDay, 0, 0); // June 20th, 2010
            DateTime newDate = new DateTime(engYY, engMM, engDD, 0, 0); // July 24th
            long totalEngDaysCount = Days.daysBetween(base, newDate).getDays();

            nepYY = startingNepYear;
            nepMM = startingNepMonth;
            nepDD = startingNepDay;

            while (totalEngDaysCount != 0) {
                int daysInMonth = NepaliCalendarUtils.INSTANCE.getDaysInMonthMap().get(nepYY)[nepMM];
                nepDD++;
                if (nepDD > daysInMonth) {
                    nepMM++;
                    nepDD = 1;
                }
                if (nepMM > 12) {
                    nepYY++;
                    nepMM = 1;
                }
                dayOfWeek++;
                if (dayOfWeek > 7) {
                    dayOfWeek = 1;
                }
                totalEngDaysCount--;
            }
            tempModel.setYear(nepYY);
            tempModel.setMonth(nepMM - 1);
            tempModel.setDay(nepDD);
            tempModel.setDayOfWeek(dayOfWeek);

            return tempModel;
        } else throw new IllegalArgumentException("Out of Range: Date is out of range to Convert");
    }

    /**
     * @param date {@link Calendar} Gregorian calendar
     * @return {@link Model} dateModel after conversion from the given Calendar
     */
    @SuppressWarnings("unused")
    public Model getNepaliDate(Calendar date) {
        return getNepaliDate(date.get(Calendar.YEAR),
                date.get(Calendar.MONTH) + 1, date.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * calculate whether english year is leap year or not
     * <p>
     * what if i tell you this method is useless! would you believe me?
     * </p>
     *
     * @param year {@code int} value of the year
     * @return {@code Boolean} true if it is leapYear and false if it is not a leapYear
     */
    private static boolean isEngLeapYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
    }

    /**
     * returns nepali month
     * <p>
     * where's waldo?
     * </p>
     *
     * @param month {@code int} [0-11] integer value of month
     * @return {@code int} representing the string value of month
     */
    @Contract(pure = true)
    public static int getNepaliMonthString(@IntRange(from = 0, to = 11) int month) {
        switch (month) {
            case 0:
                return (R.string.Baisakh);
            case 1:
                return (R.string.Jestha);
            case 2:
                return (R.string.Ashar);
            case 3:
                return (R.string.Shrawan);
            case 4:
                return (R.string.Bhadra);
            case 5:
                return (R.string.Ashoj);
            case 6:
                return (R.string.Kartik);
            case 7:
                return (R.string.Mangsir);
            case 8:
                return (R.string.Poush);
            case 9:
                return (R.string.Magh);
            case 10:
                return (R.string.Falgun);
            case 11:
                return (R.string.Chaitra);
        }
        return 0;
    }

    /**
     * check if english date is in the range of conversion
     * <p>i am writing this comment just to entertain you
     * rather than reading all boring syntax and stuff,
     * where is the fun in that???
     * </p>
     *
     * @param yy {@code int} year of english date
     * @param mm {@code int} month of english date
     * @param dd {@code int} day of a english date
     * @return {@code Boolean} true if it is in range or false if it is not in range
     */
    @Contract(pure = true)
    private static boolean isEngDateInConversionRange(int yy, int mm, int dd) {
        return (yy >= 1913 && yy <= 2033) && (mm >= 1 && mm <= 12) && (dd >= 1 && dd <= 31);
    }

    /**
     * check if nepali date is in the range of conversion
     * <p>anyone interested in improving this whole algorithm .....you need to have 3 P's </p>
     *
     * @param yy {@code int} year of nepali date
     * @param mm {@code int} month of nepali date
     * @param dd {@code int} day of a nepali date
     * @return {@code Boolean} true if it is in range / false if it is not in range
     */
    @Contract(pure = true)
    private static boolean isNepDateInConversionRange(int yy, int mm, int dd) {
        return (yy >= 1970 && yy <= 2090) && (mm >= 1 && mm <= 12) && (dd >= 1 && dd <= 32);
    }

    /**
     * returns the day of week start nepali month
     * <p>
     * who wouldn't want anything fast unless its death.
     * its better to take data from references, rather than computing at every step you need.
     * if you think memory is your first priority than performance....i would have to think again
     * </p>
     *
     * @param yy {@code int} year of nepali date [1970-2090]
     * @param mm {@code int} month of nepali date [1-12]
     * @return {@code int} first week day of given month in given year
     */
    public int getFirstWeekDayMonth(@IntRange(from = 1970, to = 2090) int yy, @IntRange(from = 1, to = 12) int mm) {
        return NepaliCalendarUtils.INSTANCE.getStartWeekDayMonthMap().get(yy)[mm];
    }

    /**
     * returns the number of days in a particular month of a nepali year
     * <p>
     * As you all are well aware
     * nepali date is not like the one in {@link GregorianCalendar}
     * </p>
     *
     * @param yy {@code int} nepali year
     * @param mm {@code int} nepali month
     * @return {@link int} number of days in a given month of a given year
     */
    public int noOfDaysInMonth(@IntRange(from = 1970, to = 2090) int yy, @IntRange(from = 1, to = 12) int mm) {
        return NepaliCalendarUtils.INSTANCE.getDaysInMonthMap().get(yy)[mm];
    }

    /**
     * returns the model with value of weekDay for a given DateModel
     * <p>
     * suppose you created new nepaliDate {@link Model} and want to know which weekday does it belong to
     * </p>
     *
     * @param nepaliDate {@link Model} any nepali date model with the missing weekDay value
     * @return {@link Model} nepaliDate is returned after setting the weekDay value
     */
    public Model fillMissingWeekDayValue(@NonNull Model nepaliDate) {
        int startWeekDay = getFirstWeekDayMonth(nepaliDate.getYear(),
                nepaliDate.getMonth());
        for (int i = 1; i < nepaliDate.getDay(); i++) {
            startWeekDay++;
            if (startWeekDay > 7) startWeekDay = 1;
        }
        nepaliDate.setDayOfWeek(startWeekDay);
        return nepaliDate;
    }

    /**
     * if you want to know the weekday of a specific date without need of passing {@link Model} dateModel
     * <p>
     * i don't see much significance of this method
     * "BIG BUT"
     * we can be lazy sometimes , we may want to do things differently or we just want to be a black sheep for no reason
     * so just in case
     * </P>
     *
     * @param yy {@code int} year of nepali date [1970-2090]
     * @param mm {@code int} month of nepali date [1-12]
     * @param dd {@code int} day of a nepali date [1-32]
     * @return week day value in {@link int} [0-7]
     */
    public int getWeekDay(@IntRange(from = 1970, to = 2090) int yy, @IntRange(from = 1, to = 12) int mm, @IntRange(from = 1, to = 32) int dd) {
        Model tempModel = fillMissingWeekDayValue(new Model(yy, mm, dd));
        return tempModel.getDayOfWeek();
    }

    /**
     * aja kati gate ho oi?
     *
     * @return {@link Model} returns the date model with value of present date
     */

    public Model getTodayNepaliDate() {
        return getNepaliDate(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
    }

    /**
     * @param tempModel {@link Model} dateModel that need to be converted into Calendar
     * @return {@link Calendar} Gregorian calendar
     */
    @SuppressWarnings("unused")
    public static Calendar convertModelToCalendar(Model tempModel) {
        return new GregorianCalendar(tempModel.getYear(), tempModel.getMonth(), tempModel.getDay(), 0, 0, 0);
    }

    /**
     * @param date {@link Calendar} Gregorian calendar
     * @return {@link Model} dateModel after conversion from the given Calendar
     */
    @SuppressWarnings("unused")
    public static Model convertCalendarToModel(Calendar date) {
        return new Model(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
    }

}
