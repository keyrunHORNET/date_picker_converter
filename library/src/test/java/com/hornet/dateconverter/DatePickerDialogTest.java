package com.hornet.dateconverter;

import com.hornet.dateconverter.DatePicker.DatePickerDialog;

import org.junit.Assert;
import org.junit.Test;

public class DatePickerDialogTest {
    @Test
    public void isHighlightedShouldReturnFalseIfNoHighlightedDaysAreSet() {
        DatePickerDialog dpd = DatePickerDialog.newInstance((view, year, monthOfYear, dayOfMonth) -> {

        });
        Assert.assertFalse(dpd.isHighlighted(1990, 1, 1));
    }
}