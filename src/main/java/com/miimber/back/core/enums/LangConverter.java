package com.miimber.back.core.enums;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LangConverter implements AttributeConverter<LangEnum, String> {

	@Override
	public String convertToDatabaseColumn(LangEnum lang) {
        if (lang == null) {
            return null;
        }
        return lang.getLang();
	}

	@Override
	public LangEnum convertToEntityAttribute(String lang) {
		if (lang == null) {
        return null;
		}

	    return Stream.of(LangEnum.values())
	      .filter(c -> c.getLang().equals(lang))
	      .findFirst()
	      .orElseThrow(IllegalArgumentException::new);
	}

}