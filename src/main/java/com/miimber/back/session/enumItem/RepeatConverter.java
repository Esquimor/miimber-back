package com.miimber.back.session.enumItem;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RepeatConverter implements AttributeConverter<RepeatEnum, String> {

	@Override
	public String convertToDatabaseColumn(RepeatEnum repeat) {
        if (repeat == null) {
            return null;
        }
        return repeat.getRepeat();
	}

	@Override
	public RepeatEnum convertToEntityAttribute(String repeat) {
		if (repeat == null) {
        return null;
		}

	    return Stream.of(RepeatEnum.values())
	      .filter(c -> c.getRepeat().equals(repeat))
	      .findFirst()
	      .orElseThrow(IllegalArgumentException::new);
	}
}
