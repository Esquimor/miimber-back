package com.miimber.back.session.dto.session;

import java.util.List;

import com.miimber.back.session.dto.templateSession.TemplateSessionTemplateCreateReadResponseDTO;
import com.miimber.back.session.model.TemplateSession;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SessionCreateResponseDTO {

	public List<SessionShortReadResponseDTO> sessions;
	public TemplateSessionTemplateCreateReadResponseDTO templateSession;

	public SessionCreateResponseDTO(List<SessionShortReadResponseDTO> sessions, TemplateSession templateSession) {
		this.sessions = sessions;
		this.templateSession = new TemplateSessionTemplateCreateReadResponseDTO(templateSession);
	}
}
