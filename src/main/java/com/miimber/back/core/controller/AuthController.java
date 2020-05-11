package com.miimber.back.core.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.miimber.back.core.config.JwtTokenUtil;
import com.miimber.back.core.dto.InvitationValidatedDTO;
import com.miimber.back.core.dto.JwtRequestDTO;
import com.miimber.back.core.dto.JwtResponseDTO;
import com.miimber.back.core.dto.PasswordForgottenDTO;
import com.miimber.back.core.dto.RegisterValidatedDTO;
import com.miimber.back.core.dto.ResetPasswordDTO;
import com.miimber.back.core.helper.MailJetService;
import com.miimber.back.core.service.JwtUserDetailsService;
import com.miimber.back.user.dto.UserRegisterDTO;
import com.miimber.back.user.model.User;
import com.miimber.back.user.model.enums.StatusEnum;
import com.miimber.back.user.service.UserService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

	@Autowired
	private MailJetService mailJetService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequestDTO authenticationRequest) throws Exception {
		authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
		
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getEmail());
		final String token = jwtTokenUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new JwtResponseDTO(token));
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> registerUser(@RequestBody UserRegisterDTO userDto) throws Exception {
		User user = userService.getUserByEmail(userDto.getEmail());
		if (user != null) {
			return new ResponseEntity(HttpStatus.CONFLICT);
		}
		User newUser = new User();
		newUser.setEmail(userDto.getEmail());
		newUser.setFirstName(userDto.getFirstName());
		newUser.setLastName(userDto.getLastName());
		newUser.setPassword(bcryptEncoder.encode(userDto.getPassword()));
		newUser.setTokenCreation(UUID.randomUUID().toString());
		newUser.setLang(userDto.getLang());
		userService.create(newUser);
		
		mailJetService.sendEmailRegister(newUser.getEmail(), newUser.getFirstName() + " " + newUser.getLastName(), newUser.getLang(), newUser.getTokenCreation(), newUser.getId());
		return ResponseEntity.ok(newUser);
	}
	
	@RequestMapping(value = "/password-forgotten", method = RequestMethod.POST)
	public ResponseEntity<?> passwordForgotten(@RequestBody PasswordForgottenDTO passwordForgottenDto) throws Exception {
		User user = userService.getUserByEmail(passwordForgottenDto.getEmail());
		String token = UUID.randomUUID().toString();
		user.setTokenPasswordForgotten(token);
		userService.update(user);
		mailJetService.sendEmailResetPassword(user.getEmail(), user.getFirstName() + " " + user.getLastName(), passwordForgottenDto.getLang(), token, user.getId());
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/reset-password", method = RequestMethod.POST)
	public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDto) throws Exception {
		User user = userService.get(resetPasswordDto.getId());
		if (user == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		if (!user.getTokenPasswordForgotten().equals(resetPasswordDto.getToken())) {
			return new ResponseEntity(HttpStatus.CONFLICT);
		}
		userService.updatePasswordUser(user, resetPasswordDto.getPassword());
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/register-validated", method = RequestMethod.POST)
	public ResponseEntity<?> registerValidated(@RequestBody RegisterValidatedDTO registerValidatedDto) throws Exception {
		User user = userService.get(registerValidatedDto.getId());
		if (user == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		if (!user.getTokenCreation().equals(registerValidatedDto.getToken())) {
			return new ResponseEntity(HttpStatus.CONFLICT);
		}
		user.setStatus(StatusEnum.Validated);
		userService.update(user);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/invitation-validated", method = RequestMethod.POST)
	public ResponseEntity<?> invitationValidated(@RequestBody InvitationValidatedDTO invitationValidatedDto) throws Exception {
		User user = userService.get(invitationValidatedDto.getId());
		if (user == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		if (!user.getTokenCreation().equals(invitationValidatedDto.getToken())) {
			return new ResponseEntity(HttpStatus.CONFLICT);
		}
		user.setStatus(StatusEnum.Validated);
		user.setPassword(bcryptEncoder.encode(invitationValidatedDto.getPassword()));
		user.setFirstName(invitationValidatedDto.getFirstName());
		user.setLastName(invitationValidatedDto.getLastName());
		user.setLang(invitationValidatedDto.getLang());
		userService.update(user);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}


