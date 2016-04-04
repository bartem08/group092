package com.interview.util;

import com.interview.model.InterviewQuestion;
import com.interview.model.Question;

import java.util.*;

/**
 * @author Artem Baranovskiy
 */
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

    public static Set<InterviewQuestion> formQuestionSet(List<Question> questions,
                                                         String[] values, String[] skipped) {
        final List<String> toSkip = formSkippedList(skipped);
        final Set<InterviewQuestion> interviewQuestions = new HashSet<>();
        for (byte i = 0; i < values.length; i++)  {
            interviewQuestions.add(interviewQuestion(toSkip, questions.get(i), values[i]));
        }
        return interviewQuestions;
    }

    private static List<String> formSkippedList(String[] skipped) {
        return skipped == null ? Collections.EMPTY_LIST : Arrays.asList(skipped);
    }

    private static InterviewQuestion interviewQuestion(List<String> toSkip, Question question, String value) {
        return toSkip.contains(question.getId()) ? new InterviewQuestion(question, Double.parseDouble(value), true)
                : new InterviewQuestion(question, Double.parseDouble(value), false);
    }

}
