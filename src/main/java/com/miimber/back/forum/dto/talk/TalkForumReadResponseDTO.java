package com.miimber.back.forum.dto.talk;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.miimber.back.forum.model.MessageForum;
import com.miimber.back.forum.model.TalkForum;
import com.miimber.back.user.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TalkForumReadResponseDTO {

	private Long id;
	private String title;
	private OffsetDateTime dateTalk;
	private UserDTO user;
	private List<MessageDTO> messages;
	
	public TalkForumReadResponseDTO(TalkForum talk) {
		this.id = talk.getId();
		this.title = talk.getTitle();
		this.dateTalk = talk.getDateTalk();
		this.user = new UserDTO(talk.getUser());
		this.messages = new ArrayList<MessageDTO>();
		for(MessageForum message: talk.getMessages()) {
			this.messages.add(new MessageDTO(message));
		}
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
	
	@Getter @Setter
	private class MessageDTO {
		private Long id;
		private String message;
		private OffsetDateTime dateMessage;
		private UserDTO user;
		
		public MessageDTO(MessageForum message) {
			this.id = message.getId();
			this.message = message.getMessage();
			this.dateMessage = message.getDateMessage();
			this.user = new UserDTO(message.getUser());
		}
	}
}
