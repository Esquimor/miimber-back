package com.miimber.back.session.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.miimber.back.core.helper.Helper;
import com.miimber.back.organization.model.Member;
import com.miimber.back.organization.service.MemberService;
import com.miimber.back.session.dto.attendee.AttendeeSessionCreateRequestDTO;
import com.miimber.back.session.dto.attendee.AttendeeSessionCreateResponseDTO;
import com.miimber.back.session.model.AttendeeSession;
import com.miimber.back.session.model.Session;
import com.miimber.back.session.service.AttendeeSessionService;
import com.miimber.back.session.service.SessionService;
import com.miimber.back.user.model.User;
import com.miimber.back.user.service.UserService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AttendeeSessionController {
	
	@Autowired
	private Helper helper;
	
	@Autowired
	private AttendeeSessionService attendeeService;

	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value= "/attendee/", method = RequestMethod.POST)
	public ResponseEntity<?> createAttendee(@RequestBody AttendeeSessionCreateRequestDTO attendeeDTO) throws Exception {
		Session session = sessionService.get(attendeeDTO.getSessionId());
		
		if (session == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		
        User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        Member member = memberService.getMemberByOrganizationAndByUser(session.getOrganization(), user);
        
        if (member == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
        if (!member.canEmergeOrganization()) {
        	return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        
        User userChecked = userService.get(attendeeDTO.getUserId());
        
        if (userChecked == null) {
        	return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        
        AttendeeSession attendee = new AttendeeSession();
        attendee.setSession(session);
        attendee.setUser(userChecked);
        attendee.setDateCheck(attendeeDTO.getDateCheck());
        
        return ResponseEntity.ok(new AttendeeSessionCreateResponseDTO(attendeeService.create(attendee)));
	}
	
	@RequestMapping(value = "/attendee/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteAttendee(@PathVariable Long id) throws Exception {
		AttendeeSession attendee = attendeeService.get(id);
		
		if (attendee == null) {
        	return new ResponseEntity(HttpStatus.NOT_FOUND);
		}

        User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        Member member = memberService.getMemberByOrganizationAndByUser(attendee.getSession().getOrganization(), user);
        
        if (member == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
        if (!member.canEmergeOrganization()) {
        	return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        
        attendeeService.delete(attendee);

        return new ResponseEntity(HttpStatus.OK);
	}
}
