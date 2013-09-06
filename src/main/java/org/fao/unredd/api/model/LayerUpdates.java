package org.fao.unredd.api.model;

import org.fao.unredd.api.json.LayerUpdateRepresentation;

public interface LayerUpdates {

	Iterable<LayerUpdateRepresentation> getJSON();

	LayerUpdate getLayerUpdate(String updateId);

}
