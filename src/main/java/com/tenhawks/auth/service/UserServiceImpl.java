package com.tenhawks.auth.service;


import com.tenhawks.auth.bean.RoleEnum;
import com.tenhawks.auth.bean.UserDetail;
import com.tenhawks.auth.domain.User;
import com.tenhawks.auth.exception.AlreadyRegisteredException;
import com.tenhawks.auth.exception.EntityNotFoundException;
import com.tenhawks.auth.repository.RoleRepository;
import com.tenhawks.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mukhtiar Ahmed
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @PostConstruct
    public void init() {
        Assert.notNull(roleRepository, "roleRepository can not be null");
        Assert.notNull(userRepository, "userRepository can not be null");
        Assert.notNull(passwordEncoder, "passwordEncoder can not be null");

    }


    /**
     * <p>
     * Saves provided {@link NotNull} {@link User} object to database.
     * </p>
     *
     * @param user {@link User}
     */
    @Override
    public void saveUser(@NotNull final User user) {
        userRepository.save(user);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetail getUserDetailByUserName(String userName) {

        User user = userRepository.findByUserName(userName);

        UserDetail userDetail = null;
        if (user != null) {
            userDetail = new UserDetail();
            userDetail.setEmailAddress(user.getEmailAddress());
            userDetail.setPassword(user.getPassword());
            userDetail.setFullName(user.getFullName());
            userDetail.setEnabled(user.getActive());
            userDetail.setRoles(user.getRoles());
            userDetail.setPhoneNumber(user.getPhoneNumber());
            userDetail.setProfileImage(user.getProfileImage());
        }

        return userDetail;
    }

    @Override
    public User getUserByUserName(final String userName) {
        User user = userRepository.findByUserName(userName);
        if(user == null) {
            throw new EntityNotFoundException(String.format("User not found by username %s", userName));
        }
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmailAddress(email);
        if(user == null) {
            throw new EntityNotFoundException(String.format("User not found by email %s", email));
        }
        return user;
    }


    @Override
    public void registerUser(User user) {
        User exits = userRepository.findByUserName(user.getUserName());
        if (exits != null) {
            throw new AlreadyRegisteredException("You seem to be already registered.");
        }

        exits = userRepository.findByEmailAddress(user.getEmailAddress());
        if (exits == null) {
            user.setRoles(Arrays.asList(RoleEnum.ROLE_USER.name()));
            user.setActive(Boolean.TRUE);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            saveUser(user);
        } else {
            throw new AlreadyRegisteredException("Email address already registered.");

        }
    }



    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
