package com.tenhawks.auth.controller;

import com.tenhawks.auth.bean.ApiResponse;
import com.tenhawks.auth.bean.UriConstant;
import com.tenhawks.auth.bean.UserDetail;
import com.tenhawks.auth.bean.Utils;
import com.tenhawks.auth.domain.User;
import com.tenhawks.auth.dto.UserDto;
import com.tenhawks.auth.exception.AuthenticationException;
import com.tenhawks.auth.mapper.UserMapper;
import com.tenhawks.auth.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


/**
 * This service is responsible to handle user registration and information
 * @author Mukhtiar
 */
@RestController
public class UserController {
    private static final Logger logger = LoggerFactory
            .getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * Get the User.
     * @return the user entity
     * @throws AuthenticationException if any other error occurred during operation
     */
    @RequestMapping(value = "/me", method = GET)
    public UserDto me() throws AuthenticationException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();
        UserDetail userDetail = userService.getUserDetailByUserName(userName);
        return null;
    }



    /**
     * Ten Hawks API end point for register new user
     * @param userDto
     * @return
     */
    @RequestMapping(value = UriConstant.USER.USER, method = RequestMethod.POST)
    public ApiResponse<String> registerUser(@Valid @RequestBody UserDto userDto) {
        logger.info("Start registerUser.");
        User user = UserMapper.toUser(userDto);
        userService.registerUser(user);

        return Utils.createApiResponse("Successfully registered",
                "Successfully registered", HttpStatus.OK);

    }


}
