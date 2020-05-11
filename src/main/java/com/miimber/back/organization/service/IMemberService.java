package com.miimber.back.organization.service;

import java.util.List;

import com.miimber.back.core.service.TemplateService;
import com.miimber.back.organization.model.Member;
import com.miimber.back.organization.model.Organization;
import com.miimber.back.user.model.User;

public interface IMemberService extends TemplateService<Member> {

	Member getMemberByOrganizationIdAndByUser(Long id, User user);
	
	List<Member> getMemberByOrganizationId(Long id);
	
	Member getMemberByOrganizationAndByUser(Organization organization, User user);
	
	boolean existsMemberByUserAndOrganization(User user, Organization organization);
	
	Member getFirstMemberOwnerForOrganization(Organization organization);
}
