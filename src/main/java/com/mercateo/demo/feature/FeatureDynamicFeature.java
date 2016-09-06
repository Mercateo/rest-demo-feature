/*
 * Created on 03.06.2015
 *
 * author joerg_adler
 */
package com.mercateo.demo.feature;

import javax.inject.Inject;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

import org.glassfish.jersey.server.model.AnnotatedMethod;

public class FeatureDynamicFeature implements DynamicFeature {
	@Inject
	private FeatureChecker featureChecker;

	@Override
	public void configure(ResourceInfo resourceInfo, FeatureContext context) {
		AnnotatedMethod am = new AnnotatedMethod(resourceInfo.getResourceMethod());
		Feature annotation = am.getAnnotation(Feature.class);
		if (annotation != null) {
			context.register(new FeatureFilter(annotation, featureChecker));
		}
	}
}
