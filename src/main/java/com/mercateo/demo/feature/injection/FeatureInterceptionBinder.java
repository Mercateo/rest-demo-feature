package com.mercateo.demo.feature.injection;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class FeatureInterceptionBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(FeatureInterceptionService.class).to(org.glassfish.hk2.api.InterceptionService.class).in(Singleton.class);
	}
}