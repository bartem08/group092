package com.interview.service.mongo;

import com.interview.model.Question;
import com.interview.repository.QuestionRepository;
import com.interview.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Anton Kruglikov.
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository repository;

    @Override
    public Question createQuestion(Question question) {
        question.setId(null);
        return repository.insert(question);
    }

    @Override
    public Question readQuestion(String id) {
        return repository.findOne(id);
    }

    @Override
    public List<Question> readAllQuestions() {
        return repository.findAll();
    }

    @Override
    public Question updateQuestion(Question question) {
        if (!repository.exists(question.getId())) {
            return null;
        }
        return repository.save(question);
    }

    @Override
    public boolean deleteQuestion(String id) {
        if (repository.exists(id)) {
            repository.delete(id);
            return true;
        }
        return false;
    }
}
