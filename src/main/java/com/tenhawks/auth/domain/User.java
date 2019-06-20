package com.tenhawks.auth.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Mukhtiar Ahmed
 */
@Data
@Table(value = "user")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User  {

  private static final long serialVersionUID = -3392490659474682931L;

  @PrimaryKey
  private UUID userId;
  @NotBlank
  private String userName;

  @Email
  private String emailAddress;

  @NotBlank
  private String fullName;

  private String profileImage;

  private String password;

  private Boolean active = Boolean.FALSE;

  private String phoneNumber;

  private List<String> roles =new ArrayList<>();

}
