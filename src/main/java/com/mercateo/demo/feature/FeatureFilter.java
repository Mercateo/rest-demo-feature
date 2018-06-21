/**
 * Copyright © 2018 Mercateo AG (http://www.mercateo.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
			throw new WebApplicationException(Response.Status.GONE);
		}
	}

}
