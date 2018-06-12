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
