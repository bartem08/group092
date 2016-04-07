package com.interview.model;

import com.interview.util.ResultFormer;
import com.interview.validation.annotation.Existed;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Artem Baranovskiy
 */
@Document(collection = "interviews")
@TypeAlias("Interview")
public class Interview extends AbstractDocument implements Serializable {

    @DBRef @Existed(collection = "interviewers", empty = false)
    private Interviewer interviewer;

    @DBRef @Existed(collection = "candidates", empty = false)
    private Candidate candidate;

    private List<InterviewQuestion> questions;

    private float result;

    private String comments;

    public Interview() {}

    public Interview(final Interviewer interviewer, final Candidate candidate) {
        setInterviewer(interviewer);
        setCandidate(candidate);
    }

    @PersistenceConstructor
    public Interview(final List<InterviewQuestion> questions, final Interviewer interviewer,
                     final Candidate candidate, final String comments) {
        this(interviewer, candidate);
        setQuestions(questions);
        setComments(comments);
    }

    public List<InterviewQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(final List<InterviewQuestion> questions) {
        this.questions = questions;
        result = ResultFormer.form(this.questions);
    }

    public Interviewer getInterviewer() {
        return interviewer;
    }

    public void setInterviewer(final Interviewer interviewer) {
        this.interviewer = interviewer;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(final Candidate candidate) {
        this.candidate = candidate;
    }

    public float getResult() {
        return result;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Interview interview = (Interview) o;

        return id != null ? id.equals(interview.id) : interview.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return String.format("Interview {interviewer=%s, candidate=%s, result=%.2f}", interviewer, candidate, result);
    }

}
