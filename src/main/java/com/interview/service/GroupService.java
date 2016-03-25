package com.interview.service;

import com.interview.model.Candidate;
import com.interview.model.Group;

import java.util.Calendar;
import java.util.List;

/**
 * @author Yegor Gulimov
 */
public interface GroupService {

    Group createGroup(Group group);

    Group readGroup(String id);

    Group readGroupByName(String name);

    List<Group> readAllGroups();

    Group updateGroup(String id, Group updatedGroup);

    boolean deleteGroup(String id);

    List<Candidate> getGroupCandidatesByDate(String id, Calendar date);

    List<Calendar> getGroupDates(String id);

}
