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
import com.mercateo.demo.services.OrderService;
import com.mercateo.demo.services.OrderServiceImpl;

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
	OrderService getOrderService() {
		return new OrderServiceImpl();
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
