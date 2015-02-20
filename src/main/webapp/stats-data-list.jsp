<%@page import="it.geosolutions.unredd.services.data.ModelDomainNames"%>
<%@page import="it.geosolutions.unredd.services.data.utils.ResourceDecorator"%>
<%@page import="it.geosolutions.unredd.services.data.ResourcePOJO"%>
<%@page import="org.fao.unredd.Util"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="bodyContent">
    <body>
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
            	ResourcePOJO statsDefMap = (ResourcePOJO)request.getAttribute("statsDef");
                for (ResourcePOJO resource : resources) { 
                %>
                    <tr>
                        <% ResourceDecorator unreddStatsData = new ResourceDecorator(resource); %>
                        <td><%= unreddStatsData.getId() %></td>
                        <td><%= unreddStatsData.getName() %></td>
                         
                        <td><a href="StatsDefShow?name=<%= statsDefMap.getName() %>"><%= statsDefMap.getName() %></a></td>
                        
                        <td><%= unreddStatsData.getFirstAttributeValue(ModelDomainNames.STATS_DEF_YEAR) %></td>
                        <td><%= unreddStatsData.getFirstAttributeValue(ModelDomainNames.STATS_DEF_MONTH) == null ? "-" : unreddStatsData.getFirstAttributeValue(ModelDomainNames.STATS_DEF_MONTH) %></td>
                        <td><a href="StatsDataShow?id=<%= unreddStatsData.getId() %>">[show data]</a></td>
                        <!--<td><a href="StatsDataReprocess?id=<%= resource.getId() %>">[reprocess]</a></td>-->
                    </tr>
                <% } %>
        </table>
</c:set>

<t:mainlayout>
	${bodyContent}
</t:mainlayout>