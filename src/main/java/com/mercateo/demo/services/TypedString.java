package com.mercateo.demo.services;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public abstract class TypedString implements Serializable {

	private static final long serialVersionUID = 1L;
	@NonNull
	@Getter
	private final String id;

	@Override
	public String toString() {
		return id;
	}

}
