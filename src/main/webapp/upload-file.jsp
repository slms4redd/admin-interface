<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:set var="bodyContent">
	
    
    <div id="tools" class="text-center">
		Upload file: <a class="btn btn-primary btn-sm" role="button" href="upload-file-multiple.jsp?type=vector">vector</a> <a
			class="btn btn-primary btn-sm" role="button" href="upload-file-single.jsp?type=raster">raster</a>
	</div>
</c:set>

<t:mainlayout>
	${bodyContent}
</t:mainlayout>