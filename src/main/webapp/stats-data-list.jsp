<%@page import="it.geosolutions.unredd.geostore.UNREDDGeostoreManager"%>
<%@page import="org.fao.unredd.Util"%>
<%@page import="java.util.Map"%>
<%@page import="it.geosolutions.unredd.geostore.model.UNREDDStatsData"%>
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
        <title>Stats Data</title>
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <h1>Stats Data</h1>
        <table class="edit">
            <tr>
                <th>
                    id
                </th>
                <th>
                    name
                </th>
                <th>
                    StatsDef
                </th>
                <th>
                    year
                </th>
                <th>
                    month
                </th>
                <th>
                    show data
                </th>
                <!--
                <th>
                    reprocess
                </th>
                -->
            </tr>
            <% 
                List<Resource> resources = (List<Resource>)request.getAttribute("resources");
                for (Resource resource : resources) { %>
                    <tr>
                        <% UNREDDStatsData unreddStatsData = new UNREDDStatsData(resource); %>
                        <td><%= resource.getId() %></td>
                        <td><%= resource.getName() %></td>
                        <!-- <td><%= unreddStatsData.getAttribute(UNREDDStatsData.Attributes.STATSDEF) %></td> -->
                        
                        <%
                            UNREDDGeostoreManager manager = Util.getGeostoreManager(getServletContext());
                            String statsDefName = unreddStatsData.getAttribute(UNREDDStatsData.Attributes.STATSDEF);
                            Resource statsDefResource = manager.searchResourceByName(statsDefName);
                        %>
                        <td><a href="StatsDefShow?name=<%= statsDefResource.getName() %>"><%= statsDefResource.getName() %></a></td>
                        
                        <td><%= unreddStatsData.getAttribute(UNREDDStatsData.Attributes.YEAR) %></td>
                        <td><%= unreddStatsData.getAttribute(UNREDDStatsData.Attributes.MONTH) == null ? "-" : unreddStatsData.getAttribute(UNREDDStatsData.Attributes.MONTH) %></td>
                        <td><a href="StatsDataShow?id=<%= resource.getId() %>">[show data]</a></td>
                        <!--<td><a href="StatsDataReprocess?id=<%= resource.getId() %>">[reprocess]</a></td>-->
                    </tr>
                <% } %>
        </table>
    </body>
</html>
