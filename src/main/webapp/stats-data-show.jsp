<%@page import="it.geosolutions.unredd.services.data.ResourcePOJO"%>
<%@page import="java.util.ArrayList"%>
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
        
        <% ResourcePOJO res = (ResourcePOJO)request.getAttribute("resource"); %>
        
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
                    Data
                </th>
                <td>
                    <table class="stats_data_table">
                    <% String data = (String)request.getAttribute("storedData");
                    String[] lines = data.split(System.getProperty("line.separator"));
                    
                    for (int i = 0; i < lines.length; i++) {
                        out.print("<tr>");
                        String strar[] = lines[i].split(";");
                        for(int j = 0; j < strar.length; j++)
                            out.print("<td>" + strar[j]+ "</td>");
                        out.print("<tr>");
                    }
                    %>
                    </table>
                </td>
            </tr>
        </table>
    </body>
</html>
