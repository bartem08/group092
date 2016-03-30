package com.interview.service;

import com.interview.model.Question;

import java.util.List;

/**
 * @author Anton Kruglikov.
 */
public interface QuestionService {

    Question createQuestion(Question question);

    Question readQuestion(String id);

    List<Question> readAllQuestions();

    Question updateQuestion(Question question);

    boolean deleteQuestion(String id);

}
