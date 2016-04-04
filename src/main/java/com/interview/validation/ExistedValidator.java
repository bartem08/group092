package com.interview.validation;

import com.interview.model.AbstractDocument;
import com.interview.validation.annotation.Existed;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;

/**
 * @author Artem Baranovskiy
 */
@Component
public class ExistedValidator implements ConstraintValidator<Existed, Object> {

    private final Logger LOG = LoggerFactory.getLogger(ExistedValidator.class);

    @Autowired
    private MongoOperations mongoOperations;

    private DBCollection collection;

    private boolean empty;

    @Override
    public void initialize(Existed existed) {
        collection = mongoOperations.getCollection(existed.collection());
        empty = existed.empty();
    }

    @Override
    public boolean isValid(final Object o, ConstraintValidatorContext constraintValidatorContext) {
        System.err.println(empty);
        if (o == null) {
            return empty;
        }
        if (o instanceof Collection) {
            return validateCollection((Collection) o);
        } else if (o instanceof AbstractDocument) {
            return validateAbstractDocument((AbstractDocument) o);
        }
        return false;
    }

    private boolean validateAbstractDocument(AbstractDocument o) {
        if (o.getId() == null) {
            LOG.error("No id for validation");
            return false;
        }
        BasicDBObject query;
        try {
            query = new BasicDBObject("_id", new ObjectId(o.getId()));
        } catch (IllegalArgumentException ex) {
            LOG.error("id {} does not exist in {}", o.getId(), collection.getName());
            return false;
        }
        return collection.findOne(query) != null;
    }

    public boolean validateCollection(final Collection<AbstractDocument> c) {
        if (c.size() == 0) {
            LOG.error("In collection must have been at least one object");
            return  false;
        }
        for (AbstractDocument abstractDocument: c) {
            if (!validateAbstractDocument(abstractDocument)) {
                LOG.error("No element with id {} in {}", abstractDocument.getId(), collection.getName());
                return false;
            }
        }
        return true;
    }

}
