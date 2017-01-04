package com.mercateo.demo.services.order;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class OrderIdConverter implements AttributeConverter<OrderId, String> {

	@Override
	public String convertToDatabaseColumn(OrderId attribute) {
		return attribute == null ? null : attribute.getId();
	}

	@Override
	public OrderId convertToEntityAttribute(String dbData) {
		return dbData == null ? null : OrderId.fromString(dbData);
	}

}
