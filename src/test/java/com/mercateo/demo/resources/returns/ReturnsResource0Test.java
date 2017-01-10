package com.mercateo.demo.resources.returns;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.WebApplicationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;

import com.mercateo.common.rest.schemagen.link.LinkMetaFactory;
import com.mercateo.common.rest.schemagen.link.relation.Rel;
import com.mercateo.common.rest.schemagen.types.ObjectWithSchema;
import com.mercateo.common.rest.schemagen.types.PaginatedResponse;
import com.mercateo.common.rest.schemagen.types.PaginatedResponseBuilderCreator;
import com.mercateo.demo.services.order.OrderId;
import com.mercateo.demo.services.returns.Return;
import com.mercateo.demo.services.returns.ReturnId;
import com.mercateo.demo.services.returns.ReturnsReadRepo;
import com.mercateo.demo.services.returns.ReturnsWriteService;

@RunWith(MockitoJUnitRunner.class)
public class ReturnsResource0Test {
	@Spy
	private LinkMetaFactory linkMetaFactory = LinkMetaFactory.createInsecureFactoryForTest();

	@Spy
	private PaginatedResponseBuilderCreator responseBuilderCreator = new PaginatedResponseBuilderCreator();

	@Mock
	private ReturnsReadRepo returnsReadService;

	@Mock
	private ReturnsWriteService returnsWriteService;

	@InjectMocks
	private ReturnsResource uut;

	@Test
	public void testGetReturns() throws Exception {
		when(returnsReadService.findAll(any())).thenReturn(new PageImpl<>(new ArrayList<>()));
		PaginatedResponse<ReturnJson> r = uut.getReturns(new SearchQueryBean(0, 20));
		assertTrue(r.schema.getByRel(Rel.CREATE).isPresent());
		verify(returnsReadService).findAll(any());
	}

	@Test
	public void testGetReturn() throws Exception {
		ReturnId id = ReturnId.fromString("1");
		when(returnsReadService.findById(id)).thenReturn(new Return(new Date(), OrderId.fromString("2")));
		ObjectWithSchema<ReturnJson> r = uut.getReturn(id);
		assertTrue(r.schema.getByRel(ReturnRel.ORDER).isPresent());
		assertTrue(r.schema.getByRel(Rel.SELF).isPresent());
		verify(returnsReadService).findById(id);
	}

	@Test(expected = WebApplicationException.class)
	public void testGetReturn_notFound() throws Exception {
		ReturnId id = ReturnId.fromString("1");
		uut.getReturn(id);
	}

	@Test
	public void testCreateNew() throws Exception {
		CreateReturnJson createJson = new CreateReturnJson("test", "2");
		when(returnsWriteService.create(createJson, OrderId.fromString("2"))).thenReturn(ReturnId.fromString("2"));
		ObjectWithSchema<Void> r = uut.createNew(createJson);
		assertTrue(r.schema.getByRel(ReturnRel.ORDER).isPresent());
		assertTrue(r.schema.getByRel(ReturnRel.RETURN).isPresent());
		verify(returnsWriteService).create(createJson, OrderId.fromString("2"));
	}

}
