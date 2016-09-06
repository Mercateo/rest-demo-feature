package com.mercateo.demo.feature.injection;

import javax.inject.Inject;

import org.glassfish.hk2.api.Factory;

import com.mercateo.demo.feature.FeatureChecker;
import com.mercateo.demo.feature.FeatureMethodChecker;

import lombok.NonNull;

public class FeatureMethodCheckerFactory implements Factory<FeatureMethodChecker> {

	private FeatureChecker featureChecker;

	@Inject
	public FeatureMethodCheckerFactory(@NonNull FeatureChecker featureChecker) {
		super();
		this.featureChecker = featureChecker;
	}

	@Override
	public void dispose(FeatureMethodChecker arg0) {
		// nothing

	}

	@Override
	public FeatureMethodChecker provide() {
		return new FeatureMethodChecker(featureChecker);
	}

}
