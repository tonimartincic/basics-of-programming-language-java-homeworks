<%@ page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<%
	String backgroundColor = (String) session.getAttribute("pickedBgCol");
	backgroundColor = backgroundColor == null ? "white" : backgroundColor;
	
	String fontColor = (String) session.getAttribute("fontColor");
	fontColor = fontColor == null ? "black" : fontColor;
%>

<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<title>Funny story</title>
	</head>
	
	<body style="background-color:<%=backgroundColor%>;">
	
	<p>
		<font color=<%=fontColor%>>
			On Aug. 15, 1943, Americans and Canadians assaulted a North Pacific island at the height of World War II.<br />
			A joint force of 34,000 American and Canadian troops, supported by warplanes and naval bombardment, moved inland<br />
			through frigid and unforgiving terrain searching for occupying Japanese forces.<br />

			By the end of the second day, 300 Allied soldiers lay dead or wounded. However, there wasn't a Japanese soldier in sight.<br /> 
			The island had been evacuated three weeks prior. It was completely deserted.<br />
		</font>
	</p>
	
	</body>
	
</html>


