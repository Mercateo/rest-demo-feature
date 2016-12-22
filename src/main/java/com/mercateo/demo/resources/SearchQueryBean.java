package com.mercateo.demo.resources;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class SearchQueryBean {
	@QueryParam("offset")
	@DefaultValue("0")
	@NonNull
	Integer offset;

	@QueryParam("limit")
	@DefaultValue("100")
	@NonNull
	Integer limit;

	@QueryParam("id")
	String id;
}
