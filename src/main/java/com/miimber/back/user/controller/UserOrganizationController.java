package com.miimber.back.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.miimber.back.core.helper.Helper;
import com.miimber.back.organization.dto.organization.OrganizationAndMemberReadResponseDTO;
import com.miimber.back.organization.model.Member;
import com.miimber.back.organization.model.Organization;
import com.miimber.back.organization.service.MemberService;
import com.miimber.back.organization.service.OrganizationService;
import com.miimber.back.user.model.User;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserOrganizationController {
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private Helper helper;

	@RequestMapping(value = "/user/organization", method = RequestMethod.GET)
	public ResponseEntity<?> readUserOrganization() throws Exception {
        User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        List<OrganizationAndMemberReadResponseDTO> responseOrganization = new ArrayList<OrganizationAndMemberReadResponseDTO>();
        for (Organization organization : organizationService.getOrganizationByUser(user)) 
        { 
        	Member member = memberService.getMemberByOrganizationAndByUser(organization, user);
        	responseOrganization.add(new OrganizationAndMemberReadResponseDTO(organization, member));
        }
		return ResponseEntity.ok(responseOrganization);
	}
}
