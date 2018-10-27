package com.tenhawks.auth.security;

import com.tenhawks.auth.domain.Client;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.*;

/**
 * Created by mukhtiar on 5/24/2018.
 */
public class CassandraClientDetails implements org.springframework.security.oauth2.provider.ClientDetails {

    protected UUID clientId;
    protected String clientSecret;
    protected String clientName;

    protected Set<String> scope = new HashSet<String>();
    protected Set<String> resourceIds = new HashSet<String>();
    protected Set<String> authorizedGrantTypes = new HashSet<String>();
    protected Set<String> registeredRedirectUris  = new HashSet<String>();
    protected List<GrantedAuthority> authorities;
    protected Integer accessTokenValiditySeconds = 2592000;
    protected Integer refreshTokenValiditySeconds = 2592000 * 30;
    protected Map<String, Object> additionalInformation = new LinkedHashMap<String, Object>();
    protected Set<String> autoApproveScopes;
    public CassandraClientDetails(Client client) {
        this.clientId = client.getClientId();
        this.clientSecret = client.getClientSecret();
        this.clientName = client.getClientName();
        this.scope = client.getScope();
        this.resourceIds = client.getResourceIds();
        this.authorizedGrantTypes = client.getAuthorizedGrantTypes();
        this.registeredRedirectUris = client.getRegisteredRedirectUris();
        this.authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(
                "ROLE_CLIENT,ROLE_TRUSTED_CLIENT");
        this.accessTokenValiditySeconds = client.getAccessTokenValiditySeconds();
        this.refreshTokenValiditySeconds = client.getRefreshTokenValiditySeconds();
        this.additionalInformation =  new HashMap<>();
        this.autoApproveScopes = client.getAutoApproveScopes();
    }

    @Override
    public String getClientId() {
        return clientId.toString();
    }

    @Override
    public Set<String> getResourceIds() {
        return resourceIds;
    }

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override
    public boolean isScoped() {
        return true;
    }

    @Override
    public Set<String> getScope() {
        return scope;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return registeredRedirectUris;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return false;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return additionalInformation;
    }
}
