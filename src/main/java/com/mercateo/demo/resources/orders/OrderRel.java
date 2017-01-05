package com.mercateo.demo.resources.orders;

import com.mercateo.common.rest.schemagen.link.relation.RelType;
import com.mercateo.common.rest.schemagen.link.relation.Relation;
import com.mercateo.common.rest.schemagen.link.relation.RelationContainer;

public enum OrderRel implements RelationContainer {

	ORDERS {
		@Override
		public Relation getRelation() {
			return Relation.of("orders", RelType.OTHER);
		}
	},

	ORDER {
		@Override
		public Relation getRelation() {
			return Relation.of("order", RelType.OTHER);
		}
	},

	ORDERS_LINKING {
		@Override
		public Relation getRelation() {
			return Relation.of("orders-linking", RelType.OTHER);
		}
	},

	RETURN {
		@Override
		public Relation getRelation() {
			return Relation.of("return", RelType.OTHER);
		}
	},

	SEND_BACK {
		@Override
		public Relation getRelation() {
			return Relation.of("send-back", RelType.INHERITED);
		}
	},

	SEND_BACK_NOUN {
		@Override
		public Relation getRelation() {
			return Relation.of("send-back-noun", RelType.INHERITED);
		}
	};

	@Override
	public Relation getRelation() {
		return Relation.of(name().toLowerCase());
	}
}
