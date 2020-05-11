package com.miimber.back.organization.dto.organization;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrganizationTokenRequestDTO {

	@NotNull
	private String token;
}
