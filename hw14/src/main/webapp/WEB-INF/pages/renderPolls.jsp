<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="hr.fer.zemris.java.p12.model.Poll" %>
<%@ page import="java.util.List" %>
<%
  List<Poll> polls = (List<Poll>) session.getAttribute("polls");
%>
<html>
  <body>

  <% if(polls.isEmpty()) { %>
    Nema glasanja.
  <% } else { %>
  	<h1>Glasanja:</h1><br>
    <ul>
    <% for(Poll poll : polls) { %>
    <li><a href="glasanje?pollID=<%=poll.getId() %>"><%=poll.getTitle()%></a></li>  
    <% } %>  
    </ul>
  <% } %>

  </body>
</html>