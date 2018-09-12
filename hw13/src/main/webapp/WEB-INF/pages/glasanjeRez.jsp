<%@ page import="java.util.Map" %>
<%@ page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

 <%
	String backgroundColor = (String) session.getAttribute("pickedBgCol");
	backgroundColor = backgroundColor == null ? "white" : backgroundColor;
	
	Map<String, String> idName = (Map<String, String>) session.getAttribute("idName");
	Map<String, Integer> idValue = (Map<String, Integer>) session.getAttribute("idNumOfVotesSorted");
	Map<String, String> bandSong = (Map<String, String>) session.getAttribute("bandSong");
%> 

<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<title>Glasanje</title>
	</head>
	
	<body style="background-color:<%=backgroundColor%>;">
	
		<h1>Rezultati glasanja</h1>
 		<p>Ovo su rezultati glasanja.</p>
 		
 		<table border="1" cellspacing="0" class="rez">
 			<thead><tr><th>Bend</th><th>Broj glasova</th></tr></thead>
 			
 			<tbody>
 				<% for(Map.Entry<String, Integer> entry : idValue.entrySet()) { %>
 					<tr><td><%=idName.get(entry.getKey()) %></td><td><%=entry.getValue() %></td></tr>
 				<% } %>
 				
			</tbody>
			
 		</table>

 		<h2>Grafički prikaz rezultata</h2>
 		
 		<img alt="Pie-chart" src="glasanje-grafika" width="400" height="300" />

 		<h2>Rezultati u XLS formatu</h2>
 		<p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a></p>

 		<h2>Razno</h2>
 		<p>Primjeri pjesama pobjedničkih bendova:</p>
 		
		<ul>
			<%for(Map.Entry<String, String> entry : bandSong.entrySet()) { %>
				<li><a href=<%=entry.getValue() %> target="_blank"><%=entry.getKey() %></a></li>
 			<%} %>
		</ul>

	</body>
	
</html>