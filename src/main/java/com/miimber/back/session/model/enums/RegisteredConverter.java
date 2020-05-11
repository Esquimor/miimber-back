package com.miimber.back.session.model.enums;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RegisteredConverter implements AttributeConverter<RegisteredEnum, String> {

	@Override
	public String convertToDatabaseColumn(RegisteredEnum status) {
        if (status == null) {
            return null;
        }
        return status.getStatus();
	}

	@Override
	public RegisteredEnum convertToEntityAttribute(String status) {
		if (status == null) {
        return null;
		}

	    return Stream.of(RegisteredEnum.values())
	      .filter(c -> c.getStatus().equals(status))
	      .findFirst()
	      .orElseThrow(IllegalArgumentException::new);
	}

}