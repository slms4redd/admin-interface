package org.fao.unredd.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.geosolutions.geostore.core.model.Attribute;
import it.geosolutions.geostore.core.model.Resource;
import it.geosolutions.geostore.core.model.enums.DataType;
import it.geosolutions.geostore.services.dto.search.SearchFilter;
import it.geosolutions.geostore.services.rest.GeoStoreClient;
import it.geosolutions.geostore.services.rest.model.RESTResource;
import it.geosolutions.geostore.services.rest.model.ResourceList;
import it.geosolutions.unredd.geostore.model.UNREDDLayer;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.fao.unredd.api.json.AddLayerRequest;
import org.fao.unredd.api.model.LayerType;
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
	private GeoStoreClient geostoreClient;

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
		ResourceList resourceList = mockResourceList();
		mockGeostoreSearchAnswer(resourceList);

		ClientResponse response = getLayersOk();

		JSONObject root = response.getEntity(JSONObject.class);
		String firstAttribute = root.names().getString(0);
		JSONArray array = root.getJSONArray(firstAttribute);
		assertEquals(array.length(), 0);
	}

	@Test
	public void testGetOneLayer() throws Exception {
		ResourceList resourceList = mockResourceList(mockResource(1L,
				"newLayer", LayerType.RASTER));
		mockGeostoreSearchAnswer(resourceList);

		ClientResponse response = getLayersOk();

		JSONObject root = response.getEntity(JSONObject.class);
		String firstAttribute = root.names().getString(0);
		JSONArray array = root.getJSONArray(firstAttribute);
		assertEquals(array.length(), 1);
	}

	@Test
	public void testGetLayers() throws Exception {
		ResourceList resourceList = mockResourceList(
				mockResource(0L, "newLayer0", LayerType.RASTER),
				mockResource(1L, "newLayer1", LayerType.VECTOR));
		mockGeostoreSearchAnswer(resourceList);

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
		when(geostoreClient.insert(any(RESTResource.class))).thenReturn(12L);

		ClientResponse response = createLayerOk(new AddLayerRequest("newlayer",
				LayerType.RASTER));
		String location = response.getHeaders().getFirst("location");
		assertTrue(location.endsWith("/layers/12"));
	}

	@Test
	public void testGetLayer() throws Exception {
		mockGeostoreSearchAnswer(mockResourceList(mockResource(12L,
				"new_layer", LayerType.VECTOR)));

		// Check actual contents by expected path
		ClientResponse response = getLayerOk("12");
		JSONObject layerObj = response.getEntity(JSONObject.class);
		assertEquals(layerObj.getString("name"), "new_layer");
		assertEquals(layerObj.getString("type"), LayerType.VECTOR.toString());
	}

	@Test
	public void testGetUnexistingLayerGives404() throws Exception {
		mockGeostoreSearchAnswer(mockResourceList());

		ClientResponse response = getLayer("an-id-that-does-not-exist");
		assertEquals(ClientResponse.Status.NOT_FOUND,
				response.getClientResponseStatus());
	}

	private void mockGeostoreSearchAnswer(ResourceList resourceList) {
		when(
				geostoreClient.searchResources(any(SearchFilter.class),
						anyInt(), anyInt(), anyBoolean(), anyBoolean()))
				.thenReturn(resourceList);
	}

	private ResourceList mockResourceList(Resource... resources) {
		ResourceList resourceList = mock(ResourceList.class);
		ArrayList<Resource> list = new ArrayList<Resource>();
		for (Resource resource : resources) {
			list.add(resource);
		}
		when(resourceList.getList()).thenReturn(list);
		return resourceList;
	}

	private Resource mockResource(long id, String name, LayerType layerType) {
		Resource resource = mock(Resource.class);
		when(resource.getId()).thenReturn(id);
		when(resource.getName()).thenReturn(name);
		List<Attribute> attributes = createAttributeList(newAttribute(
				UNREDDLayer.Attributes.LAYERTYPE.getName(),
				layerType.toString()));
		when(resource.getAttribute()).thenReturn(attributes);
		return resource;
	}

	private List<Attribute> createAttributeList(Attribute layerType) {
		List<Attribute> ret = new ArrayList<Attribute>();
		ret.add(layerType);
		ret.add(newAttribute(UNREDDLayer.Attributes.RASTERY1.getName(), 1d));
		ret.add(newAttribute(UNREDDLayer.Attributes.RASTERY0.getName(), 0d));
		ret.add(newAttribute(UNREDDLayer.Attributes.RASTERX1.getName(), 1d));
		ret.add(newAttribute(UNREDDLayer.Attributes.RASTERX0.getName(), 0d));
		ret.add(newAttribute(
				UNREDDLayer.Attributes.RASTERPIXELHEIGHT.getName(), 1d));
		ret.add(newAttribute(UNREDDLayer.Attributes.RASTERPIXELWIDTH.getName(),
				1d));
		ret.add(newAttribute(UNREDDLayer.Attributes.ORIGDATADESTPATH.getName(),
				"OrigDataDestPath"));
		ret.add(newAttribute(UNREDDLayer.Attributes.DISSMOSAICPATH.getName(),
				"DissMosaicPath"));
		ret.add(newAttribute(UNREDDLayer.Attributes.MOSAICPATH.getName(),
				"MosaicPath"));

		return ret;
	}

	private Attribute newAttribute(String attributeName, String value) {
		Attribute attribute = new Attribute();
		attribute.setName(attributeName);
		attribute.setTextValue(value);
		attribute.setType(DataType.STRING);
		return attribute;
	}

	private Attribute newAttribute(String attributeName, Double value) {
		Attribute attribute = new Attribute();
		attribute.setName(attributeName);
		attribute.setNumberValue(value);
		attribute.setType(DataType.NUMBER);
		return attribute;
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
