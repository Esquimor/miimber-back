package com.miimber.back.organization.model.enums;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class StateOrganizationConverter implements AttributeConverter<StateOrganizationEnum, String> {

	@Override
	public String convertToDatabaseColumn(StateOrganizationEnum state) {
        if (state == null) {
            return null;
        }
        return state.getState();
	}

	@Override
	public StateOrganizationEnum convertToEntityAttribute(String state) {
		if (state == null) {
        return null;
		}

	    return Stream.of(StateOrganizationEnum.values())
	      .filter(c -> c.getState().equals(state))
	      .findFirst()
	      .orElseThrow(IllegalArgumentException::new);
	}

}
