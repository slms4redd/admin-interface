<%-- 
    Document   : layer-edit
    Created on : Dec 30, 2011, 3:29:40 PM
    Author     : sgiaccio
--%>

<%@page import="org.fao.unredd.LayerManager"%>
<%@page import="it.geosolutions.unredd.geostore.model.UNREDDLayer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="admin-style.css" rel="stylesheet" type="text/css" />
        <title>Edit Layer</title>
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
        <h1>Edit layer</h1>
        <h2>${layer.name}</h2>
        <form action="LayerEdit" method="POST">
            <table class="edit">
                <tr>
                    <th>
                        Layer type
                    </td>
                    <th>
                        ${layer.layerType}
                    </td>
                </tr>
                <tr>
                    <td title="Absolute path where the geotiff has to be copied in, during ingestion flow">
                        Staging mosaic path
                    </td>
                    <td>
                        <input type="text" name="<%= UNREDDLayer.Attributes.MOSAICPATH.getName() %>" id="<%= UNREDDLayer.Attributes.MOSAICPATH.getName() %>" value="${layer.mosaicPath}">
                    </td>
                </tr>
                <tr>
                    <td title="Absolute path where the geotiff has to be copied in, during publish flow">
                        Dissemination mosaic path
                    </td>
                    <td>
                        <input type="text" name="<%= UNREDDLayer.Attributes.DISSMOSAICPATH.getName() %>" id="<%= UNREDDLayer.Attributes.DISSMOSAICPATH.getName() %>" value="${layer.dissMosaicPath}">
                    </td>
                </tr>
                <tr>
                    <td title="Absolute path where the orig/ data has to be moved in">
                        Destination original data absolute path
                    </td>
                    <td>
                        <input type="text" name="<%= UNREDDLayer.Attributes.ORIGDATADESTPATH.getName() %>" id="<%= UNREDDLayer.Attributes.ORIGDATADESTPATH.getName() %>" value="${layer.origDataDestPath}"></input>
                    </td>
                </tr>
                <tr>
                    <td title="Raster width in pixels">
                        Pixel width
                    </td>
                    <td>
                        <input type="text" name="<%= UNREDDLayer.Attributes.RASTERPIXELWIDTH.getName() %>" id="<%= UNREDDLayer.Attributes.RASTERPIXELWIDTH.getName() %>" value="${layer.rasterPixelWidth}"></input>
                    </td>
                </tr>
                <tr>
                    <td title="Raster height in pixels">
                        Pixel height
                    </td>
                    <td>
                        <input type="text" name="<%= UNREDDLayer.Attributes.RASTERPIXELHEIGHT.getName() %>" id="<%= UNREDDLayer.Attributes.RASTERPIXELHEIGHT.getName() %>" value="${layer.rasterPixelHeight}"></input>
                    </td>
                </tr>
                <tr>
                    <td title="Min x bound">
                        Min x
                    </td>
                    <td>
                        <input type="text" name="<%= UNREDDLayer.Attributes.RASTERX0.getName() %>" id="<%= UNREDDLayer.Attributes.RASTERX0.getName() %>" value="${layer.rasterX0}"></input>
                    </td>
                </tr>
                <tr>
                    <td title="Max x bound">
                        Max x
                    </td>
                    <td>
                        <input type="text" name="<%= UNREDDLayer.Attributes.RASTERX1.getName() %>" id="<%= UNREDDLayer.Attributes.RASTERX1.getName() %>" value="${layer.rasterX1}"></input>
                    </td>
                </tr>
                <tr>
                    <td title="Min y bound">
                        Min y
                    </td>
                   <td>
                        <input type="text" name="<%= UNREDDLayer.Attributes.RASTERY0.getName() %>" id="<%= UNREDDLayer.Attributes.RASTERY0.getName() %>" value="${layer.rasterY0}"></input>
                    </td>
                </tr>
                <tr>
                    <td title="Max y bound">
                        Max y
                    </td>
                    <td>
                        <input type="text" name="<%= UNREDDLayer.Attributes.RASTERY1.getName() %>" id="<%= UNREDDLayer.Attributes.RASTERY1.getName() %>" value="${layer.rasterY1}"></input>
                    </td>
                </tr>
                <%
                    if ("vector".equals(((LayerManager)request.getAttribute("layer")).getLayerType())) {
                %>
                    <tr>
                        <td title="Name of the numeric feature attribute to set in the raster">
                            Attribute name
                        </td>
                        <td>
                            <input type="text" name="<%= UNREDDLayer.Attributes.RASTERATTRIBNAME.getName() %>" id="<%= UNREDDLayer.Attributes.RASTERATTRIBNAME.getName() %>" value="${layer.rasterAttribName}"></input>
                        </td>
                    </tr>
                    <tr>
                        <td title="Optional CQL filter used to filter the features to be reported on the raster">
                            CQL filter
                        </td>
                        <td>
                            <input type="text" name="<%= UNREDDLayer.Attributes.RASTERCQLFILTER.getName() %>" id="<%= UNREDDLayer.Attributes.RASTERCQLFILTER.getName() %>" value="${layer.rasterCqlFiletr}"></input>
                        </td>
                    </tr>
                    <tr>
                        <td title="No-data value for the raster">
                            No-data value
                        </td>
                        <td>
                            <input type="text" name="<%= UNREDDLayer.Attributes.RASTERNODATA.getName() %>" id="<%= UNREDDLayer.Attributes.RASTERNODATA.getName() %>" value="${layer.rasterNoData}"></input>
                        </td>
                    </tr>
                <% } %>
                <tr style="background-color:#fff">
                    <th>
                        Data
                    </th>
                    <td>
                        <textarea id="xml" name="xml">${layer.data}</textarea>
                    </td>
                </tr>
                <tr>
                    <td class="button_row" colspan="2">
                        <input type="button" onClick="window.location='LayerList'" value="Cancel">
                        <input type="submit">
                    </td>
                </tr>
            </table>
            <input type="hidden" name="<%= UNREDDLayer.Attributes.LAYERTYPE.getName() %>" value="${layer.layerType}">
            <input type="hidden" name="id" value="<%= request.getParameter("id") %>">
        </form>
    </body>
</html>
