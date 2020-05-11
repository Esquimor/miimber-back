package com.miimber.back.organization.controller.organization;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.miimber.back.core.helper.Helper;
import com.miimber.back.organization.model.Member;
import com.miimber.back.organization.service.MemberService;
import com.miimber.back.session.dto.typeSession.TypeSessionReadResponseDTO;
import com.miimber.back.session.model.TypeSession;
import com.miimber.back.session.service.TypeSessionService;
import com.miimber.back.user.model.User;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrganizationTypeSessionController {

	@Autowired
	private Helper helper;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private TypeSessionService typeSessionService;
	
	@RequestMapping(value = "/organization/{id}/type-session/", method = RequestMethod.GET)
	public ResponseEntity<?> readOrganizationTypeSession(@PathVariable Long id) throws Exception {
        User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        Member memberUser = memberService.getMemberByOrganizationIdAndByUser(id, user);
        if (memberUser == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        List<TypeSessionReadResponseDTO> listTypeSessions = new ArrayList<TypeSessionReadResponseDTO>();
        
        for (TypeSession typeSession: typeSessionService.getTypeSessionByOrganizationId(id)) {
        	listTypeSessions.add(new TypeSessionReadResponseDTO(
        			typeSession
				));
        }
        return ResponseEntity.ok(listTypeSessions);
	}
}
