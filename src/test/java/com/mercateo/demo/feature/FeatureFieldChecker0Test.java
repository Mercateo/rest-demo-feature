package com.mercateo.demo.feature;

import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FeatureFieldChecker0Test {
	@Mock
	private FeatureChecker featureChecker;
	@InjectMocks
	private FeatureFieldChecker uut;

	@Test
	public void testCorrectCall() throws Exception {
		Field declaredField = this.getClass().getDeclaredField("uut");
		uut.test(declaredField, null);
		verify(featureChecker).hasFeature(declaredField);
	}

}
