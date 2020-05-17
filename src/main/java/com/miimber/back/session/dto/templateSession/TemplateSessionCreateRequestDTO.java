package com.miimber.back.session.dto.templateSession;

import java.time.OffsetTime;

import javax.validation.constraints.NotNull;

import com.miimber.back.session.enumItem.PeriodicityEnum;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TemplateSessionCreateRequestDTO {

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
	
	private OffsetTime startHour;
	private OffsetTime endHour;
}
