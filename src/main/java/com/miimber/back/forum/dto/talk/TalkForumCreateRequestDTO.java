package com.miimber.back.forum.dto.talk;

import java.time.OffsetDateTime;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TalkForumCreateRequestDTO {

	@NotNull
	private String title;
	@NotNull
	private Long idSubject;
	@NotNull
	private String message;
}
