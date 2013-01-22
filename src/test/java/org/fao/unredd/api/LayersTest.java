package org.fao.unredd.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.fao.unredd.api.json.AddLayerRequest;
import org.fao.unredd.api.json.LayerRepresentation;
import org.fao.unredd.api.model.Layer;
import org.fao.unredd.api.model.LayerType;
import org.fao.unredd.api.model.Layers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;

public class LayersTest extends JerseyTest {

	@Autowired
	private Layers layers;

	public LayersTest() {
		super(
				new WebAppDescriptor.Builder("org.fao.unredd.api.resources",
						"org.codehaus.jackson.jaxrs")
						.contextParam("contextConfigLocation",
								"classpath:/adminTestApplicationContext.xml")
						.initParam("com.sun.jersey.config.property.packages",
								"org.fao.unredd.api.resources;org.codehaus.jackson.jaxrs")
						.contextPath("/admin")
						.servletClass(SpringServlet.class)
						.contextListenerClass(ContextLoaderListener.class)
						.requestListenerClass(RequestContextListener.class)
						.build());

		TestInjector.wire(this);
	}

	@Test
	public void testCreateLayerNullFail() throws Exception {
		AddLayerRequest request = new AddLayerRequest(null, null);
		ClientResponse response = createLayer(request);
		assertEquals(ClientResponse.Status.BAD_REQUEST,
				response.getClientResponseStatus());
		JSONArray errorList = response.getEntity(JSONArray.class);
		assertEquals(2, errorList.length());
	}

	@Test
	public void testGetEmptyLayers() throws Exception {
		when(layers.getJSON()).thenReturn(
				Collections.<LayerRepresentation> emptyList());

		ClientResponse response = getLayersOk();

		JSONObject root = response.getEntity(JSONObject.class);
		String firstAttribute = root.names().getString(0);
		JSONArray array = root.getJSONArray(firstAttribute);
		assertEquals(array.length(), 0);
	}

	@Test
	public void testGetOneLayer() throws Exception {
		LayerRepresentation layer = new LayerRepresentation("0", "newLayer",
				LayerType.RASTER);
		List<LayerRepresentation> layerList = Collections
				.<LayerRepresentation> singletonList((LayerRepresentation) layer);
		when(layers.getJSON()).thenReturn(layerList);

		ClientResponse response = getLayersOk();

		JSONObject root = response.getEntity(JSONObject.class);
		String firstAttribute = root.names().getString(0);
		JSONArray array = root.getJSONArray(firstAttribute);
		assertEquals(array.length(), 1);
	}

	@Test
	public void testGetLayers() throws Exception {
		ArrayList<LayerRepresentation> layerList = new ArrayList<LayerRepresentation>();
		layerList.add(new LayerRepresentation("0", "newLayer0",
				LayerType.RASTER));
		layerList.add(new LayerRepresentation("1", "newLayer1",
				LayerType.VECTOR));
		when(layers.getJSON()).thenReturn(layerList);

		ClientResponse response = getLayersOk();

		JSONObject root = response.getEntity(JSONObject.class);
		String firstAttribute = root.names().getString(0);
		JSONArray array = root.getJSONArray(firstAttribute);
		assertEquals(array.length(), 2);
		assertEquals(array.getJSONObject(0).getString("id"), "0");
		assertEquals(array.getJSONObject(0).getString("name"), "newLayer0");
		assertEquals(array.getJSONObject(1).getString("id"), "1");
		assertEquals(array.getJSONObject(1).getString("name"), "newLayer1");
	}

	@Test
	public void testCreateLayer() throws Exception {
		Layer layer = mock(Layer.class);
		when(layer.getJSON()).thenReturn(
				new LayerRepresentation("12", "newlayer", LayerType.RASTER));
		when(layers.addLayer(any(AddLayerRequest.class))).thenReturn(layer);

		ClientResponse response = createLayerOk(new AddLayerRequest("newlayer",
				LayerType.RASTER));
		String location = response.getHeaders().getFirst("location");

		assertTrue(location.endsWith("/layers/12"));
		verify(layers).addLayer(
				new AddLayerRequest("newlayer", LayerType.RASTER));
	}

	@Test
	public void testGetLayer() throws Exception {
		Layer layer = mock(Layer.class);
		when(layer.getJSON()).thenReturn(
				new LayerRepresentation("12", "new_layer", LayerType.VECTOR));
		when(layers.getLayer("12")).thenReturn(layer);

		// Check actual contents by expected path
		ClientResponse response = getLayerOk("12");
		JSONObject layerObj = response.getEntity(JSONObject.class);
		assertEquals(layerObj.getString("name"), "new_layer");
		assertEquals(layerObj.getString("type"), LayerType.VECTOR.toString());
	}

	@Test
	public void testGetUnexistingLayerGives404() throws Exception {
		when(layers.getLayer("an-id-that-does-not-exist")).thenThrow(
				new IllegalArgumentException());

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

	private ClientResponse createLayerOk(AddLayerRequest layer) {
		ClientResponse response = createLayer(layer);
		assertEquals(ClientResponse.Status.CREATED,
				response.getClientResponseStatus());
		return response;
	}

	private ClientResponse createLayer(AddLayerRequest layer) {
		String jsonString = "{\"name\":" + jsonAttribute(layer.getName())
				+ ",\"type\":" + jsonAttribute(layer.getType()) + "}";
		return createLayer(jsonString);
	}

	private ClientResponse createLayer(String jsonString) {
		WebResource webResource = resource();
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
