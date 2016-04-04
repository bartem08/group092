package com.interview.model.dto;

import com.interview.model.Candidate;

import java.util.Calendar;

/**
 * DTO for convenient usage candidates in group view on UI
 *
 * @author Yegor Gulimov
 */
public class CandidateDTO implements Comparable<CandidateDTO> {
    private String id;
    private String fullName;
    private Calendar date;
    private String interviewId;

    public CandidateDTO(Candidate candidate, String interviewId) {
        id = candidate.getId();
        fullName = candidate.getLastName() + " " + candidate.getFirstName();
        date = candidate.getDate();
        this.interviewId = interviewId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(String interviewId) {
        this.interviewId = interviewId;
    }

    @Override
    public int compareTo(CandidateDTO o) {
        return date.before(o.date) ? -1 : 1;
    }
}
