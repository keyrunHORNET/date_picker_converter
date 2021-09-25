package com.hornet.dateconverter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Kiran Gyawali on 7/1/2019.
 */
@RunWith(RobolectricTestRunner.class)
public class DateConverterTest {
    private DateConverter dc;

    @Before
    public void init() {
        dc = new DateConverter();
    }

    @Test
    public void aTestConversion() {
        Model nepaliDate = dc.getNepaliDate(2019, 7, 1);

        assertThat(nepaliDate.getDay(), is(16));
        assertThat(nepaliDate.getMonth(), is(2));//ashar
        assertThat(nepaliDate.getYear(), is(2076));

        Model englishDate = dc.getEnglishDate(2076, 3, 16);

        assertThat(englishDate.getDay(), is(1));
        assertThat(englishDate.getMonth(), is(6));//july
        assertThat(englishDate.getYear(), is(2019));

    }

    @Test
    public void bTestConversion() {
        Model nepaliDate = dc.getNepaliDate(2007, 2, 19);

        assertThat(nepaliDate.getDay(), is(7));
        assertThat(nepaliDate.getMonth(), is(10));//falgun
        assertThat(nepaliDate.getYear(), is(2063));

        Model englishDate = dc.getEnglishDate(2063, 11, 7);

        assertThat(englishDate.getDay(), is(19));
        assertThat(englishDate.getMonth(), is(1));//feb
        assertThat(englishDate.getYear(), is(2007));

    }

    @Test
    public void cTestConversion() {
        Model nepaliDate = dc.getNepaliDate(1997, 2, 18);

        assertThat(nepaliDate.getDay(), is(7));
        assertThat(nepaliDate.getMonth(), is(10));//falgun
        assertThat(nepaliDate.getYear(), is(2053));

        Model englishDate = dc.getEnglishDate(2053, 11, 7);

        assertThat(englishDate.getDay(), is(18));
        assertThat(englishDate.getMonth(), is(1));//feb
        assertThat(englishDate.getYear(), is(1997));

    }

    @Test
    public void dTestConversion() {
        Model nepaliDate = dc.getNepaliDate(2027, 2, 19);

        assertThat(nepaliDate.getDay(), is(7));
        assertThat(nepaliDate.getMonth(), is(10));//falgun
        assertThat(nepaliDate.getYear(), is(2083));

        Model englishDate = dc.getEnglishDate(2083, 11, 7);

        assertThat(englishDate.getDay(), is(19));
        assertThat(englishDate.getMonth(), is(1));//feb
        assertThat(englishDate.getYear(), is(2027));

    }

    @Test
    public void eTestConversion() {
        Model nepaliDate = dc.getNepaliDate(2017, 2, 18);

        assertThat(nepaliDate.getDay(), is(7));
        assertThat(nepaliDate.getMonth(), is(10));//falgun
        assertThat(nepaliDate.getYear(), is(2073));

        Model englishDate = dc.getEnglishDate(2073, 11, 7);

        assertThat(englishDate.getDay(), is(18));
        assertThat(englishDate.getMonth(), is(1));//feb
        assertThat(englishDate.getYear(), is(2017));

    }

    @Test
    public void getEnglishDate() {
        Calendar calendar = new GregorianCalendar(2017, 1, 18);
        Model nepaliDate = dc.getNepaliDate(calendar);

        assertThat(nepaliDate.getDay(), is(7));
        assertThat(nepaliDate.getMonth(), is(10));//falgun
        assertThat(nepaliDate.getYear(), is(2073));


    }

    @Test
    public void getNepaliDate() {
        Model nepaliDate = new Model(2073, 11, 7);//falgun
        Calendar englishDate = dc.getEnglishDate(nepaliDate);

        assertThat(englishDate.get(Calendar.DAY_OF_MONTH), is(18));
        assertThat(englishDate.get(Calendar.MONTH), is(1));
        assertThat(englishDate.get(Calendar.YEAR), is(2017));
    }

    @Test
    public void getFirstWeekDayMonth() {
        assertThat(dc.getFirstWeekDayMonth(1980, 1), is(6));
    }

    @Test
    public void noOfDaysInMonth() {
        assertThat(dc.noOfDaysInMonth(2090, 9), is(29));
    }

    @Test
    public void getWeekDay() {
        assertThat(dc.getWeekDay(1980, 1, 1), is(6));
    }

    @Test
    public void getTodayNepaliDate() {
        Model todayNepaliDate = dc.getTodayNepaliDate();

        Calendar todayCalendar = Calendar.getInstance();
        Model nepaliDate = dc.getNepaliDate(todayCalendar);

        assertThat(nepaliDate.getDay(), is(todayNepaliDate.getDay()));
        assertThat(nepaliDate.getMonth(), is(todayNepaliDate.getMonth()));
        assertThat(nepaliDate.getYear(), is(todayNepaliDate.getYear()));

    }

    @Test
    public void convertModelToCalendar() {
        Model testModel = dc.getTodayNepaliDate();

        Calendar testCalendar = DateConverter.convertModelToCalendar(testModel);

        assertThat(testModel.getYear(), is(testCalendar.get(Calendar.YEAR)));
        assertThat(testModel.getMonth(), is(testCalendar.get(Calendar.MONTH)));
        assertThat(testModel.getDay(), is(testCalendar.get(Calendar.DAY_OF_MONTH)));
    }

    @Test
    public void convertCalendarToModel() {

        Calendar testCalendar = Calendar.getInstance();

        Model testModel = DateConverter.convertCalendarToModel(testCalendar);

        assertThat(testModel.getYear(), is(testCalendar.get(Calendar.YEAR)));
        assertThat(testModel.getMonth(), is(testCalendar.get(Calendar.MONTH)));
        assertThat(testModel.getDay(), is(testCalendar.get(Calendar.DAY_OF_MONTH)));
    }
}
