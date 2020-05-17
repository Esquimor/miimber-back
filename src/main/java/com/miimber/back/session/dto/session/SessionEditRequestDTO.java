package com.miimber.back.session.dto.session;

import java.time.LocalDate;
import java.time.OffsetTime;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SessionEditRequestDTO {

	@NotNull
	private String title;
	@NotNull
	private String description;
	@NotNull
	private long typeSessionId;
	@NotNull
	private int limit;

	@NotNull
	private OffsetTime start;
	@NotNull
	private OffsetTime end;

	@NotNull
	private LocalDate sessionDate;
}
