package com.mercateo.demo.feature;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;

import lombok.Data;

public class FeatureMethodInterceptor0Test {
	@Data
	public static class TestBean {
		@Feature(KnownFeatureId.TICKET_5)
		private TestBean[] t1;

		private TestBean[] t2;
		@Feature(KnownFeatureId.TICKET_5)
		private TestBean t3;

		private TestBean t4;
	}

	public static ArgumentMatcher<Field> argm = new ArgumentMatcher<Field>() {

		@Override
		public boolean matches(Object argument) {
			return !(argument instanceof Field) || ((Field) argument).getAnnotation(Feature.class) == null;
		}
	};

	private FeatureChecker featureChecker;

	private FeatureMethodInterceptor uut;

	@Before
	public void setup() {
		featureChecker = mock(FeatureChecker.class);
		uut = new FeatureMethodInterceptor(featureChecker);
	}

	@Test
	public void testTestCorrectNullSetting() {
		when(featureChecker.hasFeature(Matchers.argThat(argm))).thenReturn(true);
		TestBean t = new TestBean();
		TestBean t1 = new TestBean();
		TestBean t2 = new TestBean();
		t2.setT3(new TestBean());
		t2.setT4(new TestBean());
		TestBean t3 = new TestBean();
		TestBean t4 = new TestBean();
		t4.setT3(new TestBean());
		t.setT1(new TestBean[] { t1 });
		t.setT2(new TestBean[] { t2 });
		t.setT3(t3);
		t.setT4(t4);

		assertNotNull(t.getT1());
		assertNotNull(t.getT2());
		assertNotNull(t.getT3());
		assertNotNull(t.getT4());

		uut.searchForFeatures(TestBean.class, t);
		assertNull(t.getT1());
		assertNotNull(t.getT2());
		assertNull(t.getT3());
		assertNotNull(t.getT4());

		assertNull(t.getT2()[0].getT3());
		assertNotNull(t.getT2()[0].getT4());
		assertNull(t.getT4().getT3());
	}

}
