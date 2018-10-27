package com.tenhawks.auth.security;

import com.tenhawks.auth.bean.AuthHelper;
import com.tenhawks.auth.bean.UserDetail;
import com.tenhawks.auth.exception.AuthenticationException;
import com.tenhawks.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * @author Mukhtiar
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication) {

		String userName = authentication.getName();
		String password = authentication.getCredentials().toString();

		UserDetail user = userService.getUserDetailByUserName(userName);
		
		if (user == null) {
			throw new AuthenticationException("Invalid username and/or password");
		} else if(!user.isEnabled()) {
			throw new AuthenticationException("your account is blocked");
		}

		if (AuthHelper.isPasswordMatched(password, user.getPassword())) {
			
			return new UsernamePasswordAuthenticationToken(userName, password, user.getAuthorities());
		}  else {    	
			throw new AuthenticationException("Invalid username and/or password");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(
				UsernamePasswordAuthenticationToken.class);
	}
}
