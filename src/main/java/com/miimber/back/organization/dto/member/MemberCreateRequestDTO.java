package com.miimber.back.organization.dto.member;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberCreateRequestDTO extends MemberUpdateRequestDTO {

	@NotNull
	private Long idOrganization;
	
	@NotNull
	private Long idUser;
}
