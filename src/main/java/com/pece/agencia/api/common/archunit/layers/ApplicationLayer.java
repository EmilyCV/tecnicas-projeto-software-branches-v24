package com.pece.agencia.api.common.archunit.layers;

import org.jmolecules.architecture.onion.simplified.ApplicationRing;
import org.jmolecules.stereotype.Stereotype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ApplicationRing
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PACKAGE, ElementType.TYPE})
@Stereotype
public @interface ApplicationLayer {
}
