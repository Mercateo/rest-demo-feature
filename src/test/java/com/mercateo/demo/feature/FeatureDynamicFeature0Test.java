package com.mercateo.demo.feature;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;

import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FeatureDynamicFeature0Test {
	@Mock
	private FeatureChecker featureChecker;
	@InjectMocks
	private FeatureDynamicFeature uut;

	@Test
	public void testConfigureWithout() throws Exception {
		ResourceInfo resourceInfo = mock(ResourceInfo.class);
		Method method = this.getClass().getMethod("testConfigureWithout");
		when(resourceInfo.getResourceClass()).thenReturn((Class) this.getClass());
		when(resourceInfo.getResourceMethod()).thenReturn(method);
		FeatureContext featureContext = mock(FeatureContext.class);

		uut.configure(resourceInfo, featureContext);

		verifyNoMoreInteractions(featureContext);
	}

	@Test
	@Feature(KnownFeatureId.TICKET_5)
	public void testConfigureWith() throws Exception {
		ResourceInfo resourceInfo = mock(ResourceInfo.class);
		Method method = this.getClass().getMethod("testConfigureWith");
		when(resourceInfo.getResourceMethod()).thenReturn(method);
		when(resourceInfo.getResourceClass()).thenReturn((Class) this.getClass());
		FeatureContext featureContext = mock(FeatureContext.class);

		uut.configure(resourceInfo, featureContext);

		verify(featureContext).register(any(FeatureFilter.class));
	}

}
