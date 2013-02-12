package org.fao.unredd.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.geosolutions.geostore.core.model.Attribute;
import it.geosolutions.geostore.core.model.Resource;
import it.geosolutions.unredd.geostore.model.UNREDDLayerUpdate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.fao.unredd.api.json.LayerUpdateRepresentation;
import org.fao.unredd.api.json.LayerUpdatesResponseRoot;
import org.fao.unredd.api.model.LayerType;
import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class LayerUpdatesTest extends AbstractRestTest {

	@Test
	public void testGetLayerUpdateOfLayer() {
		mockLayerSearchAnswer(mockResourceList(mockLayer(1, "forest_mask",
				LayerType.RASTER)));
		mockLayerUpdateSearchAnswer(mockResourceList(
				mockLayerUpdate(0L, "layerupdate0", "forest_mask", "2000",
						null, null, "false"),
				mockLayerUpdate(1L, "layerupdate1", "forest_mask", "2010",
						null, null, "true")));

		ClientResponse response = getLayerUpdatesOk();

		LayerUpdatesResponseRoot root = response
				.getEntity(LayerUpdatesResponseRoot.class);
		Iterator<LayerUpdateRepresentation> layerIterator = root
				.getLayerUpdates().iterator();
		assertTrue(layerIterator.hasNext());
		LayerUpdateRepresentation layer = layerIterator.next();
		assertEquals("0", layer.getId());
		assertEquals("layerupdate0", layer.getName());

		assertTrue(layerIterator.hasNext());
		layer = layerIterator.next();
		assertEquals("1", layer.getId());
		assertEquals("layerupdate1", layer.getName());

		assertFalse(layerIterator.hasNext());
	}

	@Test
	public void testGetLayerUpdatesFromNonExistentLayer() throws Exception {
		mockLayerSearchAnswer(mockResourceList());

		ClientResponse response = getLayerUpdates();
		assertEquals(404, response.getStatus());
	}

	@Test
	public void testGetAllLayerUpdatesEmpty() {
		mockLayerUpdateSearchAnswer(mockResourceList());

		ClientResponse response = getAllLayerUpdates();
		LayerUpdatesResponseRoot root = response
				.getEntity(LayerUpdatesResponseRoot.class);
		assertFalse(root.getLayerUpdates().iterator().hasNext());
	}

	@Test
	public void testGetOneLayerUpdate() throws Exception {
		mockLayerUpdateSearchAnswer(mockResourceList(mockLayerUpdate(1L,
				"layerupdate1", "forest_mask", "2010", null, null, "true")));

		ClientResponse response = getAllLayerUpdates();

		LayerUpdatesResponseRoot root = response
				.getEntity(LayerUpdatesResponseRoot.class);
		Iterator<LayerUpdateRepresentation> layerIterator = root
				.getLayerUpdates().iterator();
		assertTrue(layerIterator.hasNext());
		layerIterator.next();
		assertFalse(layerIterator.hasNext());
	}

	@Test
	public void testGetAllLayerUpdates() throws Exception {
		mockLayerUpdateSearchAnswer(mockResourceList(
				mockLayerUpdate(0L, "layerupdate0", "forest_mask", "2000",
						null, null, "false"),
				mockLayerUpdate(1L, "layerupdate1", "forest_mask", "2010",
						null, null, "true")));

		ClientResponse response = getAllLayerUpdates();

		LayerUpdatesResponseRoot root = response
				.getEntity(LayerUpdatesResponseRoot.class);
		Iterator<LayerUpdateRepresentation> layerIterator = root
				.getLayerUpdates().iterator();
		assertTrue(layerIterator.hasNext());
		LayerUpdateRepresentation layer = layerIterator.next();
		assertEquals("0", layer.getId());
		assertEquals("layerupdate0", layer.getName());

		assertTrue(layerIterator.hasNext());
		layer = layerIterator.next();
		assertEquals("1", layer.getId());
		assertEquals("layerupdate1", layer.getName());

		assertFalse(layerIterator.hasNext());
	}

	private Resource mockLayerUpdate(long id, String name, String layerName,
			String year, String month, String day, String published) {
		Resource resource = mock(Resource.class);
		when(resource.getId()).thenReturn(id);
		when(resource.getName()).thenReturn(name);
		List<Attribute> attributes = createAttributeList(layerName, year,
				month, day, published);
		when(resource.getAttribute()).thenReturn(attributes);
		return resource;
	}

	private List<Attribute> createAttributeList(String layerName, String year,
			String month, String day, String published) {
		List<Attribute> ret = new ArrayList<Attribute>();
		ret.add(newAttribute(UNREDDLayerUpdate.Attributes.LAYER.getName(),
				layerName));
		ret.add(newAttribute(UNREDDLayerUpdate.Attributes.YEAR.getName(), year));
		ret.add(newAttribute(UNREDDLayerUpdate.Attributes.MONTH.getName(),
				month));
		ret.add(newAttribute(UNREDDLayerUpdate.Attributes.DAY.getName(), day));
		ret.add(newAttribute(UNREDDLayerUpdate.Attributes.PUBLISHED.getName(),
				published));
		return ret;
	}

	private ClientResponse getAllLayerUpdates() {
		WebResource webResource = resource();
		ClientResponse response = webResource.path("layerupdates").get(
				ClientResponse.class);
		assertEquals(ClientResponse.Status.OK,
				response.getClientResponseStatus());
		return response;
	}

	private ClientResponse getLayerUpdatesOk() {
		ClientResponse response = getLayerUpdates();
		assertEquals(ClientResponse.Status.OK,
				response.getClientResponseStatus());
		return response;
	}

	private ClientResponse getLayerUpdates() {
		WebResource webResource = resource();
		ClientResponse response = webResource.path("layers/1/layerupdates")
				.get(ClientResponse.class);
		return response;
	}
}
