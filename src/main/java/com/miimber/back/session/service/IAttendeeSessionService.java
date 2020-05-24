package com.miimber.back.session.service;

import java.time.LocalDate;
import java.util.List;

import com.miimber.back.core.service.TemplateService;
import com.miimber.back.session.model.AttendeeSession;
import com.miimber.back.session.repository.custom.StatQuery;

public interface IAttendeeSessionService extends TemplateService<AttendeeSession> {
	
	List<StatQuery> getAllStats(Long id, LocalDate start, LocalDate end);
}
