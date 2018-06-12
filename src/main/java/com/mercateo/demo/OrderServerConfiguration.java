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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mercateo.common.rest.schemagen.JsonHyperSchemaCreator;
import com.mercateo.common.rest.schemagen.JsonSchemaGenerator;
import com.mercateo.common.rest.schemagen.RestJsonSchemaGenerator;
import com.mercateo.common.rest.schemagen.types.ObjectWithSchemaCreator;
import com.mercateo.common.rest.schemagen.types.PaginatedResponseBuilderCreator;
import com.mercateo.demo.feature.FeatureChecker;
import com.mercateo.demo.feature.SimpleFeatureChecker;
import com.mercateo.demo.feature.TypedFeatureChecker;

@Configuration
public class OrderServerConfiguration {

	@Bean
	public JsonSchemaGenerator jsonSchemaGenerator() {
		return new RestJsonSchemaGenerator();
	}

	@Bean
	public FeatureChecker getFeatureChecker() {
		return new SimpleFeatureChecker();
	}

	@Bean
	public TypedFeatureChecker getTypedFeatureChecker(FeatureChecker featureChecker) {
		return new TypedFeatureChecker(featureChecker);
	}

	@Bean
	JsonHyperSchemaCreator jsonHyperSchemaCreator() {
		return new JsonHyperSchemaCreator();
	}

	@Bean
	ObjectWithSchemaCreator objectWithSchemaCreator() {
		return new ObjectWithSchemaCreator();
	}

	@Bean
	PaginatedResponseBuilderCreator paginatedResponseBuilderCreator() {
		return new PaginatedResponseBuilderCreator();
	}
}
