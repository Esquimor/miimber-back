package com.miimber.back.user.model.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<StatusEnum, String> {

	@Override
	public String convertToDatabaseColumn(StatusEnum role) {
        if (role == null) {
            return null;
        }
        return role.getRole();
	}

	@Override
	public StatusEnum convertToEntityAttribute(String role) {
		if (role == null) {
        return null;
		}

	    return Stream.of(StatusEnum.values())
	      .filter(c -> c.getRole().equals(role))
	      .findFirst()
	      .orElseThrow(IllegalArgumentException::new);
	}

}
