/**
 * Copyright © 2018 Mercateo AG (http://www.mercateo.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
