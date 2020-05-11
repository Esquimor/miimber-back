package com.miimber.back.session.dto.typeSession;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TypeSessionNameUpdateRequestDTO {

	@NotNull
	private String name;
}
