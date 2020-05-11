package com.miimber.back.organization.service;

import java.util.List;

import com.miimber.back.core.service.TemplateService;
import com.miimber.back.organization.model.Organization;
import com.miimber.back.user.model.User;

public interface IOrganizationService extends TemplateService<Organization> {
	List<Organization> getOrganizationEditable(User user);
	
	List<Organization> getOrganizationByUser(User user);
	
	Organization getOrganizationByName(String name);
	
	Organization getOrganizationByStripe(String stripe);
}
