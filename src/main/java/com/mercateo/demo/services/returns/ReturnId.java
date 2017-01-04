package com.mercateo.demo.services.returns;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.mercateo.demo.services.TypedString;

import lombok.NonNull;

public class ReturnId extends TypedString {

	private static final long serialVersionUID = 1L;

	private ReturnId(String id) {
		super(id);
	}

	public static ReturnId fromString(@NonNull String id) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(id));
		return new ReturnId(id);
	}

}
