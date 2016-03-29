package com.interview.model;

import com.interview.validation.annotation.Existed;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Artem Baranovskiy
 */
@Document(collection = "interviews")
@TypeAlias("Interview")
public class Interview extends AbstractDocument implements Serializable {

    @DBRef @Existed(collection = "interviewers")
    private Interviewer interviewer;

    private Set<InterviewQuestion> questions;

    private double maxValue;

    private double finalValue;

    private List<String> comments;

    public Interview() {}

    public Interview(final Interviewer interviewer) {
        setInterviewer(interviewer);
    }

    @PersistenceConstructor
    public Interview(Set<InterviewQuestion> questions, Interviewer interviewer,
                     Double maxValue, Double finalValue, List<String> comments) {
        this(interviewer);
        setQuestions(questions);
        setMaxValue(maxValue);
        setFinalValue(finalValue);
        setComments(comments);
    }

    public Set<InterviewQuestion> getQuestions() {
        return questions;
    }

    public void addQuestion(final InterviewQuestion question) {
        if (questions == null) {
            questions = new HashSet<>();
        }
        if (!question.isSkipped()) {
            finalValue += question.getFinalQuestionValue();
            maxValue += question.getMaxQuestionValue();
        }
    }

    public void setQuestions(Set<InterviewQuestion> questions) {
        this.questions = questions;
    }

    public Interviewer getInterviewer() {
        return interviewer;
    }

    public void setInterviewer(final Interviewer interviewer) {
        this.interviewer = interviewer;
    }

    public double getFinalValue() {
        return finalValue;
    }

    private void setFinalValue(Double finalValue) {
        if (finalValue != null)
            this.finalValue = finalValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    private void setMaxValue(Double maxValue) {
        if (maxValue != null) {
            this.maxValue = maxValue;
        }
    }

    public List<String> getComments() {
        return comments;
    }

    private void setComments(List<String> comments) {
        this.comments = comments;
    }

    public void addComment(final String comment) {
        if (comments == null) {
            comments = new ArrayList<>();
        }
        comments.add(comment);
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
        return String.format("Interview{comments=%s, interviewer=%s, finalValue=%s}", comments, interviewer, finalValue);
    }

}
