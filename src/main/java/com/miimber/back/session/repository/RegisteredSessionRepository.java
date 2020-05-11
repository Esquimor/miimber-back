package com.miimber.back.session.repository;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.miimber.back.session.model.RegisteredSession;
import com.miimber.back.session.model.Session;

public interface RegisteredSessionRepository extends CrudRepository<RegisteredSession, Long>, PagingAndSortingRepository<RegisteredSession, Long>{

	Long countBySession(Session session);
	
	Long countRegisteredBySessionAndDateRegisteredBefore(Session session, OffsetDateTime dateToCompare);
	
	RegisteredSession findFirstBySessionAndDateRegisteredAfter(Session session, OffsetDateTime dateBeforeNext);
	
	List<RegisteredSession> findBySession(Session session, Pageable pageable);
}
