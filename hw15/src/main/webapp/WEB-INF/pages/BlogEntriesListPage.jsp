<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<html>
	<head>
		<title>Blog entries</title>
		
		<style type="text/css">
			body {
    			background-color: #b2b9ff;
    		}
			.error {
			   font-family: fantasy;
			   font-size: 1.0em;
			   color: #FF0000;
			}
			
			h1, h2, h3 { 
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
			
			h1 { 
    		 font-size: 2.5em;
			}
			
			h2 { 
    		 font-size: 1.5em;
			}
			
			h3 { 
    	   	 font-size: 1.0em;
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
		<h1 align="center">Blog entries</h1>
		<h2 align="center">Author: ${nick}</h2>
		
		<h3>Entries</h3>
		<c:forEach var="blogEntry" items="${blogEntries}">
       		<a href="/blog/servleti/blogEntry?id=${blogEntry.id}">${blogEntry.title}</a> <br>
     	</c:forEach>
		
		<c:choose>
			<c:when test="${creator}">
				<h3>Logged as author</h3> 
				<a href="${nick}/new">Add new blog entry</a> <br>
			</c:when>
			<c:otherwise>
				
			</c:otherwise>
		</c:choose> 
		
		<br>
		<button type="button" class="button button2" onclick="location.href='/blog/servleti/main'" value="Submit">Main page</button>
	</body>
</html>