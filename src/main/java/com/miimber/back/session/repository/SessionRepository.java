package com.miimber.back.session.repository;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.miimber.back.organization.model.enums.StateOrganizationEnum;
import com.miimber.back.session.model.Session;
import com.miimber.back.user.model.User;

public interface SessionRepository extends CrudRepository<Session, Long>  {

	List<Session> findByOrganizationIdAndSessionDateBetween(long id, LocalDate min, LocalDate max);
	
	List<Session> findByOrganizationMembersUserAndSessionDateBetweenAndOrganizationState(User user, LocalDate min, LocalDate max, StateOrganizationEnum state);
}
