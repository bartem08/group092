package com.interview.util;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import java.util.Date;

/**
 * Created by NSS on 28.03.2016.
 */
@Document
@CompoundIndexes({
        @CompoundIndex(name = "i_username", def = "{'username': 1}"),
        @CompoundIndex(name = "i_series", def = "{'series': 1}")
})
public class Token extends PersistentRememberMeToken {

    @Id
    private String id;

    public Token(String id, String username, String series, String tokenValue, Date date) {
        super(username, series, tokenValue, date);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
