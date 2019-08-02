package com.tenhawks.auth.security;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.tenhawks.auth.domain.AccessToken;
import com.tenhawks.auth.domain.RefreshToken;
import com.tenhawks.auth.repository.AccessTokenRepository;
import com.tenhawks.auth.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.oauth2.common.ExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Collections2.transform;

/**
 * Created by mukhtiar on 5/22/2018.
 */
public class CassandraTokenStore implements TokenStore {

    @Autowired
    private AccessTokenRepository accessTokenRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private AuthenticationKeyGenerator authenticationKeyGenerator;

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
         return readAuthentication(token.getValue());
    }

    @Override
    public OAuth2Authentication readAuthentication(String token) {
        AccessToken accessToken = accessTokenRepository.findByTokenId(token);
        if(accessToken != null) {
            try {
                return deserializeAuthentication(accessToken.getAuthentication());
            } catch (IllegalArgumentException e) {
                removeAccessToken(token);
            }
        }
        return null;
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        ByteBuffer bufferedToken = serializeAccessToken(token);
        ByteBuffer bufferedAuthentication = serializeAuthentication(authentication);
        String refreshToken = null;
        if (token.getRefreshToken() != null) {
            refreshToken = token.getRefreshToken().getValue();
        }

        if (readAccessToken(token.getValue())!=null) {
            removeAccessToken(token.getValue());
        }

        AccessToken accessToken = new AccessToken();
        accessToken.setTokenId(token.getValue());
        accessToken.setToken(bufferedToken);
        accessToken.setAuthentication(bufferedAuthentication);
        accessToken.setRefreshToken(refreshToken);
        accessToken.setClientId(authentication.getOAuth2Request().getClientId());
        accessToken.setUsername(authentication.isClientOnly() ? null : authentication.getName());
        accessToken.setAuthenticationId(authenticationKeyGenerator.extractKey(authentication));

        accessTokenRepository.save(accessToken);

        if (token.getRefreshToken() != null && token.getRefreshToken().getValue() != null) {
            storeRefreshToken(token.getRefreshToken(), authentication);
        }




    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenId) {
        AccessToken accessToken = accessTokenRepository.findByTokenId(tokenId);

        if(accessToken != null) {
            try {
                return deserializeAccessToken(accessToken.getToken());
            } catch (IllegalArgumentException e) {
                removeAccessToken(tokenId);
            }

        }
        return null;
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        removeAccessToken(token.getValue());
    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {

        ByteBuffer bufferedRefreshToken = serializeRefreshToken(refreshToken);
        ByteBuffer bufferedAuthentication = serializeAuthentication(authentication);
        final String tokenKey = refreshToken.getValue();

        if (refreshToken instanceof ExpiringOAuth2RefreshToken) {
            ExpiringOAuth2RefreshToken expiringRefreshToken = (ExpiringOAuth2RefreshToken) refreshToken;
            Date expiration = expiringRefreshToken.getExpiration();
            if (expiration != null) {
                int seconds = Long.valueOf((expiration.getTime() - System.currentTimeMillis()) / 1000L).intValue();

            }
        }

        refreshTokenRepository.save( new RefreshToken(tokenKey, bufferedRefreshToken, bufferedAuthentication));
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenId) {
        RefreshToken refreshToken = refreshTokenRepository.findByTokenId(tokenId);
        if (refreshToken != null) {
            try {
                return deserializeRefreshToken(refreshToken.getToken());
            } catch (IllegalArgumentException e) {
                removeRefreshToken(tokenId);
            }
        }
        return null;
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        RefreshToken refreshToken = refreshTokenRepository.findByTokenId(token.getValue());
        if (refreshToken != null) {
            try {
                return deserializeAuthentication(refreshToken.getAuthentication());
            } catch (IllegalArgumentException e) {
                removeRefreshToken(token.getValue());
            }
        }
        return null;
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken token) {
            removeRefreshToken(token.getValue());
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        removeAccessTokenUsingRefreshToken(refreshToken.getValue());
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        String authenticationId = authenticationKeyGenerator.extractKey(authentication);
        AccessToken accessToken = accessTokenRepository.findByAuthenticationId(authenticationId);
        if(accessToken != null) {
            return deserializeAccessToken(accessToken.getToken());
        }
        return null;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        final List<AccessToken> oAuth2AccessTokens = accessTokenRepository.findByUsernameAndClientId(userName, clientId);
        final Collection<AccessToken> noNullTokens = filter(oAuth2AccessTokens, byNotNulls());
        return transform(noNullTokens, toOAuth2AccessToken());
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        final List<AccessToken> oAuth2AccessTokens = accessTokenRepository.findByClientId(clientId);
        final Collection<AccessToken> noNullTokens = filter(oAuth2AccessTokens, byNotNulls());
        return transform(noNullTokens, toOAuth2AccessToken());
    }


    public void removeAccessToken(final String tokenValue) {
        AccessToken accessToken = accessTokenRepository.findByTokenId(tokenValue);
        if(accessToken != null) {
            accessTokenRepository.delete(accessToken);
        }
    }

    public void removeRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByTokenId(token);
        if(refreshToken != null) {
            refreshTokenRepository.delete(refreshToken);
        }
    }

    public void removeAccessTokenUsingRefreshToken(final String refreshToken) {
        AccessToken accessToken = accessTokenRepository.findByRefreshToken(refreshToken);
        if(accessToken != null) {
            accessTokenRepository.delete(accessToken);
        }
    }

    protected ByteBuffer serializeAccessToken(OAuth2AccessToken token) {
        return ByteBuffer.wrap(SerializationUtils.serialize(token));
    }

    protected ByteBuffer serializeRefreshToken(OAuth2RefreshToken token) {
        return ByteBuffer.wrap(SerializationUtils.serialize(token));
    }

    protected ByteBuffer serializeAuthentication(OAuth2Authentication authentication) {
        return ByteBuffer.wrap(SerializationUtils.serialize(authentication));
    }

    protected OAuth2AccessToken deserializeAccessToken(ByteBuffer token) {
        byte[] bytes = new byte[token.remaining()];
        token.get(bytes);
        return SerializationUtils.deserialize(bytes);
    }

    protected OAuth2RefreshToken deserializeRefreshToken(ByteBuffer token) {
        byte[] bytes = new byte[token.remaining()];
        token.get(bytes);
        return SerializationUtils.deserialize(bytes);
    }

    protected OAuth2Authentication deserializeAuthentication(ByteBuffer authentication) {
        byte[] bytes = new byte[authentication.remaining()];
        authentication.get(bytes);
        return SerializationUtils.deserialize(bytes);
    }

    private Predicate<AccessToken> byNotNulls() {
        return token -> token != null;
    }

    private Function<AccessToken, OAuth2AccessToken> toOAuth2AccessToken() {
        return token -> deserializeAccessToken(token.getToken());
    }

}
