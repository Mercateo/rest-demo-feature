package com.mercateo.demo.feature;

import java.util.HashMap;
import java.util.Map;

import com.google.common.annotations.VisibleForTesting;

public class SimpleFeatureChecker extends FeatureChecker {
	@VisibleForTesting
	static final Map<KnownFeatureId, Boolean> featureStore = new HashMap<>();

	static {
		featureStore.put(KnownFeatureId.TICKET_5, Boolean.TRUE);
		featureStore.put(KnownFeatureId.TICKET_6, Boolean.FALSE);
	}

	@Override
	protected boolean hasFeature(Feature annotation) {
		if (annotation == null) {
			return true;
		}
		return featureStore.getOrDefault(annotation.value(), false);

	}

}
