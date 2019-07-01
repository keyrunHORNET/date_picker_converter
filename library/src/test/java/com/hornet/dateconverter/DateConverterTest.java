package com.hornet.dateconverter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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
    }

    @Test
    public void bTestConversion() {
        Model nepaliDate = dc.getNepaliDate(2007, 2, 19);
        assertThat(nepaliDate.getDay(), is(7));
        assertThat(nepaliDate.getMonth(), is(10));//falgun
        assertThat(nepaliDate.getYear(), is(2063));
    }

    @Test
    public void cTestConversion() {
        Model nepaliDate = dc.getNepaliDate(1997, 2, 18);
        assertThat(nepaliDate.getDay(), is(7));
        assertThat(nepaliDate.getMonth(), is(10));//falgun
        assertThat(nepaliDate.getYear(), is(2053));
    }

    @Test
    public void dTestConversion() {
        Model nepaliDate = dc.getNepaliDate(2027, 2, 19);
        assertThat(nepaliDate.getDay(), is(7));
        assertThat(nepaliDate.getMonth(), is(10));//falgun
        assertThat(nepaliDate.getYear(), is(2083));
    }

    @Test
    public void eTestConversion() {
        Model nepaliDate = dc.getNepaliDate(2017, 2, 18);
        assertThat(nepaliDate.getDay(), is(7));
        assertThat(nepaliDate.getMonth(), is(10));//falgun
        assertThat(nepaliDate.getYear(), is(2073));
    }
}
