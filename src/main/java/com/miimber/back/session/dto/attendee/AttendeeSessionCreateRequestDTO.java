package com.miimber.back.session.dto.attendee;

import java.time.OffsetDateTime;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AttendeeSessionCreateRequestDTO {

	@NotNull
	private long userId;
	@NotNull
	private long sessionId;
	@NotNull
	private OffsetDateTime dateCheck;
}
