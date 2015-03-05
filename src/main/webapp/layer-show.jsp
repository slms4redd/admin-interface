<%@page import="it.geosolutions.unredd.services.data.ResourcePOJO"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="java.util.List"%>
<%@page import="org.fao.unredd.servlet.StatsDefDelete"%>
<%@page import="org.fao.unredd.LayerManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="bodyContent">
        <h1><small>Layer name</small> ${layer.name}</h1>
        <table  class="table table-hover table-striped vertical-table table-condensed table-condensed">
        	<tbody>
	            <tr>
	                <th>
	                    Layer type
	                </th>
	                <td>
	                    ${layer.layerType}
	                </td>
	            </tr>
	            <tr>
	                <th title="Absolute path where the geotiff has to be copied in, during ingestion flow">
	                    Staging mosaic path
	                </th>
	                <td>
	                    ${layer.mosaicPath}
	                </td>
	            </tr>
	            <tr>
	                <th title="Absolute path where the geotiff has to be copied in, during publish flow">
	                    Dissemination mosaic path
	                </th>
	                <td>
	                    ${layer.dissMosaicPath}
	                </td>
	            </tr>
	            <tr>
	                <th title="Relative path where the orig/ data has to be moved in">
	                    Destination original data absolute path
	                </th>
	                <td>
	                    ${layer.origDataDestPath}
	                </td>
	            </tr>
	            <tr>
	                <th title="Raster width in pixels">
	                    Pixel width
	                </th>
	                <td>
	                    ${layer.rasterPixelWidth}
	                </td>
	            </tr>
	            <tr>
	                <th title="Raster height in pixels">
	                    Pixel height
	                </th>
	                <td>
	                    ${layer.rasterPixelHeight}
	                </td>
	            </tr>
	            <tr>
	                <th title="Min x bound">
	                    Min x
	                </th>
	                <td>
	                    ${layer.rasterX0}
	                </td>
	            </tr>
	            <tr>
	                <th title="Max x bound">
	                    Max x
	                </th>
	                <td>
	                    ${layer.rasterX1}
	                </td>
	            </tr>
	            <tr>
	                <th title="Min y bound">
	                    Min y
	                </th>
	                <td>
	                    ${layer.rasterY0}
	                </td>
	            </tr>
	            <tr>
	                <th title="Max y bound">
	                    Max y
	                </th>
	                <td>
	                    ${layer.rasterY1}
	                </td>
	            </tr>
	            <%
	                if ("vector".equals(((LayerManager)request.getAttribute("layer")).getLayerType())) {
	            %>
	                <tr>
	                    <th title="Name of the numeric feature attribute to set in the raster">
	                        Attribute name
	                    </th>
	                    <td>
	                        ${layer.rasterAttribName}
	                    </td>
	                </tr>
	                <tr>
	                    <th title="Optional CQL filter used to filter the features to be reported on the raster">
	                        CQL filter
	                    </th>
	                    <td>
	                        ${layer.rasterCqlFiletr}
	                    </td>
	                </tr>
	                <tr>
	                    <th title="No-data value for the raster">
	                        No-data value
	                    </th>
	                    <td>
	                        ${layer.rasterNoData}
	                    </td>
	                </tr>
	            <% } %>
	            <tr>
	                <th title="No-data value for the raster">
	                    Layer Updates <a class="btn btn-primary btn-xs" role="button" href="LayerUpdateList?layer=${layer.name}">open</a>
	                </th>
	                <td>
	                    <%
	                        List<ResourcePOJO> layerUpdates = (List<ResourcePOJO>)request.getAttribute("layerUpdates");
	                        for (ResourcePOJO res : layerUpdates) {
	                    %>
	                            <%= res.getName() %>
	                            <br>
	                    <%
	                        }
	                    %>
	                </td>
	            </tr>
	            <tr>
	                <th title="No-data value for the raster">
	                    Stats Defs <a class="btn btn-primary btn-xs" role="button" href="StatsDefList?layer=${layer.name}">open</a>
	                </th>
	                <td>
	                    <%
	                        List<ResourcePOJO> statsDefs = (List<ResourcePOJO>)request.getAttribute("statsDefs");
	                        for (ResourcePOJO res : statsDefs) {
	                    %>
	                            <a href="StatsDefShow?name=<%= res.getName() %>"><%= res.getName() %></a>
	                            <br>
	                    <%
	                        }
	                    %>
	                </td>
	            </tr>
	            <tr>
	                <th title="Name of the numeric feature attribute to set in the raster">
	                    Data
	                </th>
	                <td>
	                    <pre><%= StringEscapeUtils.escapeHtml((String)request.getAttribute("storedData")) %></pre>
	                </td>
	            </tr>
            </tbody>
        </table>
        <div id="tools">
            <a class="btn btn-primary btn-sm" role="button" href="LayerEditForm?id=${layer.id}">edit</a>
        </div>
</c:set>

<t:mainlayout>
	${bodyContent}
</t:mainlayout>