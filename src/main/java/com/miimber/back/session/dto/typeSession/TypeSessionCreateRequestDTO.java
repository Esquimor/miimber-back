package com.miimber.back.session.dto.typeSession;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TypeSessionCreateRequestDTO {

	@NotNull
	private String name;
	@NotNull
	private long organizationId;
}
