package com.interview.service;

import com.interview.model.Question;
import com.interview.model.Template;

import java.util.List;

/**
 * @author Anton Kruglikov.
 */
public interface TemplateService {

    Template createTemplate(Template template);

    Template readTemplate(String id);

    List<Template> readAllTemplates();

    Template updateTemplate(Template template);

    boolean deleteTemplate(String id);

    List<Question> getQuestionsFromTemplate(String id);

    void addQuestionToTemplate(String templateId, Question question);

    void deleteQuestionFromTemplate(String templateId, String questionId);

}
