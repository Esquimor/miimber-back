package com.miimber.back.session.dto.attendee;

import java.time.OffsetDateTime;

import com.miimber.back.session.model.AttendeeSession;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AttendeeSessionCreateResponseDTO {

	private long id;
	private OffsetDateTime dateChecked;
	private long userId;
	private long sessionId;
	
	public AttendeeSessionCreateResponseDTO(AttendeeSession attendee) {
		this.setId(attendee.getId());
		this.setDateChecked(attendee.getDateCheck());
		this.setUserId(attendee.getUser().getId());
		this.setSessionId(attendee.getSession().getId());
	}
}
