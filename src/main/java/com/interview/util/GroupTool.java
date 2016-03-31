package com.interview.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * contains helpful methods for working with Group entity
 *
 * @author Yegor Gulimov
 */
public class GroupTool {

    /**
     * Compares two calendars looking only for date not time. Calendars with same year, month and date,
     *      but different time is equal
     * @param date1 first calendar to be compared
     * @param date2 second calendar to be compared
     * @return true if both calendars is the same date despite time, false if dates are different
     */

    public static boolean isEqualDates(Calendar date1, Calendar date2) {
        return cutDate(date1).getTime().equals(cutDate(date2).getTime());
    }

    /**
     * resets calendars time related fields e.g. hours, minutes, seconds
     * @param date incoming calendar with any set of fields
     * @return new calendar with only year, month and date fields set
     */

    public static Calendar cutDate(Calendar date) {
        return new GregorianCalendar(date.get(Calendar.YEAR),
                date.get(Calendar.MONTH), date.get(Calendar.DATE));
    }
}
