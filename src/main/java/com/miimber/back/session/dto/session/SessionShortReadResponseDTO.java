package com.miimber.back.session.dto.session;

import java.time.OffsetDateTime;

import com.miimber.back.session.model.Session;
import com.miimber.back.session.model.TypeSession;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SessionShortReadResponseDTO {

	public long id;
	private String title;
	private String description;
	private OffsetDateTime start;
	private OffsetDateTime end;
	private long organizationId;
	private long templateSessionId;
	private TypeSessionDTO typeSession;
	private int limit;
	private int nbRegistereds;
	
	public SessionShortReadResponseDTO(Session session) {
		this.id = session.getId();
		this.title = session.getTitle();
		this.description = session.getDescription();
		this.start = session.getStart();
		this.end = session.getEnd();
		this.organizationId = session.getOrganization().getId();
		this.typeSession = new TypeSessionDTO(session.getTypeSession());
		this.setLimit(session.getLimit());
		if (session.getRegistereds() == null) {
			this.setNbRegistereds(0);
		} else {
			this.setNbRegistereds(session.getRegistereds().size());
		}
	}

	@Getter @Setter
	private class TypeSessionDTO {

		private long id;
		private String name;
		
		public TypeSessionDTO(TypeSession typeSession) {
			this.id = typeSession.getId();
			this.name = typeSession.getName();
		}
	}
}
