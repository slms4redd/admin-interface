<%@page import="it.geosolutions.unredd.geostore.model.UNREDDLayerUpdate"%>
<%@page import="it.geosolutions.unredd.geostore.model.UNREDDLayer"%>
<%@page import="it.geosolutions.geostore.services.rest.model.RESTStoredData"%>
<%@page import="it.geosolutions.unredd.geostore.model.UNREDDStatsDef"%>
<%@page import="it.geosolutions.geostore.core.model.Resource"%><%@
page import="it.geosolutions.unredd.geostore.model.UNREDDResource"%><%@
page import="java.util.List"%><%@
page contentType="text/html" pageEncoding="UTF-8"%><!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="admin-style.css" rel="stylesheet" type="text/css" />
        <title>Layer Updates</title>
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <h1>Layer Updates</h1>
        <table class="edit">
            <tr>
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
            </tr>
            <% for (Resource layerUpdate : (List<Resource>)request.getAttribute("resources")) { %>
                <tr>
                    <% UNREDDLayerUpdate unreddLayerUpdate = new UNREDDLayerUpdate(layerUpdate); %>
                    <td><%= layerUpdate.getId() %></td>
                    <td><%= layerUpdate.getName() %></td>
                    <td><%= unreddLayerUpdate.getAttribute(UNREDDLayerUpdate.Attributes.LAYER) %></td>
                    <td><%= unreddLayerUpdate.getAttribute(UNREDDLayerUpdate.Attributes.YEAR) %></td>
                    <td><%= unreddLayerUpdate.getAttribute(UNREDDLayerUpdate.Attributes.MONTH) == null ? "&mdash;" : unreddLayerUpdate.getAttribute(UNREDDLayerUpdate.Attributes.MONTH) %></td>
                    <td><%= unreddLayerUpdate.getAttribute(UNREDDLayerUpdate.Attributes.DAY) == null ? "&mdash;" : unreddLayerUpdate.getAttribute(UNREDDLayerUpdate.Attributes.DAY) %></td>
                    <td>
                        <% if ("true".equals(unreddLayerUpdate.getAttribute(UNREDDLayerUpdate.Attributes.PUBLISHED))) { %>
                            <span style="color:green">yes</span>
                        <% } else { %>
                            <span style="color:red">no</span>
                        <% } %>
                    </td>
                    <td>
                        
                        <% if (!"true".equals(unreddLayerUpdate.getAttribute(UNREDDLayerUpdate.Attributes.PUBLISHED))) { %>
                            <a href="LayerUpdatePublish?action=publish&layerUpdateId=<%= layerUpdate.getId() %>">[publish]</a>
                        <% } else { %>
                            published
                        <% } %>
                    </td>
                    <td>
                        <% if ("true".equals(unreddLayerUpdate.getAttribute(UNREDDLayerUpdate.Attributes.PUBLISHED))) { %>
                            <a href="LayerUpdatePublish?action=publish&layerUpdateId=<%= layerUpdate.getId() %>">[republish]</a>
                        <% } else { %>
                            -
                        <% } %>
                    </td>
                    <td><a href="LayerUpdateReprocess?layerUpdateId=<%= layerUpdate.getId() %>">[reprocess]</a></td>
                    <!--
                        <td><a href="LayerUpdateShow?id=<%= layerUpdate.getId() %>">[show]</a></td>
                    -->
                </tr>
            <% } %>
            <tr>
                <td colspan="9" style="background-color:#fff"><a href="LayerList">&lt; Layers</tr></a>
            </tr>
        </table>
    </body>
</html>
