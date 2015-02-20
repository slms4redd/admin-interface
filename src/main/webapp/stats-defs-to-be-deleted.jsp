<%@page import="java.util.List"%>
<%@page import="it.geosolutions.geostore.core.model.Resource"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="bodyContent">

        <h1>The following Stats Defs must be deleted first:</h1>
        <table>
            <% for (Resource statsDef : (List<Resource>)request.getAttribute("statsDefs")) { %>
                <tr><td><%= statsDef.getName() %></td></tr>
            <% } %>
        </table>
        <br>
        <a href="StatsDefList">Stats Defs</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="LayerList">back</a>
</c:set>

<t:mainlayout>
	${bodyContent}
</t:mainlayout>
        