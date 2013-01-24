package org.fao.unredd.api.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.fao.unredd.api.json.AddLayerRequest;
import org.fao.unredd.api.json.ResponseRoot;
import org.fao.unredd.api.model.Layers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/layers")
public class LayerListResource {

	@Autowired
	private Layers layers;

	@Context
	private UriInfo uriInfo;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseRoot asJSON() {
		return ResponseRoot.newLayers(layers.getJSON());
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addLayer(AddLayerRequest layerRequest) {
		List<String> errors = new ArrayList<String>();
		checkNull(errors, layerRequest.getName(), "name");
		checkNull(errors, layerRequest.getType(), "type");
		checkNull(errors, layerRequest.getDestOrigAbsPath(),
				"Data original path");
		checkNull(errors, layerRequest.getDissMosaicPath(),
				"Dissemination mosaic path");
		checkNull(errors, layerRequest.getStgMosaicPath(),
				"Staging mosaic path");
		checkNull(errors, layerRequest.getMaxx(), "max x");
		checkNull(errors, layerRequest.getMaxy(), "max y");
		checkNull(errors, layerRequest.getMinx(), "min x");
		checkNull(errors, layerRequest.getMiny(), "min y");
		checkNull(errors, layerRequest.getPixelHeight(), "data height");
		checkNull(errors, layerRequest.getPixelWidth(), "data width");
		if (errors.size() > 0) {
			throw new BadRequestException(errors);
		}

		long id = layers.addLayer(layerRequest);
		UriBuilder uriBuilder = UriBuilder.fromUri(uriInfo.getRequestUri());
		uriBuilder.path("{id}");
		URI location = uriBuilder.build(Long.toString(id));
		return Response.created(location).type(MediaType.APPLICATION_JSON)
				.build();
	}

	private void checkNull(List<String> errors, Object object, String string) {
		if (object == null) {
			errors.add(string + " cannot be null");
		}
	}
}
