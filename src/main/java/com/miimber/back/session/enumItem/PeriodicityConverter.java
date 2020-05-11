package com.miimber.back.session.enumItem;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PeriodicityConverter implements AttributeConverter<PeriodicityEnum, String> {

	@Override
	public String convertToDatabaseColumn(PeriodicityEnum periodicity) {
        if (periodicity == null) {
            return null;
        }
        return periodicity.getPeriodicity();
	}

	@Override
	public PeriodicityEnum convertToEntityAttribute(String periodicity) {
		if (periodicity == null) {
        return null;
		}

	    return Stream.of(PeriodicityEnum.values())
	      .filter(c -> c.getPeriodicity().equals(periodicity))
	      .findFirst()
	      .orElseThrow(IllegalArgumentException::new);
	}
}
