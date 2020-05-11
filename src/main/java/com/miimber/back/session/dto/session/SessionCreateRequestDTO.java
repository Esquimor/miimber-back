package com.miimber.back.session.dto.session;

import java.time.OffsetDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.miimber.back.session.enumItem.PeriodicityEnum;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SessionCreateRequestDTO {

	@NotNull
	private String title;
	@NotNull
	private String description;
	@NotNull
	private OffsetDateTime start;
	@NotNull
	private OffsetDateTime end;
	@NotNull
	private OffsetDateTime startDate;
	@NotNull
	private OffsetDateTime endDate;
	@NotNull
	private PeriodicityEnum periodicity;
	@NotNull
	private List<Integer> days;
	@NotNull
	private int repeat;
	@NotNull
	private long typeSessionId;
	@NotNull
	private long organizationId;
	@NotNull
	private int limit;
}
