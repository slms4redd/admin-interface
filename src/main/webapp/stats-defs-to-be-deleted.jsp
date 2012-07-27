<%@page import="java.util.List"%>
<%@page import="it.geosolutions.geostore.core.model.Resource"%>
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
        <h1>The following Stats Defs must be deleted first:</h1>
        <table>
            <% for (Resource statsDef : (List<Resource>)request.getAttribute("statsDefs")) { %>
                <tr><td><%= statsDef.getName() %></td></tr>
            <% } %>
        </table>
        <br>
        <a href="StatsDefList">Stats Defs</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="LayerList">back</a>
    </body>
</html>
