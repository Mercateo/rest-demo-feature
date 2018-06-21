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

import java.lang.annotation.Annotation;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class TypedFeatureChecker {
	@NonNull
	private FeatureChecker featureChecker;

	public boolean isTicket_5() {
		return featureChecker.hasFeature(new Feature() {

			@Override
			public Class<? extends Annotation> annotationType() {
				return Feature.class;
			}

			@Override
			public KnownFeatureId value() {
				return KnownFeatureId.TICKET_5;
			}
		});
	}
}
