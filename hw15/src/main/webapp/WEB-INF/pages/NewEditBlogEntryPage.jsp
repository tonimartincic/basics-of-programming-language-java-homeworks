<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<html>
	<head>
		<title>New/edit blog entry</title>
		
		<style type="text/css">
			body {
    			background-color: #b2b9ff;
    		}
			.error {
			   font-family: fantasy;
			   font-size: 1.0em;
			   color: #FF0000;
			}
			
			h1 { 
	    		color:#002259; 
	    		display: block;
			    font-size: 2.5em;
			    margin-top: 0.3em;
			    margin-bottom: 0.3em;
			    margin-left: 0;
			    margin-right: 0;
			    font-weight: bold;
			    font-family:verdana;   
			}
			
			h2 { 
	    		color:#002259; 
	    		display: block;
			    font-size: 1.5em;
			    margin-top: 0.3em;
			    margin-bottom: 0.3em;
			    margin-left: 0;
			    margin-right: 0;
			    font-weight: bold;
			    font-family:verdana;
			}
			
			.button {
			    border: none;
			    color: white;
			    padding: 10px 20px;
			    text-align: center;
			    text-decoration: none;
			    display: inline-block;
			    font-size: 16px;
			    margin: 2px 1px;
			    cursor: pointer;
			    border-radius: 10px;
			}
		
			.button1 { 
	    		background-color: #c40000; 
			}
			
			.button2 { 
	    		background-color: #234893;  
			}
    
		</style>
	</head>

	<body>
		<h1 align="center">New/edit blog entry</h1>
	    <h2 align="center">Author: ${sessionScope.currentUserNick}</h2> 
		
		<form action="/blog/servleti/author/${sessionScope.currentUserNick}?id=${record.id}" method="post" id="blogEntryForm">
		
			Title <input type="text" name="title" value='<c:out value="${record.title}"/>' size="30"><br>
					   <c:if test="${record.hasError('title')}">
					   	<div class="error"><c:out value="${record.getError('title')}"/></div>
					   </c:if>
	
			<%-- <input type="text" name="text" value='<c:out value="${record.text}"/>' size="500"><br> --%>
		    <textarea placeholder="Enter text..." rows="15" cols="100" name="text" form="blogEntryForm">${record.text}</textarea><br>
				<c:if test="${record.hasError('text')}">
					<div class="error"><c:out value="${record.getError('text')}"/></div>
				</c:if>
					  
			<input type="submit" name="method" value="Submit">
			<input type="submit" name="method" value="Cancel">
		
		</form>

		<br>
		<button type="button" class="button button2" onclick="location.href='/blog/servleti/main'" value="Submit">Main page</button>
	</body>
</html>