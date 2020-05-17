package com.miimber.back.session.dto.session;

import java.time.LocalDate;
import java.time.OffsetTime;
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
	private PeriodicityEnum periodicity;
	@NotNull
	private Integer day;
	@NotNull
	private long typeSessionId;
	@NotNull
	private long organizationId;
	@NotNull
	private int limit;
	
	private LocalDate sessionDate;
	
	private OffsetTime startHour;
	private OffsetTime endHour;
	
	private List<SessionPeriodDTO> periods;
}
