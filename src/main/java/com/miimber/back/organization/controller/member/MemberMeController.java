package com.miimber.back.organization.controller.member;

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
import com.miimber.back.organization.dto.member.MemberReadUpdateResponseDTO;
import com.miimber.back.organization.model.Member;
import com.miimber.back.organization.service.MemberService;
import com.miimber.back.user.model.User;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MemberMeController {
	
	@Autowired
	private Helper helper;
	
	@Autowired
	private MemberService memberService;

	
	@RequestMapping(value = "member/me/{id}", method = RequestMethod.GET) 
	public ResponseEntity<?> readMemberMe(@PathVariable Long id) throws Exception {
        User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Member member = memberService.getMemberByOrganizationIdAndByUser(id, user);
        if (member == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(new MemberReadUpdateResponseDTO(member));
	}
}
