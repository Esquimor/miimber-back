package com.miimber.back.organization.dto.member;

import javax.validation.constraints.NotNull;

import com.miimber.back.organization.model.Member;
import com.miimber.back.organization.model.enums.RoleEnum;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberReadUpdateResponseDTO extends MemberUpdateRequestDTO {
	
	@NotNull
	private Long id;
	
	@NotNull
	private Long idUser;
	
	@NotNull
	private Long idOrganization;
	
	public MemberReadUpdateResponseDTO(Member member) {
		this.setRole(member.getRole());
		this.id = member.getId();
		this.idUser = member.getUser().getId();
		this.idOrganization = member.getOrganization().getId();
	}
	
	public MemberReadUpdateResponseDTO(RoleEnum role, Long id, Long idUser, Long idOrganization) {
		this.setRole(role);
		this.id = id;
		this.idUser = idUser;
		this.idOrganization = idOrganization;
	}
}
