package com.mercateo.demo.resources;

import javax.ws.rs.PathParam;

import lombok.Data;

@Data
public class IdBean {
	@PathParam("id")
	private String id;
}
