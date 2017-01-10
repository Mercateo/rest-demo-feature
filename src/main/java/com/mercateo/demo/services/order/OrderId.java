package com.mercateo.demo.services.order;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.mercateo.demo.services.TypedString;

import lombok.NonNull;

public class OrderId extends TypedString {

	private static final long serialVersionUID = 1L;

	private OrderId(String id) {
		super(id);
	}

public static OrderId fromString(@NonNull String id) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(id));
		return new OrderId(id);
	}

}
