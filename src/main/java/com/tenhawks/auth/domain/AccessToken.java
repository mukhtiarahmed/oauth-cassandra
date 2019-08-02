package com.tenhawks.auth.domain;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;


import java.nio.ByteBuffer;

/**
 * Created by mukhtiar on 5/22/2018.
 */
@Table(value = "access_token")
@Data
public class AccessToken {

    @PrimaryKey
    private String tokenId;

    private ByteBuffer token;

    private String authenticationId;

    private String username;

    private String clientId;

    private ByteBuffer authentication;

    private String refreshToken;

}
