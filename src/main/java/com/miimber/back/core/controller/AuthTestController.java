package com.miimber.back.core.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.miimber.back.user.dto.UserRegisterDTO;
import com.miimber.back.user.model.User;
import com.miimber.back.user.service.UserService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthTestController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@RequestMapping(value = "/test/register", method = RequestMethod.POST)
	public ResponseEntity<?> registerUser(@RequestBody UserRegisterDTO user) throws Exception {
		User newUser = new User();
		newUser.setEmail(user.getEmail());
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setTokenCreation(UUID.randomUUID().toString());
		newUser.setLang(user.getLang());
		return ResponseEntity.ok(userService.create(newUser));
	}
}
