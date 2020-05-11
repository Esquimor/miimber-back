package com.miimber.back.session.dto.comment;

import java.time.OffsetDateTime;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentSessionCreateRequestDTO {

	@NotNull
	private String comment;
	@NotNull
	private OffsetDateTime dateComment;
	@NotNull
	private Long sessionId;
	private Long commentParentId;
}
