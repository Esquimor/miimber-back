package com.miimber.back.session.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.miimber.back.session.model.AttendeeSession;
import com.miimber.back.session.repository.custom.StatQuery;

public interface AttendeeSessionRepository extends CrudRepository<AttendeeSession, Long>{

    @Query(value = "select count(*) as count, sessions.id as id, sessions.template_session_id as templateSessionId, sessions.session_date as date, "+
    		"template_sessions.recurrency as recurrency, " +
    		"template_sessions.start_hour as startHour, template_sessions.end_hour as endHour " +
    		"from attendees " + 
    		"INNER JOIN sessions ON sessions.id = attendees.session_id " + 
    		"INNER JOIN template_sessions ON template_sessions.id = sessions.template_session_id " + 
    		"where template_sessions.id in :templateSessionId "+
    		"and sessions.organization_id = :idOrg " +
    		"and template_sessions.recurrency = M " +
    		"and sessions.session_date between :start and :end " +
    		"group by sessions.id, template_sessions.id", nativeQuery = true)
    List<StatQuery> findStatByTempalteSessionIdAndOrgId(@Param("templateSessionId") List<Long> ids, @Param("idOrg") Long id, @Param("start") LocalDate start, @Param("end") LocalDate end);
    
    @Query(value = "select count(*) as count, sessions.id as id, sessions.template_session_id as templateSessionId, sessions.session_date as date, "+
    		"template_sessions.recurrency as recurrency, " + 
    		"template_sessions.start_hour as startHour, template_sessions.end_hour as endHour " +
    		"from attendees " + 
    		"INNER JOIN sessions ON sessions.id = attendees.session_id " + 
    		"INNER JOIN template_sessions ON template_sessions.id = sessions.template_session_id " + 
    		"where sessions.organization_id = :idOrg " +
    		"and template_sessions.recurrency = O " +
    		"and sessions.session_date between :start and :end " +
    		"group by sessions.id, template_sessions.id", nativeQuery = true)
    List<StatQuery> findStatByTempalteSessionRecurrencyOneAndOrgId(@Param("idOrg") Long id, @Param("start") LocalDate start, @Param("end") LocalDate end);
    

    @Query(value = "select count(*) as count, sessions.id as id, sessions.template_session_id as templateSessionId, sessions.session_date as date, "+
    		"template_sessions.recurrency as recurrency, " + 
    		"template_sessions.start_hour as startHour, template_sessions.end_hour as endHour " +
    		"from attendees " + 
    		"INNER JOIN sessions ON sessions.id = attendees.session_id " + 
    		"INNER JOIN template_sessions ON template_sessions.id = sessions.template_session_id " + 
    		"where sessions.organization_id = :idOrg " + 
    		"and sessions.session_date between :start and :end " +
    		"group by sessions.id, template_sessions.id", nativeQuery = true)
    List<StatQuery> findStatByOrgId(@Param("idOrg") Long id, @Param("start") LocalDate start, @Param("end") LocalDate end);
}