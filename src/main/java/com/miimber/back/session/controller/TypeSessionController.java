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
import com.miimber.back.session.dto.typeSession.TypeSessionCreateRequestDTO;
import com.miimber.back.session.dto.typeSession.TypeSessionNameUpdateRequestDTO;
import com.miimber.back.session.dto.typeSession.TypeSessionReadResponseDTO;
import com.miimber.back.session.model.TypeSession;
import com.miimber.back.session.service.TypeSessionService;
import com.miimber.back.user.model.User;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TypeSessionController {
	
	@Autowired
	private TypeSessionService typeSessionService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private Helper helper;
	

	@RequestMapping(value = "/type-session/", method = RequestMethod.POST)
	public ResponseEntity<?> createTypeSession(@RequestBody TypeSessionCreateRequestDTO typeSessionDto) throws Exception {
        User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        Member memberUser = memberService.getMemberByOrganizationIdAndByUser(typeSessionDto.getOrganizationId(), user);
        if (memberUser == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (!memberUser.canEditOrganization()) {
        	return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        
        TypeSession typeSession = new TypeSession();
        typeSession.setName(typeSessionDto.getName());
        typeSession.setOrganization(memberUser.getOrganization());

        return ResponseEntity.ok(new TypeSessionReadResponseDTO(typeSessionService.create(typeSession)));
	}
	
	@RequestMapping(value = "/type-session/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateTypeSession(@RequestBody TypeSessionNameUpdateRequestDTO typeSessionDto, @PathVariable Long id) throws Exception {
        User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        TypeSession typeSession = typeSessionService.get(id);
        if (typeSession == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        Member memberUser = memberService.getMemberByOrganizationAndByUser(typeSession.getOrganization(), user);
        if (memberUser == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (!memberUser.canEditOrganization()) {
        	return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        typeSession.setName(typeSessionDto.getName());

        return ResponseEntity.ok(new TypeSessionReadResponseDTO(typeSessionService.update(typeSession)));
	}
	
	@RequestMapping(value = "/type-session/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteTypeSession(@PathVariable Long id) throws Exception {
        User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        TypeSession typeSession = typeSessionService.get(id);
        if (typeSession == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        Member memberUser = memberService.getMemberByOrganizationAndByUser(typeSession.getOrganization(), user);
        if (memberUser == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (!memberUser.canEditOrganization()) {
        	return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        typeSessionService.delete(typeSession);
        return new ResponseEntity(HttpStatus.OK);
	}
}
