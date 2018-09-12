<%@ page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<%
     RequestDispatcher rd = request.getRequestDispatcher("/index.html");
     rd.forward(request, response);
%> 