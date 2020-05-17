package com.miimber.back.user.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;

import com.miimber.back.session.model.Session;
import com.miimber.back.session.model.TemplateSession;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserSessionReadResponseDTO {

	private long id; 
	private String title;
	private OffsetDateTime start;
	private OffsetDateTime end;
	private String typeSessionName;
	private String organizationName;
	private int limit;
	private int nbRegistereds;
	
	public UserSessionReadResponseDTO(Session session) {
		TemplateSession templateSession = session.getTemplateSession();
		this.setId(session.getId());
		this.setTitle(templateSession.getTitle());
		this.setTypeSessionName(templateSession.getTypeSession().getName());
		this.setOrganizationName(session.getOrganization().getName());
		this.setLimit(templateSession.getLimit());
		this.setNbRegistereds(session.getRegistereds().size());
		this.start = session.getStartDate();
		this.end = session.getEndDate();
	}
}
