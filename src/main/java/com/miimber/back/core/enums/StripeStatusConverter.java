package com.miimber.back.core.enums;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class StripeStatusConverter implements AttributeConverter<StripeStatusEnum, String> {

	@Override
	public String convertToDatabaseColumn(StripeStatusEnum status) {
        if (status == null) {
            return null;
        }
        return status.getStatus();
	}

	@Override
	public StripeStatusEnum convertToEntityAttribute(String status) {
		if (status == null) {
			return null;
		}

	    return Stream.of(StripeStatusEnum.values())
	      .filter(c -> c.getStatus().equals(status))
	      .findFirst()
	      .orElseThrow(IllegalArgumentException::new);
	}

}