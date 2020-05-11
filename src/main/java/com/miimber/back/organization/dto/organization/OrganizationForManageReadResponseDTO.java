package com.miimber.back.organization.dto.organization;

import java.util.ArrayList;
import java.util.List;

import com.miimber.back.organization.model.Member;
import com.miimber.back.organization.model.Organization;
import com.miimber.back.organization.model.enums.RoleEnum;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrganizationForManageReadResponseDTO extends OrganizationCreateReadUpdateResponseDTO {

	private List<MemberDTO> members;
	private String plan;
	private Boolean isPayed;
	private Boolean isFree;
	
	public OrganizationForManageReadResponseDTO(Organization organization) {
		super(organization);
		this.setMembers(convertMembersDTO(organization.getMembers()));
        this.isFree = organization.getStripe() == null;
        if (organization.isActif()) {
        	this.isPayed = true;
        } else {
        	this.isPayed = false;
        }
	}
	
	private List<MemberDTO> convertMembersDTO(List<Member> members) {
		List<MemberDTO> membersConverted = new ArrayList<MemberDTO>();
		for (Member member : members) 
        { 
			membersConverted.add(convertMemberDTO(member));
        }
		return membersConverted;
	}
	
	private MemberDTO convertMemberDTO(Member member) {
		return new MemberDTO(
				member.getId(),
				member.getUser().getLastName(),
				member.getUser().getFirstName(),
				member.getRole()
				);
	}

	@Getter @Setter
	private class MemberDTO {
		
		private Long id;
		private String lastName;
		private String firstName;
		private RoleEnum role;
		
		public MemberDTO(Long id, String lastName, String firstName, RoleEnum role) {
			this.id = id;
			this.lastName = lastName;
			this.firstName = firstName;
			this.role = role;
		}
	}
}
