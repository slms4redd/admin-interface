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
        <title>Stat Defs</title>
        <script type="text/javascript">
            var confirmDelete = function(name) {
                return confirm("Are you sure you want to delete " + name + "?");
            }
        </script>
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <h1>Stat Defs<% if (request.getParameter("layer") != null) out.print(" - " + request.getParameter("layer")); %> </h1> 
        <table class="edit">
            <tr>
                <th>
                    id
                </th>
                <th>
                    name
                </th>
                <th>
                    show
                </th>
                <th>
                    stats data list
                </th>
                <th>
                    chart script list
                </th>
                <th>
                    edit
                </th>
                <th>
                    reporocess
                </th>
                <th>
                    delete
                </th>
            </tr>
            <% 
                for (Resource statsDef : (List<Resource>)request.getAttribute("resources")) { %>
                    <tr>
                        <td><%= statsDef.getId() %></td>
                        <td><%= statsDef.getName() %></td>
                        <td><a href="StatsDefShow?name=<%= statsDef.getName() %>">[show]</a></td>
                        <td><a href="StatsDataList?stats_def=<%= statsDef.getName() %>">[stats data list]</a></td>
                        <td><a href="ChartScriptList?stats_def=<%= statsDef.getName() %>">[chart script list]</a></td>
                        <td><a href="StatsDefEditForm?name=<%= statsDef.getName() %>">[edit]</a></td>
                        <td><a href="StatsDefReprocess?id=<%= statsDef.getId() %>">[reprocess]</a></td>
                        <td><a onclick="return confirmDelete('<%= statsDef.getName() %>')" href="StatsDefDelete?statsDefName=<%= statsDef.getName() %>">[delete]</a></td>
                    </tr>
                <% } %>
        </table>
        <div id="tools">
            <a href="StatsDefEditForm">Add stat def</a>
            <br><br><a href="StatsDefList">Show all</a>
        </div>
    </body>
</html>
