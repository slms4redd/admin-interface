<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="java.util.ArrayList"%><%@
page import="it.geosolutions.geostore.services.rest.model.RESTStoredData"%><%@
page import="it.geosolutions.unredd.geostore.model.UNREDDStatsDef"%><%@
page import="it.geosolutions.geostore.core.model.Resource"%><%@
page import="java.util.List"%><%@
page contentType="text/html" pageEncoding="UTF-8"%><!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="admin-style.css" rel="stylesheet" type="text/css" />
        <title>StatsDef</title>
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <h1>StatsDef</h1>
        
        <%
        Resource res = (Resource)request.getAttribute("resource");
        %>
        
        <table class="edit">
            <tr>
                <th>
                    Name
                </th>
                <td>
                    ${resource.name}
                </td>
            </tr>
            <tr>
                <th>
                    Layers
                </th>
                <td>
                    <ul>
                        <%
                        for (Resource layer : (List<Resource>)request.getAttribute("relatedLayers")) { 
                        %>
                            <a href="LayerShow?id=<%= layer.getId() %>"><%= layer.getName() %></a>
                            <br>
                        <% } %>
                    </ul>
                </td>
            </tr>
            <tr>
                <th>
                    Chart Scripts
                </th>
                <td>
                    <ul>
                        <%
                        List<Resource> chartScripts = (List<Resource>)request.getAttribute("relatedChartScripts");
                        if(chartScripts != null){
                           for (Resource chartScript : chartScripts) {
                        %>
                            <a href="ChartScriptShow?id=<%= chartScript.getId() %>"><%= chartScript.getName() %></a>
                            <br>
                        <% }
                         }
                        %>
                    </ul>
                </td>
            </tr>
            <tr>
                <th>
                    Zonal Layer
                </th>
                <td>
                    <ul>
                       ${zonalLayer}
                    </ul>
                </td>
            </tr>
            <tr>
                <th>
                    XML
                </th>
                <td>
                    <% String data = (String)request.getAttribute("storedData"); %>
                    <pre><%= data == null ? "" : StringEscapeUtils.escapeHtml(data) %></pre>
                </td>
            </tr>
        </table>
        <div id="tools">
            <a href="StatsDefEditForm?name=<%= res.getName() %>">[edit]</a>
        </div>
    </body>
</html>
