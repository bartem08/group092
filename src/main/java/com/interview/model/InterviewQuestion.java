package com.interview.model;

/**
 * @author Anton Kruglikov.
 */
public class InterviewQuestion {

    private Question question;

    private float finalQuestionValue;

    private boolean skipped;

    public InterviewQuestion() {}

    public InterviewQuestion(Question question, float finalQuestionValue, boolean skipped) {
        setQuestion(question);
        setFinalQuestionValue(finalQuestionValue);
        setSkipped(skipped);
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setFinalQuestionValue(float value) {
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
        return question == null ? "empty interview question"
                : String.format("Question %s, max: %d, final: %.2f, skipped %b",
                question.getQuestionString(), question.getMaxQuestionValue(), getFinalQuestionValue(), skipped);
    }

}
