<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	String backgroundColor = (String) session.getAttribute("pickedBgCol");
	backgroundColor = backgroundColor == null ? "white" : backgroundColor;
%>

<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<title>Trigonometric</title>
	</head>

	<body style="background-color:<%=backgroundColor%>;">
		
		<table border="1">
			<tr><th>x (in degrees)</th><th>Sin(x)</th><th>Cos(x)</th></tr>
			<c:forEach var="record" items="${results}">
				<tr>
					<td>${record.angle}</td>
					<td><fmt:formatNumber type="number" maxFractionDigits="6" value="${record.sin}"/></td>
					<td><fmt:formatNumber type="number" maxFractionDigits="6" value="${record.cos}"/></td>
				</tr>
			</c:forEach>
		</table>
	
	</body>
	
</html>