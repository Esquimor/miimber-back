package com.miimber.back.core.dto;

import javax.validation.constraints.NotNull;

import com.miimber.back.core.enums.LangEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordForgottenDTO {

	@NotNull
	private String email;
	@NotNull
	private LangEnum lang;
}
