package com.hornet.dateconverter

import android.util.Log
import android.util.SparseArray
import androidx.annotation.IntRange
import java.util.*

object NepaliCalendarUtils {

    val daysInMonthMap: SparseArray<IntArray> = setDaysInMonthMap()
    val startWeekDayMonthMap: SparseArray<IntArray> = setStartWeekDayMonthMap()


    private fun setDaysInMonthMap(): SparseArray<IntArray> {
        val daysInMonthMap: SparseArray<IntArray> = SparseArray()

        /*
         The 0s at index 0 are dummy values so as to make the int array of
         days in months seems more intuitive that index 1 refers to first
         month "Baisakh", index 2 refers to second month "Jesth" and so on.
         */


        /*  INFO
         *  this is a dummy data, last month value is an educated guess but other are dummy,
         *  do not rely on this and its of no use except to prevent null
         *  don't believe the values of year 1969
         *  however it does not effect the conversion
         */daysInMonthMap.put(1969, intArrayOf(0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 31))

        /*
         *  below dates are based on
         *  based on https://github.com/bahadurbaniya/Date-Converter-Bikram-Sambat-to-English-Date/blob/master/src/main/java/np/com/converter/date/nepali/Lookup.java
         */
        daysInMonthMap.put(1970, intArrayOf(0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(1971, intArrayOf(0, 31, 31, 32, 31, 32, 30, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(1972, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31))
        daysInMonthMap.put(1973, intArrayOf(0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31))
        daysInMonthMap.put(1974, intArrayOf(0, 30, 32, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(1975, intArrayOf(0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(1976, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31))
        daysInMonthMap.put(1977, intArrayOf(0, 30, 32, 31, 32, 31, 31, 29, 30, 29, 30, 29, 31))
        daysInMonthMap.put(1978, intArrayOf(0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(1979, intArrayOf(0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(1980, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31))
        daysInMonthMap.put(1981, intArrayOf(0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30))
        daysInMonthMap.put(1982, intArrayOf(0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(1983, intArrayOf(0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(1984, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31))
        daysInMonthMap.put(1985, intArrayOf(0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30))
        daysInMonthMap.put(1986, intArrayOf(0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(1987, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(1988, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31))
        daysInMonthMap.put(1989, intArrayOf(0, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(1990, intArrayOf(0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(1991, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30))
        daysInMonthMap.put(1992, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31))
        daysInMonthMap.put(1993, intArrayOf(0, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(1994, intArrayOf(0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(1995, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30))
        daysInMonthMap.put(1996, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31))
        daysInMonthMap.put(1997, intArrayOf(0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(1998, intArrayOf(0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(1999, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31))


        //old
        daysInMonthMap.put(2000, intArrayOf(0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31))
        daysInMonthMap.put(2001, intArrayOf(0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2002, intArrayOf(0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2003, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31))
        daysInMonthMap.put(2004, intArrayOf(0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31))
        daysInMonthMap.put(2005, intArrayOf(0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2006, intArrayOf(0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2007, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31))
        daysInMonthMap.put(2008, intArrayOf(0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 29, 31))
        daysInMonthMap.put(2009, intArrayOf(0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2010, intArrayOf(0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2011, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31))
        daysInMonthMap.put(2012, intArrayOf(0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30))
        daysInMonthMap.put(2013, intArrayOf(0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2014, intArrayOf(0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2015, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31))
        daysInMonthMap.put(2016, intArrayOf(0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30))
        daysInMonthMap.put(2017, intArrayOf(0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2018, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2019, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31))
        daysInMonthMap.put(2020, intArrayOf(0, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2021, intArrayOf(0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2022, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30))
        daysInMonthMap.put(2023, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31))
        daysInMonthMap.put(2024, intArrayOf(0, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2025, intArrayOf(0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2026, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31))
        daysInMonthMap.put(2027, intArrayOf(0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31))
        daysInMonthMap.put(2028, intArrayOf(0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2029, intArrayOf(0, 31, 31, 32, 31, 32, 30, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2030, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31))
        daysInMonthMap.put(2031, intArrayOf(0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31))
        daysInMonthMap.put(2032, intArrayOf(0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2033, intArrayOf(0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2034, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31))
        daysInMonthMap.put(2035, intArrayOf(0, 30, 32, 31, 32, 31, 31, 29, 30, 30, 29, 29, 31))
        daysInMonthMap.put(2036, intArrayOf(0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2037, intArrayOf(0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2038, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31))
        daysInMonthMap.put(2039, intArrayOf(0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30))
        daysInMonthMap.put(2040, intArrayOf(0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2041, intArrayOf(0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2042, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31))
        daysInMonthMap.put(2043, intArrayOf(0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30))
        daysInMonthMap.put(2044, intArrayOf(0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2045, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2046, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31))
        daysInMonthMap.put(2047, intArrayOf(0, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2048, intArrayOf(0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2049, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30))
        daysInMonthMap.put(2050, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31))
        daysInMonthMap.put(2051, intArrayOf(0, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2052, intArrayOf(0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2053, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30))
        daysInMonthMap.put(2054, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31))
        daysInMonthMap.put(2055, intArrayOf(0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2056, intArrayOf(0, 31, 31, 32, 31, 32, 30, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2057, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31))
        daysInMonthMap.put(2058, intArrayOf(0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31))
        daysInMonthMap.put(2059, intArrayOf(0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2060, intArrayOf(0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2061, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31))
        daysInMonthMap.put(2062, intArrayOf(0, 30, 32, 31, 32, 31, 31, 29, 30, 29, 30, 29, 31))
        daysInMonthMap.put(2063, intArrayOf(0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2064, intArrayOf(0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2065, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31))
        daysInMonthMap.put(2066, intArrayOf(0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 29, 31))
        daysInMonthMap.put(2067, intArrayOf(0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2068, intArrayOf(0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2069, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31))
        daysInMonthMap.put(2070, intArrayOf(0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30))
        daysInMonthMap.put(2071, intArrayOf(0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2072, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2073, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31))
        daysInMonthMap.put(2074, intArrayOf(0, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2075, intArrayOf(0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2076, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30))
        daysInMonthMap.put(2077, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31))
        daysInMonthMap.put(2078, intArrayOf(0, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2079, intArrayOf(0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30))
        daysInMonthMap.put(2080, intArrayOf(0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30))
        daysInMonthMap.put(2081, intArrayOf(0, 31, 31, 32, 32, 31, 30, 30, 30, 29, 30, 30, 30))
        daysInMonthMap.put(2082, intArrayOf(0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30))
        daysInMonthMap.put(2083, intArrayOf(0, 31, 31, 32, 31, 31, 30, 30, 30, 29, 30, 30, 30))
        daysInMonthMap.put(2084, intArrayOf(0, 31, 31, 32, 31, 31, 30, 30, 30, 29, 30, 30, 30))
        daysInMonthMap.put(2085, intArrayOf(0, 31, 32, 31, 32, 30, 31, 30, 30, 29, 30, 30, 30))
        daysInMonthMap.put(2086, intArrayOf(0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30))
        daysInMonthMap.put(2087, intArrayOf(0, 31, 31, 32, 31, 31, 31, 30, 30, 29, 30, 30, 30))
        daysInMonthMap.put(2088, intArrayOf(0, 30, 31, 32, 32, 30, 31, 30, 30, 29, 30, 30, 30))
        daysInMonthMap.put(2089, intArrayOf(0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30))
        daysInMonthMap.put(2090, intArrayOf(0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30))
        /* */ /*new*/ /*
        // based on https://github.com/bahadurbaniya/Date-Converter-Bikram-Sambat-to-English-Date/blob/master/src/main/java/np/com/converter/date/nepali/Lookup.java
        daysInMonthMap.put(2091, new int[]{31, 31, 32, 31, 31, 31, 30, 30, 29, 30, 30, 30});// 2091
        daysInMonthMap.put(2092, new int[]{31, 31, 32, 32, 31, 30, 30, 30, 29, 30, 30, 30});// 2092
        daysInMonthMap.put(2093, new int[]{31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30});// 2093
        daysInMonthMap.put(2094, new int[]{31, 31, 32, 31, 31, 30, 30, 30, 29, 30, 30, 30});// 2094
        daysInMonthMap.put(2095, new int[]{31, 31, 32, 31, 31, 31, 30, 29, 30, 30, 30, 30});// 2095
        daysInMonthMap.put(2096, new int[]{30, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30});// 2096
        daysInMonthMap.put(2097, new int[]{31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30});// 2097
        daysInMonthMap.put(2098, new int[]{31, 31, 32, 31, 31, 31, 29, 30, 29, 30, 30, 31});// 2098
        daysInMonthMap.put(2099, new int[]{31, 31, 32, 31, 31, 31, 30, 29, 29, 30, 30, 30});// 2099
        daysInMonthMap.put(2100, new int[]{31, 32, 31, 32, 30, 31, 30, 29, 30, 29, 30, 30});// 2100
        */
        return daysInMonthMap
    }

    private fun setStartWeekDayMonthMap(): SparseArray<IntArray> {
        val startWeekDayMonthMap: SparseArray<IntArray> = SparseArray()

        /*
         The 0s at index 0 are dummy values so as to make the int array of
         days in months seems more intuitive that index 1 refers to first
         month "Baisakh", index 2 refers to second month "Jesth" and so on.
         */

        // based on www.ashesh.com.np/neplai-date-converter
        startWeekDayMonthMap.put(1969, intArrayOf(0, 7, 2, 6, 2, 6, 2, 4, 6, 1, 2, 4, 5))
        startWeekDayMonthMap.put(1970, intArrayOf(0, 1, 4, 7, 4, 7, 3, 6, 1, 2, 4, 5, 7))
        startWeekDayMonthMap.put(1971, intArrayOf(0, 2, 5, 1, 5, 1, 5, 7, 2, 3, 5, 6, 1))
        startWeekDayMonthMap.put(1972, intArrayOf(0, 3, 6, 3, 6, 3, 6, 1, 3, 5, 6, 7, 2))
        startWeekDayMonthMap.put(1973, intArrayOf(0, 5, 7, 4, 7, 4, 7, 2, 4, 6, 7, 2, 3))
        startWeekDayMonthMap.put(1974, intArrayOf(0, 6, 1, 5, 2, 5, 1, 4, 6, 7, 2, 3, 5))
        startWeekDayMonthMap.put(1975, intArrayOf(0, 7, 3, 6, 3, 7, 3, 5, 7, 1, 3, 4, 6))
        startWeekDayMonthMap.put(1976, intArrayOf(0, 1, 4, 1, 4, 1, 4, 6, 1, 3, 4, 5, 7))
        startWeekDayMonthMap.put(1977, intArrayOf(0, 3, 5, 2, 5, 2, 5, 1, 2, 4, 5, 7, 1))
        startWeekDayMonthMap.put(1978, intArrayOf(0, 4, 7, 3, 7, 3, 6, 2, 4, 5, 7, 1, 3))
        startWeekDayMonthMap.put(1979, intArrayOf(0, 5, 1, 4, 1, 5, 1, 3, 5, 6, 1, 2, 4))
        startWeekDayMonthMap.put(1980, intArrayOf(0, 6, 2, 6, 2, 6, 2, 4, 6, 1, 2, 3, 5))
        startWeekDayMonthMap.put(1981, intArrayOf(0, 1, 4, 7, 3, 7, 3, 6, 7, 2, 4, 5, 7))
        startWeekDayMonthMap.put(1982, intArrayOf(0, 2, 5, 1, 5, 1, 4, 7, 2, 3, 5, 6, 1))
        startWeekDayMonthMap.put(1983, intArrayOf(0, 3, 6, 2, 6, 3, 6, 1, 3, 4, 6, 7, 2))
        startWeekDayMonthMap.put(1984, intArrayOf(0, 4, 7, 4, 7, 4, 7, 2, 4, 6, 7, 1, 3))
        startWeekDayMonthMap.put(1985, intArrayOf(0, 6, 2, 5, 1, 5, 1, 4, 5, 7, 2, 3, 5))
        startWeekDayMonthMap.put(1986, intArrayOf(0, 7, 3, 6, 3, 6, 2, 5, 7, 1, 3, 4, 6))
        startWeekDayMonthMap.put(1987, intArrayOf(0, 1, 4, 1, 4, 1, 4, 6, 1, 2, 4, 5, 7))
        startWeekDayMonthMap.put(1988, intArrayOf(0, 2, 5, 2, 5, 2, 5, 7, 2, 4, 5, 6, 1))
        startWeekDayMonthMap.put(1989, intArrayOf(0, 4, 7, 3, 6, 3, 6, 2, 4, 5, 7, 1, 3))
        startWeekDayMonthMap.put(1990, intArrayOf(0, 5, 1, 4, 1, 4, 7, 3, 5, 6, 1, 2, 4))
        startWeekDayMonthMap.put(1991, intArrayOf(0, 6, 2, 6, 2, 6, 2, 4, 6, 1, 2, 3, 5))
        startWeekDayMonthMap.put(1992, intArrayOf(0, 7, 3, 7, 3, 7, 3, 5, 7, 2, 3, 5, 6))
        startWeekDayMonthMap.put(1993, intArrayOf(0, 2, 5, 1, 4, 1, 4, 7, 2, 3, 5, 6, 1))
        startWeekDayMonthMap.put(1994, intArrayOf(0, 3, 6, 2, 6, 2, 5, 1, 3, 4, 6, 7, 2))
        startWeekDayMonthMap.put(1995, intArrayOf(0, 4, 7, 4, 7, 4, 7, 2, 4, 6, 7, 1, 3))
        startWeekDayMonthMap.put(1996, intArrayOf(0, 5, 1, 5, 1, 5, 1, 3, 5, 7, 1, 3, 4))
        startWeekDayMonthMap.put(1997, intArrayOf(0, 7, 3, 6, 3, 6, 2, 5, 7, 1, 3, 4, 6))
        startWeekDayMonthMap.put(1998, intArrayOf(0, 1, 4, 7, 4, 7, 3, 6, 1, 2, 4, 5, 7))
        startWeekDayMonthMap.put(1999, intArrayOf(0, 2, 5, 2, 5, 2, 5, 7, 2, 4, 5, 6, 1))

        /*old*/
        startWeekDayMonthMap.put(2000, intArrayOf(0, 4, 6, 3, 6, 3, 6, 1, 3, 5, 6, 1, 2))
        startWeekDayMonthMap.put(2001, intArrayOf(0, 5, 1, 4, 1, 4, 7, 3, 5, 6, 1, 2, 4))
        startWeekDayMonthMap.put(2002, intArrayOf(0, 6, 2, 5, 2, 6, 2, 4, 6, 7, 2, 3, 5))
        startWeekDayMonthMap.put(2003, intArrayOf(0, 7, 3, 7, 3, 7, 3, 5, 7, 2, 3, 4, 6))
        startWeekDayMonthMap.put(2004, intArrayOf(0, 2, 4, 1, 4, 1, 4, 6, 1, 3, 4, 6, 7))
        startWeekDayMonthMap.put(2005, intArrayOf(0, 3, 6, 2, 6, 2, 5, 1, 3, 4, 6, 7, 2))
        startWeekDayMonthMap.put(2006, intArrayOf(0, 4, 7, 3, 7, 4, 7, 2, 4, 5, 7, 1, 3))
        startWeekDayMonthMap.put(2007, intArrayOf(0, 5, 1, 5, 1, 5, 1, 3, 5, 7, 1, 2, 4))
        startWeekDayMonthMap.put(2008, intArrayOf(0, 7, 3, 6, 2, 6, 2, 5, 6, 1, 3, 4, 5))
        startWeekDayMonthMap.put(2009, intArrayOf(0, 1, 4, 7, 4, 7, 3, 6, 1, 2, 4, 5, 7))
        startWeekDayMonthMap.put(2010, intArrayOf(0, 2, 5, 1, 5, 2, 5, 7, 2, 3, 5, 6, 1))
        startWeekDayMonthMap.put(2011, intArrayOf(0, 3, 6, 3, 6, 3, 6, 1, 3, 5, 6, 7, 2))
        startWeekDayMonthMap.put(2012, intArrayOf(0, 5, 1, 4, 7, 4, 7, 3, 4, 6, 1, 2, 4))
        startWeekDayMonthMap.put(2013, intArrayOf(0, 6, 2, 5, 2, 5, 1, 4, 6, 7, 2, 3, 5))
        startWeekDayMonthMap.put(2014, intArrayOf(0, 7, 3, 6, 3, 7, 3, 5, 7, 1, 3, 4, 6))
        startWeekDayMonthMap.put(2015, intArrayOf(0, 1, 4, 1, 4, 1, 4, 6, 1, 3, 4, 5, 7))
        startWeekDayMonthMap.put(2016, intArrayOf(0, 3, 6, 2, 5, 2, 5, 1, 2, 4, 6, 7, 2))
        startWeekDayMonthMap.put(2017, intArrayOf(0, 4, 7, 3, 7, 3, 6, 2, 4, 5, 7, 1, 3))
        startWeekDayMonthMap.put(2018, intArrayOf(0, 5, 1, 5, 1, 5, 1, 3, 5, 6, 1, 2, 4))
        startWeekDayMonthMap.put(2019, intArrayOf(0, 6, 2, 6, 2, 6, 2, 4, 6, 1, 2, 4, 5))
        startWeekDayMonthMap.put(2020, intArrayOf(0, 1, 4, 7, 3, 7, 3, 6, 1, 2, 4, 5, 7))
        startWeekDayMonthMap.put(2021, intArrayOf(0, 2, 5, 1, 5, 1, 4, 7, 2, 3, 5, 6, 1))
        startWeekDayMonthMap.put(2022, intArrayOf(0, 3, 6, 3, 6, 3, 6, 1, 3, 5, 6, 7, 2))
        startWeekDayMonthMap.put(2023, intArrayOf(0, 4, 7, 4, 7, 4, 7, 2, 4, 6, 7, 2, 3))
        startWeekDayMonthMap.put(2024, intArrayOf(0, 6, 2, 5, 1, 5, 1, 4, 6, 7, 2, 3, 5))
        startWeekDayMonthMap.put(2025, intArrayOf(0, 7, 3, 6, 3, 6, 2, 5, 7, 1, 3, 4, 6))
        startWeekDayMonthMap.put(2026, intArrayOf(0, 1, 4, 1, 4, 1, 4, 6, 1, 3, 4, 5, 7))
        startWeekDayMonthMap.put(2027, intArrayOf(0, 3, 5, 2, 5, 2, 5, 7, 2, 4, 5, 7, 1))
        startWeekDayMonthMap.put(2028, intArrayOf(0, 4, 7, 3, 7, 3, 6, 2, 4, 5, 7, 1, 3))
        startWeekDayMonthMap.put(2029, intArrayOf(0, 5, 1, 4, 1, 4, 1, 3, 5, 6, 1, 2, 4))
        startWeekDayMonthMap.put(2030, intArrayOf(0, 6, 2, 6, 2, 6, 2, 4, 6, 1, 2, 3, 5))
        startWeekDayMonthMap.put(2031, intArrayOf(0, 1, 3, 7, 3, 7, 3, 5, 7, 2, 3, 5, 6))
        startWeekDayMonthMap.put(2032, intArrayOf(0, 2, 5, 1, 5, 1, 4, 7, 2, 3, 5, 6, 1))
        startWeekDayMonthMap.put(2033, intArrayOf(0, 3, 6, 2, 6, 3, 6, 1, 3, 4, 6, 7, 2))
        startWeekDayMonthMap.put(2034, intArrayOf(0, 4, 7, 4, 7, 4, 7, 2, 4, 6, 7, 1, 3))
        startWeekDayMonthMap.put(2035, intArrayOf(0, 6, 1, 5, 1, 5, 1, 4, 5, 7, 2, 3, 4))
        startWeekDayMonthMap.put(2036, intArrayOf(0, 7, 3, 6, 3, 6, 2, 5, 7, 1, 3, 4, 6))
        startWeekDayMonthMap.put(2037, intArrayOf(0, 1, 4, 7, 4, 1, 4, 6, 1, 2, 4, 5, 7))
        startWeekDayMonthMap.put(2038, intArrayOf(0, 2, 5, 2, 5, 2, 5, 7, 2, 4, 5, 6, 1))
        startWeekDayMonthMap.put(2039, intArrayOf(0, 4, 7, 3, 6, 3, 6, 2, 3, 5, 7, 1, 3))
        startWeekDayMonthMap.put(2040, intArrayOf(0, 5, 1, 4, 1, 4, 7, 3, 5, 6, 1, 2, 4))
        startWeekDayMonthMap.put(2041, intArrayOf(0, 6, 2, 5, 2, 6, 2, 4, 6, 7, 2, 3, 5))
        startWeekDayMonthMap.put(2042, intArrayOf(0, 7, 3, 7, 3, 7, 3, 5, 7, 2, 3, 4, 6))
        startWeekDayMonthMap.put(2043, intArrayOf(0, 2, 5, 1, 4, 1, 4, 7, 1, 3, 5, 6, 1))
        startWeekDayMonthMap.put(2044, intArrayOf(0, 3, 6, 2, 6, 2, 5, 1, 3, 4, 6, 7, 2))
        startWeekDayMonthMap.put(2045, intArrayOf(0, 4, 7, 4, 7, 4, 7, 2, 4, 5, 7, 1, 3))
        startWeekDayMonthMap.put(2046, intArrayOf(0, 5, 1, 5, 1, 5, 1, 3, 5, 7, 1, 2, 4))
        startWeekDayMonthMap.put(2047, intArrayOf(0, 7, 3, 6, 2, 6, 2, 5, 7, 1, 3, 4, 6))
        startWeekDayMonthMap.put(2048, intArrayOf(0, 1, 4, 7, 4, 7, 3, 6, 1, 2, 4, 5, 7))
        startWeekDayMonthMap.put(2049, intArrayOf(0, 2, 5, 2, 5, 2, 5, 7, 2, 4, 5, 6, 1))
        startWeekDayMonthMap.put(2050, intArrayOf(0, 3, 6, 3, 6, 3, 6, 1, 3, 5, 6, 1, 2))
        startWeekDayMonthMap.put(2051, intArrayOf(0, 5, 1, 4, 7, 4, 7, 3, 5, 6, 1, 2, 4))
        startWeekDayMonthMap.put(2052, intArrayOf(0, 6, 2, 5, 2, 5, 1, 4, 6, 7, 2, 3, 5))
        startWeekDayMonthMap.put(2053, intArrayOf(0, 7, 3, 7, 3, 7, 3, 5, 7, 2, 3, 4, 6))
        startWeekDayMonthMap.put(2054, intArrayOf(0, 1, 4, 1, 4, 1, 4, 6, 1, 3, 4, 6, 7))
        startWeekDayMonthMap.put(2055, intArrayOf(0, 3, 6, 2, 6, 2, 5, 1, 3, 4, 6, 7, 2))
        startWeekDayMonthMap.put(2056, intArrayOf(0, 4, 7, 3, 7, 3, 7, 2, 4, 5, 7, 1, 3))
        startWeekDayMonthMap.put(2057, intArrayOf(0, 5, 1, 5, 1, 5, 1, 3, 5, 7, 1, 2, 4))
        startWeekDayMonthMap.put(2058, intArrayOf(0, 7, 2, 6, 2, 6, 2, 4, 6, 1, 2, 4, 5))
        startWeekDayMonthMap.put(2059, intArrayOf(0, 1, 4, 7, 4, 7, 3, 6, 1, 2, 4, 5, 7))
        startWeekDayMonthMap.put(2060, intArrayOf(0, 2, 5, 1, 5, 2, 5, 7, 2, 3, 5, 6, 1))
        startWeekDayMonthMap.put(2061, intArrayOf(0, 3, 6, 3, 6, 3, 6, 1, 3, 5, 6, 7, 2))
        startWeekDayMonthMap.put(2062, intArrayOf(0, 5, 7, 4, 7, 4, 7, 3, 4, 6, 7, 2, 3))
        startWeekDayMonthMap.put(2063, intArrayOf(0, 6, 2, 5, 2, 5, 1, 4, 6, 7, 2, 3, 5))
        startWeekDayMonthMap.put(2064, intArrayOf(0, 7, 3, 6, 3, 7, 3, 5, 7, 1, 3, 4, 6))
        startWeekDayMonthMap.put(2065, intArrayOf(0, 1, 4, 1, 4, 1, 4, 6, 1, 3, 4, 5, 7))
        startWeekDayMonthMap.put(2066, intArrayOf(0, 3, 6, 2, 5, 2, 5, 1, 2, 4, 6, 7, 1))
        startWeekDayMonthMap.put(2067, intArrayOf(0, 4, 7, 3, 7, 3, 6, 2, 4, 5, 7, 1, 3))
        startWeekDayMonthMap.put(2068, intArrayOf(0, 5, 1, 4, 1, 5, 1, 3, 5, 6, 1, 2, 4))
        startWeekDayMonthMap.put(2069, intArrayOf(0, 6, 2, 6, 2, 6, 2, 4, 6, 1, 2, 3, 5))
        startWeekDayMonthMap.put(2070, intArrayOf(0, 1, 4, 7, 3, 7, 3, 6, 7, 2, 4, 5, 7))
        startWeekDayMonthMap.put(2071, intArrayOf(0, 2, 5, 1, 5, 1, 4, 7, 2, 3, 5, 6, 1))
        startWeekDayMonthMap.put(2072, intArrayOf(0, 3, 6, 3, 6, 3, 6, 1, 3, 4, 6, 7, 2))
        startWeekDayMonthMap.put(2073, intArrayOf(0, 4, 7, 4, 7, 4, 7, 2, 4, 6, 7, 1, 3))
        startWeekDayMonthMap.put(2074, intArrayOf(0, 6, 2, 5, 1, 5, 1, 4, 6, 7, 2, 3, 5))
        startWeekDayMonthMap.put(2075, intArrayOf(0, 7, 3, 6, 3, 6, 2, 5, 7, 1, 3, 4, 6))
        startWeekDayMonthMap.put(2076, intArrayOf(0, 1, 4, 1, 4, 1, 4, 6, 1, 3, 4, 5, 7))
        startWeekDayMonthMap.put(2077, intArrayOf(0, 2, 5, 2, 5, 2, 5, 7, 2, 4, 5, 7, 1))
        startWeekDayMonthMap.put(2078, intArrayOf(0, 4, 7, 3, 6, 3, 6, 2, 4, 5, 7, 1, 3))
        startWeekDayMonthMap.put(2079, intArrayOf(0, 5, 1, 4, 1, 4, 7, 3, 5, 6, 1, 2, 4))
        startWeekDayMonthMap.put(2080, intArrayOf(0, 6, 2, 6, 2, 6, 2, 4, 6, 1, 2, 3, 5))
        startWeekDayMonthMap.put(2081, intArrayOf(0, 7, 3, 6, 3, 7, 3, 5, 7, 2, 3, 5, 7))
        startWeekDayMonthMap.put(2082, intArrayOf(0, 2, 4, 1, 4, 1, 4, 6, 1, 3, 4, 6, 1))
        startWeekDayMonthMap.put(2083, intArrayOf(0, 3, 6, 2, 6, 2, 5, 7, 2, 4, 5, 7, 2))
        startWeekDayMonthMap.put(2084, intArrayOf(0, 4, 7, 3, 7, 3, 6, 1, 3, 5, 6, 1, 3))
        startWeekDayMonthMap.put(2085, intArrayOf(0, 5, 1, 5, 1, 5, 7, 3, 5, 7, 1, 3, 5))
        startWeekDayMonthMap.put(2086, intArrayOf(0, 7, 2, 6, 2, 6, 2, 4, 6, 1, 2, 4, 6))
        startWeekDayMonthMap.put(2087, intArrayOf(0, 1, 4, 7, 4, 7, 3, 6, 1, 3, 4, 6, 1))
        startWeekDayMonthMap.put(2088, intArrayOf(0, 3, 5, 1, 5, 2, 4, 7, 2, 4, 5, 7, 2))
        startWeekDayMonthMap.put(2089, intArrayOf(0, 4, 6, 3, 6, 3, 6, 1, 3, 5, 6, 1, 3))
        startWeekDayMonthMap.put(2090, intArrayOf(0, 5, 7, 4, 7, 4, 7, 2, 4, 6, 7, 2, 4))
        /*  */ /*start of new but dummy data just for test todo fix with real data*/ /*
        startWeekDayMonthMap.put(2091, new int[]{0, 5, 1, 4, 1, 4, 7, 3, 5, 6, 1, 2, 4});
        startWeekDayMonthMap.put(2092, new int[]{0, 6, 2, 5, 2, 6, 2, 4, 6, 7, 2, 3, 5});
        startWeekDayMonthMap.put(2093, new int[]{0, 7, 3, 7, 3, 7, 3, 5, 7, 2, 3, 4, 6});
        startWeekDayMonthMap.put(2094, new int[]{0, 2, 4, 1, 4, 1, 4, 6, 1, 3, 4, 6, 7});
        startWeekDayMonthMap.put(2095, new int[]{0, 3, 6, 2, 6, 2, 5, 1, 3, 4, 6, 7, 2});
        startWeekDayMonthMap.put(2096, new int[]{0, 4, 7, 3, 7, 4, 7, 2, 4, 5, 7, 1, 3});
        startWeekDayMonthMap.put(2097, new int[]{0, 5, 1, 5, 1, 5, 1, 3, 5, 7, 1, 2, 4});
        startWeekDayMonthMap.put(2098, new int[]{0, 7, 3, 6, 2, 6, 2, 5, 6, 1, 3, 4, 5});
        startWeekDayMonthMap.put(2099, new int[]{0, 1, 4, 7, 4, 7, 3, 6, 1, 2, 4, 5, 7});
        startWeekDayMonthMap.put(2100, new int[]{0, 4, 6, 3, 6, 3, 6, 1, 3, 5, 6, 1, 2});
        */
        return startWeekDayMonthMap
    }

    fun getSaturdays(@IntRange(from = 1970, to = 2090) year: Int): ArrayList<Model> {
        val saturdays = ArrayList<Model>()
        for (month in 1..12) {
            saturdays.addAll(getSaturdays(year, month))
        }
        return saturdays
    }

    fun getSaturdays(@IntRange(from = 1970, to = 2090) year: Int, @IntRange(from = 1, to = 12) month: Int): ArrayList<Model> {
        val saturdays = ArrayList<Model>()
        val startDayOfMonth = startWeekDayMonthMap[year][month]
        for (i in startDayOfMonth until (daysInMonthMap.get(year)[month] + startDayOfMonth)) {
            if (i % 7 == 0) {
                saturdays.add(Model(year, month - 1, i - startDayOfMonth + 1))
            }
        }
        return saturdays
    }


    fun getAllSaturdays(): ArrayList<Model> {
        val saturdays = ArrayList<Model>()
        for (year in 1970..2090) {
            saturdays.addAll(getSaturdays(year))
        }
        return saturdays
    }

}