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
		<title>Welcome!</title>
	</head>
	
	<body style="background-color:<%=backgroundColor%>;">
		<p><a href="colors.jsp">Background color chooser</a></p>
		
		<form action="trigonometric" method="GET">
 			First angle:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
 			Last angle:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
 			<input type="submit" value="Table"><input type="reset" value="Reset">
		</form> 
		
		<p><a href="randomFontColor">Funny story</a></p>
		
		<p><a href="reportImage">Report image</a></p>
		
		<p><a href="powers?a=1&b=100&n=3">Powers</a></p>
		
		<p><a href="appinfo.jsp">Application info</a></p>
		
		<p><a href="glasanje">Glasanje</a></p>
		
	</body>
	
</html>



