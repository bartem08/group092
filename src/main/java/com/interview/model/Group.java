package com.interview.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yegor Gulimov
 *
 * Group is an entity for containing candidates which are of particular group
 * Group is POJO. Relation with entity Candidate realized by List<Candidate> candidates field
 * Filed candidates is wired with candidates collection in database so if candidate which are
 *      in list will be deleted from database it'll be deleted from list too
 *
 * Group is descendant of AbstractDocument and ihnerited String id field(serves for identification
 *      of Group in database) and corresponding getter and setter
 *
 */

@Document(collection = "groups")
@TypeAlias("Group")
public class Group extends AbstractDocument implements Serializable {

    private String name;

    @DBRef
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
