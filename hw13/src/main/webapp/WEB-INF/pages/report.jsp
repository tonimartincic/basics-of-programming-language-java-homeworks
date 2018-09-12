<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<%
	String backgroundColor = (String) session.getAttribute("pickedBgCol");
	backgroundColor = backgroundColor == null ? "white" : backgroundColor;
%>

<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<title>Image</title>
	</head>
	
	<h1>OS usage</h1>

	<p>Here are the results of OS usage in survey that we completed.</p>
	
	<body style="background-color:<%=backgroundColor%>;">
	 
		<img src="pictures/image.png" />  
	
	</body>
	
</html>