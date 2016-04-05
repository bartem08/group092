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
    private List<CandidateDTO> candidates = new ArrayList<>();

    public GroupDayDTO(List<CandidateDTO> candidates, Calendar date) {
        this.candidates = candidates;
        this.date = date;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public List<CandidateDTO> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<CandidateDTO> candidates) {
        this.candidates = candidates;
    }

    @Override
    public int compareTo(GroupDayDTO o) {
        return date.before(o.date) ? -1 : 1;
    }
}
