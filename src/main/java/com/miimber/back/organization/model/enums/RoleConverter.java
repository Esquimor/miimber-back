package com.miimber.back.organization.model.enums;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<RoleEnum, String> {

	@Override
	public String convertToDatabaseColumn(RoleEnum role) {
        if (role == null) {
            return null;
        }
        return role.getRole();
	}

	@Override
	public RoleEnum convertToEntityAttribute(String role) {
		if (role == null) {
        return null;
		}

	    return Stream.of(RoleEnum.values())
	      .filter(c -> c.getRole().equals(role))
	      .findFirst()
	      .orElseThrow(IllegalArgumentException::new);
	}

}
