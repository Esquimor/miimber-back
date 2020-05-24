package com.miimber.back.organization.controller.organization;

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
import com.miimber.back.organization.dto.organization.OrganizationStatisticRequestDTO;
import com.miimber.back.organization.model.Member;
import com.miimber.back.organization.service.MemberService;
import com.miimber.back.organization.service.OrganizationService;
import com.miimber.back.session.service.AttendeeSessionService;
import com.miimber.back.session.service.SessionService;
import com.miimber.back.user.model.User;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrganizationStatisticController {

	@Autowired
	private Helper helper;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private AttendeeSessionService attendeeService;
	
	@RequestMapping(value = "/organization/{id}/statistic", method = RequestMethod.POST)
	public ResponseEntity<?> statisticOrganization(@PathVariable Long id, @RequestBody OrganizationStatisticRequestDTO statisticDto) throws Exception {
        User user = helper.getUserToken((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        Member memberUser = memberService.getMemberByOrganizationIdAndByUser(id, user);
        if (memberUser == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (!memberUser.canEditOrganization()) {
        	return new ResponseEntity(HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok(attendeeService.getAllStats(id, statisticDto.getStart(), statisticDto.getEnd()));
	}
}
