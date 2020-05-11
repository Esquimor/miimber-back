package com.miimber.back.session.dto.registered;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisteredSessionCreateRequestDTO {

	@NotNull
	private long sessionId;
}
