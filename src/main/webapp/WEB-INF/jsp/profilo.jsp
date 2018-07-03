<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>
<%@ page import="com.student.project.TurismoDolomiti.dto.LoggedUserDTO" %>
<%@ page import="com.student.project.TurismoDolomiti.entity.Utente" %>
<%@ page import="com.student.project.TurismoDolomiti.enums.CredenzialiUtente" %>
<%@ page import="com.student.project.TurismoDolomiti.enums.Sesso" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%
	LoggedUserDTO loggedUser = (LoggedUserDTO) request.getAttribute("loggedUser");
	Boolean logged = (Boolean) request.getAttribute("logged");
	Utente utente = (Utente) request.getAttribute("utente");
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	String bDay = utente.getDataNascita().toLocalDate().format(formatter);
	String credenziali = null;
	String sesso = null;
	if(utente.getCredenziali().equals(CredenzialiUtente.Normale)) {
		credenziali = "Utente";
	}
	else if(utente.getCredenziali().equals(CredenzialiUtente.GestoreRifugio)) {
		credenziali = "Gestore di rifugi";
	}
	else if(utente.getCredenziali().equals(CredenzialiUtente.Admin)) {
		credenziali = "Amministratore";
	}
	if(utente.getSesso().equals(Sesso.M)) {
		sesso = "Uomo";
	}
	else sesso = "Donna";
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="/webjars/bootstrap/4.1.0/css/bootstrap.min.css" />
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<title>Turismo Dolomiti</title>
		<style>
			.container.my-container {
				margin-right: 70px;
				margin-left: 70px;
			}
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
			.rounded-circle.profilePhoto {
				width:250px;
				height:250px;
				object-fit: cover;
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
			.log {
				
				padding-right:10px;
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
			.nav-link:hover {
				transition: color 0.5s ease;
				color:#d3d3d3;
			}
		</style>
		<script type="text/javascript" src="/webjars/jquery/3.3.1/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
		<script type="text/javascript" src="/webjars/bootstrap/4.1.0/js/bootstrap.min.js"></script>
	</head>
	<body>
		<%@ include file="/include/header.txt" %>
		<main>
			<div class="modal fade" id="deleteUtenteModal" tabindex="-1" role="dialog" aria-labelledby="deleteUtenteModalLabel" aria-hidden="true">
					  <div class="modal-dialog" role="document">
					    <div class="modal-content">
					      <div class="modal-header">
					        <h5 class="modal-title" id="deleteUtenteModalLabel">Eliminazione</h5>
					        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
					          <span aria-hidden="true">&times;</span>
					        </button>
					      </div>
					      <div class="modal-body">
					      	<p class="lead">Confermi di voler eliminare il profilo?</p>
					      </div>
					      <div class="modal-footer">
					        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
					        <a class="btn btn-danger" href="/profilo/${idUtente}/cancella">Conferma</a>
					      </div>
					    </div>
					  </div>
				</div>
			<div class="container my-container">
				<div class="row">
					<div class="col-md-4">
						<img src="${utente.getProfilePhotoPath()}" class="rounded-circle profilePhoto" alt="Foto profilo"> 
					</div>
					<div class="col-md-6">
						<h1 class="display-4">${utente.getNome()} ${utente.getCognome()}</h1>
						<p class="lead"><%=credenziali%></p>
						<p class="lead">Data di nascita: <%=bDay%> </p>
						<p class="lead">Sesso: <%=sesso%></p>
						<p class="lead">Email: ${utente.getEmail()} </p>
						<p class="lead">Telefono: ${utente.getTel()} </p>
						<p class="lead">Descrizione: ${utente.getDescrizione()}</p>
					</div>
					<% if(logged && (loggedUser.getIdUtente() == utente.getId() || loggedUser.getCredenziali().equals(CredenzialiUtente.Admin))){ %>
					<div class="col-md-1">
						<div class="btn-group" role="group">
						  	<button type="button" class="btn btn-light" data-toggle="modal" data-target="#deleteUtenteModal" title="elimina">
								<i class="fa fa-trash-o fa-lg"></i>
							</button>
						  	<div class="btn-group" role="group">
								<div class="dropdown">
				  					<button class="btn btn-light dropdown-toggle" type="button" id="modifyButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" title="modifica">
				    					<i class="fa fa-pencil"></i>
				  					</button>
				  					<div class="dropdown-menu" aria-labelledby="modifyButton">
					    				<a class="dropdown-item" href="/profilo/${utente.getId()}/modifica">Modifica profilo</a>
					    				<a class="dropdown-item" href="/profilo/${utente.getId()}/modifica/foto">Modifica foto profilo</a>
				  					</div>
								</div>
							</div>
							<%if(utente.getCredenziali().compareTo(CredenzialiUtente.GestoreRifugio)>=0 ) { %>
								<a class="btn btn-light" href="/profilo/${utente.getId()}/elencoRifugiGestiti">I miei rifugi</a>
							<% } %>
						</div>
					</div>
					<% } %>
				</div>
			</div>
		</main>
		<%@ include file="/include/footer.txt" %>
	</body>
</html>