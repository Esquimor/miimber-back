package com.miimber.back.forum.dto.category;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryForumMultipleUpdateRequestDTO {

	@NotNull
	private List<CategoryForumTemplateUpdateRequestDTO> categories;
}
