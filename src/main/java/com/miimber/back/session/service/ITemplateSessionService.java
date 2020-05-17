package com.miimber.back.session.service;

import java.util.List;

import com.miimber.back.core.service.TemplateService;
import com.miimber.back.organization.model.Organization;
import com.miimber.back.session.model.TemplateSession;

public interface ITemplateSessionService extends TemplateService<TemplateSession> {

	List<TemplateSession> getMultipleByOrganization(Organization organization);
	
	List<TemplateSession> getMultipleByOrganizationAndGoing(Organization organization);
}
