package com.interview.model.dto;

import com.interview.model.Candidate;
import com.interview.model.Group;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.testng.Assert.*;

public class GroupDayDTOTest {

    @Test
    public void givenWhenCreateGroupDayDtoWithTwoArgConstructorThenGroupDayCreates() {
        List<Candidate> candidates = new ArrayList<>();
        Candidate candidate1 = new Candidate();
        Candidate candidate2 = new Candidate();
        candidate1.setName("Test1");
        candidate2.setName("Test2");
        candidates.add(candidate1);
        candidates.add(candidate2);
        Calendar calendar = new GregorianCalendar(2000, 1, 1);

        GroupDayDTO groupDayDTO = new GroupDayDTO(candidates, calendar);

        assertNotNull(groupDayDTO);
        assertEquals(groupDayDTO.getDate(), calendar);
        assertEquals(groupDayDTO.getCandidates(), candidates);
    }

    @Test
    public void givenWhenCompareToCallThenResultsMayVaryDependsOnOtherDtoFields() {
        GroupDayDTO groupDayDTO = new GroupDayDTO(new ArrayList<>(), new GregorianCalendar(2000, 1, 2));
        GroupDayDTO groupDayDTOLess = new GroupDayDTO(new ArrayList<>(), new GregorianCalendar(2000, 1, 1));
        GroupDayDTO groupDayDTOGreater = new GroupDayDTO(new ArrayList<>(), new GregorianCalendar(2000, 1, 3));

        assertTrue(groupDayDTO.compareTo(groupDayDTOLess) > 0);
        assertTrue(groupDayDTO.compareTo(groupDayDTOGreater) < 0);
    }
}