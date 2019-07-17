# Nepali Date picker Converter
[![](https://jitpack.io/v/keyrunHORNET/date_picker_converter.svg)](https://jitpack.io/#keyrunHORNET/date_picker_converter)

 This work is the derivative of [Material Date Time Picker](https://github.com/wdullaer/MaterialDateTimePicker), in the original work,  calender
 of a date picker is based on the [Gregorian Calendar](https://en.wikipedia.org/wiki/Gregorian_calendar), but this tries to offer you a calendar based on [Bikram Sambat](https://en.wikipedia.org/wiki/Vikram_Samvat).
 The library uses the code from the [Material Date Time Picker](https://github.com/wdullaer/MaterialDateTimePicker) as a base 
 and tweaked it to fill the need for Nepali Calendar System with the extra feature of converting Gregorian(AD) date to Nepali(BS) and Vice versa.

### Contributor
 `Jeffrey Jongko`

Date Picker | Calendar 
---- | ---- 
![Date Picker](https://raw.githubusercontent.com/keyrunHORNET/date_picker_converter/master/Screenshot_2018-06-04.png) | ![Calendar](https://raw.githubusercontent.com/keyrunHORNET/date_picker_converter/master/calendar.png) 

### demo (old version)
https://play.google.com/store/apps/details?id=com.hornet.nepalidateconverter


## Setup
    
 The easiest way:

   Step 1.
   Add the JitPack repository to your build file.
  Add it in your root build.gradle at the end of repositories:

```java

   allprojects {
		 repositories {
			  ...
			  maven { url "https://jitpack.io" }
		 }
	}
 ```
   Step 2.
   Add the dependency
```java

 dependencies {
	       implementation 'com.github.keyrunHORNET:date_picker_converter:$latest_version'
	}
 ```
You may also add the library as an Android Library to your project. All the library files live in ```library```.

## Using Calendar
include the calendar in your desired layout
```xml
 <com.hornet.dateconverter.CalendarView.Calendar
        android:id="@+id/calendar"
        android:layout_width="wrap_content"
        android:layout_height="300dp" />
```
In order to receive the date checked in the calendar, you will need to implement the `CalendarView.Calendar.OnDateSetListener` interface. This will be a the `Activity` or `Fragment` that creates the calendar in their respective layout. Then hook up the interface with calendar in your layout.

```java
calendar.setOnDateSetListener(this);

@Override
   public void onDateClick(View calendar, int year, int month, int day) {
        Toast.makeText(this, "year :: " + year + "  month :: " + (month + 1) + " day :: " + day, Toast.LENGTH_SHORT).show();
   }

```
## Using Date Picker / Time Picker
 
### Implement an `OnDateSetListener` / `OnTimeSetListener`
In order to receive the date set in the picker, you will need to implement the 
`OnDateSetListener` interfaces. Typically this will be the `Activity` or `Fragment` that creates the Pickers. The callbacks use the same API as the standard Android pickers.
```java

@Override
public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
  String date = "You picked the following date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
  dateTextView.setText(date);
}
```

```java

@Override
public void onTimeSet(RadialPickerLayout view, int hour, int minute, int second) {
  String time = "You picked the following time:- "+hour+":"+minute+":"+second;
  timeTextView.setText(time);
}
```

### Create a `DatePickerDialog` / `TimePickerDialog` using the supplied factory
You will need to create a new instance of `DatePickerDialog` using the static `newInstance()` method, supplying proper default values and a callback. Once the dialogs are configured, you can call `show()`.
```java
DatePickerDialog dpd = DatePickerDialog.newInstance(MainActivity.this);
dpd.show(getFragmentManager(), "Datepickerdialog");
```

## Using Date Converter

```java
DateConverter dc=new DateConverter();
```
`year` `month` `day` when passed beyond the conversion range throws an `IllegalArgumentException`.


* Converting english Date to Nepali date (i.e A.D to B.S):
```java
getNepaliDate(int engYY,int engMM,int engDD);
```
you can also pass the calendar instance as an argument

* Converting Nepali Date to English date (i.e B.S to A.D):
```java
getEnglishDate(int nepYY,int nepMM,int nepDD);
```

Both of the above method returns the model of date.
`Model` is a DTO with getter and setter for `year` `month` and `day` .


Example:
```java
Model outputOfConversion=dc.getEnglishDate(nepYY,nepMM,nepDD);

int year=outputOfConversion.getYear();
int month=outputOfConversion.getMonth(); 
int day=outputOfConversion.getDay();
```

### Additional Options

* `isEngDateInRange(int yy,int mm,int dd)` this static method of class `DateConverter` returns `true` if english date is within the range of conversion.

* `isNepDateInRange(int yy,int mm,int dd)` this static method of class `DateConverter` returns `true` if nepali date is within the range of conversion.

* `getFirstWeekDayMonth(int yy,int mm)` public method of class `DateConverter` returns `int` range from `1-7` the starting week day of a given month in given nepali year.

* `noOfDaysInMonth(int yy,int mm)` public method of class `DateConverter` returns the no of days `int` in a particular month of a given nepali year.

* `convertModelToCalendar(Model dateModel)` public method of class `DateConverter` returns Gregorian `Calendar`   

* `convertCalendarToModel(Calendar dateModel)` public method of class `DateConverter` returns Nepali date `Model`   

* `getTodayNepaliDate()` public method of class `DateConverter` returns Nepali Date Model   

* `getWeekDay(int nepYY, int nepMM, int nepDD)` public method of class `DateConverter` returns `int` [1-7] representing seven days of week   

* `DatePickerDialog` dark theme
The `DatePickerDialog` has a dark theme that can be set by calling
```java
dpd.setThemeDark(true);
```

* `setAccentColor(String color)` and `setAccentColor(int color)`
Set the accentColor to be used by the Dialog. The String version parses the color out using `Color.parseColor()`. The int version requires a ColorInt bytestring. It will explicitly set the color to fully opaque.

* `DatePickerDialog` `setTitle(String title)`
Shows a title at the top of the `DatePickerDialog`

* `showYearPickerFirst(boolean yearPicker)`  
Show the year picker first, rather than the month and day picker.

* `setScrollOrientation(int orientation)`
change the scroll orientation of the picker.

* `setVersion(int version)`
choose between two versions of picker.

* `setMinDate(Model minDate)`
enable only dates after minDate for selection in picker.

* `setMaxDate(Model maxDate)`
disable all dates after maxDate for selection in picker.

* `setHighlightedDays(List<Model> days)`
highlight all the days provided in the ArrayList of model, as an argument.

* `setSelectableDays(List<Model> days)`
only the days provided in the ArrayList of model, as an argument can be selected in picker, all other dates are disabled.

* `setDisabledDays(List<Model> days)`
 days provided in the ArrayList of model, as an argument can not be selected in picker, all other dates are enabled.

* `dismissOnPause(boolean dismissOnPause)`  
Set whether the picker dismisses itself when the parent Activity is paused or whether it recreates itself when the Activity is resumed.


## FAQ
### Why Date Model instead of Java `Calendar`
In java `Calendar` class DAY_OF_MONTH cant be assigned to 32, and throws an `IllegalArgumentError`,but in Bikram Sambat Calendar system we have DAY_OF_MONTH with the value of 32 in several month.

### Why does the `DatePickerDialog` and `DateConverter` return the selected month -1?
In the java `Calendar` class months use 0 based indexing: January is month 0, December is month 11. This convention is widely used in the java world, for example the native Android DatePicker.And in Nepali Calendar `Baisakh` is month 0, `Chaitra` is month 11.

## License

Copyright (c) 2016 Kiran Gyawali

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


#### Heads Up
* input argument on conversion method ==> month starts from 1 and ends at 12
* output of conversion ==> month starts from 0 and ends at 11

