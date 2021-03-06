<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%@ page import="com.student.project.TurismoDolomiti.dto.LoggedUserDTO" %>
<%@ page import="com.student.project.TurismoDolomiti.enums.CredenzialiUtente" %>
    
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
		<link rel="stylesheet" type="text/css" href="/css/turismoDolomitiCommon.css"/>
		<style>
			* {
			  	margin: 0;
			  	padding: 0;
			}
			.container {
				position:absolute;
				right:0px;
			}
			body {
				margin-top: 0;
				padding-top: 0;
				background: rgb(255, 255, 255);
			}
			
			.home {
				position: relative;
				margin-top: 0;
				max-width: 1239px;
				height: 400px;
				margin-left: auto;
				margin-right: auto;
				text-align: center;
				background-image: url("/sfondo1.jpg");
				background-repeat: no-repeat;
				background-size: cover;
				margin-bottom: 20px;
				border-radius: 0px 0px 5px 5px;
				
				
			}
			.title {
				position: absoulute;
				top:0px;
				margin-left: auto;
				margin-right: auto;
				max-width: 600px;
				background: rgba(255, 255, 255, 0.5);
				border-radius: 0px 0px 5px 5px;
				
			}
			.title > h1 {
				font-variant: small-caps;
				font-family: "Bahnschrift Light Condensed", Bahnschrift, serif;
				font-size: 450%;
				
			}
			.title > pre {
				font-size: 130%;
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
				max-height: 220px;
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
				font-size: 120%;
				font-style: italic;
				font-family: "Bahnschrift Light Condensed", Bahnschrift, serif;	
			}
			.card.myCard {
				margin-left:5px;
				margin-right:5px;
			}
			.myHomeCardHref {
				position:absolute;
				bottom:20px;
			}

		</style>
		<title> Turismo Dolomiti </title>
		
	</head>
	<body>
		<main>
			<div class="home">
			<%@include file="/include/profileAndLogIcons.txt" %>
			<div class="title">
		
			<h1> Turismo Dolomiti </h1>
			<pre> 
"Le montagne sono le grandi cattedrali della terra,
con i loro portali di roccia, i mosaici di nubi,
i cori dei torrenti, gli altari di neve,
le volte di porpora scintillanti di stelle."
									John Ruskin	
			</pre>
			</div>
			</div>
			
			<div class="card-deck">
				<div class="card myCard">
					<img class="card-img-top" src="escCard.jpg" alt="Card image">
					<div class="card-img-overlay">
						<h2 class="card-title"> Escursioni </h2>
						<p class="card-text"> Le più belle escursioni delle dolomiti </p>
						<a href="/elencoEscursioni" class="btn btn-primary myHomeCardHref"> Vedi elenco</a>
					</div>	
				</div>
				<div class="card myCard">
					<img class="card-img-top" src="rifCard.jpg" alt="Card image">
					<div class="card-img-overlay">
						<h2 class="card-title"> Rifugi </h2>
						<p class="card-text"> I più bei rifugi delle dolomiti </p>
						<a href="/elencoRifugi" class="btn btn-primary myHomeCardHref"> Vedi elenco </a>
					</div>
					
				</div>
				
				<% if(logged) { %>
				<div class="card myCard">
					<img class="card-img-top" src="leMiePrenotazioni.jpg" alt="Card image">
					<div class="card-img-overlay">
						<h2 class="card-title"> Prenotazioni </h2>
						<p class="card-text"> Controlla i tuoi viaggi </p>
						<a href="/leMiePrenotazioni" class="btn btn-primary myHomeCardHref"> Vedi elenco </a>
					</div>
				</div>
				<% if(loggedUser.getCredenziali().compareTo(CredenzialiUtente.GestoreRifugio)>=0) { %>
				<div class="card myCard">
					<img class="card-img-top" src="iMieiRifugi.jpg" alt="Card image">
					<div class="card-img-overlay">
						<h2 class="card-title"> I miei rifugi </h2>
						<p class="card-text"> Controlla i rifugi che gestisci </p>
						<a href="/elencoRifugi" class="btn btn-primary myHomeCardHref"> Vedi elenco </a>
					</div>
				
					
				</div>
				<% } 
					if(loggedUser.getCredenziali().equals(CredenzialiUtente.Admin)) {
				%>
				<div class="card myCard">
					<img class="card-img-top" src="escursioniDaCompletare.jpg" alt="Card image">
					<div class="card-img-overlay">
						<h2 class="card-title"> Escursioni incomplete </h2>
						<p class="card-text"> Completale </p>
						<a href="/elencoEscursioniDaCompletare" class="btn btn-primary myHomeCardHref"> Vedi elenco </a>
					</div>
				</div>
				<div class="card myCard">
					<img class="card-img-top" src="elencoUtenti.jpg" alt="Card image">
					<div class="card-img-overlay">
						<h2 class="card-title"> Utenti </h2>
						<p class="card-text"> Monitora gli utenti nel sistema </p>
						<a href="/elencoUtenti" class="btn btn-primary myHomeCardHref"> Vedi elenco </a>
					</div>
				</div>
				<% } %>
				<%  } %>
			</div>
		</main>
		<%@ include file="/include/footer.txt" %>
	</body>
</html>