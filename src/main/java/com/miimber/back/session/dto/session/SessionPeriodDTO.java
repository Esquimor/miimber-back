package com.miimber.back.session.dto.session;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SessionPeriodDTO {
	@NotNull
	private LocalDate start;
	@NotNull
	private LocalDate end;
	
	public Boolean isPeriodValid() {
		if (this.start == null) return false;
		if (this.end == null) return false;
		if (this.start.isAfter(this.end)) return false;
		return true;
	}
}
