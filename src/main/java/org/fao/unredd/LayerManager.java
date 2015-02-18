package org.fao.unredd;


import it.geosolutions.unredd.services.data.CategoryPOJO;
import it.geosolutions.unredd.services.data.ModelDomainNames;
import it.geosolutions.unredd.services.data.ResourcePOJO;
import it.geosolutions.unredd.services.data.utils.ResourceDecorator;

import java.io.IOException;


/**
 *
 * @author sgiaccio
 */
public class LayerManager {
    
    protected long id;
    
    protected String name;
    
    protected String layerType;
    protected String mosaicPath;
    protected String dissMosaicPath;
    protected String origDataDestPath;
    
    protected long   rasterPixelWidth;
    protected long   rasterPixelHeight;
    protected double rasterX0;
    protected double rasterX1;
    protected double rasterY0;
    protected double rasterY1;
    protected String rasterAttribName;
    protected String rasterCqlFiletr;
    protected double rasterNoData;
    
    protected String data;
    
    /**
     * TODO What's that? Why should be useful?
     */
    private boolean newLayer;
    
    public LayerManager() throws Exception {
        newLayer = true;
    }
    
    public LayerManager(ResourcePOJO resource) throws IOException {
        // throw exception if not a Layer
        if (!CategoryPOJO.LAYER.equals(resource.getCategory())) {
            throw new IOException("Resource with id='" + resource.getId() + "' is not a Layer");
        }
        newLayer = false;
        
        ResourceDecorator unreddLayer = new ResourceDecorator(resource);

        this.id = unreddLayer.getId();
        this.name = unreddLayer.getName();

        this.layerType = unreddLayer.getFirstAttributeValue(ModelDomainNames.LAYER_LAYERTYPE);
        this.mosaicPath = unreddLayer.getFirstAttributeValue(ModelDomainNames.LAYER_MOSAICPATH);
        this.dissMosaicPath = unreddLayer.getFirstAttributeValue(ModelDomainNames.LAYER_DISSMOSAICPATH);
        this.origDataDestPath = unreddLayer.getFirstAttributeValue(ModelDomainNames.LAYER_ORIGDATADESTPATH);
        this.rasterPixelWidth = (long) Double.parseDouble(unreddLayer.getFirstAttributeValue(ModelDomainNames.LAYER_RASTERPIXELWIDTH));
        this.rasterPixelHeight = (long) Double.parseDouble(unreddLayer.getFirstAttributeValue(ModelDomainNames.LAYER_RASTERPIXELHEIGHT));
        this.rasterX0 = Double.parseDouble(unreddLayer.getFirstAttributeValue(ModelDomainNames.LAYER_RASTERX0));
        this.rasterX1 = Double.parseDouble(unreddLayer.getFirstAttributeValue(ModelDomainNames.LAYER_RASTERX1));
        this.rasterY0 = Double.parseDouble(unreddLayer.getFirstAttributeValue(ModelDomainNames.LAYER_RASTERY0));
        this.rasterY1 = Double.parseDouble(unreddLayer.getFirstAttributeValue(ModelDomainNames.LAYER_RASTERY1));

        // Why retrieve it now?
        //this.data = client.getData(id, MediaType.WILDCARD_TYPE);

        if ("vector".equals(layerType)) {
            this.rasterAttribName = unreddLayer.getFirstAttributeValue(ModelDomainNames.LAYER_RASTERATTRIBNAME);
            this.rasterCqlFiletr = unreddLayer.getFirstAttributeValue(ModelDomainNames.LAYER_RASTERCQLFILTER);
            this.rasterNoData = Double.parseDouble(unreddLayer.getFirstAttributeValue(ModelDomainNames.LAYER_RASTERNODATA));
        }
    }
    
    public long getId()
    {
        return id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }

    public String getOrigDataDestPath() {
        return origDataDestPath;
    }

    public void setOrigDataDestPath(String destOrigRelativePath) {
        this.origDataDestPath = destOrigRelativePath;
    }

    public String getMosaicPath() {
        return mosaicPath;
    }

    public void setMosaicPath(String mosaicPath) {
        this.mosaicPath = mosaicPath;
    }

    public String getDissMosaicPath() {
        return dissMosaicPath;
    }

