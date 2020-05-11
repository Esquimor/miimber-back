package com.miimber.back.session.service;

import java.time.OffsetDateTime;
import java.util.List;

import com.miimber.back.core.service.TemplateService;
import com.miimber.back.session.model.Session;
import com.miimber.back.user.model.User;

public interface ISessionService extends TemplateService<Session> {
	
	List<Session> getSessionByOrganizationId(long id, OffsetDateTime min, OffsetDateTime max);
	
	List<Session> getSessionByUserAndDate(User user, OffsetDateTime min, OffsetDateTime max);
}
