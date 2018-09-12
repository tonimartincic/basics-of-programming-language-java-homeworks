<%@ page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@ page import="hr.fer.zemris.java.p12.model.Poll" %>
<%@ page import="hr.fer.zemris.java.p12.model.PollOption" %>
<%@ page import="java.util.List" %>

 <%
 	Poll poll = (Poll)session.getAttribute("poll");
 	List<PollOption> pollOptions = (List<PollOption>)session.getAttribute("pollOptions");
 	List<PollOption> bestPollOptions = (List<PollOption>)session.getAttribute("bestPollOptions");
%> 

<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<title>Glasanje</title>
	</head>
	
	<body>
	
		<h1>Rezultati glasanja</h1>
 		<p>Ovo su rezultati glasanja.</p>
 		
 		<table border="1" cellspacing="0" class="rez">
 			<thead><tr><th>Opcija</th><th>Broj glasova</th></tr></thead>
 			
 			<tbody>
 				<% for(PollOption pollOption : pollOptions) { %>
 					<tr><td><%=pollOption.getOptionTitle() %></td><td><%=pollOption.getVotesCount() %></td></tr>
 				<% } %>
			</tbody>
			
 		</table>

 		<h2>Grafički prikaz rezultata</h2>
 		
 		<img alt="Pie-chart" src="glasanje-grafika" width="400" height="300" />

 		<h2>Rezultati u XLS formatu</h2>
 		<p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a></p>

 		<h2>Razno</h2>
 		<p>Linkovi na sadržaje o pobjednicima</p>
 		
		<ul>
			<% for(PollOption bpo : bestPollOptions) { %>
				<li><a href=<%=bpo.getOptionLink() %> target="_blank"><%=bpo.getOptionTitle() %></a></li>
 			<%} %>
		</ul> 

	</body>
	
</html>