package com.miimber.back.organization.dto.organization;

import com.miimber.back.organization.model.Organization;
import com.miimber.back.organization.model.enums.StateOrganizationEnum;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrganizationCreateReadUpdateResponseDTO {

	private long id;
	private String name;
	private StateOrganizationEnum state;
	
	public OrganizationCreateReadUpdateResponseDTO(Organization organization) {
		this.setId(organization.getId());
		this.setName(organization.getName());
		this.state = organization.getState();
	}
}
