package com.springsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@Order(value=1)
public class BasicSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.antMatcher("/guest/user/**").httpBasic().
         realmName("spring-app").
         and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
         and().csrf().disable().
         authorizeRequests().antMatchers("/guest/user/**").hasAnyRole("USER","EXECUTIVE","MANAGER").anyRequest().authenticated();
	}
	
	@Bean
    public NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
	 
	 @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.inMemoryAuthentication().withUser("app_user").password("test123").roles("USER");
	        auth.inMemoryAuthentication().withUser("app_manager").password("test123").roles("USER","MANAGER");
	        auth.inMemoryAuthentication().withUser("app_executive").password("test123").roles("EXECUTIVE","MANAGER","USER");
	       
	       
	    }

}
