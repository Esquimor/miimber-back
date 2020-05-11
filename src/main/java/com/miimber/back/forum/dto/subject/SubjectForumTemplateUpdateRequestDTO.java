package com.miimber.back.forum.dto.subject;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SubjectForumTemplateUpdateRequestDTO {

	@NotNull
	private Long id;
	@NotNull
	private String title;
	@NotNull
	private Integer position;
}
