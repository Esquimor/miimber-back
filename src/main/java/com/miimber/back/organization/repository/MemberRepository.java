package com.miimber.back.organization.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.miimber.back.organization.model.Member;
import com.miimber.back.organization.model.Organization;
import com.miimber.back.organization.model.enums.RoleEnum;
import com.miimber.back.user.model.User;

public interface MemberRepository extends CrudRepository<Member, Long> {
	Member findOneByOrganizationIdAndUser(Long id, User user);
	
	Member findOneByOrganizationAndUser(Organization organization, User user);
	
	List<Member> findByOrganizationId(Long id);
	
	boolean existsMemberByUserAndOrganization(User user, Organization organization);
	
	Member findFirstByOrganizationAndRole(Organization organization, RoleEnum role);
}
