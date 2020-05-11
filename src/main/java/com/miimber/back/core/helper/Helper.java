package com.miimber.back.core.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.miimber.back.user.model.User;
import com.miimber.back.user.service.UserService;

@Service
public class Helper {

	@Autowired
	private UserService userService;

	public User getUserToken(UserDetails currentUser) {
		return userService.getUserByEmail(currentUser.getUsername());
	}
}
