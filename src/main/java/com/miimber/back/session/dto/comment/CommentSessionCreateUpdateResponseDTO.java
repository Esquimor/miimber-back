package com.miimber.back.session.dto.comment;

import java.time.OffsetDateTime;

import com.miimber.back.session.model.CommentSession;
import com.miimber.back.user.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentSessionCreateUpdateResponseDTO {
	
	private Long id;
	private String comment;
	private OffsetDateTime dateComment;
	private UserDTO user;
	
	public CommentSessionCreateUpdateResponseDTO(CommentSession comment) {
		this.id = comment.getId();
		this.comment = comment.getComment();
		this.dateComment = comment.getDateComment();
		this.user = new UserDTO(comment.getUser());
	}
	
	@Getter @Setter
	private class UserDTO {
	
		private Long id;
		private String firstName;
		private String lastName;
		private String email;
		
		public UserDTO(User user) {
			this.id = user.getId();
			this.firstName= user.getFirstName();
			this.lastName = user.getLastName();
			this.email = user.getEmail();
		}
	}
}
