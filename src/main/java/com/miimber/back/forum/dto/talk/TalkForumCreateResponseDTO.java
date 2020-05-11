package com.miimber.back.forum.dto.talk;

import java.time.OffsetDateTime;

import com.miimber.back.forum.model.TalkForum;
import com.miimber.back.user.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TalkForumCreateResponseDTO {

	private Long id;
	private String title;
	private OffsetDateTime datePost;
	private UserDTO user;
	
	public TalkForumCreateResponseDTO(TalkForum post) {
		this.id = post.getId();
		this.title = post.getTitle();
		this.datePost = post.getDateTalk();
		this.user = new UserDTO(post.getUser());
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
