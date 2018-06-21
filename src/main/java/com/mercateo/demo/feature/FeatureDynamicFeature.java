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

import java.lang.reflect.Method;

import javax.inject.Inject;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

public class FeatureDynamicFeature implements DynamicFeature {
	@Inject
	private FeatureChecker featureChecker;

	@Override
	public void configure(ResourceInfo resourceInfo, FeatureContext context) {
		Method am = resourceInfo.getResourceMethod();
		Feature annotation = am.getAnnotation(Feature.class);
		if (annotation != null) {
			context.register(new FeatureFilter(annotation, featureChecker));
		}
		annotation = resourceInfo.getResourceClass().getAnnotation(Feature.class);
		if (annotation != null) {
			context.register(new FeatureFilter(annotation, featureChecker));
		}
	}
}
