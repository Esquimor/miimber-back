package com.miimber.back.session.controller;

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
import com.miimber.back.organization.model.Member;
import com.miimber.back.organization.service.MemberService;
import com.miimber.back.session.dto.templateSession.TemplateSessionCreateRequestDTO;
import com.miimber.back.session.dto.templateSession.TemplateSessionTemplateCreateReadResponseDTO;
import com.miimber.back.session.model.TemplateSession;
import com.miimber.back.session.model.TypeSession;
import com.miimber.back.session.model.enums.TemplateSessionEnum;
import com.miimber.back.session.model.enums.TemplateSessionStatusEnum;
import com.miimber.back.session.service.TemplateSessionService;
import com.miimber.back.session.service.TypeSessionService;
import com.miimber.back.user.model.User;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TemplateSessionController {

	@Autowired
	private TemplateSessionService templateSessionService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private TypeSessionService typeSessionService;
	
	@Autowired
	private Helper helper;
	
	@RequestMapping(value = "/organization/{id}/template-session/", method = RequestMethod.GET)
	public ResponseEntity<?> readTemplateSession(@PathVariable Long id) throws Exception {
        User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        Member memberUser = memberService.getMemberByOrganizationIdAndByUser(id, user);
        if (memberUser == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (!memberUser.canEditOrganization()) {
			return new ResponseEntity(HttpStatus.CONFLICT);
        }
        
        List<TemplateSessionTemplateCreateReadResponseDTO> templates = new ArrayList<TemplateSessionTemplateCreateReadResponseDTO>();
        for(TemplateSession templateSession: templateSessionService.getMultipleByOrganization(memberUser.getOrganization())) {
        	templates.add(new TemplateSessionTemplateCreateReadResponseDTO(templateSession));
        }
        return ResponseEntity.ok(templates);
	}
	
	@RequestMapping(value = "/organization/{id}/template-session/going", method = RequestMethod.GET)
	public ResponseEntity<?> readGoingTemplateSession(@PathVariable Long id) throws Exception {
        User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        Member memberUser = memberService.getMemberByOrganizationIdAndByUser(id, user);
        if (memberUser == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (!memberUser.canEditOrganization()) {
			return new ResponseEntity(HttpStatus.CONFLICT);
        }
        
        List<TemplateSessionTemplateCreateReadResponseDTO> templates = new ArrayList<TemplateSessionTemplateCreateReadResponseDTO>();
        for(TemplateSession templateSession: templateSessionService.getMultipleByOrganizationAndGoing(memberUser.getOrganization())) {
        	templates.add(new TemplateSessionTemplateCreateReadResponseDTO(templateSession));
        }
        return ResponseEntity.ok(templates);
	}
	
	@RequestMapping(value = "/template-session/", method = RequestMethod.POST)
	public ResponseEntity<?> createTemplateSession(@RequestBody TemplateSessionCreateRequestDTO templateDto) throws Exception {
        User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        Member memberUser = memberService.getMemberByOrganizationIdAndByUser(templateDto.getOrganizationId(), user);
        if (memberUser == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (!memberUser.canEditOrganization()) {
        	return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        
        TypeSession typeSession = typeSessionService.get(templateDto.getTypeSessionId());
        if (typeSession == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

		TemplateSession templateSession = new TemplateSession();
		templateSession.setTitle(templateDto.getTitle());
		templateSession.setLimit(templateDto.getLimit());
		templateSession.setStartHour(templateDto.getStartHour());
		templateSession.setEndHour(templateDto.getEndHour());
		templateSession.setDescription(templateDto.getDescription());
		templateSession.setOrganization(memberUser.getOrganization());
		templateSession.setTypeSession(typeSession);
		templateSession.setRecurrency(TemplateSessionEnum.MULTIPLE);
		templateSession.setDay(templateDto.getDay());
		templateSession.setStatus(TemplateSessionStatusEnum.GOING);
		templateSession = templateSessionService.create(templateSession);
		
		return ResponseEntity.ok(new TemplateSessionTemplateCreateReadResponseDTO(templateSession));
	}
	
	@RequestMapping(value = "/template-session/{id}/archive", method = RequestMethod.PUT)
	public ResponseEntity<?> archiveTemplateSession(@PathVariable Long id) throws Exception {
		TemplateSession templateSession = templateSessionService.get(id);
		if (templateSession == null) {
			return (ResponseEntity<?>) ResponseEntity.notFound();
		}

        User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        Member memberUser = memberService.getMemberByOrganizationAndByUser(templateSession.getOrganization(), user);
        if (memberUser == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (!memberUser.canEditOrganization()) {
        	return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

		templateSession.setStatus(TemplateSessionStatusEnum.ARCHIVE);
		templateSessionService.update(templateSession);
		
		return new ResponseEntity(HttpStatus.OK);
	}
}
