package com.mercateo.demo.feature;

import static org.mockito.Mockito.verify;

import java.lang.reflect.Method;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.mercateo.common.rest.schemagen.link.CallScope;
import com.mercateo.common.rest.schemagen.link.Scope;

@RunWith(MockitoJUnitRunner.class)
public class FeatureMethodChecker0Test {
	@Mock
	private FeatureChecker featureChecker;
	@InjectMocks
	private FeatureMethodChecker uut;

	@Test
	public void testTest() throws Exception {
		Method method = getClass().getMethod("testTest");
		Scope scope = new CallScope(getClass(), method, new Object[0], null);
		uut.test(scope);
		verify(featureChecker).hasFeature(scope);
	}

}
