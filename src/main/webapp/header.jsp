<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%-- Determine the page name --%>
<c:set var="fullPageName"
	value="${fn:split(pageContext.request.requestURI, '/')}" />
<c:set var="fullPageLink"
	value="${fullPageName[fn:length(fullPageName)-1]}" />
<c:set var="pageName"
	value="${fn:substringBefore(fullPageName[fn:length(fullPageName)-1],'.jsp')}" />

<%-- NOTE ABOUT NavBar and Breadcrumb components 
The navBar Links and the Breadcrumb structure are implicitly extracted from the JSPs names.
Please follow the existing name convention when adding new pages in order to keep valid these components.

ISSUE1: At the moment the main sections have no way to be identified as is so are displayed in the Breadcrumb followed by a fake subsection.

ISSUE2: Improve breadcrumb adding links to it 
 
--%>
<c:choose>
	<c:when test="${fn:contains(pageName, 'layer')}">
		<c:set var="isLayer" value="true" />
		<c:set var="mainSection" value="layers" />
		<c:set var="subSection" value="${fn:substringAfter(pageName,'-')}" />
	</c:when>
	<c:when test="${fn:contains(pageName, 'stats')}">
		<c:set var="isStats" value="true" />
		<c:set var="mainSection" value="Stats defs" />
		<c:set var="subSection" value="${fn:substringAfter(pageName,'-')}" />
	</c:when>
	<c:when test="${fn:contains(pageName, 'chart')}">
		<c:set var="isChart" value="true" />
		<c:set var="mainSection" value="Chart scripts" />
		<c:set var="subSection" value="${fn:substringAfter(pageName,'-')}" />
	</c:when>
	<c:otherwise>
		<!-- Until another Home page won't be present -->
		<c:set var="isLayer" value="true" />
		<c:set var="mainSection" value="" />
		<c:set var="subSection" value="" />
	</c:otherwise>
</c:choose>
<sec:authorize access="isAuthenticated()">
	<div id="navbar">
		<nav class="navbar navbar-default">
	
			<!-- Mobile menu (hidden in desktop application) -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
			</div>
	
			<div class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
	
					<li <c:if test="${isLayer}">
						class="active"
					</c:if>><a
						href="LayerList">Layers</a></li>
	
					<li <c:if test="${isStats}">
						class="active"
					</c:if>><a
						href="StatsDefList">Stats defs</a></li>
	
					<li <c:if test="${isChart}">
						class="active"
					</c:if>><a
						href="ChartScriptList">Chart scripts</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="<c:url value="j_spring_security_logout" />">logout</a></li>
				</ul>
			</div>
		</nav>
	</div>
	<div id="breadcrumb">
		<ol class="breadcrumb">
			<li><a href="LayerList">Home</a></li>
			<c:if test="${fn:length(mainSection) != 0}">
				<li><span>${mainSection}</span></li>
			</c:if>
			<c:if test="${fn:length(subSection) != 0}">
				<li><span>${subSection}</span></li>
			</c:if>
		</ol>
	</div>
</sec:authorize>

