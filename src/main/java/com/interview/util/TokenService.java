package com.interview.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by NSS on 28.03.2016.
 */
@Component
public class TokenService implements PersistentTokenRepository {

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        tokenRepository.save(new Token(null,
                token.getUsername(),
                token.getSeries(),
                token.getTokenValue(),
                token.getDate()));
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        Token token = tokenRepository.findBySeries(series);
        tokenRepository.save(new Token(token.getId(), token.getUsername(), series, tokenValue, lastUsed));
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        return tokenRepository.findBySeries(seriesId);
    }

    @Override
    public void removeUserTokens(String username) {
        Token token = tokenRepository.findByUsername(username);
        tokenRepository.delete(token);
    }
}
