package com.mercateo.demo.feature;

import com.mercateo.common.rest.schemagen.link.Scope;
import com.mercateo.common.rest.schemagen.plugin.MethodCheckerForLink;

public class FeatureMethodChecker implements MethodCheckerForLink {

	private FeatureChecker featureChecker;

	public FeatureMethodChecker(FeatureChecker featureChecker) {
		super();
		this.featureChecker = featureChecker;
	}

	@Override
	public boolean test(Scope t) {
		return featureChecker.hasFeature(t.getInvokedMethod());
	}

}
