package com.miimber.back.organization.dto.organization;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrganizationStatisticRequestDTO {

	private LocalDate start;
	private LocalDate end;
	private Boolean all;
	private Boolean unique;
	private List<Long> template;
}
