<%@tag description="layout tag for the SLMS Administration Interface"
	pageEncoding="UTF-8"%>
<html>
	<head>
		<title>SLMS Admin UI - The Administration Interface for the SLMS web Platform</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		
		<!-- JQuery library -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
		<!-- Bootstrap compiled and minified CSS -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
		<!-- Bootstrap compiled and minified JavaScript -->
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
		
		<!-- Application custom stylesheet -->
		<link href="css/admin-style.css" rel="stylesheet" type="text/css" />
		
		<link href='http://fonts.googleapis.com/css?family=Archivo+Black' rel='stylesheet' type='text/css'>
		<script src="scripts/codemirror/codemirror.js"></script>
        <link rel="stylesheet" href="scripts/codemirror/codemirror.css">
        <script src="scripts/codemirror/mode/xml/xml.js"></script>
        <script>
        	//Enable Bootstrap's popover feature
        	$(function () {
      	  		$('[data-toggle="popover"]').popover()
      		})
        </script>
	</head>
	<body>
		<div id="wrapper">
			<div id="pageheader">
				<p id="headtitle">
					SLMS - Administration Dashboard
				</p>
			</div>
			<div id="menubar">
				<jsp:include page="/header.jsp" />
			</div>
			<div id="content">
				<jsp:doBody />
			</div>
			<div id="pagefooter">
				<hr />
				<span>Powered by <a href="http://slms4redd.org/">SLMS</a> Team @ FAO hq - Rome</span>
			</div>
		</div>
	</body>
</html>