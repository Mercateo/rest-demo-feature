package com.mercateo.demo.feature;

import static org.mockito.Mockito.when;

import javax.ws.rs.WebApplicationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FeatureFilter0Test {
	@Mock
	private Feature feature;

	@Mock
	private FeatureChecker featureChecker;
	@InjectMocks
	private FeatureFilter uut;

	@Test(expected = WebApplicationException.class)
	public void testFilter() throws Exception {
		when(featureChecker.hasFeature(feature)).thenReturn(false);
		uut.filter(null);

	}

	@Test
	public void testFilterNotThere() throws Exception {
		when(featureChecker.hasFeature(feature)).thenReturn(true);
		uut.filter(null);
	}

}
