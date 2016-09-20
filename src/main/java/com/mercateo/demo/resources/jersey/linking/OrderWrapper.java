package com.mercateo.demo.resources.jersey.linking;

import java.util.List;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.mercateo.demo.resources.json.OrderJson;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderWrapper {

	@InjectLinks({
			@InjectLink(resource = OrdersLinkingResource.class, rel = "self", method = "getOrder", bindings = {
					@Binding(name = "orderId", value = "${instance.order.id}") }),
			@InjectLink(resource = OrdersLinkingResource.class, rel = "send-back", method = "sendBack", condition = "${resource.featureChecker.ticket_5}", bindings = {
					@Binding(name = "orderId", value = "${instance.order.id}") }) })
	@XmlJavaTypeAdapter(Link.JaxbAdapter.class)
	List<Link> links;
	@JsonUnwrapped
	private OrderJson order;
}
