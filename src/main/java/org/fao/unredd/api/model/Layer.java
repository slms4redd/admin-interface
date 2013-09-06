package org.fao.unredd.api.model;

import org.fao.unredd.api.json.LayerRepresentation;

public interface Layer {

	LayerRepresentation getJSON();

	LayerUpdates getLayerUpdates();
}
