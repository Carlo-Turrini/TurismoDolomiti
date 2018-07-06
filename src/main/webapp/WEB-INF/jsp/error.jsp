<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<%@ page session="false" %>
<%
	String redirectUrl = (String) request.getAttribute("redirectUrl");
	if(redirectUrl == null) {
		redirectUrl = "/home";
	}
%>
    
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Turismo Dolomiti</title>
	<link rel="stylesheet" type="text/css" href="/webjars/bootstrap/4.1.0/css/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="/css/turismoDolomitiCommon.css"/>
	<style>
		img {
			width:420px;
			height:420px;
		}
	</style>
    <script>
     	function onLoadHandler() {
     		setTimeout(function() {
     		  window.location.href = "<%=redirectUrl%>";
     		}, 5000);
     	}
     	window.addEventListener("load", onLoadHandler);
     </script>
</head>
<body>
	<main>
		<div class="jumbotron">
			<div class="row">
				<div class="col">
					<img src="/error.png">
				</div>
				<div class="col">
					<h1 class="display-4">Errore!</h1>
					<hr class="my-4">
					<p class="lead">${message}</p>
				</div>
	
			</div>
		</div>
	</main>
	<%@ include file="/include/footer.txt" %>
</body>
</html>