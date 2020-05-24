package com.miimber.back.session.repository.custom;

import java.time.LocalDate;
import java.time.LocalTime;

public interface StatQuery {

	Long getCount();
    Long getId();
    Long getTemplateSessionId();
    LocalDate getDate();
    String getRecurrency();
	LocalTime getStartHour();
	LocalTime getEndHour();
}
