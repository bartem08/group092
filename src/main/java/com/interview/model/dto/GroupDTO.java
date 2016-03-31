package com.interview.model.dto;

import com.interview.model.Group;

import java.io.Serializable;

/**
 * DTO for Group entity
 * Serves for transporting groups without group inner structure(candidates) disclosure
 * also allows sort groups by name
 *
 * @author Yegor Gulimov
 */
public class GroupDTO implements Serializable, Comparable<GroupDTO> {

    private String id;

    private String name;

    public GroupDTO(Group group) {
        id = group.getId();
        name = group.getName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(GroupDTO o) {
        return name.toLowerCase().compareTo(o.name.toLowerCase());
    }
}
