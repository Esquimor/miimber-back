package com.miimber.back.organization.controller.member;

import java.util.function.Predicate;
import java.util.stream.Collectors;

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
import com.miimber.back.core.helper.StripeService;
import com.miimber.back.organization.dto.member.MemberCreateRequestDTO;
import com.miimber.back.organization.dto.member.MemberCreateResponseDTO;
import com.miimber.back.organization.dto.member.MemberReadUpdateResponseDTO;
import com.miimber.back.organization.dto.member.MemberUpdateRequestDTO;
import com.miimber.back.organization.model.Member;
import com.miimber.back.organization.model.Organization;
import com.miimber.back.organization.model.enums.RoleEnum;
import com.miimber.back.organization.service.MemberService;
import com.miimber.back.organization.service.OrganizationService;
import com.miimber.back.user.model.User;
import com.miimber.back.user.service.UserService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MemberController {
	
	@Autowired
	private Helper helper;

	@Autowired
	private StripeService stripeService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrganizationService organizationService;

	@RequestMapping(value = "/member/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateRole(@RequestBody MemberUpdateRequestDTO memberDto, @PathVariable Long id) throws Exception {
        User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        Member memberToEdit = memberService.get(id);
        if (memberToEdit == null ) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        Member member = memberService.getMemberByOrganizationAndByUser(memberToEdit.getOrganization(), user);
        // If member can't edit organization Send UnAuthorized
        if (!member.canEditOrganization()) {
        	return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        // If member isn't a owner and try to put owner to other member
        if (member.getRole() != RoleEnum.OWNER && memberDto.getRole() == RoleEnum.OWNER) {
        	return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        // If memberToEdit is a owner and member isn't.
        if (memberToEdit.getRole() == RoleEnum.OWNER && member.getRole() != RoleEnum.OWNER) {
        	return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        // Keep at least one owner by organization
        if (memberDto.getRole() != RoleEnum.OWNER && member.getId() == memberToEdit.getId()) {
            Predicate<Member> byType = m -> m.getRole() == RoleEnum.OWNER;
            int numberOwners = member.getOrganization().getMembers().stream().filter(byType)
                    .collect(Collectors.toList()).size();
            System.out.print(numberOwners);
            if (numberOwners == 1) {
            	return new ResponseEntity(HttpStatus.CONFLICT);
            }
        }
        memberToEdit.setRole(memberDto.getRole());
        return ResponseEntity.ok(new MemberReadUpdateResponseDTO(memberService.update(memberToEdit)));
	}
	
	@RequestMapping(value = "/member/", method = RequestMethod.POST)
	public ResponseEntity<?> createMember(@RequestBody MemberCreateRequestDTO memberDto) throws Exception {
        User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        Organization organization = organizationService.get(memberDto.getIdOrganization());
        if (organization == null) {
        	System.out.print("organization");
        	return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        
        Member memberUser = memberService.getMemberByOrganizationAndByUser(organization, user);
        if (memberUser == null ) {
        	System.out.print("Member");
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        
        if (!memberUser.canEditOrganization()) {
        	return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        
        User userToMember = userService.get(memberDto.getIdUser());
        if (userToMember == null ) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        Member alreadyExistMember = memberService.getMemberByOrganizationAndByUser(organization, userToMember);
        if (alreadyExistMember != null) {
        	return new ResponseEntity(HttpStatus.CONFLICT);
        }
        
        Member newMember = new Member();
        newMember.setUser(userToMember);
        newMember.setOrganization(organization);
        
        if (organization.getStripe() != null) {
            stripeService.addOneMemberSubscription(organization.getStripe());
        }
        
        return ResponseEntity.ok(new MemberCreateResponseDTO(memberService.create(newMember)));
	}

	@RequestMapping(value = "/member/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteMember(@PathVariable Long id) throws Exception {
        User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        Member member = memberService.get(id);
        if (member == null ) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        
        if (member.getOrganization().getMembers().size() == 1) {
        	return new ResponseEntity(HttpStatus.CONFLICT);
        }
        
        Member memberUser = memberService.getMemberByOrganizationAndByUser(member.getOrganization(), user);
        if (memberUser == null ) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        
        if (!memberUser.canEditOrganization()) {
        	return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        // Can't delete a owner, if not owner
        if (member.getRole() == RoleEnum.OWNER && memberUser.getRole() != RoleEnum.OWNER) {
        	return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        if (member.getOrganization().getStripe() != null) {
            stripeService.removeOneMemberSubscription(member.getOrganization().getStripe());
        }
        
        memberService.delete(member);
        return new ResponseEntity(HttpStatus.OK);
	}
}
