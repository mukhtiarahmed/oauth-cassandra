package com.tenhawks.auth.config;

import com.tenhawks.auth.security.CustomAuthenticationManager;
import com.tenhawks.auth.security.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Mukhtiar
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private CustomAuthenticationProvider customAuthenticationProvider;
  

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http    	    	
        .authorizeRequests().anyRequest().authenticated()
        .and()
        .logout().permitAll()
        .and()
        .csrf().disable();
  }  

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return new CustomAuthenticationManager(customAuthenticationProvider);
  }
}
