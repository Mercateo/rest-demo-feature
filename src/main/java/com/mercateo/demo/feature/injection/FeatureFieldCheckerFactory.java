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
