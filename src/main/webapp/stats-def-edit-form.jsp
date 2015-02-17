<%@page import="it.geosolutions.unredd.services.data.ModelDomainNames"%>
<%@page import="it.geosolutions.unredd.services.data.utils.ResourceDecorator"%>
<%@page import="it.geosolutions.unredd.services.data.ResourcePOJO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="admin-style.css" rel="stylesheet" type="text/css" />
        <title>StatsDef edit</title>
        
        <script src="scripts/codemirror/codemirror.js"></script>
        <link rel="stylesheet" href="scripts/codemirror/codemirror.css">
        <script src="scripts/codemirror/mode/xml/xml.js"></script>
        <script>
            function startXmlEditor() {
                var textarea = document.getElementById("xml");
                CodeMirror.fromTextArea(textarea ,
                {
                    height: "450px",
                    //content: textarea.value,
                    tabMode: "shift",
                    //parserfile: "parsexml.js",
                    lineNumbers: true, 
                    textWrapping: false,
                    disableSpellcheck: true,
                    //stylesheet: "./resources/org.geoserver.web.wicket.CodeMirrorEditor/js/codemirror/css/xmlcolors.css",
                    //path: "./resources/org.geoserver.web.wicket.CodeMirrorEditor/js/codemirror/js/",
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
        <h1>StatsDef edit</h1>
        
        <%
        ResourcePOJO res = (ResourcePOJO)request.getAttribute("statDef"); 
        List<String> relatedLayers;
        String zonalLayer;
        
        if (res != null) {
            ResourceDecorator statsDef = new ResourceDecorator(res);
            relatedLayers = statsDef.getAttributeValues(ModelDomainNames.STATS_DEF_LAYER);
            zonalLayer    = statsDef.getFirstAttributeValue(ModelDomainNames.STATS_DEF_ZONALLAYER);
        } else {
            relatedLayers = new ArrayList();
            zonalLayer    = null;
        }
        %>
        
        <form action="StatsDefEdit" method="POST">
            <table class="edit">
                <tr>
                    <th>
                        Name
                    </th>
                    <td>
                        <% if (res == null) { %>
                            <input type="text" id="name" name="name" value="${statDef.name}">
                        <% } else { %>
                            ${statDef.name}
                        <% } %>
                    </td>
                </tr>
                <tr>
                    <th>
                        Layers
                    </th>
                    <td>
                        <select multiple="multiple" id="<%= ModelDomainNames.STATS_DEF_LAYER.getName() %>" name="<%= ModelDomainNames.STATS_DEF_LAYER.getName() %>">
                        <%
                        for (ResourcePOJO layer : (List<ResourcePOJO>)request.getAttribute("layerList")) { %>
                            <option id="<%= layer.getName() %>"
                            <%
                            if (relatedLayers.contains(layer.getName()))
                                out.write(" selected=\"selected\"");
                            %>
                            ><%= layer.getName() %></option>
                        <% } %>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>
                        Zonal Layer
                    </th>
                    <td>
                        <select id="<%= ModelDomainNames.STATS_DEF_ZONALLAYER.getName() %>" name="<%= ModelDomainNames.STATS_DEF_ZONALLAYER.getName() %>">
                        <%
                        for (ResourcePOJO layer : (List<ResourcePOJO>)request.getAttribute("layerList")) { %>
                            <option id="<%= layer.getName() %>"
                            <%
                            if ((layer.getName().equals(zonalLayer)))
                                out.write(" selected=\"selected\"");
                            %>
                            ><%= layer.getName() %></option>
                        <% } %>
                        </select>
                    </td>
                </tr>
                <tr style="background-color:#fff">
                    <th>
                        XML
                    </th>
                    <td>
                        <% String data = (String)request.getAttribute("storedData"); %>
                        <textarea id="xml" name="xml"><%= data == null ? "" : data %></textarea>
                    </td>
                </tr>
                <tr>
                    <td class="button_row" colspan="2">
                        <input type="button" onClick="window.location='StatsDefList'" value="Cancel">
                        <input type="submit">
                    </td>
                </tr>
            </table>
            <% if (request.getParameter("name") != null) { %>
                <input type="hidden" name="id" value="${statDef.id}"></input>
                <input type="hidden" name="name" value="${statDef.name}"></input>
            <% } %>
        </form>
    </body>
</html>
