package com.miimber.back.session.service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miimber.back.organization.model.enums.StateOrganizationEnum;
import com.miimber.back.session.model.Session;
import com.miimber.back.session.repository.SessionRepository;
import com.miimber.back.user.model.User;

@Service
public class SessionService implements ISessionService {

	@Autowired
	private SessionRepository sessionRepository;
	
	@Override
	public List<Session> getSessionByOrganizationId(long id, LocalDate min, LocalDate max) {
		return sessionRepository.findByOrganizationIdAndSessionDateBetween(id, min, max);
	}

	@Override
	public List<Session> getSessionByUserAndDate(User user, LocalDate min, LocalDate max) {
		return sessionRepository.findByOrganizationMembersUserAndSessionDateBetweenAndOrganizationState(user, min, max, StateOrganizationEnum.ACTIVE);
	}

	@Override
	public Session create(Session entity) {
		return sessionRepository.save(entity);
	}

	@Override
	public Session update(Session entity) {
		return sessionRepository.save(entity);
	}

	@Override
	public void delete(Session entity) {
		sessionRepository.delete(entity);
	}

	@Override
	public Session get(Long id) {
		Optional<Session> session = sessionRepository.findById(id);
		if (session.isEmpty()) {
			return null;
		}
		return session.get();
	}

}
