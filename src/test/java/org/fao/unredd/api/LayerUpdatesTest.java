package org.fao.unredd.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.geosolutions.geostore.core.model.Attribute;
import it.geosolutions.geostore.core.model.Resource;
import it.geosolutions.geostore.services.dto.search.AndFilter;
import it.geosolutions.geostore.services.dto.search.CategoryFilter;
import it.geosolutions.geostore.services.dto.search.SearchFilter;
import it.geosolutions.geostore.services.rest.model.ResourceList;
import it.geosolutions.unredd.geostore.model.UNREDDCategories;
import it.geosolutions.unredd.geostore.model.UNREDDLayerUpdate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.fao.unredd.api.json.LayerUpdateRepresentation;
import org.fao.unredd.api.json.LayerUpdatesResponseRoot;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Test;
import org.mockito.Matchers;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class LayerUpdatesTest extends AbstractRestTest {

	@Test
	public void testGetLayerUpdate() {
		ResourceList resourceList = mockResourceList(
				mockLayerUpdate(0L, "layerupdate0", "forest_mask", "2000",
						null, null, "false"),
				mockLayerUpdate(1L, "layerupdate1", "forest_mask", "2010",
						null, null, "true"));
		mockGeostoreSearchAnswer(resourceList);

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
		ResourceList layerResponse = mockResourceList();
		ResourceList updatesResponse = mockResourceList(
				mockLayerUpdate(0L, "layerupdate0", "forest_mask", "2000",
						null, null, "false"),
				mockLayerUpdate(1L, "layerupdate1", "forest_mask", "2010",
						null, null, "true"));
		when(
				geostoreClient.searchResources(Matchers
						.argThat(new LayerCategorySearchMatcher(
								UNREDDCategories.LAYER)), anyInt(), anyInt(),
						anyBoolean(), anyBoolean())).thenReturn(layerResponse);
		when(
				geostoreClient.searchResources(Matchers
						.argThat(new LayerCategorySearchMatcher(
								UNREDDCategories.LAYERUPDATE)), anyInt(),
						anyInt(), anyBoolean(), anyBoolean())).thenReturn(
				updatesResponse);

		ClientResponse response = getLayerUpdates();
		assertEquals(404, response.getStatus());
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

	private final class LayerCategorySearchMatcher extends
			BaseMatcher<AndFilter> {
		private UNREDDCategories category;

		public LayerCategorySearchMatcher(UNREDDCategories category) {
			super();
			this.category = category;
		}

		@Override
		public boolean matches(Object item) {
			if (item instanceof AndFilter) {
				List<SearchFilter> filters = ((AndFilter) item).getFilters();
				for (SearchFilter searchFilter : filters) {
					if (searchFilter instanceof CategoryFilter) {
						if (category.getName().equals(
								((CategoryFilter) searchFilter).getName())) {
							return true;
						}
					}
				}
			}

			return false;
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("layer search");
		}
	}
}
