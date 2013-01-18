package org.fao.unredd.api.model.test;

import org.fao.unredd.api.model.LayerType;

public class TestLayer {

	private String id;
	private String name;
	private LayerType layerType;

	public TestLayer(String id, String name, LayerType layerType) {
		super();
		this.id = id;
		this.name = name;
		this.layerType = layerType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LayerType getLayerType() {
		return layerType;
	}

	public void setLayerType(LayerType layerType) {
		this.layerType = layerType;
	}

}
