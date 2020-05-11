package com.miimber.back.user.dto;

import java.time.OffsetDateTime;

import com.miimber.back.session.model.Session;

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
		this.setId(session.getId());
		this.setTitle(session.getTitle());
		this.setStart(session.getStart());
		this.setEnd(session.getEnd());
		this.setTypeSessionName(session.getTypeSession().getName());
		this.setOrganizationName(session.getOrganization().getName());
		this.setLimit(session.getLimit());
		this.setNbRegistereds(session.getRegistereds().size());
	}
}
