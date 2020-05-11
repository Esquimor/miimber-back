package com.miimber.back.organization.dto.organization;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrganizationCreateRequestDTO {

	@NotNull
	private String name;
	@NotNull
	private String tokenId;
	@NotNull
	private String subscription;
}
