<%@page import="it.geosolutions.unredd.geostore.model.UNREDDLayer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="bodyContent">


	<!-- DamianoG 20/02/2015 Why we need this here???? -->
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
	<%
            String layerType;
            if ("vector".equalsIgnoreCase(request.getParameter("type"))) {
                layerType = "VECTOR";
           } else {
                layerType = "RASTER";
           }
        %>
	<h1>Add layer</h1>
	<form action="LayerAdd" method="POST" class="form-horizontal">


		<div class="form-group">
			<label class="col-sm-2 control-label" for="layerType" title="">Layer
				type</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" name="layerType"
					id="layerType" value="<%= layerType %>" readonly="readonly" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="LayerName" title="">Layer
				name</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" name="LayerName" id="LayerName" />
			</div>
		</div>
		<div class="form-group">
			<!-- Absolute path where the geotiff has to be copied in, during ingestion flow -->
			<label class="col-sm-2 control-label" for="<%= UNREDDLayer.Attributes.MOSAICPATH.getName() %>" title="">Staging
				mosaic path</label>
			<div class="col-sm-10">
				<input type="text" class="form-control"
					name="<%= UNREDDLayer.Attributes.MOSAICPATH.getName() %>"
					id="<%= UNREDDLayer.Attributes.MOSAICPATH.getName() %>" value="">
			</div>
		</div>
		<div class="form-group">
			<!-- Absolute path where the geotiff has to be copied in, during publish flow -->
			<label class="col-sm-2 control-label" for="<%= UNREDDLayer.Attributes.DISSMOSAICPATH.getName() %>" title="">Dissemination
				mosaic path</label>
			<div class="col-sm-10">
				<input type="text" class="form-control"
					name="<%= UNREDDLayer.Attributes.DISSMOSAICPATH.getName() %>"
					id="<%= UNREDDLayer.Attributes.DISSMOSAICPATH.getName() %>"
					value="">
			</div>
		</div>
		<div class="form-group">
			<!-- Absolute path where the orig data has to be moved in -->
			<label class="col-sm-2 control-label" for="<%= UNREDDLayer.Attributes.ORIGDATADESTPATH.getName() %>" title="">Destination
				original data absolute path</label>
			<div class="col-sm-10">
				<input type="text" class="form-control"
					name="<%= UNREDDLayer.Attributes.ORIGDATADESTPATH.getName() %>"
					id="<%= UNREDDLayer.Attributes.ORIGDATADESTPATH.getName() %>"
					value="" />
			</div>
		</div>
		<div class="form-group">
			<!-- Raster width in pixels -->
			<label class="col-sm-2 control-label" for="<%= UNREDDLayer.Attributes.RASTERPIXELWIDTH.getName() %>" title="">Pixel
				width</label>
			<div class="col-sm-10">
				<input type="number" class="form-control"
					name="<%= UNREDDLayer.Attributes.RASTERPIXELWIDTH.getName() %>"
					id="<%= UNREDDLayer.Attributes.RASTERPIXELWIDTH.getName() %>"
					value=""></input>
			</div>
		</div>
		<div class="form-group">
			<!-- Raster height in pixels -->
			<label class="col-sm-2 control-label" for="<%= UNREDDLayer.Attributes.RASTERPIXELHEIGHT.getName() %>" title="">Pixel
				height</label>
			<div class="col-sm-10">
				<input type="number" class="form-control"
					name="<%= UNREDDLayer.Attributes.RASTERPIXELHEIGHT.getName() %>"
					id="<%= UNREDDLayer.Attributes.RASTERPIXELHEIGHT.getName() %>"
					value=""></input>
			</div>
		</div>
		<div class="form-group">
			<!-- Min x bound -->
			<label class="col-sm-2 control-label" for="<%= UNREDDLayer.Attributes.RASTERX0.getName() %>" title="">Min
				x</label>
			<div class="col-sm-10">
				<input type="number" class="form-control"
					name="<%= UNREDDLayer.Attributes.RASTERX0.getName() %>"
					id="<%= UNREDDLayer.Attributes.RASTERX0.getName() %>" value=""></input>
			</div>
		</div>
		<div class="form-group">
			<!-- Max x bound -->
			<label class="col-sm-2 control-label" for="<%= UNREDDLayer.Attributes.RASTERX1.getName() %>" title="">Max
				x</label>
			<div class="col-sm-10">
				<input type="number" class="form-control"
					name="<%= UNREDDLayer.Attributes.RASTERX1.getName() %>"
					id="<%= UNREDDLayer.Attributes.RASTERX1.getName() %>" value=""></input>
			</div>
		</div>
		<div class="form-group">
			<!-- Min y bound -->
			<label class="col-sm-2 control-label" for="<%= UNREDDLayer.Attributes.RASTERY0.getName() %>" title="">Min
				y</label>
			<div class="col-sm-10">
				<input type="number" class="form-control"
					name="<%= UNREDDLayer.Attributes.RASTERY0.getName() %>"
					id="<%= UNREDDLayer.Attributes.RASTERY0.getName() %>" value=""></input>
			</div>
		</div>
		<div class="form-group">
			<!-- Max y bound -->
			<label class="col-sm-2 control-label" for="<%= UNREDDLayer.Attributes.RASTERY1.getName() %>" title="">Max
				y</label>
			<div class="col-sm-10">
				<input type="number" class="form-control"
					name="<%= UNREDDLayer.Attributes.RASTERY1.getName() %>"
					id="<%= UNREDDLayer.Attributes.RASTERY1.getName() %>" value=""></input>
			</div>
		</div>
		<% if ("vector".equalsIgnoreCase(layerType)) { %>
			<div class="form-group">
				<!-- Name of the numeric feature attribute to set in the raster -->
				<label class="col-sm-2 control-label" for="<%= UNREDDLayer.Attributes.RASTERATTRIBNAME.getName() %>" title="">Attribute
					name</label>
				<div class="col-sm-10">
					<input type="text" class="form-control"
						name="<%= UNREDDLayer.Attributes.RASTERATTRIBNAME.getName() %>"
						id="<%= UNREDDLayer.Attributes.RASTERATTRIBNAME.getName() %>"
						value=""></input>
				</div>
			</div>
			<div class="form-group">
				<!-- Optional CQL filter used to filter the features to be reported on the raster -->
				<label class="col-sm-2 control-label" for="<%= UNREDDLayer.Attributes.RASTERCQLFILTER.getName() %>" title="">CQL
					filter</label>
				<div class="col-sm-10">
					<input type="text" class="form-control"
						name="<%= UNREDDLayer.Attributes.RASTERCQLFILTER.getName() %>"
						id="<%= UNREDDLayer.Attributes.RASTERCQLFILTER.getName() %>"
						value=""></input>
				</div>
			</div>
			<div class="form-group">
				<!-- No-data value for the raster -->
				<label class="col-sm-2 control-label" for="<%= UNREDDLayer.Attributes.RASTERNODATA.getName() %>" title="">No-data
					value</label>
				<div class="col-sm-10">
					<input type="text" class="form-control"
						name="<%= UNREDDLayer.Attributes.RASTERNODATA.getName() %>"
						id="<%= UNREDDLayer.Attributes.RASTERNODATA.getName() %>" value=""></input>
				</div>
			</div>
		<% } %>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="xml" title="">Data</label>
			<div class="col-sm-10">
				<textarea id="xml" class="form-control" rows="3" name="xml"></textarea>
			</div>
		</div>
		<div id="tools" class="text-center">
			<input type="button" class="btn btn-danger btn-sm"
				onClick="window.location='LayerList'" value="Cancel"> <input
				type="submit" class="btn btn-success btn-sm">
		</div>
		<input type="hidden"
			name="<%= UNREDDLayer.Attributes.LAYERTYPE.getName() %>"
			value="<%= layerType %>">
	</form>
</c:set>

<t:mainlayout>
	${bodyContent}
</t:mainlayout>