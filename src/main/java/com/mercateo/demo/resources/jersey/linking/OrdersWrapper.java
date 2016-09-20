package com.mercateo.demo.resources.jersey.linking;

import java.util.List;

import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@InjectLinks({ @InjectLink(resource = OrdersLinkingResource.class, rel = "self", method = "get") })
public class OrdersWrapper {

	private List<OrderWrapper> members;

	private int totalCount;

	private int limit;

	private int offset;

}
