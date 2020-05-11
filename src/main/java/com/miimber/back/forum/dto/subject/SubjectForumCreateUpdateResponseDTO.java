package com.miimber.back.forum.dto.subject;

import com.miimber.back.forum.model.SubjectForum;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SubjectForumCreateUpdateResponseDTO {

	private Long id;
	private String title;
	private Integer position;
	private Long idCategory;
	
	public SubjectForumCreateUpdateResponseDTO(SubjectForum subject) {
		this.id = subject.getId();
		this.title = subject.getTitle();
		this.position = subject.getPosition();
		this.idCategory = subject.getCategory().getId();
	}
}
