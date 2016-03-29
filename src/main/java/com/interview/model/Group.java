package com.interview.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yegor Gulimov
 */
@Document(collection = "groups")
@TypeAlias("Group")
public class Group extends AbstractDocument implements Serializable {

    private String name;

    private List<Candidate> candidates = new ArrayList<>();

    public Group() { }

    public Group(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    @Override
    public String toString() {
        return name;
    }

}
