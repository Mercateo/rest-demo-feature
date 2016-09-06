package com.mercateo.demo.lib.schemagen;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.ws.rs.core.Link;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.mercateo.common.rest.schemagen.JsonHyperSchema;
import com.mercateo.common.rest.schemagen.JsonHyperSchemaCreator;
import com.mercateo.common.rest.schemagen.types.ObjectWithSchemaCreator;

@RunWith(MockitoJUnitRunner.class)
public class HyperSchemaCreator0Test {
	@Mock
	private JsonHyperSchemaCreator jsonHyperSchemaCreator;

	@Mock
	private ObjectWithSchemaCreator objectWithSchemaCreator;

	@InjectMocks
	private HyperSchemaCreator uut;

	@SuppressWarnings("unchecked")
	@Test
	public void testCorrectCalls_1() {
		JsonHyperSchema value = mock(JsonHyperSchema.class);
		when(jsonHyperSchemaCreator.from(any())).thenReturn(value);
		Object object = new Object();
		Link link = mock(Link.class);
		uut.create(object, Optional.of(link));
	}

}
