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
package com.mercateo.demo.resources.jersey.linking;

import java.util.List;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.mercateo.demo.resources.orders.OrderJson;

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
