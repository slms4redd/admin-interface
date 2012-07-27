<%@page import="it.geosolutions.unredd.geostore.model.UNREDDChartScript"%>
<%@page import="java.util.ArrayList"%>
<%@page import="it.geosolutions.unredd.geostore.model.UNREDDStatsDef"%>
<%@page import="it.geosolutions.geostore.core.model.Resource"%><%@
page import="java.util.List"%><%@
page contentType="text/html" pageEncoding="UTF-8"%><!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="admin-style.css" rel="stylesheet" type="text/css" />
        <title>ChartScript</title>
    </head>
    <body>
        <h1>ChartScript</h1>
        
        <% Resource res = (Resource)request.getAttribute("resource"); %>
        
        <table class="edit">
            <tr>
                <th>
                    Name
                </th>
                <td>
                    <%= res.getName() %>
                </td>
            </tr>
            <tr>
                <th>
                    StatsDef
                </th>
                <td>
                    <%
                    List<String> relatedStatDefs;
                    if (res != null) {
                        UNREDDChartScript chartScript = new UNREDDChartScript(res);
                        relatedStatDefs = chartScript.getReverseAttributes(UNREDDChartScript.ReverseAttributes.STATSDEF.getName());
                    } else {
                        relatedStatDefs = new ArrayList();
                    }
                    for (String statsDef : relatedStatDefs) { %>
                        <a href="StatsDefShow?name=<%= statsDef %>"><%= statsDef %></a><br>
                    <% } %>
                </td>
            </tr>
            <tr>
                <th>
                    Script path
                </th>
                <td>
                    <%= res == null ? "" : new UNREDDChartScript(res).getAttribute(UNREDDChartScript.Attributes.SCRIPTPATH)  %>
                </td>
            </tr>
        </table>
        <div id="tools">
            <a href="ChartScriptEditForm?id=<%= res.getId() %>">[edit]</a>
        </div>
    </body>
</html>
