<%@page import="it.geosolutions.unredd.services.data.ResourcePOJO"%>
<%@page import="it.geosolutions.unredd.geostore.model.UNREDDLayer"%>
<%@page import="it.geosolutions.geostore.services.rest.model.RESTStoredData"%>
<%@page import="it.geosolutions.unredd.geostore.model.UNREDDStatsDef"%>
<%@page import="it.geosolutions.unredd.geostore.model.UNREDDResource"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="bodyContent">
        <h1>Chart Data - <%= request.getParameter("chart_script") %></h1>
            <% 
            	List<ResourcePOJO> resources = (List<ResourcePOJO>)request.getAttribute("chartData");
            	if(resources == null || resources.isEmpty()){ %>
            	    <p><b>No Chart data have been found... You should process the stats...<b></b></p>
            <%	    	    
            	}
            	else{
	                for (ResourcePOJO res : resources) { 
	        %>
	                	<a href="<%= request.getAttribute("geostoreURL") %>/misc/category/name/ChartData/resource/name/<%= res.getName() %>/data?name=<%= res.getName() %>"><%= res.getName() %></a><br>
            <%      }
            	}
            %>
        </table>
</c:set>

<t:mainlayout>
	${bodyContent}
</t:mainlayout>
