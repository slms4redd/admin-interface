<%-- 
    Document   : layer-edit
    Created on : Dec 30, 2011, 3:29:40 PM
    Author     : sgiaccio
--%>

<%@page import="it.geosolutions.unredd.geostore.model.UNREDDLayer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="admin-style.css" rel="stylesheet" type="text/css" />
        <title>Add Layer</title>
        <script src="scripts/codemirror/codemirror.js"></script>
        <link rel="stylesheet" href="scripts/codemirror/codemirror.css">
        <script src="scripts/codemirror/mode/xml/xml.js"></script>
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
        <style type="text/css">
            .CodeMirror {
              width: 700px;
              border: 1px solid grey;
            }
            
            .CodeMirror-scroll {
                height: 600px;
            }
        </style>
    </head>
    <body onLoad="startXmlEditor()">
        <jsp:include page="header.jsp" />
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
                    <th title="Relative path where the geotiff has to be copied in">
                        Destination absolute path
                    </th>
                    <td>
                        <input type="text" name="<%= UNREDDLayer.Attributes.MOSAICPATH.getName() %>" id="<%= UNREDDLayer.Attributes.MOSAICPATH.getName() %>" value="">
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
    </body>
</html>
