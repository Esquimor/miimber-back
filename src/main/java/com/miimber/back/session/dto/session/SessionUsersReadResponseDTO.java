package com.miimber.back.session.dto.session;

import java.time.OffsetDateTime;
import java.util.List;

import com.miimber.back.session.model.CommentSession;
import com.miimber.back.session.model.Session;
import com.miimber.back.user.dto.TemplateAttendeeDTO;
import com.miimber.back.user.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SessionUsersReadResponseDTO {
		
	private long id;
	private String title;
	private List<TemplateAttendeeDTO> users;
	
	public SessionUsersReadResponseDTO(Session session, List<TemplateAttendeeDTO> users) {
		this.setId(session.getId());
		this.setTitle(session.getTitle());
		this.setUsers(users);
	}
}
