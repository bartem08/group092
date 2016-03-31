package com.interview.util;

import com.interview.model.InterviewQuestion;

import java.util.Set;

public class ResultFormer {

    public static double form(final Set<InterviewQuestion> questions) {
        if (questions == null) {
            return 0;
        }
        double max = 0, actual = 0;
        for (InterviewQuestion question: questions) {
            if (!question.isSkipped()) {
                max += question.getMaxQuestionValue();
                actual += question.getFinalQuestionValue();
            }
        }
        return (actual != 0) && (max != 0) ? (actual/max) * 100 : 0;
    }

}
