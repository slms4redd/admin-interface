<%@page import="it.geosolutions.unredd.services.data.ModelDomainNames"%>
<%@page
	import="it.geosolutions.unredd.services.data.utils.ResourceDecorator"%>
<%@page import="it.geosolutions.unredd.services.data.ResourcePOJO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="bodyContent">
	<script type="text/javascript">
		var confirmDelete = function(name) {
			return confirm("Are you sure you want to delete " + name + "?");
		}
	</script>
	<h1>Layers</h1>
	<table class="table table-hover table-striped ">
		<thead>
			<tr>
				<th>id</th>
				<th>name</th>
				<th>type</th>
				<th>show</th>
				<th>layer updates</th>
				<th>edit</th>
				<th>delete</th>
			</tr>
		</thead>
		<tbody>
		<%
		    for (ResourcePOJO layer : (List<ResourcePOJO>) request.getAttribute("resources")) {
		            ResourceDecorator rd = new ResourceDecorator(layer);
		%>
			<tr>
				<td><%=layer.getId()%></td>
				<td><%=layer.getName()%></td>
				<td><%=rd.getFirstAttributeValue(ModelDomainNames.LAYER_LAYERTYPE)%></td>
				<td><a class="btn btn-primary btn-xs" role="button" href="LayerShow?id=<%=layer.getId()%>">show</a></td>
				<td>
					<%
					    // if (new UNREDDLayer(layer).getAttribute(UNREDDLayer.Attributes.RASTERATTRIBNAME) != null) { /* DEBUG */
					%>
					<%
					    //if (true) { /* DEBUG */
					%> <a class="btn btn-primary btn-xs" role="button"
					href="LayerUpdateList?layer=<%=layer.getName()%>">layer updates</a>
				</td>
				<td><a class="btn btn-primary btn-xs" role="button" href="LayerEditForm?id=<%=layer.getId()%>">edit</a></td>
				<td><a class="btn btn-danger btn-xs" role="button" onclick="return confirmDelete('<%=layer.getName()%>')"
					href="LayerDelete?name=<%=layer.getName()%>">delete</a></td>
			</tr>
		<%
		    }
		%>
		</tbody>
	</table>
	<div id="tools" class="text-center">
		Add layer: <a class="btn btn-primary btn-sm" role="button" href="layer-add.jsp?type=vector">vector</a> <a
			class="btn btn-primary btn-sm" role="button" href="layer-add.jsp?type=raster">raster</a>
	</div>
</c:set>

<t:mainlayout>
	${bodyContent}
</t:mainlayout>




