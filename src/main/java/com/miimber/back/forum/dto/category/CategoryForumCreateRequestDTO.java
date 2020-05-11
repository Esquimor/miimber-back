package com.miimber.back.forum.dto.category;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryForumCreateRequestDTO {

	@NotNull
	private String title;
	@NotNull
	private Integer position;
}
