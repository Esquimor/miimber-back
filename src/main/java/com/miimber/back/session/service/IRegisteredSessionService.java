package com.miimber.back.session.service;

import java.time.OffsetDateTime;
import java.util.List;

import com.miimber.back.core.service.TemplateService;
import com.miimber.back.session.model.RegisteredSession;
import com.miimber.back.session.model.Session;

public interface IRegisteredSessionService extends TemplateService<RegisteredSession> {
	
	Long countRegisteredBySession(Session session);
	
	Long countRegisteredBySessionAndBefore(Session session, OffsetDateTime dateToCompare);
	
	RegisteredSession getNextRegistered(Session session, OffsetDateTime dateBeforeNext);
	
	List<RegisteredSession> findRegisteredBySessionBetweenLimit(Session session, int min, int max);
}
