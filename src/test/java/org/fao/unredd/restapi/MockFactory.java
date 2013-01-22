package org.fao.unredd.restapi;

import static org.mockito.Mockito.mock;

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
}
