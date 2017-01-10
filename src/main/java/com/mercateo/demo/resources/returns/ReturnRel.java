package com.mercateo.demo.resources.returns;

import com.mercateo.common.rest.schemagen.link.relation.RelType;
import com.mercateo.common.rest.schemagen.link.relation.Relation;
import com.mercateo.common.rest.schemagen.link.relation.RelationContainer;

public enum ReturnRel implements RelationContainer {
	RETURNS {
		@Override
		public Relation getRelation() {
			return Relation.of("returns", RelType.OTHER);
		}
	},

	RETURN {
		@Override
		public Relation getRelation() {
			return Relation.of("return", RelType.OTHER);
		}
	},

	ORDER {
		@Override
		public Relation getRelation() {
			return Relation.of("order", RelType.OTHER);
		}
	};

	@Override
	public Relation getRelation() {
		return Relation.of(name().toLowerCase());
	}
}
