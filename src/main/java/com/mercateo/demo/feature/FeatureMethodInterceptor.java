package com.mercateo.demo.feature;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Set;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.reflections.ReflectionUtils;

import com.google.common.annotations.VisibleForTesting;

import lombok.NonNull;

/**
 * this is for demonstration of a method interceptor way. Normally, you would
 * not have a {@link FeatureDynamicFeature} and {@link FeatureMethodInterceptor}
 * 
 * @author joerg_adler
 *
 */
public class FeatureMethodInterceptor implements MethodInterceptor {

	private FeatureChecker featureChecker;

	public FeatureMethodInterceptor(@NonNull FeatureChecker featureChecker) {
		super();
		this.featureChecker = featureChecker;
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Class<?>[] paramTypes = invocation.getMethod().getParameterTypes();
		Object[] params = invocation.getArguments();
		for (int i = 0; i < params.length; i++) {
			searchForFeatures(paramTypes[i], params[i]);
		}
		return invocation.proceed();
	}

	@VisibleForTesting
	void searchForFeatures(Class<?> clazz, Object value) {
		if (value == null) {
			return;
		}
		@SuppressWarnings("unchecked")
		Set<Field> fields = ReflectionUtils.getAllFields(clazz);
		for (Field field : fields) {
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			if (!featureChecker.hasFeature(field)) {
				field.setAccessible(true);
				try {
					field.set(value, null);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			} else if (field.getType().isArray() && !field.getType().getComponentType().isPrimitive()) {
				field.setAccessible(true);
				try {
					Object[] fieldArray = (Object[]) field.get(value);
					if (fieldArray == null) {
						continue;
					}
					for (int i = 0; i < fieldArray.length; i++) {
						searchForFeatures(field.getType().getComponentType(), fieldArray[i]);
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			} else if (!field.getType().isPrimitive()) {
				field.setAccessible(true);
				try {
					searchForFeatures(field.getType(), field.get(value));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
}
