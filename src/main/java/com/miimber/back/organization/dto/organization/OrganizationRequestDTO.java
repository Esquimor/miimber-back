package com.miimber.back.organization.dto.organization;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrganizationRequestDTO {

	@NotNull
	private String name;
}
