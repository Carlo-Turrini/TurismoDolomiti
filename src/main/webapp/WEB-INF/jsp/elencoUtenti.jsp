<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>
<%@ page import="com.student.project.TurismoDolomiti.dto.LoggedUserDTO" %>
<%@ page import="com.student.project.TurismoDolomiti.entity.CredenzialiUtente" %>
<%@ page import="com.student.project.TurismoDolomiti.entity.Utente" %>
<%@ page import="java.util.List" %>
<%
	LoggedUserDTO loggedUser = (LoggedUserDTO) request.getAttribute("loggedUser");
	Boolean logged = (Boolean) request.getAttribute("logged");
	List<Utente> utenti = (List<Utente>) request.getAttribute("utenti");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Turismo Dolomiti</title>
		
				<link rel="stylesheet" type="text/css" href="/webjars/bootstrap/4.1.0/css/bootstrap.min.css" />
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<style>
			.row {
				padding-right:0px;
				margin-left: 0px;
				margin-right: 0px;
			}
			.row.header-sfondo {
				height:250px;
			}
			.header-sfondo {
				background-image: url("/header-sfondo1.jpg");
				background-repeat: no-repeat;
				
				background-size: cover;
			}
			.col-md-1.log {
				position:absolute;
				right:10px;
			}
			.title-header {
				font-variant: small-caps;
				font-family: "Bahnschrift Light Condensed", Bahnschrift, serif;
				color: white;
				font-size: 500%;
				padding-left:20px;
			}
			.divisoreNav {
				margin-top:0px;			
			
			}
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
			.lead {
				margin-bottom:1px;
			}
			.display-4 {
				font-size:2.5rem;
			}
			.nav-link{
				color:#5E5956;
				font-size: 16px; 
			}
			.nav-link:hover, .myCardDelBtnLink:hover {
				transition: color 0.5s ease;
				color:#d3d3d3;
			}
			.fotoArticle {
				width:150px;
				height:150px;
				object-fit:cover;
			}
			.rifCard {
				height:300px;
			}
			.card-img-top.myCardImg {
				width:100%;
				height:180px;
				object-fit:cover;
			}
			.cardText {
			 margin-bottom:0px;
			}
			.cardSub {
				color:#5E5956;
				font-size: 1rem;
				margin-bottom:5px;
			}
			.pageTitle {
				font-variant: small-caps;
			}
			a.cardHref {
				text-decoration:none;
				color:black;
			}
			</style>
	</head>
	<body>
		<%@ include file="/include/header.txt" %>
		<main>
			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<h1 class="pageTitle">Elenco utenti</h1>
						<hr>
						<div class="row">
							<% for(Utente ut : utenti) { %>
							<div class="col-md-4">
								<a href="/profilo/<%=ut.getId()%>" class="cardHref">
									<div class="card rifCard">
										<img class="card-img-top myCardImg" src="<%=ut.getProfilePhotoPath()%>">
										<div class="card-body">
											<h5 class="card-title cardText"><%=ut.getNome()%> <%=ut.getCognome()%></h5>
											<p class="card-text cardSub"><%=ut.getCredenziali().toString()%></p>
											<p class="card-text cardText"> <%=ut.getEmail()%></p>
										</div>
									</div>
								</a>
							</div>
							<% } %>
						</div>
					</div>
				</div>
				
			</div>
		</main>
		<%@ include file="/include/footer.txt" %>
	</body>
</html>