package com.mercateo.demo.lib.schemagen;

import static com.mercateo.demo.lib.OptionalUtils.collect;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.core.Link;

import org.springframework.stereotype.Component;

import com.mercateo.common.rest.schemagen.JsonHyperSchema;
import com.mercateo.common.rest.schemagen.JsonHyperSchemaCreator;
import com.mercateo.common.rest.schemagen.types.ObjectWithSchema;
import com.mercateo.common.rest.schemagen.types.ObjectWithSchemaCreator;

import lombok.NonNull;

@Component
public class HyperSchemaCreator {
	private ObjectWithSchemaCreator objectWithSchemaCreator;

	private JsonHyperSchemaCreator jsonHyperSchemaCreator;

	@Inject
	public HyperSchemaCreator(@NonNull ObjectWithSchemaCreator objectWithSchemaCreator,
			@NonNull JsonHyperSchemaCreator jsonHyperSchemaCreator) {
		super();
		this.objectWithSchemaCreator = objectWithSchemaCreator;
		this.jsonHyperSchemaCreator = jsonHyperSchemaCreator;
	}

	@SuppressWarnings("unchecked")
	public <T> ObjectWithSchema<T> create(T object, Optional<Link>... links) {
		JsonHyperSchema hyperSchema = jsonHyperSchemaCreator.from(collect(links));
		return objectWithSchemaCreator.create(object, hyperSchema);
	}
}
