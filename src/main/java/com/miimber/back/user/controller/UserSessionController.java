package com.miimber.back.user.controller;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miimber.back.core.helper.Helper;
import com.miimber.back.session.model.Session;
import com.miimber.back.session.service.SessionService;
import com.miimber.back.user.dto.UserSessionReadResponseDTO;
import com.miimber.back.user.model.User;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserSessionController {

	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private Helper helper;
	
	@RequestMapping(value = "/user/session/", method = RequestMethod.GET)
	public ResponseEntity<?> readUserSessions(@RequestParam String minDate, @RequestParam String maxDate) throws Exception {
        User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        List<UserSessionReadResponseDTO> sessions = new ArrayList<UserSessionReadResponseDTO>();
        for (Session session : sessionService.getSessionByUserAndDate(user, LocalDate.parse(minDate), LocalDate.parse(maxDate)))
        {
        	sessions.add(SessionToUserSessionDTO(session));
        }
        return ResponseEntity.ok(sessions);
	}
	
	private UserSessionReadResponseDTO SessionToUserSessionDTO(Session session) {
		return new UserSessionReadResponseDTO(session);
	}
}
