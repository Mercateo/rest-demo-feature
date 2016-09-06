package com.mercateo.demo.resources.json;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mercateo.demo.feature.Feature;
import com.mercateo.demo.feature.KnownFeatureId;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SendBackJson {
	@NotNull
	@Nonnull
	private String message;

	@Feature(KnownFeatureId.TICKET_6)
	private CARRIER preferredCarrier;
}