    public void setDissMosaicPath(String dissMosaicPath) {
        this.dissMosaicPath = dissMosaicPath;
    }

    public String getLayerType() {
        return layerType;
    }

    public void setLayerType(String layerType) {
        this.layerType = layerType;
    }

    public boolean isNewLayer() {
        return newLayer;
    }

    public void setNewLayer(boolean newLayer) {
        this.newLayer = newLayer;
    }

    public String getRasterAttribName() {
        return rasterAttribName;
    }

    public void setRasterAttribName(String rasterAttribName) {
        this.rasterAttribName = rasterAttribName;
    }

    public String getRasterCqlFiletr() {
        return rasterCqlFiletr;
    }

    public void setRasterCqlFiletr(String rasterCqlFiletr) {
        this.rasterCqlFiletr = rasterCqlFiletr;
    }

    public double getRasterNoData() {
        return rasterNoData;
    }

    public void setRasterNoData(double rasterNoData) {
        this.rasterNoData = rasterNoData;
    }

    public long getRasterPixelHeight() {
        return rasterPixelHeight;
    }

    public void setRasterPixelHeight(int rasterPixelHeight) {
        this.rasterPixelHeight = rasterPixelHeight;
    }

    public long getRasterPixelWidth() {
        return rasterPixelWidth;
    }

    public void setRasterPixelWidth(int rasterPixelWidth) {
        this.rasterPixelWidth = rasterPixelWidth;
    }

    public double getRasterX0() {
        return rasterX0;
    }

    public void setRasterX0(double rasterX0) {
        this.rasterX0 = rasterX0;
    }

    public double getRasterX1() {
        return rasterX1;
    }

    public void setRasterX1(double rasterX1) {
        this.rasterX1 = rasterX1;
    }

    public double getRasterY0() {
        return rasterY0;
    }

    public void setRasterY0(double rasterY0) {
        this.rasterY0 = rasterY0;
    }

    public double getRasterY1() {
        return rasterY1;
    }

    public void setRasterY1(double rasterY1) {
        this.rasterY1 = rasterY1;
    }
    
    public String getData() {
        return data;
    }
    
    public void setData(String data) {
        this.data = data;
    }
    
    public ResourcePOJO toResourcePOJO() {

        ResourcePOJO unreddLayerRes = new ResourcePOJO();
        ResourceDecorator unreddLayer = new ResourceDecorator(unreddLayerRes);
        
        unreddLayer.addTextAttribute(ModelDomainNames.LAYER_LAYERTYPE, layerType); //
        unreddLayer.addTextAttribute(ModelDomainNames.LAYER_ORIGDATADESTPATH, origDataDestPath); // relative path where the geotiff has to be copied in
        unreddLayer.addTextAttribute(ModelDomainNames.LAYER_MOSAICPATH, mosaicPath); // relative path where the orig/data has to be moved in

        // attributes for vector layers: rasterization
        unreddLayer.addTextAttribute(ModelDomainNames.LAYER_RASTERATTRIBNAME, rasterAttribName); // name of the numeric feature attribute to set in the raster
        unreddLayer.addTextAttribute(ModelDomainNames.LAYER_RASTERCQLFILTER, rasterCqlFiletr); // optional CQL filter used to filter the features to be reported on the raster
        unreddLayer.addTextAttribute(ModelDomainNames.LAYER_RASTERNODATA, "" + rasterNoData); // nodata value for the raster
        unreddLayer.addTextAttribute(ModelDomainNames.LAYER_RASTERPIXELHEIGHT, "" + rasterPixelHeight);
        unreddLayer.addTextAttribute(ModelDomainNames.LAYER_RASTERPIXELWIDTH, "" + rasterPixelWidth);
        unreddLayer.addTextAttribute(ModelDomainNames.LAYER_RASTERX0, "" + rasterX0);
        unreddLayer.addTextAttribute(ModelDomainNames.LAYER_RASTERX1, "" + rasterX1);
        unreddLayer.addTextAttribute(ModelDomainNames.LAYER_RASTERY0, "" + rasterY0);
        unreddLayer.addTextAttribute(ModelDomainNames.LAYER_RASTERY1, "" + rasterY1);
        unreddLayer.setName(name);
        
        return unreddLayerRes;
    }
    
}
