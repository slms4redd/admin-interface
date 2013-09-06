package org.fao.unredd.api.model.geostore;

import it.geosolutions.geostore.core.model.Attribute;
import it.geosolutions.geostore.core.model.Resource;

import java.util.NoSuchElementException;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class AbstractGeostoreElement {

	protected Resource resource;

	public AbstractGeostoreElement(Resource resource) {
		super();
		this.resource = resource;
	}

	protected Attribute getAttribute(String attributeName) {
		try {
			return Iterables.find(resource.getAttribute(), new AttributeFinder(
					attributeName));
		} catch (NoSuchElementException e) {
			/*
			 * Should never ask for an non existing attribute
			 */
			throw new RuntimeException(attributeName);
		}
	}

	protected class AttributeFinder implements Predicate<Attribute> {

		private String attributeName;

		public AttributeFinder(String attributeName) {
			this.attributeName = attributeName;
		}

		public boolean apply(Attribute at) {
			return at.getName().equals(attributeName);
		}
	}

}
