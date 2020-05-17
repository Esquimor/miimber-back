package com.miimber.back.session.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.miimber.back.organization.model.Organization;
import com.miimber.back.session.model.TemplateSession;
import com.miimber.back.session.model.enums.TemplateSessionEnum;
import com.miimber.back.session.model.enums.TemplateSessionStatusEnum;

public interface TemplateSessionRepository extends CrudRepository<TemplateSession, Long>{

	List<TemplateSession> findByRecurrencyAndOrganization(TemplateSessionEnum type, Organization organization);
	
	List<TemplateSession> findByRecurrencyAndStatusAndOrganization(TemplateSessionEnum type, TemplateSessionStatusEnum status, Organization organization);
}
