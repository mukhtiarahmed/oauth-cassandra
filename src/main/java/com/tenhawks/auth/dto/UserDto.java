/*
 * Copyright (C) 2016 Beaver Technologies., All Rights Reserved.
 */
package com.tenhawks.auth.dto;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The dto class for the user.
 *
 * @author Mukhtiar Ahmed
 * @version 1.0
 */
@Data
@NoArgsConstructor
public class UserDto {

    /**
	 * The serial version id.
	 */
	private static final long serialVersionUID = 11L;

    private String userName;

    private String userId;

    private String emailAddress;

    private String password;

    private String fullName;

    private boolean enabled;

    private String phoneNumber;

    private String profileImage;


}
