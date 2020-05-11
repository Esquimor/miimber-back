package com.miimber.back.session.controller;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

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
import com.miimber.back.core.helper.MailJetService;
import com.miimber.back.organization.model.Member;
import com.miimber.back.organization.model.Organization;
import com.miimber.back.organization.service.MemberService;
import com.miimber.back.session.dto.session.SessionCreateRequestDTO;
import com.miimber.back.session.dto.session.SessionEditRequestDTO;
import com.miimber.back.session.dto.session.SessionReadResponseDTO;
import com.miimber.back.session.dto.session.SessionShortReadResponseDTO;
import com.miimber.back.session.dto.session.SessionUsersReadResponseDTO;
import com.miimber.back.session.model.AttendeeSession;
import com.miimber.back.session.model.RegisteredSession;
import com.miimber.back.session.model.Session;
import com.miimber.back.session.model.TemplateSession;
import com.miimber.back.session.model.TypeSession;
import com.miimber.back.session.service.RegisteredSessionService;
import com.miimber.back.session.service.SessionService;
import com.miimber.back.session.service.TemplateSessionService;
import com.miimber.back.session.service.TypeSessionService;
import com.miimber.back.user.dto.TemplateAttendeeDTO;
import com.miimber.back.user.model.User;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SessionController {

	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private TypeSessionService typeSessionService;
	
	@Autowired
	private TemplateSessionService templateSessionService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private Helper helper;
	
	@Autowired
	private MailJetService mailjetService;
	
	@RequestMapping(value = "/session/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> readSession(@PathVariable Long id) throws Exception {
        User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        Session session = sessionService.get(id);
        
        if (session == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        
        if (!session.getOrganization().isActif()) {
        	return new ResponseEntity(HttpStatus.CONFLICT);
        }
        
        Member member = memberService.getMemberByOrganizationAndByUser(session.getOrganization(), user);

		return ResponseEntity.ok(SessionAndMemberToSessionReadDTO(session, member, user.getId()));
	}
	
	@RequestMapping(value = "/session/{id}/user/", method = RequestMethod.GET)
	public ResponseEntity<?> readSessionUsers(@PathVariable Long id) throws Exception {
        User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        Session session = sessionService.get(id);
        
        if (session == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        Member memberUser = memberService.getMemberByOrganizationAndByUser(session.getOrganization(), user);
        
        if (!memberUser.canEmergeOrganization()) {
        	return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

		List<AttendeeSession> attendees = session.getAttendees();
		List<RegisteredSession> registereds = session.getRegistereds();
        
		List<TemplateAttendeeDTO> users = new ArrayList<TemplateAttendeeDTO>();
		
		if (session.getLimit() == 0) {
			List<Member> members = session.getOrganization().getMembers();
			
	    	users.addAll(getAllMembers(members, attendees, registereds));
	    	users.addAll(getAllAttendeesWithoutAlreadyUsers(attendees, registereds, users));
	    	users.addAll(getAllRegisteredsWithourAlreadyUsers(registereds, users));
		} else {
			users.addAll(getAllRegistered(registereds, attendees));
		}
		
		return ResponseEntity.ok(new SessionUsersReadResponseDTO(session, users));
	}

	
	@RequestMapping(value = "/session/", method = RequestMethod.POST)
	public ResponseEntity<?> createSession(@RequestBody SessionCreateRequestDTO sessionDto) throws Exception {
        User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        Member memberUser = memberService.getMemberByOrganizationIdAndByUser(sessionDto.getOrganizationId(), user);
        if (memberUser == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (!memberUser.canEditOrganization()) {
        	return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        
        TypeSession typeSession = typeSessionService.get(sessionDto.getTypeSessionId());
        if (typeSession == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        
        TemplateSession templateSession = templateSessionService.create(new TemplateSession());
        
        List<Session> listSession = new ArrayList<Session>();
        OffsetDateTime cursor =  sessionDto.getStartDate();
        switch (sessionDto.getPeriodicity()) {
        	case ONCE: {
                listSession.add(sessionService.create(
                		createSessionBySessionDtoAndDate(sessionDto, sessionDto.getStart(), typeSession, memberUser.getOrganization(), templateSession)
                		));
        		break;
        	}
        	case EVERYDAY: {
                OffsetDateTime endDate = sessionDto.getEndDate().plusDays(2);
                cursor = cursor.plusDays(1);
        		while(cursor.isBefore(endDate)) {
                    listSession.add(sessionService.create(
                    		createSessionBySessionDtoAndDate(sessionDto, cursor, typeSession, memberUser.getOrganization(), templateSession)
                    		));
                    cursor = cursor.plusDays(1);
        		}
        		break;
        	}
        	case BY_WEEK: {
                OffsetDateTime endDate = sessionDto.getEndDate().plusDays(2);
                cursor = cursor.plusDays(1);
        		while(cursor.isBefore(endDate)) {
        			if (sessionDto.getDays().contains(cursor.getDayOfWeek().getValue())) {
                        listSession.add(sessionService.create(
                        		createSessionBySessionDtoAndDate(sessionDto, cursor, typeSession, memberUser.getOrganization(), templateSession)
                        		));
        			}
        			if (cursor.getDayOfWeek() == DayOfWeek.SUNDAY) {
        				cursor = cursor.plusWeeks(sessionDto.getRepeat() - 1);
        			}
                    cursor = cursor.plusDays(1);
        		}
        		break;
        	}
        	default : {
        		return new ResponseEntity(HttpStatus.CONFLICT);
        	}
        }

		return ResponseEntity.ok(listSessionToListSessionDto(listSession));
	}
	
	@RequestMapping(value = "/session/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateSession(@RequestBody SessionEditRequestDTO sessionDto, @PathVariable Long id) throws Exception {
        User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        Session session = sessionService.get(id);
        
        if (session == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        
        Member memberUser = memberService.getMemberByOrganizationAndByUser(session.getOrganization(), user);
        if (memberUser == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (!memberUser.canEditOrganization()) {
        	return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        
        TypeSession typeSession = typeSessionService.get(sessionDto.getTypeSessionId());
        if (typeSession == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        
        int oldLimit = session.getLimit();
        int newLimit = sessionDto.getLimit();
        
        session.setTitle(sessionDto.getTitle());
        session.setDescription(sessionDto.getDescription());
        session.setStart(sessionDto.getStart());
        session.setEnd(sessionDto.getEnd());
        session.setTypeSession(typeSession);
        session.setLimit(sessionDto.getLimit());
        
        
        // Add taken registered
        if (oldLimit < newLimit) {
        	List<RegisteredSession> registereds = session.getRegistereds();
        	for (int i = 0; i < registereds.size(); i++) {
        		if (oldLimit < i + 1 && i + 1 <= newLimit) {
            		User userRegistered = registereds.get(i).getUser();
            		mailjetService.sendEmailTakenSession(userRegistered.getEmail(), userRegistered.getFullName() , userRegistered.getLang(), session);
        		}
        	}
        }
        
        // Remove taken registered
        if (oldLimit > newLimit) {
        	List<RegisteredSession> registereds = session.getRegistereds();
        	for (int i = 0; i < registereds.size(); i++) {
        		if (oldLimit >= i + 1 && i + 1 > newLimit) {
            		User userRegistered = registereds.get(i).getUser();
            		mailjetService.sendEmailWaitingSession(userRegistered.getEmail(), userRegistered.getFullName() , userRegistered.getLang(), session);
        		}
        	}
        }
        
        if (oldLimit != 0 && newLimit == 0) {
        	List<RegisteredSession> registereds = session.getRegistereds();
        	for (int i = 0; i < registereds.size(); i++) {
        		User userRegistered = registereds.get(i).getUser();
        		mailjetService.sendEmailTakenSession(userRegistered.getEmail(), userRegistered.getFullName() , userRegistered.getLang(), session);
        	}
        }
        
		return ResponseEntity.ok(new SessionShortReadResponseDTO(sessionService.update(session)));
	}
	
	@RequestMapping(value= "/session/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteSession(@PathVariable Long id) throws Exception {
        User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        Session session = sessionService.get(id);
        
        if (session == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        
        Member memberUser = memberService.getMemberByOrganizationAndByUser(session.getOrganization(), user);
        if (memberUser == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (!memberUser.canEditOrganization()) {
        	return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        
        for(RegisteredSession registered: session.getRegistereds()) {
    		User userRegistered = registered.getUser();
    		mailjetService.sendEmailCanceledSession(userRegistered.getEmail(), userRegistered.getFullName() , userRegistered.getLang(), session);
        }
        
        sessionService.delete(session);

        return new ResponseEntity(HttpStatus.OK);
	}
	
	private List<SessionShortReadResponseDTO> listSessionToListSessionDto(List<Session> listSession) {
		List<SessionShortReadResponseDTO> listSessionDto = new ArrayList<SessionShortReadResponseDTO>();
		for(Session session: listSession) {
			listSessionDto.add(new SessionShortReadResponseDTO(session));
		}
		return listSessionDto;
	}
	
	
	private Session createSessionBySessionDtoAndDate(SessionCreateRequestDTO sessionDto, OffsetDateTime date, TypeSession typeSession, Organization organization, TemplateSession templateSession) {
		Session session = new Session();
        session.setTitle(sessionDto.getTitle());
        session.setDescription(sessionDto.getDescription());
        session.setStart(mixDateAndTime(date, sessionDto.getStart()));
        session.setEnd(mixDateAndTime(date, sessionDto.getEnd()));
        session.setTypeSession(typeSession);
        session.setOrganization(organization);
        session.setLimit(sessionDto.getLimit());
        session.setTemplateSession(templateSession);
        return session;
	}
	
	private OffsetDateTime mixDateAndTime(OffsetDateTime date, OffsetDateTime time) {
		return OffsetDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), time.getHour(), time.getMinute(), time.getSecond(), time.getNano(), time.getOffset());
	}
	
	private SessionReadResponseDTO SessionAndMemberToSessionReadDTO(Session session, Member member, Long userId) {
		return new SessionReadResponseDTO(session, member, userId);
	}

	// List all organization members
	private List<TemplateAttendeeDTO> getAllMembers(List<Member> members, List<AttendeeSession> attendees, List<RegisteredSession> registereds) {
		List<TemplateAttendeeDTO> users = new ArrayList<TemplateAttendeeDTO>();
		for(Member member: members) {
			Long attendeId = 0L;
			boolean isRegistered = false;
			// Look if memberOrganization is present
			for(AttendeeSession attendee: attendees) {
				if (attendee.getUser().getId() == member.getUser().getId()) {
					attendeId = attendee.getId();
					break;
				}
			}
			for(RegisteredSession registered: registereds) {
				if (member.getUser().getId() == registered.getUser().getId()) {
					isRegistered = true;
					break;
				}
			}
			// add it
			users.add(new TemplateAttendeeDTO(member, attendeId, isRegistered));
		}
		return users;
	}

	// List all users outside organization
	private List<TemplateAttendeeDTO> getAllAttendeesWithoutAlreadyUsers(List<AttendeeSession> attendees, List<RegisteredSession> registereds, List<TemplateAttendeeDTO> alreadyUsers) {
		List<TemplateAttendeeDTO> users = new ArrayList<TemplateAttendeeDTO>();
		for(AttendeeSession attendee: attendees) {
			User userAttendee = attendee.getUser();
			boolean alreadyTaken = false;
			// Look if attendee is already a member
			for(TemplateAttendeeDTO user: alreadyUsers) {
				if (user.getId() == userAttendee.getId()) {
					alreadyTaken = true;
					break;
				}
			}
			// If attendee is not a member add it
			if (alreadyTaken == false) {
				boolean isRegistered = false;
				for(RegisteredSession registered: registereds) {
					if (attendee.getUser().getId() == registered.getUser().getId()) {
						isRegistered = true;
						break;
					}
				}
				users.add(new TemplateAttendeeDTO(userAttendee, attendee.getId(), isRegistered));
			}
		}
		return users;
	}
	
	private List<TemplateAttendeeDTO> getAllRegisteredsWithourAlreadyUsers(List<RegisteredSession> registereds, List<TemplateAttendeeDTO> alreadyUsers) {
		List<TemplateAttendeeDTO> users = new ArrayList<TemplateAttendeeDTO>();
		for(RegisteredSession registered: registereds) {
			// Look if attendee is already a member
			User userRegistered = registered.getUser();
			boolean alreadyTaken = false;
			for(TemplateAttendeeDTO user: alreadyUsers) {
				if (user.getId() == userRegistered.getId()) {
					alreadyTaken = true;
					break;
				}
			}
			if (alreadyTaken == false) {
				users.add(new TemplateAttendeeDTO(userRegistered, 0L, false));
			}
		}
		return users;
	}
	
	private List<TemplateAttendeeDTO> getAllRegistered(List<RegisteredSession> registereds, List<AttendeeSession> attendees) {
		List<TemplateAttendeeDTO> users = new ArrayList<TemplateAttendeeDTO>();
		for(RegisteredSession registered: registereds) {
			// Look if attendee is already a member
			Long userId = registered.getUser().getId();
			Long attendeeId = 0L;
			for(AttendeeSession attendee: attendees) {
				if (attendee.getUser().getId() == userId) {
					attendeeId = attendee.getId();
					break;
				}
			}
			User userRegistered = registered.getUser();
			users.add(new TemplateAttendeeDTO(userRegistered, attendeeId, true));
		}
		return users;
	}
}
