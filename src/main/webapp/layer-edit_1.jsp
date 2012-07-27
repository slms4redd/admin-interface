<%-- 
    Document   : layer-edit
    Created on : Dec 30, 2011, 3:29:40 PM
    Author     : sgiaccio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <jsp:useBean id="layer" class="org.fao.unredd.LayerBean" scope="request">
            <%-- this body is executed only if the bean is created --%>

            <%-- intialize bean properties --%>
            <%
                layer.initWithId(94);
            %>
        </jsp:useBean>
        <h1>Edit layer</h1>
        <form>
            <table>
                <tr>
                    <td title="Relative path where the geotiff has to be copied in">
                        Layer type
                    </td>
                    <td>
                        <jsp:getProperty name="layer" property="layerType" />
                    </td>
                </tr>
                <tr>
                    <td title="Relative path where the geotiff has to be copied in">
                        Destination relative path
                    </td>
                    <td>
                        <input type="text" name="destRelativePath" id="destRelativePath" value="<jsp:getProperty name="layer" property="destRelativePath" />">
                    </td>
                </tr>
                <tr>
                    <td title="Relative path where the orig/ data has to be moved in">
                        Destination original data relative path
                    </td>
                    <td>
                        <input type="text" name="destOrigRelativePath" id="destOrigRelativePath" value="<jsp:getProperty name="layer" property="destOrigRelativePath" />"></input>
                    </td>
                </tr>
                <tr>
                    <td title="Raster width in pixels">
                        Pixel width
                    </td>
                    <td>
                        <input type="text" name="rasterPixelWidth" id="rasterPixelWidth" value="<jsp:getProperty name="layer" property="rasterPixelWidth" />"></input>
                    </td>
                </tr>
                <tr>
                    <td title="Raster height in pixels">
                        Pixel height
                    </td>
                    <td>
                        <input type="text" name="rasterPixelHeight" id="rasterPixelHeight" value="<jsp:getProperty name="layer" property="rasterPixelHeight" />"></input>
                    </td>
                </tr>
                <tr>
                    <td title="Min x bound">
                        Min x
                    </td>
                    <td>
                        <input type="text" name="rasterX0" id="rasterX0" value="<jsp:getProperty name="layer" property="rasterX0" />"></input>
                    </td>
                </tr>
                <tr>
                    <td title="Max x bound">
                        Max x
                    </td>
                    <td>
                        <input type="text" name="rasterX1" id="rasterX1" value="<jsp:getProperty name="layer" property="rasterX1" />"></input>
                    </td>
                </tr>
                <tr>
                    <td title="Min y bound">
                        Min y
                    </td>
                   <td>
                        <input type="text" name="rasterY0" id="rasterY0" value="<jsp:getProperty name="layer" property="rasterY0" />"></input>
                    </td>
                </tr>
                <tr>
                    <td title="Max y bound">
                        Max y
                    </td>
                    <td>
                        <input type="text" name="rasterY1" id="rasterY1" value="<jsp:getProperty name="layer" property="rasterY1" />"></input>
                    </td>
                </tr>
                <tr>
                    <td title="Name of the numeric feature attribute to set in the raster">
                        Attribute name
                    </td>
                    <td>
                        <input type="text" name="rasterAttribName" id="rasterAttribName" value="<jsp:getProperty name="layer" property="rasterAttribName" />"></input>
                    </td>
                </tr>
                <tr>
                    <td title="Optional CQL filter used to filter the features to be reported on the raster">
                        CQL filter
                    </td>
                    <td>
                        <input type="text" name="rasterCqlFiletr" id="rasterCqlFiletr" value="<jsp:getProperty name="layer" property="rasterCqlFiletr" />"></input>
                    </td>
                </tr>
                <tr>
                    <td title="No-data value for the raster">
                        No-data value
                    </td>
                    <td>
                        <input type="text" name="rasterNoData" id="rasterNoData" value="<jsp:getProperty name="layer" property="rasterNoData" />"></input>
                    </td>
                </tr>
            </table>
            <input type="hidden" name="<%= UNREDDLayer.Attributes.LAYERTYPE.getName() %>" value="<jsp:getProperty name="layer" property="layerType" />">
            <input type="hidden" name="id" value="92">
        </form>
    </body>
</html>
