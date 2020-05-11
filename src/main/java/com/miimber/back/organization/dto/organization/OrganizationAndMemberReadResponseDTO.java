package com.miimber.back.organization.dto.organization;

import com.miimber.back.organization.model.Member;
import com.miimber.back.organization.model.Organization;
import com.miimber.back.organization.model.enums.RoleEnum;
import com.miimber.back.organization.model.enums.StateOrganizationEnum;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrganizationAndMemberReadResponseDTO {
	
	private long id;
	private String name;
	private StateOrganizationEnum state;
	private UserDTO user;

	public OrganizationAndMemberReadResponseDTO(Organization organization, Member member) {
		this.id = organization.getId();
		this.name = organization.getName();
		this.state = organization.getState();
		if (member == null) {
			this.setUser(null);
		} else {
			this.setUser(new UserDTO(member));
		}
	}

	@Getter @Setter
	private class UserDTO {
		
		private long id;
		private RoleEnum role;
		
		public UserDTO(Member member) {
			this.setId(member.getId());
			this.setRole(member.getRole());
		}
	}
}
