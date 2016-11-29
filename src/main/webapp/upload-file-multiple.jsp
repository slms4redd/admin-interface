<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:set var="bodyContent">
	
    <form method="POST" action="UploadShape" enctype="multipart/form-data">
        File1 to upload: <input type="file" name="file" multiple ><br /> 
        Name1: <input type="text" name="name"><br /> <br />
        
        <script>        
        //$('#form').submit(function(eventObj) {
		//    $(this).append('<input type="text" name="field_name" value="value" /> ');
		//    return true;
		//});
        </script>
        
        <input type="submit" value="Upload"> Press here to upload the file!
    </form>
    
</c:set>

<t:mainlayout>
	${bodyContent}
</t:mainlayout>