package com.miimber.back.organization.dto.member;

import javax.validation.constraints.NotNull;

import com.miimber.back.core.enums.LangEnum;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberAndUserCreateRequestDTO extends MemberUpdateRequestDTO {

	@NotNull
	private Long idOrganization;
	
	@NotNull
	private String email;
	
	@NotNull
	private String firstName;
	
	@NotNull
	private String lastName;
	
	@NotNull
	private LangEnum lang;
}
