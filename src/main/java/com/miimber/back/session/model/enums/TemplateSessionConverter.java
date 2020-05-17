package com.miimber.back.session.model.enums;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TemplateSessionConverter implements AttributeConverter<TemplateSessionEnum, String> {

	@Override
	public String convertToDatabaseColumn(TemplateSessionEnum recurrency) {
        if (recurrency == null) {
            return null;
        }
        return recurrency.getRecurrency();
	}

	@Override
	public TemplateSessionEnum convertToEntityAttribute(String recurrency) {
		if (recurrency == null) {
        return null;
		}

	    return Stream.of(TemplateSessionEnum.values())
	      .filter(c -> c.getRecurrency().equals(recurrency))
	      .findFirst()
	      .orElseThrow(IllegalArgumentException::new);
	}
}
