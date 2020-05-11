package com.miimber.back.organization.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.miimber.back.organization.model.Organization;
import com.miimber.back.organization.model.enums.RoleEnum;
import com.miimber.back.user.model.User;

public interface OrganizationRepository extends CrudRepository<Organization, Long> {
	
	List<Organization> findByMembersUserAndMembersRoleIn(User user, List<RoleEnum> roles);
	
	List<Organization> findByMembersUser(User user);
	
	Organization findByName(String name);
	
	Organization findByStripe(String stripe);
}
