package com.miimber.back.forum.dto.category;

import java.util.ArrayList;
import java.util.List;

import com.miimber.back.forum.model.CategoryForum;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryForumCreateUpdateResponseDTO {

	private Long id;
	private String title;
	private Integer position;
	private List<Object> subjects;
	
	public CategoryForumCreateUpdateResponseDTO(CategoryForum category) {
		this.id = category.getId();
		this.title = category.getTitle();
		this.position = category.getPosition();
		this.subjects = new ArrayList<Object>();
	}
}
