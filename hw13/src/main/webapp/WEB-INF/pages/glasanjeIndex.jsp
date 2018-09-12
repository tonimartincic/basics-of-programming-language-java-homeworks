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
		<title>Glasanje</title>
	</head>
	
	<body style="background-color:<%=backgroundColor%>;">

		<h1>Glasanje za omiljeni bend:</h1>
	 	<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!</p>
	 	
	 	<ol>
			<c:forEach var="band" items="${bands}">
				<li><a href="glasanje-glasaj?id=${band.key}">${band.value}</a></li>
			</c:forEach>
		</ol>
	 	
	</body>
	
</html>