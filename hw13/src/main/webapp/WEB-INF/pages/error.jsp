<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<%
	String backgroundColor = (String) session.getAttribute("pickedBgCol");
	backgroundColor = backgroundColor == null ? "white" : backgroundColor;
	
	String errorMessage = (String) session.getAttribute("errorMessage");
%>

<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<title>Error</title>
	</head>
	
	<body style="background-color:<%=backgroundColor%>;">
	
		<p><%=errorMessage %></p>
	
	</body>
	
</html>