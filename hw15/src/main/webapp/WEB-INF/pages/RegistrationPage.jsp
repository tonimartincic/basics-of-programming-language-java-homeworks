<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Registration</title>
		
		<style type="text/css">
			body {
    			background-color: #b2b9ff;
    		}
    		
			.error {
			   font-family: fantasy;
			   /* font-weight: bold; */
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
		    
		    p {
		    	font-size: 1.2em;
		    }
		</style>
	</head>

	<body>
		<h1>Registration</h1>

		<form action="register" method="post">
		
			<p>First name</p><input type="text" name="firstName" value='<c:out value="${record.firstName}"/>' size="50"><br>
					   <c:if test="${record.hasError('firstName')}">
					   	<div class="error"><c:out value="${record.getError('firstName')}"/></div>
					   </c:if>
	
			<p>Last name</p><input type="text" name="lastName" value='<c:out value="${record.lastName}"/>' size="50"><br>
					  <c:if test="${record.hasError('lastName')}">
					  	<div class="error"><c:out value="${record.getError('lastName')}"/></div>
					  </c:if>
			
			<p>Nick</p><input type="text" name="nick" value='<c:out value="${record.nick}"/>' size="50"><br>
				 <c:if test="${record.hasError('nick')}">
				 	<div class="error"><c:out value="${record.getError('nick')}"/></div>
				 </c:if>
	
			<p>EMail</p><input type="text" name="email" value='<c:out value="${record.email}"/>' size="50"><br>
				  <c:if test="${record.hasError('email')}">
				  	 <div class="error"><c:out value="${record.getError('email')}"/></div>
				  </c:if>
			
			<p>Password</p><input type="password" name="password" value='<c:out value="${record.password}"/>' size="50"><br>
					 <c:if test="${record.hasError('password')}">
					 	<div class="error"><c:out value="${record.getError('password')}"/></div>
					 </c:if>
	
			<br>
			<input type="submit" name="method" value="Register">
			<input type="submit" name="method" value="Cancel">
		
		</form>

	</body>
</html>