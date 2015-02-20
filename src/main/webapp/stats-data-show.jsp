<%@page import="it.geosolutions.unredd.services.data.ResourcePOJO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="bodyContent">
    <body>
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
</c:set>

<t:mainlayout>
	${bodyContent}
</t:mainlayout>