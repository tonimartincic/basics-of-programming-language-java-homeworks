<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>Blog entries</title>
		
		<style type="text/css">
			body {
    			background-color: #b2b9ff;
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
	
	<h2>Invalid url!</h2> <br>
	
	<button type="button" class="button button2" onclick="location.href='/blog/servleti/main'" value="Submit">Main page</button>
</html>