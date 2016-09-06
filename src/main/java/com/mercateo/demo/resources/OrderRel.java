package com.mercateo.demo.resources;

import com.mercateo.common.rest.schemagen.link.relation.RelType;
import com.mercateo.common.rest.schemagen.link.relation.Relation;
import com.mercateo.common.rest.schemagen.link.relation.RelationContainer;

public enum OrderRel implements RelationContainer {

	ORDERS,

	SEND_BACK {
		@Override
		public Relation getRelation() {
			return Relation.of("send-back", RelType.INHERITED);
		}
	};

	@Override
	public Relation getRelation() {
		return Relation.of(name().toLowerCase());
	}
}
