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
	<style>
		footer {
				margin-left:auto;
				margin-right:auto;
				margin-top: 20px;
			  	padding:20px 40px;
			  	border-top: 1px solid #d4d4d4;
				text-align:right;
				font-size:11px;
				max-width: 1219px;
			}
			
			footer a{
			  	text-decoration:none;
			  	color:#5E5956;
			}
			
			footer #privacy {
			  	float:right;
			}
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
	<%@ include file="/include/footer.txt" %>
</body>
</html>