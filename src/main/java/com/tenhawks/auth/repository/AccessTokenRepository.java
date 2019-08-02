package com.tenhawks.auth.repository;

import com.tenhawks.auth.domain.AccessToken;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccessTokenRepository extends CassandraRepository<AccessToken, UUID> {

    @Query("select * from access_token where tokenid = ?0")
    AccessToken findByTokenId(String tokenId);

    @Query("select * from access_token where authenticationId = ?0  ALLOW FILTERING")
    AccessToken findByAuthenticationId(String authenticationId);

    @Query("select * from access_token where refreshtoken = ?0  ALLOW FILTERING")
    AccessToken findByRefreshToken(String refreshToken);

    @Query("select * from access_token where clientid = ?0  ALLOW FILTERING")
    List<AccessToken> findByClientId(String clientId);

    @Query("select * from access_token where username =?0 and clientid = ?1  ALLOW FILTERING")
    List<AccessToken>  findByUsernameAndClientId(String username, String clientId);
}
