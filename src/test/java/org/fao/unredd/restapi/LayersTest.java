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

public class LayersTest extends JerseyTest {

	public LayersTest() {
		super("org.fao.unredd.resources", "org.codehaus.jackson.jaxrs");
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

	@Test
	public void testCreateLayerNullFail() throws Exception {
		Layer layer = new Layer(null, null, null);
		ClientResponse response = createLayer(layer);
		assertEquals(ClientResponse.Status.BAD_REQUEST,
				response.getClientResponseStatus());
		JSONArray errorList = response.getEntity(JSONArray.class);
		assertEquals(2, errorList.length());
	}

	@Test
	public void testCreateLayerWithIdFail() throws Exception {
		Layer layer = new Layer("1", "name", LayerType.RASTER);
		ClientResponse response = createLayer(layer);
		assertEquals(ClientResponse.Status.BAD_REQUEST,
				response.getClientResponseStatus());
		JSONArray errorList = response.getEntity(JSONObject.class)
				.getJSONArray("errorList");
		assertEquals(errorList.length(), 1);
	}

	@Test
	public void testGetEmptyLayers() throws Exception {
		ClientResponse response = getLayersOk();

		JSONArray array = response.getEntity(JSONArray.class);
		assertEquals(array.length(), 0);
	}

	@Test
	public void testGetOneLayer() throws Exception {
		createLayerOk(new Layer(null, "newlayer1", LayerType.RASTER));

		ClientResponse response = getLayersOk();

		JSONArray array = response.getEntity(JSONArray.class);
		assertEquals(array.length(), 1);
	}

	@Test
	public void testGetLayers() throws Exception {
		createLayerOk(new Layer(null, "newlayer1", LayerType.RASTER));
		createLayerOk(new Layer(null, "newlayer2", LayerType.VECTOR));

		ClientResponse response = getLayersOk();

		JSONArray array = response.getEntity(JSONArray.class);
		assertEquals(array.length(), 2);
		assertTrue(array.getJSONObject(0).getString("name")
				.matches("newlayer."));
		assertTrue(array.getJSONObject(1).getString("name")
				.matches("newlayer."));
		assertTrue(!array.getJSONObject(0).getString("name")
				.equals(array.getJSONObject(1).getString("name")));
	}

	private ClientResponse createLayerOk(Layer layer) {
		ClientResponse response = createLayer(layer);
		assertEquals(ClientResponse.Status.CREATED,
				response.getClientResponseStatus());
		return response;
	}

	private ClientResponse createLayer(Layer layer) {
		WebResource webResource = resource();
		ClientResponse response = webResource.path("layers")
				.type(MediaType.APPLICATION_JSON).entity(layer)
				.post(ClientResponse.class);
		return response;
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
