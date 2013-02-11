package org.fao.unredd.api.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.fao.unredd.api.json.LayerUpdatesResponseRoot;
import org.fao.unredd.api.model.Layer;
import org.fao.unredd.api.model.LayerUpdates;
import org.fao.unredd.api.model.Layers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/layers/{layerId}/layerupdates")
public class LayerUpdateListResource {

	@Autowired
	private Layers layers;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public LayerUpdatesResponseRoot asJSON(@PathParam("layerId") String layerId) {
		Layer layer = layers.getLayer(layerId);

		LayerUpdates updates = layer.getLayerUpdates();

		return new LayerUpdatesResponseRoot(updates.getJSON());
	}

}
