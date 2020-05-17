package com.miimber.back.organization.controller.organization;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miimber.back.session.dto.session.SessionShortReadResponseDTO;
import com.miimber.back.session.model.Session;
import com.miimber.back.session.service.SessionService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrganizationSessionController {
	
	@Autowired
	private SessionService sessionService;
	
	@RequestMapping(value= "/organization/{id}/session/", method = RequestMethod.GET)
	public ResponseEntity<?> readOrganizationSession(@PathVariable Long id, @RequestParam String minDate, @RequestParam String maxDate) throws Exception {
        List<SessionShortReadResponseDTO> listSessions = new ArrayList<SessionShortReadResponseDTO>();
        
        for (Session session: sessionService.getSessionByOrganizationId(id, LocalDate.parse(minDate), LocalDate.parse(maxDate))) {
        	listSessions.add(new SessionShortReadResponseDTO(session));
        }
        return ResponseEntity.ok(listSessions);
	}

}
