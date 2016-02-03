<%@page	import="it.geosolutions.unredd.services.data.utils.ResourceDecorator"%>
<%@page import="it.geosolutions.unredd.services.data.ModelDomainNames"%>
<%@page import="it.geosolutions.unredd.services.data.ResourcePOJO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="bodyContent">
	<h1>ChartScript edit</h1>

	<% ResourcePOJO res = (ResourcePOJO)request.getAttribute("resource"); %>

	<form action="ChartScriptEdit" method="POST" class="form-horizontal">
		<div class="form-group">
			<label class="col-sm-2 control-label" for="layerType" title="">Name</label>
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
					data-content="The name of this Chart script. Must be unique, short but at the same time descriptive. Although they are allowed, please don't use special charachters or whitespaces">?</button>
			</div>
		</div>

		<div class="form-group">
			<label class="col-sm-2 control-label" for="layerType" title="">StatsDef</label>
			<div class="col-sm-7">
				<select multiple="multiple" class="form-control"
					id="<%= ModelDomainNames.CHARTSCRIPT_STATDEF.getName() %>"
					name="<%= ModelDomainNames.CHARTSCRIPT_STATDEF.getName() %>">
					<%
						List<String> relatedStatDefs;
						if (res != null) {
						    ResourceDecorator statsDef = new ResourceDecorator(res);
						    relatedStatDefs = statsDef.getAttributeValues(ModelDomainNames.CHARTSCRIPT_STATDEF);
						} else {
						    relatedStatDefs = new ArrayList();
						}
						for (ResourcePOJO statsDef : (List<ResourcePOJO>)request.getAttribute("statsDefList")) { 
					%>
					<option id="<%= statsDef.getName() %>"
						<%
						if (relatedStatDefs.contains(statsDef.getName()))
						    out.write(" selected=\"selected\"");
					%>><%= statsDef.getName() %></option>
					<% } %>
				</select>
			</div>
			<div class="col-sm-3">
				<button type="button" class="btn btn-xs btn-info"
					data-toggle="popover" title="StatDef"
					data-content="The Statistics definitions associated to this script. After computing a statistic the system will generate charts based on this scripts.">?</button>
			</div>
		</div>

		<div class="form-group">
			<label class="col-sm-2 control-label" for="layerType" title="">Script
				path</label>
			<div class="col-sm-7">
				<input id="<%= ModelDomainNames.CHARTSCRIPT_SCRIPTPATH.getName() %>"
					class="form-control"
					name="<%= ModelDomainNames.CHARTSCRIPT_SCRIPTPATH.getName() %>"
					type="text"
					value="<%= res == null ? "" : new ResourceDecorator(res).getAttributeValues(ModelDomainNames.CHARTSCRIPT_SCRIPTPATH).get(0)  %>">
			</div>
			<div class="col-sm-3">
				<button type="button" class="btn btn-xs btn-info"
					data-toggle="popover" title="ScriptPath"
					data-content="The path on the server filesystem where the script is stored">?</button>
			</div>
		</div>

		<div class="text-center">
			<input class="btn btn-danger btn-sm" role="button" type="button"
				onClick="window.location='ChartScriptList'" value="Cancel">
			<input class="btn btn-success btn-sm" role="button" type="submit">
		</div>
		<% if (request.getParameter("id") != null) { %>
		<input type="hidden" name="id"
			value="<%= request.getParameter("id") %>"></input>
		<% } %>
	</form>
</c:set>

<t:mainlayout>
	${bodyContent}
</t:mainlayout>