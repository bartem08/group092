package com.interview.util;

import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.testng.Assert.*;

/**
 * Unit test for GroupTool class
 *
 * @author Yegor Gulimov
 */

public class GroupToolTest {

    @Test
    public void givenWhenIsEqualDateCallWithSameCalendarsThenReturnTrue() {
        Calendar calendar1 = new GregorianCalendar(2000, 1, 1, 12, 15);
        Calendar calendar2 = new GregorianCalendar(2000, 1, 1, 10, 30);

        assertTrue(GroupTool.isEqualDates(calendar1, calendar2));
    }

    @Test
    public void givenWhenIsEqualDateCallWithDifferentCalendarsThenReturnFalse() {
        Calendar calendar1 = new GregorianCalendar(2000, 1, 1, 12, 15);
        Calendar calendar2 = new GregorianCalendar(2000, 1, 2, 10, 30);

        assertFalse(GroupTool.isEqualDates(calendar1, calendar2));
    }

    @Test
    public void givenWhenCutDateCallThenReturnedDateHoursAndMinutesAreZero() {
        Calendar calendar1 = new GregorianCalendar(2000, 1, 1, 12, 15);
        Calendar cutCalendar = new GregorianCalendar(2000, 1, 1, 0, 0);

        assertEquals(GroupTool.cutDate(calendar1), cutCalendar);
    }

}