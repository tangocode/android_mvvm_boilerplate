package com.boiler_plate.mobile.di;

import java.lang.annotation.Retention;
import javax.inject.Scope;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
/**
 * A scoping annotation to permit objects whose lifetime should
 * conform to the life of the activity to be memorized in the
 * correct component.
 *
 * @author      Juan Garcia
 * @created 	2016-01-26
 */
@Scope
@Retention(RUNTIME)
public @interface PerActivity {}
