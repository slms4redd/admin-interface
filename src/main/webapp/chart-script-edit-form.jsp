<%@page import="it.geosolutions.unredd.geostore.model.UNREDDChartScript"%>
<%@page import="java.util.ArrayList"%>
<%@page import="it.geosolutions.geostore.services.rest.model.RESTStoredData"%>
<%@page import="it.geosolutions.unredd.geostore.model.UNREDDStatsDef"%>
<%@page import="it.geosolutions.geostore.core.model.Resource"%><%@
page import="java.util.List"%><%@
page contentType="text/html" pageEncoding="UTF-8"%><!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="admin-style.css" rel="stylesheet" type="text/css" />
        <title>ChartScript edit</title>
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <h1>ChartScript edit</h1>
        
        <% Resource res = (Resource)request.getAttribute("resource"); %>
        
        <form action="ChartScriptEdit" method="POST">
            <table class="edit">
                <tr>
                    <th>
                        Name
                    </th>
                    <td>
                        <% if (res == null) { %>
                            <input type="text" id="name" name="name" value="${resource.name}">
                        <% } else { %>
                            ${resource.name}
                        <% } %>
                    </td>
                </tr>
                <tr>
                    <th>
                        StatsDef
                    </th>
                    <td>
                        <select multiple="multiple" id="<%= UNREDDChartScript.ReverseAttributes.STATSDEF.getName() %>" name="<%= UNREDDChartScript.ReverseAttributes.STATSDEF.getName() %>">
                        <%
                        List<String> relatedStatDefs;
                        if (res != null) {
                            UNREDDChartScript statsDef = new UNREDDChartScript(res);
                            relatedStatDefs = statsDef.getReverseAttributes(UNREDDChartScript.ReverseAttributes.STATSDEF.getName());
                        } else {
                            relatedStatDefs = new ArrayList();
                        }
                        for (Resource statsDef : (List<Resource>)request.getAttribute("statsDefList")) { %>
                            <option id="<%= statsDef.getName() %>"
                            <%
                            if (relatedStatDefs.contains(statsDef.getName()))
                                out.write(" selected=\"selected\"");
                            %>
                            ><%= statsDef.getName() %></option>
                        <% } %>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>
                        Script path
                    </th>
                    <td>
                        <input id="<%= UNREDDChartScript.Attributes.SCRIPTPATH.getName() %>" name="<%= UNREDDChartScript.Attributes.SCRIPTPATH.getName() %>" type="text" value="<%= res == null ? "" : new UNREDDChartScript(res).getAttribute(UNREDDChartScript.Attributes.SCRIPTPATH)  %>">
                    </td>
                </tr>
                <tr>
                    <td class="button_row" colspan="2">
                        <input type="button" onClick="window.location='ChartScriptList'" value="Cancel">
                        <input type="submit">
                    </td>
                </tr>
            </table>
            <% if (request.getParameter("id") != null) { %>
                <input type="hidden" name="id" value="<%= request.getParameter("id") %>"></input>
            <% } %>
        </form>
    </body>
</html>
