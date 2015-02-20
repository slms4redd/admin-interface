<%@tag description="layout tag for the SLMS Administration Interface"
	pageEncoding="UTF-8"%>
<html>
	<head>
		<title>SLMS Admin UI - The Administration Interface for the SLMS web Platform</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/admin-style.css" rel="stylesheet" type="text/css" />
		<link href='http://fonts.googleapis.com/css?family=Archivo+Black' rel='stylesheet' type='text/css'>
		
		<script src="scripts/codemirror/codemirror.js"></script>
        <link rel="stylesheet" href="scripts/codemirror/codemirror.css">
        <script src="scripts/codemirror/mode/xml/xml.js"></script>
		
		
	</head>
	<body>
		<div id="wrapper">
			<div id="pageheader">
				<p id="headtitle">
					SLMS - Administration Dashboard
				</p>
			</div>
			<div id="navbar">
				<jsp:include page="header.jsp" />
			</div>
			<div id="content">
				<jsp:doBody />
			</div>
			<div id="pagefooter">
				<hr />
				Powered by <a href="http://slms4redd.org/">SLMS</a> Team @ FAO hq - Rome
			</div>
		</div>
	</body>
</html>