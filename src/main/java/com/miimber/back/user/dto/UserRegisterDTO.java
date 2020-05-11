package com.miimber.back.user.dto;

import javax.validation.constraints.NotNull;

import com.miimber.back.core.enums.LangEnum;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserRegisterDTO {

	@NotNull
	private String email;
	@NotNull
	private String password;
	@NotNull
	private String lastName;
	@NotNull
	private String firstName;
	@NotNull
	private LangEnum lang;
}
