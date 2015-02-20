<%@page import="it.geosolutions.unredd.services.data.utils.ResourceDecorator"%>
<%@page import="it.geosolutions.unredd.services.data.ModelDomainNames"%>
<%@page import="it.geosolutions.unredd.services.data.ResourcePOJO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="bodyContent">
        <h1>ChartScript edit</h1>
        
        <% ResourcePOJO res = (ResourcePOJO)request.getAttribute("resource"); %>
        
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
                        <select multiple="multiple" id="<%= ModelDomainNames.CHARTSCRIPT_STATDEF.getName() %>" name="<%= ModelDomainNames.CHARTSCRIPT_STATDEF.getName() %>">
                        <%
                        List<String> relatedStatDefs;
                        if (res != null) {
                            ResourceDecorator statsDef = new ResourceDecorator(res);
                            relatedStatDefs = statsDef.getAttributeValues(ModelDomainNames.CHARTSCRIPT_STATDEF);
                        } else {
                            relatedStatDefs = new ArrayList();
                        }
                        for (ResourcePOJO statsDef : (List<ResourcePOJO>)request.getAttribute("statsDefList")) { %>
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
                        <input id="<%= ModelDomainNames.CHARTSCRIPT_SCRIPTPATH.getName() %>" name="<%= ModelDomainNames.CHARTSCRIPT_SCRIPTPATH.getName() %>" type="text" value="<%= res == null ? "" : new ResourceDecorator(res).getAttributeValues(ModelDomainNames.CHARTSCRIPT_SCRIPTPATH).get(0)  %>">
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
</c:set>

<t:mainlayout>
	${bodyContent}
</t:mainlayout>