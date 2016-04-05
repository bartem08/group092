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

    private byte maxQuestionValue;

    public Question() {}

    @PersistenceConstructor
    public Question(String questionString, byte maxQuestionValue) {
        setQuestionString(questionString);
        setMaxQuestionValue(maxQuestionValue);
    }

    public void setQuestionString(String questionString) {
        this.questionString = questionString;
    }

    public String getQuestionString() {
        return questionString;
    }

    public void setMaxQuestionValue(byte maxQuestionValue) {
        this.maxQuestionValue = maxQuestionValue;
    }

    public byte getMaxQuestionValue() {
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

        if (questionString != null ? !questionString.equals(question.questionString) : question.questionString != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return questionString != null ? questionString.hashCode() : 0;
    }

}
