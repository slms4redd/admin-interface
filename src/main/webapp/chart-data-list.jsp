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
        <title>Chart Data</title>
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <h1>Chart Data - <%= request.getParameter("chart_script") %></h1>
            <% 
                for (Resource res : (List<Resource>)request.getAttribute("chartData")) { %>
                <a href="<%= request.getAttribute("geostoreURL") %>/misc/category/name/ChartData/resource/name/<%= res.getName() %>/data?name=<%= res.getName() %>"><%= res.getName() %></a><br>
            <% } %>
        </table>
    </body>
</html>
