package com.miimber.back.core.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDTO {

	@NotNull
	private Long id;
	
	@NotNull
	private String token;
	
	@NotNull
	private String password;
}
