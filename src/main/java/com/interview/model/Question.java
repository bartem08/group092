package com.interview.model;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author Anton Kruglikov.
 */
@Document(collection="questions")
@TypeAlias("Question")
public class Question extends AbstractDocument implements Serializable {

    private String questionString;

    private double maxQuestionValue;

    public Question() {}

    @PersistenceConstructor
    public Question(String questionString, double maxQuestionValue) {
        setQuestionString(questionString);
        setMaxQuestionValue(maxQuestionValue);
    }

    public void setQuestionString(String questionString) {
        this.questionString = questionString;
    }

    public String getQuestionString() {
        return questionString;
    }

    public void setMaxQuestionValue(double maxQuestionValue) {
        this.maxQuestionValue = maxQuestionValue;
    }

    public double getMaxQuestionValue() {
        return maxQuestionValue;
    }

    @Override
    public String toString() {
        return "[" + getQuestionString()
                + ", " + getMaxQuestionValue()
                + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (id != null ? !id.equals(question.id) : question.id != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
