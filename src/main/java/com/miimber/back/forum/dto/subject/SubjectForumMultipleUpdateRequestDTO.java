package com.miimber.back.forum.dto.subject;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SubjectForumMultipleUpdateRequestDTO {

	@NotNull
	private List<SubjectForumTemplateUpdateRequestDTO> subjects;
}
