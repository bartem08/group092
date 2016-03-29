package com.interview.model;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * @author Anton Kruglikov.
 */
@Document(collection="templates")
@TypeAlias("Template")
public class Template extends AbstractDocument implements Serializable {

    private String name;

    private boolean favourite;

    @DBRef
    private Interviewer interviewer;

    @DBRef
    private List<Question> questions;

    public Template() {}

    @PersistenceConstructor
    public Template(String name, List<Question> questions, boolean favourite, Interviewer interviewer) {
        setName(name);
        setQuestions(questions);
        setFavourite(favourite);
        setInterviewer(interviewer);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public Interviewer getInterviewer() {
        return interviewer;
    }

    public void setInterviewer(Interviewer interviewer) {
        this.interviewer = interviewer;
    }

    @Override
    public String toString() {
        return "[" + getName()
                + ", " + getQuestions()
                + ", " + isFavourite()
                + ", " + getInterviewer()
                + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Template template = (Template) o;

        if (id != null ? !id.equals(template.id) : template.id != null) return false;
        if (name != null ? !name.equals(template.name) : template.name != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

}
