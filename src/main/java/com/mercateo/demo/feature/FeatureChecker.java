/*
 * Created on 03.06.2015
 *
 * author joerg_adler
 */
package com.mercateo.demo.feature;

import java.lang.reflect.Field;

import com.mercateo.common.rest.schemagen.link.Scope;

import lombok.NonNull;

public abstract class FeatureChecker {

	protected abstract boolean hasFeature(Feature annotation);

	public boolean hasFeature(@NonNull Scope scope) {
		Feature methodAnnotation = scope.getInvokedMethod().getAnnotation(Feature.class);
		if (methodAnnotation != null) {
			return hasFeature(methodAnnotation);
		} else {
			return hasFeature(scope.getInvokedClass().getAnnotation(Feature.class));
		}
	}

	public boolean hasFeature(@NonNull Field field) {
		return hasFeature(getFeatureAnnotation(field));
	}

	private Feature getFeatureAnnotation(Field field) {
		return field.getAnnotation(Feature.class);
	}
}
