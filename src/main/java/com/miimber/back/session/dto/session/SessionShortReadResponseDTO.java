package com.miimber.back.session.dto.session;

import java.time.OffsetDateTime;

import com.miimber.back.session.model.Session;
import com.miimber.back.session.model.TemplateSession;
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
		TemplateSession templateSession = session.getTemplateSession();
		this.id = session.getId();
		this.title = templateSession.getTitle();
		this.description = templateSession.getDescription();
		this.start = session.getStartDate();
		this.end = session.getEndDate();
		this.organizationId = session.getOrganization().getId();
		this.typeSession = new TypeSessionDTO(templateSession.getTypeSession());
		this.setLimit(templateSession.getLimit());
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
