<%@page import="it.geosolutions.unredd.services.data.ModelDomainNames"%>
<%@page
	import="it.geosolutions.unredd.services.data.utils.ResourceDecorator"%>
<%@page import="it.geosolutions.unredd.services.data.ResourcePOJO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
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
                    //content: textarea.value,
                    tabMode: "shift",
                    //parserfile: "parsexml.js",
                    lineNumbers: true, 
                    textWrapping: false,
                    disableSpellcheck: true,
                    //stylesheet: "./resources/org.geoserver.web.wicket.CodeMirrorEditor/js/codemirror/css/xmlcolors.css",
                    //path: "./resources/org.geoserver.web.wicket.CodeMirrorEditor/js/codemirror/js/",
                    initCallback: function(ed) {
                       ed.win.document.body.style.fontSize = 12;
                    }
                }
            );
        }
        </script>
		<h1>StatsDef edit</h1>

		<%
        ResourcePOJO res = (ResourcePOJO)request.getAttribute("resource"); 
        List<String> relatedLayers;
        String zonalLayer;
        
        if (res != null) {
            ResourceDecorator statsDef = new ResourceDecorator(res);
            relatedLayers = statsDef.getAttributeValues(ModelDomainNames.STATS_DEF_LAYER);
            zonalLayer    = statsDef.getFirstAttributeValue(ModelDomainNames.STATS_DEF_ZONALLAYER);
        } else {
            relatedLayers = new ArrayList();
            zonalLayer    = null;
        }
        %>

		<form action="StatsDefEdit" method="POST" class="form-horizontal">
			<div class="form-group">
				<label class="col-sm-2 control-label" for="layerType" title="">
					Name</label>
				<div class="col-sm-7">

					<% if (res == null) { %>
					<input type="text" class="form-control" id="name" name="name"
						value="${resource.name}">
					<% } else { %>
					<input type="text" class="form-control" id="name" name="name"
						value="${resource.name}" readonly="readonly">
					<% } %>
				</div>
				<div class="col-sm-3">
					<button type="button" class="btn btn-xs btn-info"
						data-toggle="popover" title="Name"
						data-content="The name of this Statistic Definition. Must be unique, short but at the same time descriptive. Although they are allowed, please don't use special charachters or whitespaces">?</button>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="layerType" title="">
					Layers</label>
				<div class="col-sm-7">

					<select multiple="multiple" class="form-control"
						id="<%= ModelDomainNames.STATS_DEF_LAYER.getName() %>"
						name="<%= ModelDomainNames.STATS_DEF_LAYER.getName() %>">
						<%
	                        for (ResourcePOJO layer : (List<ResourcePOJO>)request.getAttribute("layerList")) { %>
						<option id="<%= layer.getName() %>"
							<%
	                            if (relatedLayers.contains(layer.getName()))
	                                out.write(" selected=\"selected\"");
	                            %>><%= layer.getName() %></option>
						<% } %>
					</select>
				</div>
				<div class="col-sm-3">
					<button type="button" class="btn btn-xs btn-info"
						data-toggle="popover" title="Layers"
						data-content="The Timeseries layers associated to this Statistic definition. After the Timeseries layer ingestion process is successfully finished the system will compute this statistic and all other statistic associated to a layer.">?</button>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="layerType" title="">
					Zonal Layer</label>
				<div class="col-sm-7">

					<select class="form-control"
						id="<%= ModelDomainNames.STATS_DEF_ZONALLAYER.getName() %>"
						name="<%= ModelDomainNames.STATS_DEF_ZONALLAYER.getName() %>">
						<%
	                        for (ResourcePOJO layer : (List<ResourcePOJO>)request.getAttribute("layerList")) { %>
						<option id="<%= layer.getName() %>"
							<%
	                            if ((layer.getName().equals(zonalLayer)))
	                                out.write(" selected=\"selected\"");
	                            %>><%= layer.getName() %></option>
						<% } %>
					</select>
				</div>
				<div class="col-sm-3">
					<button type="button" class="btn btn-xs btn-info"
						data-toggle="popover" title="Zonal layer"
						data-content="N/A Obsolete?">?</button>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="layerType" title="">
					XML</label>
				<div class="col-sm-10">

					<% String data = (String)request.getAttribute("storedData"); %>
					<textarea id="xml" name="xml"><%= data == null ? "" : data %></textarea>
				</div>
			</div>
			<div class="text-center">
				<input class="btn btn-danger btn-sm" role="button" type="button"
					onClick="window.location='StatsDefList'" value="Cancel"> <input
					class="btn btn-success btn-sm" role="button" type="submit">
			</div>
			<% if (request.getParameter("name") != null) { %>
			<input type="hidden" name="id" value="${resource.id}"></input> <input
				type="hidden" name="name" value="${resource.name}"></input>
			<% } %>
		</form>
</c:set>

<t:mainlayout>
	${bodyContent}
</t:mainlayout>
