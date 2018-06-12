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
package com.mercateo.demo.resources.returns;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mercateo.demo.feature.Feature;
import com.mercateo.demo.feature.KnownFeatureId;
import com.mercateo.demo.resources.CARRIER;
import com.mercateo.demo.services.returns.Return;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
public class ReturnJson {
	@Feature(KnownFeatureId.TICKET_6)
	private CARRIER carrier;

	private long date;

	@NonNull
	private String id;

	public static ReturnJson from(Return returnEntity) {
		return new ReturnJson(returnEntity.getCarrier(), returnEntity.getDate().getTime(),
				returnEntity.getId().getId());
	}

}
