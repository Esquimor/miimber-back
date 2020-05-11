package com.miimber.back.organization.controller.organization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.miimber.back.core.helper.Helper;
import com.miimber.back.organization.dto.organization.OrganizationCreateReadUpdateResponseDTO;
import com.miimber.back.organization.dto.organization.OrganizationCreateRequestDTO;
import com.miimber.back.organization.model.Member;
import com.miimber.back.organization.model.Organization;
import com.miimber.back.organization.model.enums.RoleEnum;
import com.miimber.back.organization.service.MemberService;
import com.miimber.back.organization.service.OrganizationService;
import com.miimber.back.session.model.TypeSession;
import com.miimber.back.session.service.TypeSessionService;
import com.miimber.back.user.model.User;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrganizationTestController {
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private TypeSessionService typeSessionService;
	
	@Autowired
	private Helper helper;
	
	@RequestMapping(value = "/test/organization/", method = RequestMethod.POST)
	public ResponseEntity<?> readOrganizationAll(@RequestBody OrganizationCreateRequestDTO organizationDto) throws Exception {
        UserDetails currentUser = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = helper.getUserToken(currentUser);
        
        Organization newOrganization = new Organization(organizationDto.getName());
        newOrganization= organizationService.create(newOrganization);
        newOrganization.setStripe(null);
        
        Member newMember = new Member();
        newMember.setOrganization(newOrganization);
        newMember.setUser(user);
        newMember.setRole(RoleEnum.OWNER);
        newMember = memberService.create(newMember);
        
        newOrganization.addMember(newMember);
        
        TypeSession newTypeSession = new TypeSession();
        newTypeSession.setOrganization(newOrganization);
        newTypeSession.setName("Default");
        
        typeSessionService.create(newTypeSession);
        
        return ResponseEntity.ok(new OrganizationCreateReadUpdateResponseDTO(newOrganization));
	}
}
