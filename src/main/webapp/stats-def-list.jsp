<%@page import="it.geosolutions.unredd.services.data.ResourcePOJO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="bodyContent">
    	<script type="text/javascript">
            var confirmDelete = function(name) {
                return confirm("Are you sure you want to delete " + name + "?");
            }
        </script>
        <h1>Stat Defs<% if (request.getParameter("layer") != null) out.print(" - " + request.getParameter("layer")); %> </h1> 
        <table class="edit">
            <tr>
                <th>
                    id
                </th>
                <th>
                    name
                </th>
                <th>
                    show
                </th>
                <th>
                    stats data list
                </th>
                <th>
                    chart script list
                </th>
                <th>
                    edit
                </th>
                <th>
                    reporocess
                </th>
                <th>
                    delete
                </th>
            </tr>
            <% 
                for (ResourcePOJO statsDef : (List<ResourcePOJO>)request.getAttribute("resources")) { %>
                    <tr>
                        <td><%= statsDef.getId() %></td>
                        <td><%= statsDef.getName() %></td>
                        <td><a class="btn btn-primary btn-sm" role="button" href="StatsDefShow?name=<%= statsDef.getName() %>">show</a></td>
                        <td><a class="btn btn-primary btn-sm" role="button" href="StatsDataList?stats_def=<%= statsDef.getName() %>">stats data list</a></td>
                        <td><a class="btn btn-primary btn-sm" role="button" href="ChartScriptList?stats_def=<%= statsDef.getName() %>">chart script list</a></td>
                        <td><a class="btn btn-primary btn-sm" role="button" href="StatsDefEditForm?name=<%= statsDef.getName() %>">edit</a></td>
                        <td><a class="btn btn-success btn-sm" role="button" href="StatsDefReprocess?id=<%= statsDef.getId() %>">reprocess</a></td>
                        <td><a class="btn btn-danger btn-sm" role="button" onclick="return confirmDelete('<%= statsDef.getName() %>')" href="StatsDefDelete?statsDefName=<%= statsDef.getName() %>">delete</a></td>
                    </tr>
                <% } %>
        </table>
        <div id="tools">
            <a class="btn btn-default btn-sm" role="button" href="StatsDefEditForm">Add stat def</a>
            <a class="btn btn-default btn-sm" role="button" href="StatsDefList">Show all</a>
        </div>
</c:set>

<t:mainlayout>
	${bodyContent}
</t:mainlayout>