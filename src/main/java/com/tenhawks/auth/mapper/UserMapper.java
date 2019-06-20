package com.tenhawks.auth.mapper;

import com.tenhawks.auth.domain.User;
import com.tenhawks.auth.dto.UserDto;

/**
 * Created by mukhtiar on 11/8/2018.
 */
public final class UserMapper {

    private UserMapper() {

    }

    public static User toUser(UserDto dto) {
        User user = new User();
        user.setEmailAddress(dto.getEmailAddress());
        user.setFullName(dto.getFullName());
        user.setUserName(dto.getUserName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setProfileImage(dto.getProfileImage());
        return user;
    }

    public static UserDto toUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setEmailAddress(user.getEmailAddress());
        dto.setFullName(user.getFullName());
        dto.setUserName(user.getUserName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setProfileImage(user.getProfileImage());
        dto.setUserId(user.getUserId().toString());
        return dto;
    }



}
