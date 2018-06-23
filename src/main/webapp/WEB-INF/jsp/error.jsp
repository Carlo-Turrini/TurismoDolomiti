<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<%
	String redirectUrl = (String) request.getAttribute("redirectUrl");
	if(redirectUrl == null) {
		redirectUrl = "/home";
	}
%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
    <script>
     	function onLoadHandler() {
     		setTimeout(function() {
     		  window.location.href = "${redirectUrl}";
     		}, 3000);
     	}
     	window.addEventListener("load", onLoadHandler);
     </script>
</head>
<body>
	<h2> ${exception} </h2>
	<h3> ${message} </h3>
</body>
</html>