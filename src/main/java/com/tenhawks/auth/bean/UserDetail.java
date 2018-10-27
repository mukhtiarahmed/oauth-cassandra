package com.tenhawks.auth.bean;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mukhtiar Ahmed
 */
public class UserDetail implements UserDetails {

	private static final long serialVersionUID = 12112526L;

	private String userId;

	private String userName;

	private String emailAddress;

	private String password;

	private String fullName;

	private boolean enabled;

	private String phoneNumber;
	
	private String profileImage;

	private List<String> roles;


	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return emailAddress;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	@Override
	public List<GrantedAuthority> getAuthorities() {
		return roles.stream().map( role -> new SimpleGrantedAuthority(role)).collect( Collectors.toList());
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.enabled;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.enabled;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.enabled;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return Boolean.TRUE;
		}
		if ((obj instanceof UserDetail)) {
			UserDetail other = (UserDetail) obj;
			return this.userId.equals(other.getUserId());
		} else {
			return Boolean.FALSE;
		}
	}



}
