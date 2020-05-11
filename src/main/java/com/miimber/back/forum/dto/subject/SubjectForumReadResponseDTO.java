package com.miimber.back.forum.dto.subject;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.miimber.back.forum.model.CategoryForum;
import com.miimber.back.forum.model.TalkForum;
import com.miimber.back.forum.model.SubjectForum;
import com.miimber.back.user.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SubjectForumReadResponseDTO {

	private Long id;
	private String title;
	private CategoryDTO category;
	private List<TalkDTO> talks;
	
	public SubjectForumReadResponseDTO(SubjectForum subject) {
		this.id = subject.getId();
		this.title = subject.getTitle();
		this.category = new CategoryDTO(subject.getCategory());
		this.talks = new ArrayList<TalkDTO>();
		for(TalkForum talk: subject.getPosts()) {
			this.talks.add(new TalkDTO(talk));
		}
	}
	
	@Getter @Setter
	private class CategoryDTO {
		
		private Long id;
		private String title;
		
		public CategoryDTO(CategoryForum category) {
			this.id = category.getId();
			this.title = category.getTitle();
		}
	}
	
	@Getter @Setter
	private class TalkDTO {
		
		private Long id;
		private String title;
		private OffsetDateTime dateTalk;
		private UserDTO user;
		
		public TalkDTO(TalkForum talk) {
			this.id = talk.getId();
			this.title = talk.getTitle();
			this.dateTalk = talk.getDateTalk();
			this.user = new UserDTO(talk.getUser());
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
}
