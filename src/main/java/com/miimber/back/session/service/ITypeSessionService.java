package com.miimber.back.session.service;

import java.util.List;

import com.miimber.back.core.service.TemplateService;
import com.miimber.back.session.model.TypeSession;

public interface ITypeSessionService extends TemplateService<TypeSession> {
	
	List<TypeSession> getTypeSessionByOrganizationId(long id);
}
