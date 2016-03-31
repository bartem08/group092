package com.interview.model.dto;

import com.interview.model.Group;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Unit test for GroupDTO
 *
 * @author Yegor Gulimov
 */

public class GroupDTOTest {

    @Test
    public void givenWhenGroupDtoCreateWithOneArgConstructorThenGroupDtoCreated() {
        Group group = new Group("Test");
        group.setId("testId");
        GroupDTO groupDTO = new GroupDTO(group);

        assertNotNull(groupDTO);
        assertEquals(groupDTO.getId(), group.getId());
        assertEquals(groupDTO.getName(), group.getName());
    }

    @Test
    public void givenWhenGroupDtoCompareToCallThenResultIsDifferentDependingOnOtherGroupDto() {
        GroupDTO groupDto = new GroupDTO(new Group("Test"));
        GroupDTO groupDtoLess = new GroupDTO(new Group("A_Test"));
        GroupDTO groupDtoGreater = new GroupDTO(new Group("Z_Test"));

        assertEquals(groupDto.compareTo(groupDto), 0);
        assertTrue(groupDto.compareTo(groupDtoLess) > 0);
        assertTrue(groupDto.compareTo(groupDtoGreater) < 0);
    }
}