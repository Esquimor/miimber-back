package com.miimber.back.session.service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

import com.miimber.back.core.service.TemplateService;
import com.miimber.back.session.model.Session;
import com.miimber.back.user.model.User;

public interface ISessionService extends TemplateService<Session> {
	
	List<Session> getSessionByOrganizationId(long id, LocalDate min, LocalDate max);
	
	List<Session> getSessionByUserAndDate(User user, LocalDate min, LocalDate max);
}
