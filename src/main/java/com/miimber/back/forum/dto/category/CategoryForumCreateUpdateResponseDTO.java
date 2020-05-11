package com.miimber.back.forum.dto.category;

import com.miimber.back.forum.model.CategoryForum;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryForumCreateUpdateResponseDTO {

	private Long id;
	private String title;
	private Integer position;
	
	public CategoryForumCreateUpdateResponseDTO(CategoryForum category) {
		this.id = category.getId();
		this.title = category.getTitle();
		this.position = category.getPosition();
	}
}
