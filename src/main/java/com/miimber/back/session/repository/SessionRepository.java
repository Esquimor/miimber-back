package com.miimber.back.session.repository;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.miimber.back.organization.model.enums.StateOrganizationEnum;
import com.miimber.back.session.model.Session;
import com.miimber.back.user.model.User;

public interface SessionRepository extends CrudRepository<Session, Long>  {

	List<Session> findByOrganizationIdAndStartBetween(long id, OffsetDateTime min, OffsetDateTime max);
	
	List<Session> findByOrganizationMembersUserAndStartBetweenAndOrganizationState(User user, OffsetDateTime min, OffsetDateTime max, StateOrganizationEnum state);
}
