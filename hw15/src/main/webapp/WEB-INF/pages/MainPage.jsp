<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<html>
	<head>
		<title>Main page</title>
		
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
    
		</style>
	</head>

	<body>
		<h1 align="center">Main page</h1>
		
		<p>
			<c:choose>
				<c:when test="${sessionScope.currentUserId != null}" >
					User first name: <%= session.getAttribute("currentUserFn") %> <br>
					User last name : <%= session.getAttribute("currentUserLn") %> <br>
					<a href="logout">Logout</a>
				</c:when>
				<c:otherwise>
					Not logged in
				</c:otherwise>
			</c:choose> 
		</p>

		<c:choose>
			<c:when test="${sessionScope.currentUserId != null}">
				
			</c:when>
			<c:otherwise>
				<h2>Login</h2>
				<form action="main" method="post">
					Nick <br>
					<input type="text" name="nick" value='<c:out value="${record.nick}"/>' size="40"><br>
					<c:if test="${record.hasError('nick')}">
					<div class="error"><c:out value="${record.getError('nick')}"/></div>
					</c:if>
					
					Password <br>
					<input type="password" name="password" value='<c:out value="${record.password}"/>' size="40"><br>
					<c:if test="${record.hasError('password')}">
					<div class="error"><c:out value="${record.getError('password')}"/></div>
					</c:if>
					
					<input type="submit" name="method" value="Login">
				</form>
				
				<h2>Registration</h2>
				<a href="register">Register</a>	
			</c:otherwise>
		</c:choose> 
		
		<h2>Registered authors</h2>
		<c:forEach var="nick" items="${allNicks}">
       		<a href="author/${nick}">${nick}</a> <br>
     	</c:forEach>
		
	</body>
</html>