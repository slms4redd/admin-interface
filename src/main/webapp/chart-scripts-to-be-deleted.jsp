<%@page import="it.geosolutions.unredd.services.data.ResourcePOJO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="admin-style.css" rel="stylesheet" type="text/css" />
        <title>Chart Scripts</title>
    </head>
    <body>
        <jsp:include page="header.jsp" /> 
        <h1>The following Chart Scripts must be deleted first:</h1>
        <table>
            <% for (ResourcePOJO chartScript : (List<ResourcePOJO>)request.getAttribute("chartScripts")) { %>
                <tr><td><%= chartScript.getName() %></td></tr>
            <% } %>
        </table>
        <br>
        <a href="ChartScriptList">Chart Scripts</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="StatsDefList">back</a>
    </body>
</html>
