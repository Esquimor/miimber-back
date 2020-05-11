package com.miimber.back.session.dto.comment;

import java.time.OffsetDateTime;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentSessionUpdateRequestDTO {

	@NotNull
	private Long id;
	@NotNull
	private String comment;
	@NotNull
	private OffsetDateTime dateComment;
}
