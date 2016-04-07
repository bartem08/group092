package com.interview.repository;

import com.interview.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Yegor Gulimov
 */
@Repository
public interface GroupRepository extends MongoRepository<Group, String> {

    Group findGroupByName(String name);
    List<Group> findByInterviewersId(String interviewerId);
}
