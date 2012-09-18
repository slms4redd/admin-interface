package org.fao.unredd;


import it.geosolutions.geostore.core.model.Resource;
import it.geosolutions.geostore.services.rest.GeoStoreClient;
import it.geosolutions.geostore.services.rest.model.RESTResource;
import it.geosolutions.unredd.geostore.model.UNREDDCategories;
import it.geosolutions.unredd.geostore.model.UNREDDLayer;
import javax.ws.rs.core.MediaType;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sgiaccio
 */
public class LayerBean {
    protected UNREDDLayer unreddLayer;
    
    protected long id;
    
    protected String name;
    
    protected String layerType;
    protected String mosaicPath;
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
    
    private boolean newLayer;
    protected String geostoreUrl, geostoreUserId, geostorePassword;
    
    
    public LayerBean() {
        newLayer = true;
        //unreddLayer = new UNREDDLayer();
    }
    
    public void initWithId(long id) throws Exception {
        GeoStoreClient client = new GeoStoreClient();
        client.setGeostoreRestUrl(geostoreUrl);
        client.setUsername(geostoreUserId);
        client.setPassword(geostorePassword);

        Resource resource = client.getResource(id);
        
        // throw exception if not a Layer
        if (!UNREDDCategories.LAYER.getName().equals(resource.getCategory().getName())) {
            throw new Exception("Resource with id=" + id + " is not a Layer");
        }
        
        newLayer = false;
        
        this.id = id;
        this.name = resource.getName();
        
        unreddLayer = new UNREDDLayer(resource);
        
        this.layerType            = unreddLayer.getAttribute(UNREDDLayer.Attributes.LAYERTYPE);
        this.mosaicPath           = unreddLayer.getAttribute(UNREDDLayer.Attributes.MOSAICPATH);
        this.origDataDestPath     = unreddLayer.getAttribute(UNREDDLayer.Attributes.ORIGDATADESTPATH);
        
        this.rasterPixelWidth     = (long)Double.parseDouble(unreddLayer.getAttribute(UNREDDLayer.Attributes.RASTERPIXELWIDTH));
        this.rasterPixelHeight    = (long)Double.parseDouble(unreddLayer.getAttribute(UNREDDLayer.Attributes.RASTERPIXELHEIGHT));
        this.rasterX0             = Double.parseDouble(unreddLayer.getAttribute(UNREDDLayer.Attributes.RASTERX0));
        this.rasterX1             = Double.parseDouble(unreddLayer.getAttribute(UNREDDLayer.Attributes.RASTERX1));
        this.rasterY0             = Double.parseDouble(unreddLayer.getAttribute(UNREDDLayer.Attributes.RASTERY0));
        this.rasterY1             = Double.parseDouble(unreddLayer.getAttribute(UNREDDLayer.Attributes.RASTERY1));
        
        this.data = client.getData(id, MediaType.WILDCARD_TYPE);;
                
        if ("vector".equals(layerType))
        {
            this.rasterAttribName     = unreddLayer.getAttribute(UNREDDLayer.Attributes.RASTERATTRIBNAME);
            this.rasterCqlFiletr      = unreddLayer.getAttribute(UNREDDLayer.Attributes.RASTERCQLFILTER);
            this.rasterNoData         = Double.parseDouble(unreddLayer.getAttribute(UNREDDLayer.Attributes.RASTERNODATA));
        }
        
        //unreddLayer = new UNREDDLayer(resource);
    }
    
    public void setGeostoreConnectionParams(String geostoreUrl, String geostoreUserId, String geostorePassword)
    {
        setGeostoreUrl(geostoreUrl);
        setGeostoreUserId(geostoreUserId);
        setGeostorePassword(geostorePassword);
    }
    
    public String getGeostorePassword() {
        return geostorePassword;
    }

    public void setGeostorePassword(String geostorePassword) {
        this.geostorePassword = geostorePassword;
    }

    public String getGeostoreUrl() {
        return geostoreUrl;
    }

    public void setGeostoreUrl(String geostoreUrl) {
        this.geostoreUrl = geostoreUrl;
    }

    public String getGeostoreUserId() {
        return geostoreUserId;
    }

    public void setGeostoreUserId(String geostoreUserId) {
        this.geostoreUserId = geostoreUserId;
    }
    
    public long getId()
    {
        return id;
    }
    
    public UNREDDLayer getResource()
    {
        return unreddLayer;
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

    public UNREDDLayer getUnreddLayer() {
        return unreddLayer;
    }

    public void setUnreddLayer(UNREDDLayer unreddLayer) {
        this.unreddLayer = unreddLayer;
    }
    
    public String getData() {
        return data;
    }
    
    public void setData(String data) {
        this.data = data;
    }
    
    public long save() {
        GeoStoreClient client = new GeoStoreClient();
        client.setGeostoreRestUrl(geostoreUrl);
        client.setUsername(geostoreUserId);
        client.setPassword(geostorePassword);
        
        unreddLayer.setAttribute(UNREDDLayer.Attributes.LAYERTYPE, layerType); // “raster” | “vector”
        unreddLayer.setAttribute(UNREDDLayer.Attributes.ORIGDATADESTPATH, origDataDestPath); // relative path where the geotiff has to be copied in
        unreddLayer.setAttribute(UNREDDLayer.Attributes.MOSAICPATH, mosaicPath); // relative path where the orig/data has to be moved in

        // attributes for vector layers: rasterization
        unreddLayer.setAttribute(UNREDDLayer.Attributes.RASTERATTRIBNAME, rasterAttribName); // name of the numeric feature attribute to set in the raster
        unreddLayer.setAttribute(UNREDDLayer.Attributes.RASTERCQLFILTER, rasterCqlFiletr); // optional CQL filter used to filter the features to be reported on the raster
        unreddLayer.setAttribute(UNREDDLayer.Attributes.RASTERNODATA, "" + rasterNoData); // nodata value for the raster
        unreddLayer.setAttribute(UNREDDLayer.Attributes.RASTERPIXELHEIGHT, "" + rasterPixelHeight);
        unreddLayer.setAttribute(UNREDDLayer.Attributes.RASTERPIXELWIDTH, "" + rasterPixelWidth);
        unreddLayer.setAttribute(UNREDDLayer.Attributes.RASTERX0, "" + rasterX0);
        unreddLayer.setAttribute(UNREDDLayer.Attributes.RASTERX1, "" + rasterX1);
        unreddLayer.setAttribute(UNREDDLayer.Attributes.RASTERY0, "" + rasterY0);
        unreddLayer.setAttribute(UNREDDLayer.Attributes.RASTERY1, "" + rasterY1);

        RESTResource layerRestResource = unreddLayer.createRESTResource();
        layerRestResource.setName(name);
        
        long newId = client.insert(layerRestResource); // insert
        //client.updateResource(id, layerRestResource); // update
        
        return newId;
    }
    
}
