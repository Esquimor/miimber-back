package com.miimber.back.organization.controller.member;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.miimber.back.core.helper.Helper;
import com.miimber.back.core.helper.MailJetService;
import com.miimber.back.core.helper.StripeService;
import com.miimber.back.organization.dto.member.MemberAndUserCreateRequestDTO;
import com.miimber.back.organization.dto.member.MemberCreateResponseDTO;
import com.miimber.back.organization.model.Member;
import com.miimber.back.organization.model.Organization;
import com.miimber.back.organization.service.MemberService;
import com.miimber.back.organization.service.OrganizationService;
import com.miimber.back.user.model.User;
import com.miimber.back.user.service.UserService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MemberUserController {

	@Autowired
	private MailJetService mailJetService;

	@Autowired
	private Helper helper;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private StripeService stripeService;
	
	@RequestMapping(value = "/member/user", method = RequestMethod.POST)
	public ResponseEntity<?> createMemberAndUser(@RequestBody MemberAndUserCreateRequestDTO memberByOrganizationRequestDto) throws Exception {
        User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        Member memberUser = memberService.getMemberByOrganizationIdAndByUser(memberByOrganizationRequestDto.getIdOrganization(), user);
        if (memberUser == null ) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        
        if (!memberUser.canEditOrganization()) {
        	return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        
        Organization organization = organizationService.get(memberByOrganizationRequestDto.getIdOrganization());
        if (organization == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        String token = UUID.randomUUID().toString();
		
        User newUser = new User();
        newUser.setEmail(memberByOrganizationRequestDto.getEmail());
        newUser.setFirstName(memberByOrganizationRequestDto.getFirstName());
        newUser.setLastName(memberByOrganizationRequestDto.getLastName());
        newUser.setTokenCreation(token);
		newUser.setLang(memberByOrganizationRequestDto.getLang());
		
        newUser = userService.create(newUser);
        
        Member newMember = new Member();
        newMember.setUser(newUser);
        newMember.setOrganization(organization);

		mailJetService.sendEmailInvitation(newUser.getEmail(), newUser.getFirstName() + " " + newUser.getLastName(), memberByOrganizationRequestDto.getLang(), token, newUser.getId(), user.getFirstName() + " "+ user.getLastName(), organization.getName());
        
        if (organization.getStripe() != null) {
            stripeService.addOneMemberSubscription(organization.getStripe());
        }
        
        return ResponseEntity.ok(new MemberCreateResponseDTO(memberService.create(newMember)));
	}
}
