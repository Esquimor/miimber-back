package com.miimber.back.organization.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miimber.back.organization.model.Organization;
import com.miimber.back.organization.model.enums.RoleEnum;
import com.miimber.back.organization.repository.OrganizationRepository;
import com.miimber.back.user.model.User;

@Service
public class OrganizationService implements IOrganizationService {

	@Autowired
	private OrganizationRepository organizationRepository;
	
	@Override
	public List<Organization> getOrganizationEditable(User user) {
		List<RoleEnum> editableRoles = new ArrayList<RoleEnum>();
		editableRoles.add(RoleEnum.OWNER);
		editableRoles.add(RoleEnum.OFFICE);
		editableRoles.add(RoleEnum.OFFICE_INSTRUCTOR);
		return organizationRepository.findByMembersUserAndMembersRoleIn(user, editableRoles);
	}

	@Override
	public List<Organization> getOrganizationByUser(User user) {
		return organizationRepository.findByMembersUser(user);
	}


	@Override
	public Organization getOrganizationByName(String name) {
		return organizationRepository.findByName(name);
	}

	@Override
	public Organization create(Organization entity) {
		return organizationRepository.save(entity);
	}

	@Override
	public Organization update(Organization entity) {
		return organizationRepository.save(entity);
	}

	@Override
	public void delete(Organization entity) {
		organizationRepository.delete(entity);
	}

	@Override
	public Organization get(Long id) {
		Optional<Organization> organization = organizationRepository.findById(id);
		if (organization.isEmpty()) {
			return null;
		}
		return organization.get();
	}

	@Override
	public Organization getOrganizationByStripe(String stripe) {
		return organizationRepository.findByStripe(stripe);
	}
}
