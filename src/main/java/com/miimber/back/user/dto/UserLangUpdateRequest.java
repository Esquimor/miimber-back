package com.miimber.back.user.dto;

import javax.validation.constraints.NotNull;

import com.miimber.back.core.enums.LangEnum;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserLangUpdateRequest {

	@NotNull
	private LangEnum lang;
}
