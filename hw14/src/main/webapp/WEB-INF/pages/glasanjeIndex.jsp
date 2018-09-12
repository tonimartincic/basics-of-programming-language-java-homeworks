<%@page import="hr.fer.zemris.java.p12.model.PollOption"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@ page import="java.util.List" %>
<%@ page import="hr.fer.zemris.java.p12.model.Poll" %>

<%
	Poll poll = (Poll)session.getAttribute("poll");
	List<PollOption> pollOptions = (List<PollOption>)session.getAttribute("pollOptions");
%>

<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<title>Glasanje</title>
	</head>
	
	<body>

		<h1><%=poll.getTitle() %></h1>
	 	<p><%=poll.getMessage() %></p>
	 	
	 	<ol>
			<% for(PollOption pO : pollOptions) { %>
				 <li><a href="glasanje-glasaj?id=<%=pO.getId()%>&pollID=<%=poll.getId()%>"><%=pO.getOptionTitle() %></a></li> 
			<% } %>
		</ol>
	 	
	</body>
	
</html>