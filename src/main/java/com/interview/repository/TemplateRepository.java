package com.interview.repository;

import com.interview.model.Template;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Anton Kruglikov.
 */
@Repository
public interface TemplateRepository extends MongoRepository<Template, String> {

    Template findTemplateByName(String name);

    List<Template> findByInterviewerId(String id);

}
