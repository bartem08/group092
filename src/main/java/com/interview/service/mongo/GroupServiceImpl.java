package com.interview.service.mongo;

import com.interview.model.Candidate;
import com.interview.model.Group;
import com.interview.repository.GroupRepository;
import com.interview.service.GroupService;
import com.interview.util.GroupTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Contains basic CRUD and improved operations on Group entity
 *      and provides Group relations with database
 *
 * @author Yegor Gulimov
 */

@Service
public class GroupServiceImpl implements GroupService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GroupRepository groupRepository;

    /**
     * adds group to database
     * @param group to be added in database
     *              group have to match following conditions:
     *              - group not null
     *              - group name not null
     *              - group name not empty
     *              - group name is unique
     * @return group saved in database on success, null on failed conditions
     */

    @Override
    public Group createGroup(Group group) {

        if (!isValidGroup(group)) {
            return null;
        }

        if (groupRepository.findGroupByName(group.getName()) != null) {
            log.error("Group name must be unique. Name '{}' already exists in database", group.getName());
            return null;
        }

        group = groupRepository.save(group);
        log.info("Group '{}' with id '{}' has been added to database", group.getName(), group.getId());
        return group;
    }

    /**
     * reads group from database by id
     * @param id group id to be read from database
     * @return group from database with specified id on success,
     *      null if group with given id not found
     */

    @Override
    public Group readGroup(String id) {

        Group group = groupRepository.findOne(id);

        if (group == null) {
            log.error("Cannot read group. Group with id '{}' doesn't exists in database", id);
            return null;
        }

        log.info("Group '{}' with id '{}' has been read from database", group.getName(), group.getId());

        return group;
    }

    /**
     * reads group from database by unique name
     * @param name of group to be read from database
     * @return group from database with specified name on success,
     *      null if group with given name not found
     */

    @Override
    public Group readGroupByName(String name) {

        Group group = groupRepository.findGroupByName(name);

        if (group == null) {
            log.error("Cannot read group. Group with name '{}' doesn't exists in database", name);
            return null;
        }

        log.info("Group '{}' with id '{}' has been read from database",
                group.getName(), group.getId());

        return group;
    }

    /**
     * reads all groups from database
     * @return list of groups
     */

    @Override
    public List<Group> readAllGroups() {

        List<Group> groups = groupRepository.findAll();

        if (groups.isEmpty()) {
            log.info("There are no groups in database");
        } else {
            log.info("Groups were read from database. Groups count: {}", groups.size());
        }

        return groups;
    }

    /**
     * updates group with given id in database using fields of updated group except id
     * @param id of group to be updated
     * @param updatedGroup contains fields to be updated in existed group
     * @return updated group from database on success, null on validation fail
     */

    @Override
    public Group updateGroup(String id, Group updatedGroup) {

        if (!isValidGroup(updatedGroup)) {
            return null;
        }

        Group group = groupRepository.findOne(id);

        if (group == null) {
            log.error("Cannot read group. Group with id '{}' doesn't exists in database", id);
            return null;
        }

        if (!group.getName().equals(updatedGroup.getName())) {
            if (groupRepository.findGroupByName(updatedGroup.getName()) != null) {
                log.error("Cannot update group name. Group with name '{}' already exists in database",
                        updatedGroup.getName());
                return null;
            }
        }

        //copying all updated group fields in existed group except id and save it in database
        String groupId = group.getId();
        group = updatedGroup;
        group.setId(groupId);   //set old group id

        log.info("Group with id '{}' has been updated in database", groupId);

        return groupRepository.save(group);
    }

    /**
     * removes group from database according to specified id
     * @param id of group to be removed
     * @return true if group with given id found in database, otherwise - false
     */

    @Override
    public boolean deleteGroup(String id) {

        Group group = groupRepository.findOne(id);

        if (group == null) {
            log.error("Cannot remove group. Group with id '{}' doesn't exists in database", id);
            return false;
        }

        groupRepository.delete(id);
        log.error("Group '{}' with id '{}' has been removed from database", group.getName(), id);

        return true;
    }

    /**
     * gets list of candidates with given date of group with specified id
     * @param id of group in which search will be performed
     * @param date only candidates with this date will be included in resulted list
     * @return list of candidates from specified group with specified date
     */

    @Override
    public List<Candidate> getGroupCandidatesByDate(String id, Calendar date) {

        Group group = groupRepository.findOne(id);

        if (group == null) {
            log.error("Cannot read group. Group with id '{}' doesn't exists in database", id);
            return null;
        }

        //add candidates with corresponding date to result list
        List<Candidate> currentDateCandidates = new ArrayList<>();
        for (Candidate candidate : group.getCandidates()) {
            if (GroupTool.isEqualDates(candidate.getDate(), date)) {
                currentDateCandidates.add(candidate);
            }
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        if (currentDateCandidates.isEmpty()) {
            log.info("There are no candidates in group '{}' with date '{}",
                    group.getName(), simpleDateFormat.format(date.getTime()));
        } else {
            log.info("Candidates with date '{}' were read from group '{}' with id '{}'. Candidates count: {}",
                    simpleDateFormat.format(date), group.getName(), group.getId(), currentDateCandidates.size());
        }

        return currentDateCandidates;
    }

    /**
     * scans all candidates in group and finds distinct list of candidate dates
     * @param id of group to be scanned
     * @return list of distinct dates
     */

    @Override
    public List<Calendar> getGroupDates(String id) {

        Group group = groupRepository.findOne(id);

        if (group == null) {
            log.error("Cannot read group. Group with id '{}' doesn't exists in database", id);
            return null;
        }

        Set<Calendar> groupDatesSet = new HashSet<>();

        List<Candidate> candidates = group.getCandidates();
        for (Candidate candidate : candidates) {
            groupDatesSet.add(GroupTool.cutDate(candidate.getDate()));
        }

        List<Calendar> groupDatesList = new ArrayList<>();;
        if (groupDatesSet.isEmpty()) {
            log.info("There are no dates found for group '{}' with id '{}'", group.getName(), group.getId());
        } else {
            log.info("Dates for group '{}' with id '{}' has been read from database. Dates count: {}",
                    group.getName(), group.getId(), groupDatesSet.size());
            for (Calendar calendar : groupDatesSet) {
                groupDatesList.add(calendar);
            }
        }

        return groupDatesList;
    }

    /**
     * checks that group param is responds for following conditions:
     *     - group not null
     *     - group name not null
     *     - group name not empty
     * @param group to be checked
     * @return true if group is valid, otherwise - false
     */

    @SuppressWarnings("all")
    private boolean isValidGroup(Group group) {

        boolean validGroup = true;

        if (group == null) {
            log.error("Group must not be null");
            validGroup = false;
        }

        if (group.getName() == null) {
            log.error("Group name must not be null");
            validGroup = false;
        }

        if (group.getName().equals("")) {
            log.error("Group name must not be empty");
            validGroup = false;
        }

        return validGroup;
    }
}
