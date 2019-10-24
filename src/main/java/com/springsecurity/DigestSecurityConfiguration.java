package com.springsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

/**
 * Created by JavaDeveloperZone on 04-08-2017.
 */
@Configuration
@Order(value = 2)
public class DigestSecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/guest/admin/**").addFilter(digestAuthenticationFilter()) 
				.exceptionHandling().authenticationEntryPoint(digestEntryPoint()) 
				.and().authorizeRequests().antMatchers("/guest/admin/**").hasAnyRole("ADMIN", "USER") 
				.anyRequest().authenticated();
	}

	DigestAuthenticationFilter digestAuthenticationFilter() throws Exception {
		DigestAuthenticationFilter digestAuthenticationFilter = new DigestAuthenticationFilter();
		digestAuthenticationFilter.setUserDetailsService(userDetailsServiceBean());
		digestAuthenticationFilter.setAuthenticationEntryPoint(digestEntryPoint());
		return digestAuthenticationFilter;
	}

	@Override
	@Bean
	public UserDetailsService userDetailsServiceBean() {
		InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();

		inMemoryUserDetailsManager
				.createUser(User.withUsername("admin").password("test123").roles("USER", "ADMIN").build());
		inMemoryUserDetailsManager
				.createUser(User.withUsername("chathuranga").password("test123").roles("USER").build());

		return inMemoryUserDetailsManager;
	}

	@Bean
	DigestAuthenticationEntryPoint digestEntryPoint() {
		DigestAuthenticationEntryPoint bauth = new DigestAuthenticationEntryPoint();
		bauth.setRealmName("admin digest realm");
		bauth.setKey("MySecureKey");
		return bauth;
	}

	@Bean
	public AuthenticationManager customAuthenticationManager() throws Exception {
		return authenticationManager();
	}

	@Bean
	public NoOpPasswordEncoder passwordEncoder() {
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}
}