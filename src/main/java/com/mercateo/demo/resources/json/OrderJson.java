package com.mercateo.demo.resources.json;

import com.mercateo.demo.services.STATE;

import lombok.Value;

@Value
public class OrderJson {
	private String id;

	private double total;

	private STATE state;
}
