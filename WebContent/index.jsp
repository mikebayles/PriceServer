<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
	<html>
		<head>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<link href="css/main.css" rel="stylesheet">
			<title>Trade In Price Tracker</title>
		</head>
	<body>
		<div id="wrapper" class="center">
			<h1>Amazon Buy Back Tracker</h1>
			<h2>Enter ASIN number below to find your book</h2>
			<form id="searchForm" action="servlet/BookServlet">
				<input type="hidden" name="method" value="search">
				<input class="textbox" type="text" name="book">
				<input class="button" type="submit">
			</form>
			
			<div>
				
			</div>
		</div>
	</body>
</html>