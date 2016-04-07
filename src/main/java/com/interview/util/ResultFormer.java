package com.interview.util;

import com.interview.model.Interview;
import com.interview.model.InterviewQuestion;
import com.interview.model.Question;

import java.util.*;

/**
 * @author Artem Baranovskiy
 */
public class ResultFormer {

    public static float form(final List<InterviewQuestion> questions) {
        if (questions == null) {
            return 0;
        }
        short max = 0;
        float actual = 0;
        for (InterviewQuestion question: questions) {
            if (!question.isSkipped()) {
                max += question.getQuestion().getMaxQuestionValue();
                actual += question.getFinalQuestionValue();
            }
        }
        return (actual != 0) && (max != 0) ? (actual/max) * 100 : 0;
    }

    public static List<InterviewQuestion> formQuestionList(List<InterviewQuestion> questions,
                                                           List<String> values, String[] skipped) {
        final List<String> toSkip = formSkippedList(skipped);
        for (byte i = 0; i < questions.size(); i++)  {
            questions.set(i, interviewQuestion(toSkip, questions.get(i).getQuestion(), values.get(i)));
        }
        return questions;
    }

    public static List<InterviewQuestion> formQuestionList(List<Question> questions) {
        final List<InterviewQuestion> interviewQuestions = new ArrayList<>();
        for (Question question: questions) {
            interviewQuestions.add(new InterviewQuestion(question, 0, false));
        }
        return interviewQuestions;
    }

    public static List<String> formSkippedList(String[] skipped) {
        return skipped == null ? Collections.EMPTY_LIST : Arrays.asList(skipped);
    }

    public static InterviewQuestion interviewQuestion(List<String> toSkip, Question question, String value) {
        return toSkip.contains(question.getId())
                ? new InterviewQuestion(question, Float.parseFloat(value), true)
                : new InterviewQuestion(question, Float.parseFloat(value), false);
    }

}
