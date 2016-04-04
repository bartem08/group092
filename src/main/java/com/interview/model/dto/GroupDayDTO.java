package com.interview.model.dto;

import com.interview.model.Candidate;
import com.interview.model.Group;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * DTO for holding necessary info for convenient processing group view
 *
 * @author Yegor Gulimov
 */
public class GroupDayDTO implements Comparable<GroupDayDTO> {
    private Calendar date;
    private List<Candidate> candidates = new ArrayList<>();

    public GroupDayDTO(List<Candidate> candidates, Calendar date) {
        this.candidates = candidates;
        this.date = date;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    @Override
    public int compareTo(GroupDayDTO o) {
        return date.before(o.date) ? -1 : 1;
    }
}
