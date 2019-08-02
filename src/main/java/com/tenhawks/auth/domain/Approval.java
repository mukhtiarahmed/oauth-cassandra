package com.tenhawks.auth.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.security.oauth2.provider.approval.Approval.ApprovalStatus;

import java.util.Date;
import java.util.UUID;

/**
 * Created by mukhtiar on 5/22/2018.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "approval")
public class Approval {

    @PrimaryKey
    private UUID id;

    private String userId;

    private String clientId;

    private String scope;

    private ApprovalStatus status;

    private Date expiresAt;

    private Date lastUpdatedAt;

}
