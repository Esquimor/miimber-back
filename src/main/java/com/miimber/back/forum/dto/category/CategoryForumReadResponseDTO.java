package com.miimber.back.forum.dto.category;

import java.util.ArrayList;
import java.util.List;

import com.miimber.back.forum.model.CategoryForum;
import com.miimber.back.forum.model.SubjectForum;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryForumReadResponseDTO {

	private Long id;
	private String title;
	private Integer position;
	private List<SubjectDTO> subjects;
	
	public CategoryForumReadResponseDTO(CategoryForum category) {
		this.id = category.getId();
		this.title = category.getTitle();
		this.position = category.getPosition();
		this.subjects = new ArrayList<SubjectDTO>();
		
		for(SubjectForum subject: category.getSubjects()) {
			this.subjects.add(new SubjectDTO(subject));
		}
	}
	
	@Getter @Setter
	private class SubjectDTO {
		
		private Long id;
		private String title;
		private Integer position;
		
		public SubjectDTO(SubjectForum subject) {
			this.id = subject.getId();
			this.title = subject.getTitle();
			this.position = subject.getPosition();
		}
	}
}
