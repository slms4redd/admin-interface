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
The navBar Links are implicitly extracted from the JSPs names.
Please follow the existing name convention when adding new pages in order to keep valid these components.
 
--%>
<c:choose>
	<c:when test="${fn:contains(pageName, 'layer')}">
		<c:set var="isLayer" value="true" />
	</c:when>
	<c:when test="${fn:contains(pageName, 'stats')}">
		<c:set var="isStats" value="true" />
	</c:when>
	<c:when test="${fn:contains(pageName, 'chart')}">
		<c:set var="isChart" value="true" />
	</c:when>
	<c:when test="${fn:contains(pageName, 'arealayer')}">
		<c:set var="isAreaLayer" value="true" />
	</c:when>
	<c:when test="${fn:contains(pageName, 'menulayer')}">
		<c:set var="isMenuLayer" value="true" />
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
					<li <c:if test="${isMenuLayer}">class="active"</c:if>>
						<a href="LayerMenuBuilder">MenuLayer Builder</a>
					</li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Timeseries Layer<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li <c:if test="${isLayer}">class="active"</c:if>>
								<a href="LayerList">Layers</a>
							</li>
							<li <c:if test="${isStats}">class="active"</c:if>>
								<a href="StatsDefList">Stats defs</a>
							</li>
							<li <c:if test="${isChart}">class="active"</c:if>>
								<a href="ChartScriptList">Chart scripts</a>
							</li>
							<li <c:if test="${isAreaLayer}">class="active"</c:if>>
								<a href="AreaLayerBuilder">AreaLayer Builder</a>
							</li>
						</ul>
					</li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="<c:url value="j_spring_security_logout" />">logout</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right active">
					<li>
						<a href="#" data-toggle="modal" data-target=".bs-example-modal-lg">Help ?</a>
					</li>
				</ul>
			</div>
		</nav>
	</div>
	
	<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-lg">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <!-- <h4 class="modal-title" id="myModalLabel">Modal title</h4> -->
	      </div>
	      <div class="modal-body">
	      	<img src="img/quick-online-manual.png" />
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	        <!-- <button type="button" class="btn btn-primary">Save changes</button> -->
	      </div>
	    </div>
	  </div>
	</div>
	
</sec:authorize>

