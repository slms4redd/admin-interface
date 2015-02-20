<%@page import="it.geosolutions.unredd.geostore.model.UNREDDLayer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="bodyContent">
        
        
        <!-- DamianoG 20/02/2015 Why we need this here???? -->
        <script>
            function startXmlEditor() {
                var textarea = document.getElementById("xml");
                CodeMirror.fromTextArea(textarea ,
                {
                    height: "450px",
                    tabMode: "shift",
                    lineNumbers: true, 
                    textWrapping: false,
                    disableSpellcheck: true,
                    initCallback: function(ed) {
                       ed.win.document.body.style.fontSize = 12;
                    }
                }
            );
        }
        </script>
        <%
            String layerType;
            if ("vector".equalsIgnoreCase(request.getParameter("type"))) {
                layerType = "VECTOR";
           } else {
                layerType = "RASTER";
           }
        %>
        <h1>Add layer</h1>
        <form action="LayerAdd" method="POST">
            <table class="edit">
                <tr>
                    <th title="Relative path where the geotiff has to be copied in">
                        Layer type
                    </th>
                    <td>
                        <%= layerType %>
                    </td>
                </tr>
                <tr>
                    <th title="Relative path where the geotiff has to be copied in">
                        Layer name
                    </th>
                    <td>
                        <input type="text" name="LayerName" id="LayerName" value="">
                    </td>
                </tr>
                <tr>
                    <th title="Absolute path where the geotiff has to be copied in, during ingestion flow">
                        Staging mosaic path
                    </th>
                    <td>
                        <input type="text" name="<%= UNREDDLayer.Attributes.MOSAICPATH.getName() %>" id="<%= UNREDDLayer.Attributes.MOSAICPATH.getName() %>" value="">
                    </td>
                </tr>
                <tr>
                    <th title="Absolute path where the geotiff has to be copied in, during publish flow">
                        Dissemination mosaic path
                    </th>
                    <td>
                        <input type="text" name="<%= UNREDDLayer.Attributes.DISSMOSAICPATH.getName() %>" id="<%= UNREDDLayer.Attributes.DISSMOSAICPATH.getName() %>" value="">
                    </td>
                </tr>
                <tr>
                    <th title="Absolute path where the orig/ data has to be moved in">
                        Destination original data absolute path
                    </th>
                    <td>
                        <input type="text" name="<%= UNREDDLayer.Attributes.ORIGDATADESTPATH.getName() %>" id="<%= UNREDDLayer.Attributes.ORIGDATADESTPATH.getName() %>" value=""></input>
                    </td>
                </tr>
                <tr>
                    <th title="Raster width in pixels">
                        Pixel width
                    </th>
                    <th>
                        <input type="text" name="<%= UNREDDLayer.Attributes.RASTERPIXELWIDTH.getName() %>" id="<%= UNREDDLayer.Attributes.RASTERPIXELWIDTH.getName() %>" value=""></input>
                    </th>
                </tr>
                <tr>
                    <th title="Raster height in pixels">
                        Pixel height
                    </th>
                    <td>
                        <input type="text" name="<%= UNREDDLayer.Attributes.RASTERPIXELHEIGHT.getName() %>" id="<%= UNREDDLayer.Attributes.RASTERPIXELHEIGHT.getName() %>" value=""></input>
                    </td>
                </tr>
                <tr>
                    <th title="Min x bound">
                        Min x
                    </th>
                    <td>
                        <input type="text" name="<%= UNREDDLayer.Attributes.RASTERX0.getName() %>" id="<%= UNREDDLayer.Attributes.RASTERX0.getName() %>" value=""></input>
                    </td>
                </tr>
                <tr>
                    <th title="Max x bound">
                        Max x
                    </th>
                    <td>
                        <input type="text" name="<%= UNREDDLayer.Attributes.RASTERX1.getName() %>" id="<%= UNREDDLayer.Attributes.RASTERX1.getName() %>" value=""></input>
                    </td>
                </tr>
                <tr>
                    <th title="Min y bound">
                        Min y
                    </th>
                    <td>
                        <input type="text" name="<%= UNREDDLayer.Attributes.RASTERY0.getName() %>" id="<%= UNREDDLayer.Attributes.RASTERY0.getName() %>" value=""></input>
                    </td>
                </tr>
                <tr>
                    <th title="Max y bound">
                        Max y
                    </th>
                    <td>
                        <input type="text" name="<%= UNREDDLayer.Attributes.RASTERY1.getName() %>" id="<%= UNREDDLayer.Attributes.RASTERY1.getName() %>" value=""></input>
                    </td>
                </tr>
                <% if ("vector".equals(layerType)) { %>
                    <tr>
                        <th title="Name of the numeric feature attribute to set in the raster">
                            Attribute name
                        </th>
                        <td>
                            <input type="text" name="<%= UNREDDLayer.Attributes.RASTERATTRIBNAME.getName() %>" id="<%= UNREDDLayer.Attributes.RASTERATTRIBNAME.getName() %>" value=""></input>
                        </td>
                    </tr>
                    <tr>
                        <th title="Optional CQL filter used to filter the features to be reported on the raster">
                            CQL filter
                        </th>
                        <td>
                            <input type="text" name="<%= UNREDDLayer.Attributes.RASTERCQLFILTER.getName() %>" id="<%= UNREDDLayer.Attributes.RASTERCQLFILTER.getName() %>" value=""></input>
                        </td>
                    </tr>
                    <tr>
                        <th title="No-data value for the raster">
                            No-data value
                        </th>
                        <td>
                            <input type="text" name="<%= UNREDDLayer.Attributes.RASTERNODATA.getName() %>" id="<%= UNREDDLayer.Attributes.RASTERNODATA.getName() %>" value=""></input>
                        </td>
                    </tr>
                    <% } %>
                <tr style="background-color:#fff">
                    <th>
                        Data
                    </th>
                    <td>
                        <textarea id="xml" name="xml"></textarea>
                    </td>
                </tr>
                <tr>
                    <td class="button_row" colspan="2">
                        <input type="button" onClick="window.location='LayerList'" value="Cancel">
                        <input type="submit">
                    </td>
                </tr>
            </table>
            <input type="hidden" name="<%= UNREDDLayer.Attributes.LAYERTYPE.getName() %>" value="<%= layerType %>">
        </form>
</c:set>

<t:mainlayout>
	${bodyContent}
</t:mainlayout>