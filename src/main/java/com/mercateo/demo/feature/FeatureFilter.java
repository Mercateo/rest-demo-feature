/*
 * Created on 03.06.2015
 *
 * author joerg_adler
 */
package com.mercateo.demo.feature;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;

@Priority(Priorities.AUTHORIZATION)
public class FeatureFilter implements ContainerRequestFilter {

	private Feature feature;

	private FeatureChecker featureChecker;

	public FeatureFilter(Feature feature, FeatureChecker featureChecker) {
		this.feature = feature;
		this.featureChecker = featureChecker;
	}

	@Override
	public void filter(ContainerRequestContext requestContext) {
		if (!featureChecker.hasFeature(feature)) {
			throw new WebApplicationException(Response.Status.UNAUTHORIZED);
		}
	}

}
