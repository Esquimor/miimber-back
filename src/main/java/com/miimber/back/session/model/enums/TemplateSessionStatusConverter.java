package com.miimber.back.session.model.enums;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TemplateSessionStatusConverter implements AttributeConverter<TemplateSessionStatusEnum, String> {

	@Override
	public String convertToDatabaseColumn(TemplateSessionStatusEnum status) {
        if (status == null) {
            return null;
        }
        return status.getStatus();
	}

	@Override
	public TemplateSessionStatusEnum convertToEntityAttribute(String status) {
		if (status == null) {
        return null;
		}

	    return Stream.of(TemplateSessionStatusEnum.values())
	      .filter(c -> c.getStatus().equals(status))
	      .findFirst()
	      .orElseThrow(IllegalArgumentException::new);
	}
}
