<%@page import="it.geosolutions.unredd.geostore.model.UNREDDLayer"%>
<%@page import="it.geosolutions.geostore.services.rest.model.RESTStoredData"%>
<%@page import="it.geosolutions.unredd.geostore.model.UNREDDStatsDef"%>
<%@page import="it.geosolutions.geostore.core.model.Resource"%><%@
page import="it.geosolutions.unredd.geostore.model.UNREDDResource"%><%@
page import="java.util.List"%><%@
page contentType="text/html" pageEncoding="UTF-8"%><!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="admin-style.css" rel="stylesheet" type="text/css" />
        <title>Layers</title>
        <script type="text/javascript">
            var confirmDelete = function(name) {
                return confirm("Are you sure you want to delete " + name + "?");
            }
        </script>
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <h1>Layers</h1>
        <table class="edit">
            <tr>
                <th>
                    id
                </th>
                <th>
                    name
                </th>
                <th>
                    type
                </th>
                <th>
                    show
                </th>
                <th>
                    layer updates
                </th>
                <th>
                    edit
                </th>
                <th>
                    delete
                </th>
            </tr>
            <% 
                for (Resource layer : (List<Resource>)request.getAttribute("resources")) { %>
                    <tr>
                        <td><%= layer.getId() %></td>
                        <td><%= layer.getName() %></td>
                        <td><%= new UNREDDLayer(layer).getAttribute(UNREDDLayer.Attributes.LAYERTYPE) %></td>
                        <td><a href="LayerShow?id=<%= layer.getId() %>">[show]</a></td>
                        <td>
                            <% // if (new UNREDDLayer(layer).getAttribute(UNREDDLayer.Attributes.RASTERATTRIBNAME) != null) { /* DEBUG */ %>
                            <% //if (true) { /* DEBUG */ %>
                                <a href="LayerUpdateList?layer=<%= layer.getName() %>">[layer updates]</a>
                            <% //} %>
                        </td>
                        <td><a href="LayerEditForm?id=<%= layer.getId() %>">[edit]</a></td>
                        <td><a onclick="return confirmDelete('<%= layer.getName() %>')" href="LayerDelete?name=<%= layer.getName() %>">[delete]</a></td>
                    </tr>
                <% } %>
        </table>
        <div id="tools">
            Add layer: <a href="layer-add.jsp?type=vector">[vector]</a> <a href="layer-add.jsp?type=raster">[raster]</a>
        </div>
    </body>
</html>
