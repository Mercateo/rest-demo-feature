/*
 * Created on 03.06.2015
 *
 * author joerg_adler
 */
package com.mercateo.demo.feature;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * this is a marker for a technical Feature. If you want business-features go to
 * FeaturePermission
 * 
 * @author joerg_adler
 *
 */
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Feature {
	KnownFeatureId value();
}
