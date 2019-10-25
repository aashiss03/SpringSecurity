package com.springsecurity;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



public class UserProfile implements UserDetails {
	
	
	private static final long serialVersionUID = -3917999811327101259L;
	private String username;
	private String password;
	private String name;
	private String village;
	private Collection<? extends GrantedAuthority> authorities;
	
	

	public static UserProfile build(String username, String password,Collection<? extends GrantedAuthority> authorities ) {
	
			return new UserProfile(username,password,authorities);
		
	}
	
	public UserProfile(String username,String password, Collection<? extends GrantedAuthority> authorities){
		this.username=username;
		this.password=password;
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
}
