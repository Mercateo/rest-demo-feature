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
package com.mercateo.demo.feature.injection;

import javax.inject.Inject;

import org.glassfish.hk2.api.Factory;

import com.mercateo.demo.feature.FeatureChecker;
import com.mercateo.demo.feature.FeatureFieldChecker;

import lombok.NonNull;

public class FeatureFieldCheckerFactory implements Factory<FeatureFieldChecker> {

	private FeatureChecker featureChecker;

	@Inject
	public FeatureFieldCheckerFactory(@NonNull FeatureChecker featureChecker) {
		super();
		this.featureChecker = featureChecker;
	}

	@Override
	public void dispose(FeatureFieldChecker arg0) {
		// nothing

	}

	@Override
	public FeatureFieldChecker provide() {
		return new FeatureFieldChecker(featureChecker);
	}

}
