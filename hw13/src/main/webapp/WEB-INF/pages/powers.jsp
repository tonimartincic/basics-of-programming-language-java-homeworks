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
		<title>Powers</title>
	</head>
	
	<body style="background-color:<%=backgroundColor%>;">
	
		<iframe src="excel_documents/powers.xls" ></iframe>
	
	</body>
	
</html>