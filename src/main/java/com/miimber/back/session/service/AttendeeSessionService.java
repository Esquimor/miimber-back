package com.miimber.back.session.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miimber.back.session.model.AttendeeSession;
import com.miimber.back.session.repository.AttendeeSessionRepository;
import com.miimber.back.session.repository.custom.StatQuery;

@Service
public class AttendeeSessionService implements IAttendeeSessionService {

	@Autowired
	private AttendeeSessionRepository attendeeRepository;

	@Override
	public AttendeeSession create(AttendeeSession entity) {
		return attendeeRepository.save(entity);
	}

	@Override
	public AttendeeSession update(AttendeeSession entity) {
		return attendeeRepository.save(entity);
	}

	@Override
	public void delete(AttendeeSession entity) {
		attendeeRepository.delete(entity);
	}

	@Override
	public AttendeeSession get(Long id) {
		Optional<AttendeeSession> attendee = attendeeRepository.findById(id);
		if (attendee.isEmpty()) {
			return null;
		}
		return attendee.get();
	}

	@Override
	public List<StatQuery> getAllStats(Long id, LocalDate start, LocalDate end) {
		return attendeeRepository.findStatByOrgId(id, start, end);
	}

}
