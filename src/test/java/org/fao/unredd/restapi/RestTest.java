package org.fao.unredd.restapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.fao.unredd.resources.Layer;
import org.fao.unredd.resources.LayerListResource;
import org.fao.unredd.resources.LayerType;
import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.test.framework.JerseyTest;

public class RestTest extends JerseyTest {

	public RestTest() {
		super("org.fao.unredd.resources");
	}

	@Before
	public void setup() {
		LayerListResource.model.clear();
	}

	@Test
	public void testCreateLayer() {
		Layer layer = new Layer(null, "newlayer", LayerType.RASTER);
		ClientResponse response = createLayerOk(layer);
		String location = response.getHeaders().getFirst("location");
		assertTrue(location.matches("http://localhost:9998/layers/."));
		fail("query at the location and check a 200 return code");
	}

	private ClientResponse createLayerOk(Layer layer) {
		WebResource webResource = resource();
		ClientResponse responseMsg = webResource.path("layers")
				.type(MediaType.APPLICATION_JSON).entity(layer)
				.post(ClientResponse.class);
		assertEquals(ClientResponse.Status.CREATED,
				responseMsg.getClientResponseStatus());
		return responseMsg;
	}

	@Test
	public void testGetLayers() throws Exception {
		createLayerOk(new Layer(null, "newlayer1", LayerType.RASTER));
		createLayerOk(new Layer(null, "newlayer2", LayerType.VECTOR));

		ClientResponse response = getLayersOk();

		JSONObject res = response.getEntity(JSONObject.class);
		assertEquals(res.names().length(), 1);
		JSONArray array = res.getJSONArray(res.names().getString(0));
		assertEquals(array.length(), 2);
		assertTrue(array.getJSONObject(0).getString("name")
				.matches("newlayer."));
		assertTrue(array.getJSONObject(1).getString("name")
				.matches("newlayer."));
		assertTrue(!array.getJSONObject(0).getString("name")
				.equals(array.getJSONObject(1).getString("name")));
	}

	private ClientResponse getLayersOk() {
		WebResource webResource = resource();
		ClientResponse response = webResource.path("layers").get(
				ClientResponse.class);
		assertEquals(ClientResponse.Status.OK,
				response.getClientResponseStatus());
		return response;
	}
}
