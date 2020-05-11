package com.miimber.back.core.service;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.miimber.back.user.dto.UserRegisterDTO;
import com.miimber.back.user.model.User;
import com.miimber.back.user.service.UserService;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
    @Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	User user = userService.getUserByEmailAndStatusValidated(email);
		
		if (user == null) {
			throw new UsernameNotFoundException(String.format("The email %s doesn't exist", email));
		}
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),new ArrayList<>());
	}

}
