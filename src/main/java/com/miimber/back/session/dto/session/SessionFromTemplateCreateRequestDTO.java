package com.miimber.back.session.dto.session;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SessionFromTemplateCreateRequestDTO {

	@NotNull
	private long organizationId;
	@NotNull
	private long templateId;
	
	private List<SessionPeriodDTO> periods;
}
