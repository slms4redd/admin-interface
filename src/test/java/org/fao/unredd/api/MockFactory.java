package org.fao.unredd.api;

import static org.mockito.Mockito.mock;
import it.geosolutions.geostore.services.rest.GeoStoreClient;

import org.fao.unredd.api.model.Layers;

/**
 * Wrapper around mockito for testing issues
 * 
 * @author fergonco
 */
public class MockFactory {

	public static Layers mockLayers() {
		return mock(Layers.class);
	}

	public static GeoStoreClient mockGeostoreClient() {
		return mock(GeoStoreClient.class);
	}
}
