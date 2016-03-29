package com.interview.util;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by NSS on 28.03.2016.
 */
public interface TokenRepository extends MongoRepository<Token, String> {

    Token findBySeries(String series);
    Token findByUsername(String username);
}
