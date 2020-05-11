package com.miimber.back.user.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserUpdateRequestDTO {

	@NotNull
	private long id;
	@NotNull
	private String email;
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
}
