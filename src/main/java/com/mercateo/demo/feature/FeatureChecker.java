/*
 * Created on 03.06.2015
 *
 * author joerg_adler
 */
package com.mercateo.demo.feature;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import lombok.NonNull;

public abstract class FeatureChecker {

	protected abstract boolean hasFeature(Feature annotation);

	public boolean hasFeature(@NonNull Method method) {
		return hasFeature(method.getAnnotation(Feature.class));
	}

	public boolean hasFeature(@NonNull Field field) {
		return hasFeature(getFeatureAnnotation(field));
	}

	private Feature getFeatureAnnotation(Field field) {
		return field.getAnnotation(Feature.class);
	}
}
