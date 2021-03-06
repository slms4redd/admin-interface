<%@page import="it.geosolutions.unredd.services.data.ResourcePOJO"%>
<%@page import="it.geosolutions.unredd.geostore.model.UNREDDLayer"%>
<%@page import="it.geosolutions.geostore.services.rest.model.RESTStoredData"%>
<%@page import="it.geosolutions.unredd.geostore.model.UNREDDStatsDef"%>
<%@page import="it.geosolutions.unredd.geostore.model.UNREDDResource"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="bodyContent">
        <script type="text/javascript">
            var confirmDelete = function(name) {
                return confirm("Are you sure you want to delete " + name + "?");
            }
        </script>
        <h1>Chart Scripts</h1>
        <table class="table table-hover table-striped">
	        <thead>
	            <tr>
	                <th>
	                    id
	                </th>
	                <th colspan="6">
	                    name
	                </th>
	            </tr>
            </thead>
            <tbody>
	            <% 
	                List<ResourcePOJO> list = (List<ResourcePOJO>)request.getAttribute("resources");
	                list = (list == null)? new ArrayList<ResourcePOJO>():list;
	                for (ResourcePOJO chartScript : list) { %>
	                    <tr>
	                        <td><%= chartScript.getId() %></td>
	                        <td><%= chartScript.getName() %></td>
	                        <td><a class="btn btn-primary btn-xs" role="button" href="ChartScriptShow?id=<%= chartScript.getId() %>">show</a></td>
	                        <td><a class="btn btn-primary btn-xs" role="button" href="ChartDataList?chart_script=<%= chartScript.getName() %>">chart data</a></td>
	                        <td><a class="btn btn-primary btn-xs" role="button" href="ChartScriptEditForm?id=<%= chartScript.getId() %>">edit</a></td>
	                        <td><a class="btn btn-success btn-xs" role="button" href="ChartScriptReprocess?id=<%= chartScript.getId() %>">run</a></td>
	                        <td><a class="btn btn-danger btn-xs" role="button" role="button" onclick="return confirmDelete('<%= chartScript.getName() %>')" href="ChartScriptDelete?id=<%= chartScript.getId() %>">delete</a></td>
	                    </tr>
	                <% } %>
               </tbody>
        </table>
        <div id="tools" class="text-center">
            <a class="btn btn-primary btn-sm" role="button" href="ChartScriptEditForm">Add Chart Script</a>
        </div>
</c:set>

<t:mainlayout>
	${bodyContent}
</t:mainlayout>
