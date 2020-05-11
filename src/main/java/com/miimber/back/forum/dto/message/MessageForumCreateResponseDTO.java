package com.miimber.back.forum.dto.message;

import java.time.OffsetDateTime;

import com.miimber.back.forum.model.MessageForum;
import com.miimber.back.user.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MessageForumCreateResponseDTO {

	private Long id;
	private String message;
	private OffsetDateTime dateMessage;
	private UserDTO user;
	
	public MessageForumCreateResponseDTO(MessageForum message) {
		this.id = message.getId();
		this.message = message.getMessage();
		this.dateMessage = message.getDateMessage();
		this.user = new UserDTO(message.getUser());
	}
	
	@Getter @Setter
	private class UserDTO {
		
		private Long id;
		private String firstName;
		private String lastName;
		private String email;
		
		public UserDTO(User user) {
			this.id = user.getId();
			this.firstName = user.getFirstName();
			this.lastName = user.getLastName();
			this.email = user.getEmail();
		}
	}
}
