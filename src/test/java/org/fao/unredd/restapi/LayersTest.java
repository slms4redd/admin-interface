package org.fao.unredd.restapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URL;

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
		JSONArray errorList = response.getEntity(JSONArray.class);
		assertEquals(1, errorList.length());
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

	@Test
	public void testCreateGetLayer() throws Exception {
		ClientResponse response = createLayerOk(new Layer(null, "newlayer",
				LayerType.RASTER));
		String location = response.getHeaders().getFirst("location");

		// Check location
		WebResource webResource = resource();
		response = webResource.uri(new URL(location).toURI()).get(
				ClientResponse.class);
		assertEquals(ClientResponse.Status.OK,
				response.getClientResponseStatus());

		// Check actual contents by expected path
		response = getLayersOk();
		JSONArray array = response.getEntity(JSONArray.class);
		String id = array.getJSONObject(0).getString("id");
		response = getLayerOk(id);
		JSONObject layerObj = response.getEntity(JSONObject.class);
		assertEquals(layerObj.getString("name"), "newlayer");
		assertEquals(layerObj.getString("type"), LayerType.RASTER.toString());
	}

	@Test
	public void testGetUnexistingLayerGives404() throws Exception {
		ClientResponse response = getLayer("an-id-that-does-not-exist");
		assertEquals(ClientResponse.Status.NOT_FOUND,
				response.getClientResponseStatus());
	}

	private ClientResponse getLayerOk(String id) {
		ClientResponse response = getLayer(id);
		assertEquals(ClientResponse.Status.OK,
				response.getClientResponseStatus());
		return response;
	}

	private ClientResponse getLayer(String id) {
		WebResource webResource = resource();
		ClientResponse response = webResource.path("layers/" + id)
				.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		return response;
	}

	private ClientResponse createLayerOk(Layer layer) {
		ClientResponse response = createLayer(layer);
		assertEquals(ClientResponse.Status.CREATED,
				response.getClientResponseStatus());
		return response;
	}

	private ClientResponse createLayer(Layer layer) {
		WebResource webResource = resource();
		String jsonString = "{\"id\":" + jsonAttribute(layer.id) + ",\"name\":"
				+ jsonAttribute(layer.name) + ",\"type\":"
				+ jsonAttribute(layer.type) + "}";
		ClientResponse response = webResource.path("layers")
				.type(MediaType.APPLICATION_JSON).entity(jsonString)
				.post(ClientResponse.class);
		return response;
	}

	private String jsonAttribute(Object attribute) {
		if (attribute == null) {
			return "null";
		} else {
			return "\"" + attribute + "\"";
		}
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
