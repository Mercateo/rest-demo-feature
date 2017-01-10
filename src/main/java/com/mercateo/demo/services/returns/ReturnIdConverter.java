package com.mercateo.demo.services.returns;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ReturnIdConverter implements AttributeConverter<ReturnId, String> {

	@Override
	public String convertToDatabaseColumn(ReturnId attribute) {
		return attribute == null ? null : attribute.getId();
	}

	@Override
	public ReturnId convertToEntityAttribute(String dbData) {
		return dbData == null ? null : ReturnId.fromString(dbData);
	}

}
