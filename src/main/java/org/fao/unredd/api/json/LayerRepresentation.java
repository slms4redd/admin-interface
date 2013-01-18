package org.fao.unredd.api.json;

import org.fao.unredd.api.model.LayerType;

public class LayerRepresentation extends AddLayerRequest {

	protected String id;
	protected String stgMosaicPath;
	protected String dissMosaicPath;
	protected String destOrigAbsPath;
	protected Integer pixelWidth;
	protected Integer pixelHeight;
	protected Double minx;
	protected Double maxx;
	protected Double miny;
	protected Double maxy;
	protected String data;

	/**
	 * Necessary for jackson
	 */
	public LayerRepresentation() {
	}

	/**
	 * Convenience constructor for setting only the id, name and type
	 * 
	 * @param id
	 * @param name
	 * @param type
	 */
	public LayerRepresentation(String id, String name, LayerType type) {
		this(id, name, type, null, null, null, null, null, null, null, null,
				null, null);
	}

	public LayerRepresentation(String id, String name, LayerType type,
			String stgMosaicPath, String dissMosaicPath,
			String destOrigAbsPath, Integer pixelWidth, Integer pixelHeight,
			Double minx, Double maxx, Double miny, Double maxy, String data) {
		super(name, type);
		this.id = id;
		this.stgMosaicPath = stgMosaicPath;
		this.dissMosaicPath = dissMosaicPath;
		this.destOrigAbsPath = destOrigAbsPath;
		this.pixelWidth = pixelWidth;
		this.pixelHeight = pixelHeight;
		this.minx = minx;
		this.maxx = maxx;
		this.miny = miny;
		this.maxy = maxy;
		this.data = data;
	}

	public String getId() {
		return id;
	}

	public String getStgMosaicPath() {
		return stgMosaicPath;
	}

	public String getDissMosaicPath() {
		return dissMosaicPath;
	}

	public String getDestOrigAbsPath() {
		return destOrigAbsPath;
	}

	public Integer getPixelWidth() {
		return pixelWidth;
	}

	public Integer getPixelHeight() {
		return pixelHeight;
	}

	public Double getMinx() {
		return minx;
	}

	public Double getMaxx() {
		return maxx;
	}

	public Double getMiny() {
		return miny;
	}

	public Double getMaxy() {
		return maxy;
	}

	public String getData() {
		return data;
	}

	public String getETag() {
		return id + name + type;
	}

}