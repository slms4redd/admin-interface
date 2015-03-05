<%@page import="it.geosolutions.unredd.services.data.ModelDomainNames"%>
<%@page import="it.geosolutions.unredd.services.data.utils.ResourceDecorator"%>
<%@page import="it.geosolutions.unredd.services.data.ResourcePOJO"%>
<%@page import="it.geosolutions.unredd.geostore.model.UNREDDLayerUpdate"%>
<%@page import="it.geosolutions.unredd.geostore.model.UNREDDLayer"%>
<%@page import="it.geosolutions.geostore.services.rest.model.RESTStoredData"%>
<%@page import="it.geosolutions.unredd.geostore.model.UNREDDStatsDef"%>
<%@page import="it.geosolutions.unredd.geostore.model.UNREDDResource"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%><!DOCTYPE html>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="bodyContent">
        <h1>Layer Updates</h1>
        <table class="table table-hover table-striped">
            <tr>
            	<thead>
	                <th>
	                    id
	                </th>
	                <th>
	                    name
	                </th>
	                <th>
	                    layer
	                </th>
	                <th>
	                    year
	                </th>
	                <th>
	                    month
	                </th>
	                <th>
	                    day
	                </th>
	                <th>
	                    published
	                </th>
	                <th>
	                    publish
	                </th>
	                <th>
	                    republish
	                </th>
	                <th>
	                    flow
	                </th>
	                <!--
	                <th>
	                    show
	                </th>
	                -->
                </thead>
            </tr>
            <tbody>
	            <% for (ResourcePOJO layerUpdate : (List<ResourcePOJO>)request.getAttribute("resources")) { %>
	                <tr>
	                    <% ResourceDecorator unreddLayerUpdate = new ResourceDecorator(layerUpdate); %>
	                    <td><%= layerUpdate.getId() %></td>
	                    <td><%= layerUpdate.getName() %></td>
	                    <td><%= unreddLayerUpdate.getFirstAttributeValue(ModelDomainNames.ATTRIBUTES_LAYER) %></td>
	                    <td><%= unreddLayerUpdate.getFirstAttributeValue(ModelDomainNames.LAYERUPDATE_YEAR) %></td>
	                    <td><%= unreddLayerUpdate.getFirstAttributeValue(ModelDomainNames.LAYERUPDATE_MONTH) == null ? "&mdash;" : unreddLayerUpdate.getFirstAttributeValue(ModelDomainNames.LAYERUPDATE_MONTH) %></td>
	                    <td><%= unreddLayerUpdate.getFirstAttributeValue(ModelDomainNames.LAYERUPDATE_DAY) == null ? "&mdash;" : unreddLayerUpdate.getFirstAttributeValue(ModelDomainNames.LAYERUPDATE_MONTH) %></td>
	                    <td>
	                        <% boolean published = "true".equals(unreddLayerUpdate.getFirstAttributeValue(ModelDomainNames.LAYERUPDATE_PUBLISHED));
	                           if (published) { %>
	                            <span style="color:green">yes</span>
	                        <% } else { %>
	                            <span style="color:red">no</span>
	                        <% } %>
	                    </td>
	                    <td>
	                        
	                        <% if (!published) { %>
	                            <a class="btn btn-primary btn-xs" role="button" href="LayerUpdatePublish?action=publish&layerUpdateId=<%= layerUpdate.getId() %>">publish</a>
	                        <% } else { %>
	                            published
	                        <% } %>
	                    </td>
	                    <td>
	                        <% if (published) { %>
	                            <a class="btn btn-primary btn-xs" role="button" href="LayerUpdatePublish?action=publish&layerUpdateId=<%= layerUpdate.getId() %>">republish</a>
	                        <% } else { %>
	                            -
	                        <% } %>
	                    </td>
	                    <td><a class="btn btn-primary btn-xs" role="button" href="LayerUpdateReprocess?layerUpdateId=<%= layerUpdate.getId() %>">reprocess</a></td>
	                    <!--
	                        <td><a href="LayerUpdateShow?id=<%= layerUpdate.getId() %>">[show]</a></td>
	                    -->
	                </tr>
	            <% } %>
	            <tr>
	                <td colspan="9" style="background-color:#fff"><a class="btn btn-primary btn-sm" role="button" href="LayerList">&lt; Layers</tr></a>
	            </tr>
            </tbody>
        </table>
</c:set>

<t:mainlayout>
	${bodyContent}
</t:mainlayout>