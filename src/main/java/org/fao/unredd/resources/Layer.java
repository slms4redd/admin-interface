package org.fao.unredd.resources;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Layer {

	public String id;
	public String name;
	public LayerType type;

	public Layer() {
	}

	public Layer(String id, String name, LayerType type) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
	}

}
