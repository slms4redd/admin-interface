<%@page import="org.fao.unredd.LayerManager"%>
<%@page import="it.geosolutions.unredd.geostore.model.UNREDDLayer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="bodyContent">
    <body onLoad="startXmlEditor()">
    	<script>
            function startXmlEditor() {
                var textarea = document.getElementById("xml");
                CodeMirror.fromTextArea(textarea ,
                {
                    height: "450px",
                    tabMode: "shift",
                    lineNumbers: true, 
                    textWrapping: false,
                    disableSpellcheck: true,
                    initCallback: function(ed) {
                       ed.win.document.body.style.fontSize = 12;
                    }
                }
            );
        }
        </script>
    	
        <h1><small>Edit layer</small> ${layer.name}</h1>
        <form action="LayerEdit" method="POST" class="form-horizontal">
       		<div class="form-group">
       			<label class="col-sm-2 control-label" for="layerType" title="">Layer type</label>
       			<div class="col-sm-7">
       				<input type="text" class="form-control" name="layerType" id="layerType" placeholder="${layer.layerType}" readonly="readonly" />
   				</div>
   				<div class="col-sm-3">
					<button type="button" class="btn btn-xs btn-info" data-toggle="popover" title="type" data-content="The type of this timeseries layer, could be RASTER or VECTOR">?</button>
				</div>
       		</div>	
            <div class="form-group">
				<label class="col-sm-2 control-label"  for="<%= UNREDDLayer.Attributes.MOSAICPATH.getName() %>"  title="Absolute path where the geotiff has to be copied in, during ingestion flow">Staging mosaic path</label>
				<div class="col-sm-7">
					<input type="text" class="form-control" name="<%= UNREDDLayer.Attributes.MOSAICPATH.getName() %>" id="<%= UNREDDLayer.Attributes.MOSAICPATH.getName() %>" placeholder="" value="${layer.mosaicPath}" />
				</div>
				<div class="col-sm-3">
					<button type="button" class="btn btn-xs btn-info" data-toggle="popover" title="Staging mosaic path" data-content="Absolute path where the geotiff has to be copied in, during ingestion flow">?</button>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"  for="<%= UNREDDLayer.Attributes.DISSMOSAICPATH.getName() %>" title="Absolute path where the geotiff has to be copied in, during publish flow">Dissemination mosaic path</label> 
				<div class="col-sm-7">
					<input type="text" class="form-control" name="<%= UNREDDLayer.Attributes.DISSMOSAICPATH.getName() %>" id="<%= UNREDDLayer.Attributes.DISSMOSAICPATH.getName() %>" placeholder="" value="${layer.dissMosaicPath}" />
				</div>
				<div class="col-sm-3">
					<button type="button" class="btn btn-xs btn-info" data-toggle="popover" title="Dissemination mosaic path" data-content="Absolute path where the geotiff has to be copied in, during publish flow">?</button>
				</div>	
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"  for="<%= UNREDDLayer.Attributes.ORIGDATADESTPATH.getName() %>" title="Absolute path where the orig/ data has to be moved in">Destination original data absolute path</label>
				<div class="col-sm-7">
					<input type="text" class="form-control" name="<%= UNREDDLayer.Attributes.ORIGDATADESTPATH.getName() %>" id="<%= UNREDDLayer.Attributes.ORIGDATADESTPATH.getName() %>" placeholder="" value="${layer.origDataDestPath}" />
				</div>
				<div class="col-sm-3">
					<button type="button" class="btn btn-xs btn-info" data-toggle="popover" title="Destination original data absolute path" data-content="Absolute path where the orig data has to be moved in">?</button>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"  for="<%= UNREDDLayer.Attributes.RASTERPIXELWIDTH.getName() %>" title="Raster width in pixels">Pixel width</label> 
				<div class="col-sm-7">	
					<input type="number" step="any" name="<%= UNREDDLayer.Attributes.RASTERPIXELWIDTH.getName() %>" class="form-control" id="<%= UNREDDLayer.Attributes.RASTERPIXELWIDTH.getName() %>" placeholder="" value="${layer.rasterPixelWidth}" />
				</div>
				<div class="col-sm-3">
					<button type="button" class="btn btn-xs btn-info" data-toggle="popover" title="Pixel width" data-content="Raster width in pixels">?</button>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"  for="<%= UNREDDLayer.Attributes.RASTERPIXELHEIGHT.getName() %>" title="Raster height in pixels">Pixel height</label>
				<div class="col-sm-7">
					<input type="number" step="any" name="<%= UNREDDLayer.Attributes.RASTERPIXELHEIGHT.getName() %>" class="form-control" id="<%= UNREDDLayer.Attributes.RASTERPIXELHEIGHT.getName() %>" placeholder="" value="${layer.rasterPixelHeight}" />
				</div>
				<div class="col-sm-3">
					<button type="button" class="btn btn-xs btn-info" data-toggle="popover" title="Pixel height" data-content="Raster height in pixels">?</button>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"  for="<%= UNREDDLayer.Attributes.RASTERX0.getName() %>" title="Min x bound">Min x</label> 
				<div class="col-sm-7">
					<input type="number" step="any" name="<%= UNREDDLayer.Attributes.RASTERX0.getName() %>" class="form-control" id="<%= UNREDDLayer.Attributes.RASTERX0.getName() %>" placeholder="" value="${layer.rasterX0}" />
				</div>
				<div class="col-sm-3">
					<button type="button" class="btn btn-xs btn-info" data-toggle="popover" title="Min X" data-content="Min X of the layer Bounding box, see OGC specs">?</button>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"  for="<%= UNREDDLayer.Attributes.RASTERX1.getName() %>" title="Max x bound">Max x</label>
				<div class="col-sm-7">
					<input type="number" step="any" name="<%= UNREDDLayer.Attributes.RASTERX1.getName() %>" class="form-control" id="<%= UNREDDLayer.Attributes.RASTERX1.getName() %>" placeholder="" value="${layer.rasterX1}" />
				</div>
				<div class="col-sm-3">
					<button type="button" class="btn btn-xs btn-info" data-toggle="popover" title="Max X" data-content="Max X of the layer Bounding box, see OGC specs">?</button>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"  for="<%= UNREDDLayer.Attributes.RASTERY0.getName() %>" title="Min y bound">Min y</label>
				<div class="col-sm-7">
					<input type="number" step="any" name="<%= UNREDDLayer.Attributes.RASTERY0.getName() %>" class="form-control" id="<%= UNREDDLayer.Attributes.RASTERY0.getName() %>" placeholder="" value="${layer.rasterY0}" />
				</div>
				<div class="col-sm-3">
					<button type="button" class="btn btn-xs btn-info" data-toggle="popover" title="Min Y" data-content="Min Y of the layer Bounding box, see OGC specs">?</button>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"  for="<%= UNREDDLayer.Attributes.RASTERY1.getName() %>" title="Max y bound">Max y</label>
				<div class="col-sm-7">
					<input type="number" step="any" name="<%= UNREDDLayer.Attributes.RASTERY1.getName() %>" class="form-control" id="<%= UNREDDLayer.Attributes.RASTERY1.getName() %>" placeholder="" value="${layer.rasterY1}" />
				</div>
				<div class="col-sm-3">
					<button type="button" class="btn btn-xs btn-info" data-toggle="popover" title="Max Y" data-content="Max Y of the layer Bounding box, see OGC specs">?</button>
				</div>
			</div>
			<%
                   if ("vector".equalsIgnoreCase(((LayerManager)request.getAttribute("layer")).getLayerType())) {
               %>
                <div class="form-group">
					<label class="col-sm-2 control-label"  for="<%= UNREDDLayer.Attributes.RASTERATTRIBNAME.getName() %>" title="Name of the numeric feature attribute to set in the raster">Attribute name</label>
					<div class="col-sm-7">
						<input type="text" name="<%= UNREDDLayer.Attributes.RASTERATTRIBNAME.getName() %>" class="form-control" id="<%= UNREDDLayer.Attributes.RASTERATTRIBNAME.getName() %>" placeholder="" value="${layer.rasterAttribName}" />
					</div>
					<div class="col-sm-3">
						<button type="button" class="btn btn-xs btn-info" data-toggle="popover" title="Attribute name" data-content="Name of the numeric feature attribute to set in the raster">?</button>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"  for="<%= UNREDDLayer.Attributes.RASTERCQLFILTER.getName() %>" title="Optional CQL filter used to filter the features to be reported on the raster">CQL filter</label>
					<div class="col-sm-7">	
						<input type="text" name="<%= UNREDDLayer.Attributes.RASTERCQLFILTER.getName() %>" class="form-control" id="<%= UNREDDLayer.Attributes.RASTERCQLFILTER.getName() %>" placeholder="" value="${layer.rasterCqlFiletr}" />
					</div>
					<div class="col-sm-3">
						<button type="button" class="btn btn-xs btn-info" data-toggle="popover" title="CQL filter" data-content="Optional CQL filter used to filter the features to be reported on the raster">?</button>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"  for="xml" title="No-data value for the raster">No-data value</label>
					<div class="col-sm-7">
						<input type="number" class="form-control" id="<%= UNREDDLayer.Attributes.RASTERNODATA.getName() %>" name="<%= UNREDDLayer.Attributes.RASTERNODATA.getName() %>" value="${layer.data}" />
					</div>
					<div class="col-sm-3">
						<button type="button" class="btn btn-xs btn-info" data-toggle="popover" title="No-data value" data-content="No-data value for the raster">?</button>
					</div>
				</div>
			<% } %>
			<div class="form-group">
				<label class="col-sm-2 control-label"  for="xml" title="">Data</label>
				<div class="col-sm-7">
					<textarea  name="xml"  class="form-control" id="xml" rows="3">${layer.data}</textarea>
				</div>
				<div class="col-sm-3">
					<button type="button" class="btn btn-xs btn-info" data-toggle="popover" title="Data" data-content="Free-text notes about this layer">?</button>
				</div>
			</div>
			<div class="text-center">
	            <input type="button" class="btn btn-danger btn-sm" onClick="window.location='LayerList'" value="Cancel">
	           	<input type="submit" class="btn btn-success btn-sm">
           	</div>
            <input type="hidden" name="<%= UNREDDLayer.Attributes.LAYERTYPE.getName() %>" value="${layer.layerType}">
            <input type="hidden" name="id" value="<%= request.getParameter("id") %>">
        </form>
</c:set>

<t:mainlayout>
	${bodyContent}
</t:mainlayout>        