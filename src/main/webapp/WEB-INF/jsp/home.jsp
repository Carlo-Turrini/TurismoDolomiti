<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%@ page import="com.student.project.TurismoDolomiti.dto.LoggedUserDTO" %>
<%@ page import="com.student.project.TurismoDolomiti.entity.CredenzialiUtente" %>
    
 <% String messaggio = (String) request.getAttribute("messaggio");
 	LoggedUserDTO loggedUser = (LoggedUserDTO) request.getAttribute("loggedUser");
 	Boolean logged = (Boolean) request.getAttribute("logged");
 %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="/webjars/bootstrap/4.1.0/css/bootstrap.min.css" />
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<style>
			footer {
				margin-left:auto;
				margin-right:auto;
				margin-top: 20px;
			  	padding:20px 40px;
			  	border-top: 1px solid #d4d4d4;
				text-align:right;
				font-size:11px;
				background-color:white;
				max-width: 1219px;
			}
			
			footer a{
			  	text-decoration:none;
			  	color:#5E5956;
			}
			
			footer #privacy {
			  	float:right;
			}
			* {
			  	margin: 0;
			  	padding: 0;
			}
			.container {
				position:absolute;
				right:0px;
			}
			.log {
				
				padding-right:10px;
			}

			body {
				margin-top: 0;
				padding-top: 0;
				background: rgb(255, 255, 255);
			}
			
			.home {
				position: relative;
				margin-top: 0;
				max-width: 1219px;
				height: 400px;
				margin-left: auto;
				margin-right: auto;
				text-align: center;
				background-image: url("/sfondo1.jpg");
				background-repeat: no-repeat;
				background-size: cover;
				margin-bottom: 20px;
				border-radius: 5px;
				
				
			}
			.title {
				position: absoulute;
				top:0px;
				margin-left: auto;
				margin-right: auto;
				max-width: 600px;
				background: rgba(255, 255, 255, 0.5);
				border-radius: 5px;
				
			}
			.title > h1 {
				font-variant: small-caps;
				font-family: "Bahnschrift Light Condensed", Bahnschrift, serif;
				font-size: 450%;
				
			}
			.title > pre {
				font-size: 120%;
				font-style: italic;
				font-family: "Bahnschrift Light Condensed", Bahnschrift, serif;			
				}
			img {
					max-width: 100%;
					max-height: 100%;
					
			}
			.card-deck {
				margin-left: 50px;
				margin-right: 50px;
				
			}
			.card {
				max-height: 230px;
			}
			.card-img-top {
				object-fit: cover;
			}
			.card-img-overlay {
				background: rgba(255, 255, 255, 0.5);
			}
			.card-title {
				font-variant: small-caps;
				font-family: "Bahnschrift Light Condensed", Bahnschrift, serif;
			}
			.card-text {
				font-size: 130%;
				font-style: italic;
				font-family: "Bahnschrift Light Condensed", Bahnschrift, serif;	
			}
			

		</style>
		<title> Turismo Dolomiti </title>
		
	</head>
	<body>
		<div class="home">
		<%@include file="/include/profileAndLogIcons.txt" %>
		<div class="title">
	
		<h1> Turismo Dolomiti </h1>
		<pre> 
"Le montagne sono le grandi cattedrali della terra,
con i loro portali di roccia, i mosaici di nubi,
i cori dei torrenti, gli altari di neve,
le volte di porpora scintillanti ti stelle."
									John Ruskin	
		</pre>
		</div>
		</div>
		
		<div class="card-deck">
			<div class="card">
				<img class="card-img-top" src="escCard.jpg" alt="Card image">
				<div class="card-img-overlay">
					<h1 class="card-title"> Escursioni </h1>
					<p class="card-text"> Un elenco delle più belle escursioni delle dolomiti </p>
					<a href="/elencoEscursioni" class="btn btn-primary"> Vedi elenco</a>
				</div>	
			</div>
			<div class="card">
				<img class="card-img-top" src="rifCard.jpg" alt="Card image">
				<div class="card-img-overlay">
					<h1 class="card-title"> Rifugi </h1>
					<p class="card-text"> Un elenco dei più bei rifugi delle dolomiti </p>
					<a href="/elencoRifugi" class="btn btn-primary"> Vedi elenco </a>
				</div>
				
			</div>
			
			<% if(logged) { %>
			<div class="card">
				<img class="card-img-top" src="rifCard.jpg" alt="Card image">
				<div class="card-img-overlay">
					<h1 class="card-title"> Le mie prenotazioni </h1>
					<p class="card-text"> Controlla i tuoi viaggi </p>
					<a href="/leMiePrenotazioni" class="btn btn-primary"> Vedi elenco </a>
				</div>
			</div>
			<% if(loggedUser.getCredenziali().compareTo(CredenzialiUtente.GestoreRifugio)>=0) { %>
			<div class="card">
				<img class="card-img-top" src="rifCard.jpg" alt="Card image">
				<div class="card-img-overlay">
					<h1 class="card-title"> I miei rifugi </h1>
					<p class="card-text"> Controlla i rifugi che gestisci </p>
					<a href="/elencoRifugi" class="btn btn-primary"> Vedi elenco </a>
				</div>
			
				
			</div>
			<% } 
				if(loggedUser.getCredenziali().equals(CredenzialiUtente.Admin)) {
			%>
			<div class="card">
				<img class="card-img-top" src="rifCard.jpg" alt="Card image">
				<div class="card-img-overlay">
					<h1 class="card-title"> Escursioni incomplete </h1>
					<p class="card-text"> Completa le escursioni </p>
					<a href="/elencoEscursioniDaCompletare" class="btn btn-primary"> Vedi elenco </a>
				</div>
				
			</div>
			<% } %>
			<%  } %>
		</div>
		<%@ include file="/include/footer.txt" %>
	</body>
</html>