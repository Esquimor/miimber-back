package com.miimber.back.core.dto;

import javax.validation.constraints.NotNull;

import com.miimber.back.core.enums.LangEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvitationValidatedDTO {
	@NotNull
	private Long id;
	@NotNull
	private String token;
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@NotNull
	private String password;
	@NotNull
	private LangEnum lang;
}
