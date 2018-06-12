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
package com.mercateo.demo;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.mercateo.common.rest.schemagen.link.injection.LinkFactoryResourceConfig;
import com.mercateo.common.rest.schemagen.plugin.FieldCheckerForSchema;
import com.mercateo.common.rest.schemagen.plugin.MethodCheckerForLink;
import com.mercateo.demo.feature.FeatureDynamicFeature;
import com.mercateo.demo.feature.injection.FeatureFieldCheckerFactory;
import com.mercateo.demo.feature.injection.FeatureInterceptionBinder;
import com.mercateo.demo.feature.injection.FeatureMethodCheckerFactory;

import io.swagger.jaxrs.config.BeanConfig;

@Component
public class OrderServerApplication extends ResourceConfig {

	public OrderServerApplication() {
		register(JacksonFeature.class);
		// register(LoggingFilter.class);

		// Register resources and providers using package-scanning.
		final String resourceBasePackage = "com.mercateo.demo.resources";
		packages(resourceBasePackage);

		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setVersion("1.0.2");
		beanConfig.setSchemes(new String[] { "http" });
		beanConfig.setHost("localhost:9090");
		beanConfig.setBasePath("/");
		beanConfig.setResourcePackage(resourceBasePackage);
		beanConfig.setScan(true);

		register(new FeatureInterceptionBinder());

		register(io.swagger.jaxrs.listing.ApiListingResource.class);
		register(io.swagger.jaxrs.listing.SwaggerSerializers.class);

		LinkFactoryResourceConfig.configureWithoutPlugins(this);

		register(DeclarativeLinkingFeature.class);
		register(FeatureDynamicFeature.class);
		bindFeaturePlugins();
	}

	private void bindFeaturePlugins() {
		register(new AbstractBinder() {
			@Override
			protected void configure() {
				bindFactory(FeatureMethodCheckerFactory.class).to(MethodCheckerForLink.class).in(RequestScoped.class)
						.proxy(true);
				bindFactory(FeatureFieldCheckerFactory.class, Singleton.class).to(FieldCheckerForSchema.class)
						.in(Singleton.class);
			}
		});
	}

}
