<%@ page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
		<title>Colors</title>
	</head>
	<body style="background-color:<%=backgroundColor%>;">
		<p><a href="setcolor?pickedBgCol=white">WHITE</a></p>
		<p><a href="setcolor?pickedBgCol=red">RED</a></p>
		<p><a href="setcolor?pickedBgCol=green">GREEN</a></p>
		<p><a href="setcolor?pickedBgCol=cyan">CYAN</a></p>
	</body>
</html>