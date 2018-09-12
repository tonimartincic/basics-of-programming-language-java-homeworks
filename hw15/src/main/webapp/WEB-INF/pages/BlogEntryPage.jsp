<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<html>
	<head>
		<title>Blog entry</title>
		
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
			
			table {
	    		 background-color: #8992f4;
	   			 border-collapse: collapse;
	   			 border: 1px solid black;
	   			 table-layout: fixed; 
	   			 font-size: 20px;
			}
			
			td, th {
   			 border: 1px solid black;
   			 padding: 6px;
			}
			
			table#table1 {
   			 width:70%; 
    	     margin-left:15%; 
   			 margin-right:15%;
  			}
		</style>
	</head>

	<body>
		<h1 align="center">Blog entry page</h1>
		<h2 align="center">Author: ${blogEntry.creator.nick}</h2>

	    <c:choose>
    		 <c:when test="${blogEntry==null}">
    		 	 <br>
     			 There is no blog entry!
   			 </c:when>
     		 <c:otherwise>
     		 	 <br>
     		 	 <table id="table1">
  					<thead>
  						<tr bgcolor="#2b2bc6">
  							<th>${blogEntry.title}</th>
  						</tr>
  					</thead>
  					<tbody>
  						<tr style="white-space:pre-wrap; word-wrap:break-word;" >
     		 				<td>${blogEntry.text}</td>
     		 			</tr>
  					</tbody>
				 </table>
     		 		
     			 <c:choose>
					<c:when test="${creator}">
						<h3 align="center">Logged as author: <button type="button" class="button button1" onclick="location.href='author/${nick}/edit?id=${blogEntry.id}'" value="Submit">Edit blog entry</button></h3> 		
					</c:when>
					<c:otherwise>
						
					</c:otherwise>
				 </c:choose>  
     				 
      			 <c:if test="${!blogEntry.comments.isEmpty()}">
      			 	<table id="table1">
	  					<thead>
	  						<tr bgcolor="#2b2bc6">
	  							<th></th>
	  							<th>Comments</th>
	  						</tr>
	  					</thead>
	  					<tbody>
	  						<c:forEach var="e" items="${blogEntry.comments}">
       							<tr style="white-space:pre-wrap; word-wrap:break-word;">
       								<td>${e.usersEMail} ### ${e.postedOn}</td>
       								<td>${e.message}</td>
       							</tr>
      						</c:forEach>
	  					</tbody>
					 </table>
     			 </c:if>
     			 
     			 <c:choose>
					<c:when test="${sessionScope.currentUserId != null}" >
						 <form action="blogEntry?id=${blogEntry.id}" method="post" id="commentForm">
		
							<br>
							<p>Add comment</p>
							 <textarea placeholder="Enter comment..." rows="5" cols="60" name="message" form="commentForm"></textarea><br>
								<c:if test="${record.hasError('message')}">
									<div class="error"><c:out value="${record.getError('message')}"/></div>
								</c:if>
							  
							<input type="submit" name="method" value="Submit">
						 </form>
					</c:when>
					<c:otherwise>
						<br>
						<p>Login to comment</p>
					</c:otherwise>
				 </c:choose> 
     			
   	   		 </c:otherwise>
  		</c:choose>
		
		<br>
		<button type="button" class="button button2" onclick="location.href='/blog/servleti/main'" value="Submit">Main page</button>
	</body>
</html>