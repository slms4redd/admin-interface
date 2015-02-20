<%@page import="it.geosolutions.unredd.services.data.ResourcePOJO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="bodyContent">
        <h1>The following Chart Scripts must be deleted first:</h1>
        <table>
            <% for (ResourcePOJO chartScript : (List<ResourcePOJO>)request.getAttribute("chartScripts")) { %>
                <tr><td><%= chartScript.getName() %></td></tr>
            <% } %>
        </table>
        <br>
        <a href="ChartScriptList">Chart Scripts</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="StatsDefList">back</a>
</c:set>

<t:mainlayout>
	${bodyContent}
</t:mainlayout>