package com.miimber.back.session.dto.registered;

import java.time.OffsetDateTime;

import com.miimber.back.session.model.RegisteredSession;
import com.miimber.back.session.model.enums.RegisteredEnum;
import com.miimber.back.user.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisteredSessionCreateResponseDTO {

	private long id;
	private OffsetDateTime dateRegistered;
	private long sessionId;
	private boolean isMember;
	private UserDTO user;
	private RegisteredEnum status;
	
	public RegisteredSessionCreateResponseDTO(RegisteredSession registered, boolean isMember, RegisteredEnum status) {
		this.setId(registered.getId());
		this.setDateRegistered(registered.getDateRegistered());
		this.setSessionId(registered.getSession().getId());
		this.setUser(new UserDTO(registered.getUser()));
		this.setMember(isMember);
		this.setStatus(status);
	}

	@Getter @Setter
	private class UserDTO {

		private long id;
		private String firstName;
		private String lastName;
		private String email;
		
		public UserDTO(User user) {
			this.setId(user.getId());
			this.setFirstName(user.getFirstName());
			this.setLastName(user.getLastName());
			this.setEmail(user.getEmail());
		}
	}
}
