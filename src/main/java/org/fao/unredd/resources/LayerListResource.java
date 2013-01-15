package org.fao.unredd.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/layers")
public class LayerListResource {

	public static Model model;

	static {
		model = new Model();
		model.addLayer(new Layer("0", "administrative areas", LayerType.VECTOR));
		model.addLayer(new Layer("1", "forest mask", LayerType.RASTER));
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Layer[] asJSON() {
		return model.getLayers();
	}

}
