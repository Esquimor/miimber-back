package com.miimber.back.session.dto.session;

import java.util.List;

import com.miimber.back.session.model.Session;
import com.miimber.back.user.dto.TemplateAttendeeDTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SessionUsersReadResponseDTO {
		
	private long id;
	private String title;
	private List<TemplateAttendeeDTO> users;
	
	public SessionUsersReadResponseDTO(Session session, List<TemplateAttendeeDTO> users) {
		this.setId(session.getId());
		this.setTitle(session.getTemplateSession().getTitle());
		this.setUsers(users);
	}
}
