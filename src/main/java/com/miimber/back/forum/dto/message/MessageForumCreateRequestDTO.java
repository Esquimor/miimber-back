package com.miimber.back.forum.dto.message;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MessageForumCreateRequestDTO {

	@NotNull
	private String message;
	@NotNull
	private Long talkId;
}
