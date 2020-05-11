package com.miimber.back.session.enumItem;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class DayConverter implements AttributeConverter<DayEnum, String> {

	@Override
	public String convertToDatabaseColumn(DayEnum day) {
        if (day == null) {
            return null;
        }
        return day.getDay();
	}

	@Override
	public DayEnum convertToEntityAttribute(String day) {
		if (day == null) {
        return null;
		}

	    return Stream.of(DayEnum.values())
	      .filter(c -> c.getDay().equals(day))
	      .findFirst()
	      .orElseThrow(IllegalArgumentException::new);
	}
}
