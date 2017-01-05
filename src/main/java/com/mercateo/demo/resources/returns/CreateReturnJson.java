package com.mercateo.demo.resources.returns;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class CreateReturnJson extends CreateSendBackJson {

	@NotNull
	@NonNull
	private String orderId;

	public CreateReturnJson(@JsonProperty("message") String message, @JsonProperty("orderId") String orderId) {
		super(message);
		this.orderId = orderId;
	}
}
