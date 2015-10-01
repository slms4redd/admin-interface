<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="bodyContent">
		
		<br />
		
		<form name="loginForm" action="<c:url value='j_spring_security_check' />" method='POST'>
			<div class="form-group">				
				<label for="username">User</label>
				<input type="text" id="username" name="j_username" value="">
				<label for="password">Password</label>
				<input type="password" id="password" name="j_password" />
				<input name="submit" type="submit" value="submit" />
			</div>
		</form>
		<c:if test="${param.error == true}">
		   <p class="bg-danger">The username or password you have entered is invalid.<p>
		</c:if>


</c:set>

<t:mainlayout>
	${bodyContent}
</t:mainlayout>