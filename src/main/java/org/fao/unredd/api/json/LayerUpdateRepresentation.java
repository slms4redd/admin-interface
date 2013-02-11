package org.fao.unredd.api.json;

public class LayerUpdateRepresentation {

	private String id;
	private String name;
	private String layerName;
	private String year;
	private String month;
	private String day;
	private String published;

	/**
	 * Necessary for jackson
	 */
	LayerUpdateRepresentation() {
	}

	public LayerUpdateRepresentation(String id, String name, String layerName,
			String year, String month, String day, String published) {
		super();
		this.id = id;
		this.name = name;
		this.layerName = layerName;
		this.year = year;
		this.month = month;
		this.day = day;
		this.published = published;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getLayerName() {
		return layerName;
	}

	public String getYear() {
		return year;
	}

	public String getMonth() {
		return month;
	}

	public String getDay() {
		return day;
	}

	public String getPublished() {
		return published;
	}

}