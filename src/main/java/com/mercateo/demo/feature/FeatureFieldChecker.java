package com.mercateo.demo.feature;

import java.lang.reflect.Field;

import com.mercateo.common.rest.schemagen.parameter.CallContext;
import com.mercateo.common.rest.schemagen.plugin.FieldCheckerForSchema;

public class FeatureFieldChecker implements FieldCheckerForSchema {

    private FeatureChecker featureChecker;

    public FeatureFieldChecker(FeatureChecker featureChecker) {
        super();
        this.featureChecker = featureChecker;
    }

    @Override
    public boolean test(Field t, CallContext callContext) {
        return featureChecker.hasFeature(t);
    }

}
