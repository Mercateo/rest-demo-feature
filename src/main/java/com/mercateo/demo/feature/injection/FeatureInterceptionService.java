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
package com.mercateo.demo.feature.injection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.aopalliance.intercept.ConstructorInterceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.InterceptionService;

import com.mercateo.common.rest.schemagen.JerseyResource;
import com.mercateo.demo.feature.FeatureChecker;
import com.mercateo.demo.feature.FeatureMethodInterceptor;

public class FeatureInterceptionService implements InterceptionService {

	private FeatureChecker featureChecker;

	@Inject
	public FeatureInterceptionService(FeatureChecker featureChecker) {
		super();
		this.featureChecker = featureChecker;
	}

	@Override
	public List<ConstructorInterceptor> getConstructorInterceptors(Constructor<?> arg0) {
		return null;
	}

	@Override
	public Filter getDescriptorFilter() {
		return new Filter() {

			@Override
			public boolean matches(Descriptor d) {
				Class<?> descriptorClass;
				try {
					descriptorClass = this.getClass().getClassLoader().loadClass(d.getImplementation());
				} catch (ClassNotFoundException e) {
					return false;
				}
				return JerseyResource.class.isAssignableFrom(descriptorClass);
			}
		};
	}

	@Override
	public List<MethodInterceptor> getMethodInterceptors(Method method) {
		if (Modifier.isPublic(method.getModifiers()) && !method.getDeclaringClass().equals(Object.class)) {
			return Arrays.asList(new FeatureMethodInterceptor(featureChecker));
		}
		return null;
	}

}
