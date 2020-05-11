package com.miimber.back.organization.dto.organization;

import com.miimber.back.organization.model.Member;
import com.miimber.back.organization.model.enums.RoleEnum;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrganizationMembersReadResponseDTO {

	private long id;
	private RoleEnum role;
	private String firstName;
	private String lastName;
	private String email;
	private long userId;
	
	public OrganizationMembersReadResponseDTO(Member member) {
		this.setId(member.getId());
		this.setRole(member.getRole());
		this.setFirstName(member.getUser().getFirstName());
		this.setLastName(member.getUser().getLastName());
		this.setEmail(member.getUser().getEmail());
		this.setUserId(member.getUser().getId());
	}
}
