package org.fao.unredd.api.json;

import org.fao.unredd.api.model.LayerType;

public class AddLayerRequest {

	protected String name;
	protected LayerType type;
	protected String stgMosaicPath;
	protected String dissMosaicPath;
	protected String destOrigAbsPath;
	protected Integer pixelWidth;
	protected Integer pixelHeight;
	protected Double minx;
	protected Double maxx;
	protected Double miny;
	protected Double maxy;

	public AddLayerRequest() {
		// Necessary for jackson
	}

	public AddLayerRequest(String name, LayerType type, String stgMosaicPath,
			String dissMosaicPath, String destOrigAbsPath, Integer pixelWidth,
			Integer pixelHeight, Double minx, Double maxx, Double miny,
			Double maxy) {
		super();
		this.name = name;
		this.type = type;
		this.stgMosaicPath = stgMosaicPath;
		this.dissMosaicPath = dissMosaicPath;
		this.destOrigAbsPath = destOrigAbsPath;
		this.pixelWidth = pixelWidth;
		this.pixelHeight = pixelHeight;
		this.minx = minx;
		this.maxx = maxx;
		this.miny = miny;
		this.maxy = maxy;
	}

	public String getName() {
		return name;
	}

	public LayerType getType() {
		return type;
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

}
