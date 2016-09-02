package com.boiler_plate.mobile.di;
/**
 * Interface representing a contract for clients that contains a component for dependency injection.
 *
 * @author      Juan Garcia
 * @created 	2016-01-26
 */
public interface HasComponent<C> {
  C getComponent();
}
