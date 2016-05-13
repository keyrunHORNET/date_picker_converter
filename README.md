# Nepali Date picker Converter

 This work is the derivative of [Material Date Time Picker](https://github.com/wdullaer/MaterialDateTimePicker),in the original work calender
 of a date picker is based on the [Gregorian Calendar](https://en.wikipedia.org/wiki/Gregorian_calendar),
 but this tries to offer you a calendar based on [Bikram Sambat](https://en.wikipedia.org/wiki/Vikram_Samvat).
 The library uses the code from the [Material Date Time Picker](https://github.com/wdullaer/MaterialDateTimePicker) as a base 
 and tweaked it to fill the need for Nepali Calendar System with the extra feature of converting Gregorian(AD) date to Nepali(BS) and Vice versa.

 
 
## Setup
Include the library folder on your project
 
## Implement an `OnDateSetListener`
In order to receive the date set in the picker, you will need to implement the 
`OnDateSetListener` interfaces. Typically this will be the `Activity` or `Fragment` that creates the Pickers. The callbacks use the same API as the standard Android pickers.
```java

@Override
public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
  String date = "You picked the following date: "+dayOfMonth+"/"+(monthOfYear+1)"/"+year;
  dateTextView.setText(date);
}
```

### Create a `DatePickerDialog` using the supplied factory
You will need to create a new instance of `DatePickerDialog` using the static `newInstance()` method, supplying proper default values and a callback. Once the dialogs are configured, you can call `show()`.
```java
Calendar now = Calendar.getInstance();
DatePickerDialog dpd = DatePickerDialog.newInstance(
  MainActivity.this,
  now.get(Calendar.YEAR),
  now.get(Calendar.MONTH),
  now.get(Calendar.DAY_OF_MONTH)
);
dpd.show(getFragmentManager(), "Datepickerdialog");
```
