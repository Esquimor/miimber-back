package com.miimber.back.session.dto.session;

import java.time.OffsetDateTime;

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
	private OffsetDateTime start;
	@NotNull
	private OffsetDateTime end;
	@NotNull
	private long typeSessionId;
	@NotNull
	private int limit;
}
