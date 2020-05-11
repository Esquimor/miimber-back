package com.miimber.back.user.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserPasswordUpdateRequestDTO {

	@NotNull
	private String oldPassword;
	@NotNull
	private String newPassword;
}
