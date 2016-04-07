package com.interview.service.mongo;

import com.interview.model.Question;
import com.interview.model.Template;
import com.interview.repository.QuestionRepository;
import com.interview.repository.TemplateRepository;
import com.interview.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Anton Kruglikov.
 */
@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private TemplateRepository repository;
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Template createTemplate(Template template) {
        template.setId(null);
        return repository.insert(template);
    }

    @Override
    public Template readTemplate(String id) {
        return repository.findOne(id);
    }

    @Override
    public List<Template> readAllTemplates() {
        return repository.findAll();
    }

    @Override
    public Template updateTemplate(Template template) {
        if (!repository.exists(template.getId())) {
            return null;
        }
        return repository.save(template);
    }

    @Override
    public boolean deleteTemplate(String id) {
        if (repository.exists(id)) {
            repository.delete(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Question> getAllQuestionsFromTemplate(String templateId) {
        Template template = repository.findOne(templateId);
        List<Question> questions = template.getQuestions();
        return questions;
    }

    @Override
    public Template addQuestionToTemplate(String templateId, Question question) {
        if (repository.findOne(templateId) != null) {
            Template template = repository.findOne(templateId);
            template.addQuestion(question);
            return repository.save(template);
        }
        return null;
    }

    @Override
    public boolean deleteQuestionFromTemplate(String templateId, String questionId) {
        if (repository.exists(templateId) && questionRepository.exists(questionId)) {
            Template template = repository.findOne(templateId);
            List<Question> questions = template.getQuestions();
            for (Question question : questions) {
                if (question.getId().equals(questionId)) {
                    questions.remove(question);
                    repository.save(template);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<Template> readTemplatesByInterviewer(String interviewerId) {
        return repository.findByInterviewerId(interviewerId);
    }
}
