<%@page import="it.geosolutions.unredd.services.data.ModelDomainNames"%>
<%@page import="it.geosolutions.unredd.services.data.utils.ResourceDecorator"%>
<%@page import="it.geosolutions.unredd.services.data.ResourcePOJO"%>
<%@page import="org.fao.unredd.Util"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
                List<ResourcePOJO> resources = (List<ResourcePOJO>)request.getAttribute("resources");
                for (ResourcePOJO resource : resources) { %>
                    <tr>
                        <% ResourceDecorator unreddStatsData = new ResourceDecorator(resource); %>
                        <td><%= resource.getId() %></td>
                        <td><%= resource.getName() %></td>
                        <!-- <td><%= unreddStatsData.getFirstAttributeValue(ModelDomainNames.ATTRIBUTES_STATSDEF) %></td> -->
                        
                        <p>TODO Search the stats def by name!!!</p>
                        <%-- <%
                            UNREDDGeostoreManager manager = Util.getGeostoreManager(getServletContext());
                            String statsDefName = unreddStatsData.getAttribute(UNREDDStatsData.Attributes.STATSDEF);
                            ResourcePOJO statsDefResource = manager.searchResourceByName(statsDefName);
                        %> 
                        <td><a href="StatsDefShow?name=<%= statsDefResource.getName() %>"><%= statsDefResource.getName() %></a></td>
                        
                        <td><%= unreddStatsData.getFirstAttributeValue(UNREDDStatsData.Attributes.YEAR) %></td>
                        <td><%= unreddStatsData.getFirstAttributeValue(UNREDDStatsData.Attributes.MONTH) == null ? "-" : unreddStatsData.getAttribute(UNREDDStatsData.Attributes.MONTH) %></td>
                        <td><a href="StatsDataShow?id=<%= resource.getId() %>">[show data]</a></td>
                        <!--<td><a href="StatsDataReprocess?id=<%= resource.getId() %>">[reprocess]</a></td>-->
                        
                        --%>
                    </tr>
                <% } %>
        </table>
    </body>
</html>
