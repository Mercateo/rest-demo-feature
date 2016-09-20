package com.mercateo.demo.feature;

import java.lang.annotation.Annotation;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class TypedFeatureChecker {
	@NonNull
	private FeatureChecker featureChecker;

	public boolean isTicket_5() {
		return featureChecker.hasFeature(new Feature() {

			@Override
			public Class<? extends Annotation> annotationType() {
				return Feature.class;
			}

			@Override
			public KnownFeatureId value() {
				return KnownFeatureId.TICKET_5;
			}
		});
	}
}
