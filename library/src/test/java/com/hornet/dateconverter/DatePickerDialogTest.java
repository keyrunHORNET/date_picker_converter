package com.hornet.dateconverter;

import com.hornet.dateconverter.DatePicker.DatePickerDialog;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class DatePickerDialogTest {
    @Test
    public void isHighlightedShouldReturnFalseIfNoHighlightedDaysAreSet() {
        DatePickerDialog dpd = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

            }
        });
        Assert.assertFalse(dpd.isHighlighted(1990, 1, 1));
    }
}