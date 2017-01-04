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

	@NonNull
	private long date;

	@NonNull
	private String id;

	public static ReturnJson from(Return returnEntity) {
		return new ReturnJson(returnEntity.getCarrier(), returnEntity.getDate().getTime(),
				returnEntity.getId().getId());
	}

}
