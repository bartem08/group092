package com.interview.model;

/**
 * @author Anton Kruglikov.
 */
public class InterviewQuestion extends Question {

    private double finalQuestionValue;

    private boolean skipped;

    public InterviewQuestion() {}

    public InterviewQuestion(Question question, double finalQuestionValue, boolean skipped) {
        super(question.getQuestionString(), question.getMaxQuestionValue());
        setId(question.getId());
        setFinalQuestionValue(finalQuestionValue);
        setSkipped(skipped);
    }

    public void setFinalQuestionValue(double value) {
        this.finalQuestionValue = value;
    }

    public double getFinalQuestionValue() {
        return finalQuestionValue;
    }

    public void setSkipped(boolean skipped) {
        this.skipped = skipped;
    }

    public boolean isSkipped() {
        return skipped;
    }

    @Override
    public String toString() {
        return "[" + getQuestionString()
                + ", " + getMaxQuestionValue()
                + ", " + getFinalQuestionValue()
                + ", " + isSkipped()
                + "]";
    }

}
