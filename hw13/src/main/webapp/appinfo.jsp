<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<%!
	private static String getTimeFromMilis(long numberOfMilis) {
		int ms = (int) numberOfMilis % 1000;
		int s = (int) numberOfMilis / 1000 % 60;
		int m = (int) numberOfMilis / (1000 * 60) % 60;
		int h = (int) numberOfMilis / (1000 * 60 * 60) % 60;
		int d = (int) numberOfMilis / (1000 * 60 * 60 * 60) / 24;
	 	
	 	return String.format("%d days %d hours %d minutes %d seconds and %d miliseconds", d, h, m, s, ms);
	}
%>

<%
	String backgroundColor = (String) session.getAttribute("pickedBgCol");
	backgroundColor = backgroundColor == null ? "white" : backgroundColor;
	
	Date startTime = (Date) getServletContext().getAttribute("startTime");
	Date currentTime = new Date();
	
	long numberOfMilis = currentTime.getTime() - startTime.getTime();
	String time = getTimeFromMilis(numberOfMilis);
%>

<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<meta http-equiv="refresh" content="1">
		<title>Application info</title>
	</head>
	
	<body style="background-color:<%=backgroundColor%>;">
		
		<p>This application is already running: <%=time %> </p>
		
	</body>
	
</html>