<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    
    <%
	 session = request.getSession(false);
	if(session.getAttribute("user")!=null && !(null==session)){System.out.println("Null NAHI HAI");
		response.sendRedirect(getServletContext().getContextPath()+"/jsp/index.jsp");
	}
	else{
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Form</title>
<br><br>
<style>
body {
    background: #ffffff url("../imgs/SHAS.jpeg") no-repeat left top;
}
fieldset {
    border:0;
    padding:5px;
    margin-bottom:0px;
    background:#EEE;
    border-radius: 10px;
    -moz-border-radius: 5px;
    -webkit-border-radius: 8px;
	background:-webkit-liner-gradient(top,#EEEEEE,#FFFFFF);
    background:linear-gradient(top,#EFEFEF,#FFFFFF);
    box-shadow:3px 3px 50px #666;
    -moz-box-shadow:3px 3px 10px #666;
    -webkit-box-shadow:3px 3px 10px #666;
    position:relative;
    }
</style>
</head>
<body>
<h1 style="color:#0000D2; text-align:center; text-Shadow: 5px 5px 8px white">Welcome to SHAS</h1>
<center>
	<form action="http://localhost:8080/SHAS/LoginServlet" method="post">
	<fieldset style="width: 400px; height: 190px">
	<legend style=" color: blue;font-Size: 120%; font-Weight: 700; "></legend>
	<p style=" color: blue;font-Size: 120%; font-Weight: 700; ">Login to SHAS </p>
	<table>	 
	<% //if(request.getParameter("error")=="invalidUser"){ 
	if(request.getParameter("error")!=null){ %>
	<tr><td colspan = 2 style=" color: red"> Invalid Username/Password. Please try again.</td> </tr>
	<%} %>
		<tr>
			<td style=" color: blue; font-Weight: 700">Username </td>
			<td> <input type=username name ="username">
		</tr>
		<tr>
			<td style=" color: blue; font-Weight: 700"> Password </td>
			<td> <input type=password name ="password">
		</tr>
		<tr>
		</tr>
		<tr>
			<td><br><br></br></td>
			<td>
			     <input type="image" src="../imgs/submit.png" alt="Submit" width="70" height="34"> 
			     &nbsp;&nbsp;			    
			    <input type="image" src="../imgs/refresh.png" alt="Refresh" align="right" width="70" height="34">
			</td>		
		</tr>
	</table>
	</fieldset>
	</form> <br/>
	<a href="http://localhost:8080/SHAS/jsp/register.jsp">Register</a> now to get started!!.
</center>
</body>

</html>
<%}%>