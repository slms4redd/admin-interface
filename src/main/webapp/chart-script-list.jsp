<%@page import="it.geosolutions.unredd.geostore.model.UNREDDLayer"%>
<%@page import="it.geosolutions.geostore.services.rest.model.RESTStoredData"%>
<%@page import="it.geosolutions.unredd.geostore.model.UNREDDStatsDef"%>
<%@page import="it.geosolutions.geostore.core.model.Resource"%>
<%@page import="it.geosolutions.unredd.geostore.model.UNREDDResource"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%><!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="admin-style.css" rel="stylesheet" type="text/css" />
        <title>Chart Scripts</title>
        <script type="text/javascript">
            var confirmDelete = function(name) {
                return confirm("Are you sure you want to delete " + name + "?");
            }
        </script>
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <h1>Chart Scripts</h1>
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
                    chart data
                </th>
                <th>
                    edit
                </th>
                <th>
                    run
                </th>
                <th>
                    delete
                </th>
            </tr>
            <% 
                List<Resource> list = (List<Resource>)request.getAttribute("resources");
                list = (list == null)? new ArrayList<Resource>():list;
                for (Resource chartScript : list) { %>
                    <tr>
                        <td><%= chartScript.getId() %></td>
                        <td><%= chartScript.getName() %></td>
                        <td><a href="ChartScriptShow?id=<%= chartScript.getId() %>">[show]</a></td>
                        <td><a href="ChartDataList?chart_script=<%= chartScript.getName() %>">[chart data]</a></td>
                        <td><a href="ChartScriptEditForm?id=<%= chartScript.getId() %>">[edit]</a></td>
                        <td><a href="ChartScriptReprocess?id=<%= chartScript.getId() %>">[run]</a></td>
                        <td><a onclick="return confirmDelete('<%= chartScript.getName() %>')" href="ChartScriptDelete?id=<%= chartScript.getId() %>">[delete]</a></td>
                    </tr>
                <% } %>
        </table>
        <div id="tools">
            <a href="ChartScriptEditForm">Add Chart Script</a>
        </div>
    </body>
</html>
