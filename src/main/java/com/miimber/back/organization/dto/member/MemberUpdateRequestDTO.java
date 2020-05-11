package com.miimber.back.organization.dto.member;

import javax.validation.constraints.NotNull;

import com.miimber.back.organization.model.enums.RoleEnum;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberUpdateRequestDTO {

	@NotNull
	private RoleEnum role;
}
