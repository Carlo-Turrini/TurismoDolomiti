<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page import="com.student.project.TurismoDolomiti.dto.LoggedUserDTO" %>
<%@ page import="com.student.project.TurismoDolomiti.enums.CredenzialiUtente" %>
<%@ page import="com.student.project.TurismoDolomiti.entity.Prenotazione" %>
<%@ page import="com.student.project.TurismoDolomiti.dto.PrenInfoDTO" %>
<%@ page import="com.student.project.TurismoDolomiti.dto.CameraPrenInfoDTO" %>
<%@page import="java.util.LinkedList" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.List" %>

<%
	LoggedUserDTO loggedUser = (LoggedUserDTO) request.getAttribute("loggedUser");
	Boolean logged = (Boolean) request.getAttribute("logged");
	String tipologia = (String) request.getAttribute("tipologia");
	PrenInfoDTO prenInfo = (PrenInfoDTO) request.getAttribute("prenInfo");
	List<CameraPrenInfoDTO> camerePrenInfo = (List<CameraPrenInfoDTO>) request.getAttribute("camerePrenInfo");
	Prenotazione pren = (Prenotazione) request.getAttribute("pren");
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	String deleteUrl = null;
	String backUrl = null;
	String backNameBtn = null;
	if(tipologia.equals("rifugio")) {
		deleteUrl = "/rifugio/" + prenInfo.getIdRif() + "/prenotazioni/" + pren.getId() + "/cancella";
		backUrl = "/rifugio/" + prenInfo.getIdRif() + "/prenotazioni";
		backNameBtn = "Le prenotazioni del rifugio";
	}
	else if(tipologia.equals("utente")) {
		deleteUrl = "/leMiePrenotazioni/" + pren.getId() + "/cancella";
		backUrl = "/leMiePrenotazioni";
		backNameBtn = "Le mie prenotazioni";
	}
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Turismo Dolomiti</title>
		
		<link rel="stylesheet" type="text/css" href="/webjars/bootstrap/4.1.0/css/bootstrap.min.css" />
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" type="text/css" href="/css/turismoDolomitiCommon.css"/>
		<style>
			.boldSpan {
				font-weight:600;
			}
			.sub {
				font-size:1.05rem;
			}
			.cardImg {
				height:100%;
				width:100%;
				object-fit:cover;
				
			}
			.myHrefBtn {
				width:100%;
				text-align:center;
			}
		</style>
			
		<script type="text/javascript" src="/webjars/jquery/3.3.1/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
		<script type="text/javascript" src="/webjars/bootstrap/4.1.0/js/bootstrap.min.js"></script>
	</head>
	<body>
		<%@ include file="/include/header.txt" %>
		<main>
			<div class="modal fade" id="deletePrenotazioneModal" tabindex="-1" role="dialog" aria-labelledby="deletePrenotazioneModalLabel" aria-hidden="true">
		  		<div class="modal-dialog" role="document">
				    <div class="modal-content">
				      <div class="modal-header">
				        <h5 class="modal-title" id="deletePrenotazioneModalLabel">Cancellazione</h5>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
				          <span aria-hidden="true">&times;</span>
				        </button>
				      </div>
				      <div class="modal-body">
				      	<p class="lead">Confermi di voler cancellare la prenotazione?</p>
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
				        <a class="btn btn-danger" href="<%=deleteUrl%>">Conferma</a>
				      </div>
				    </div>
		  		</div>
			</div>
			<div class="container">
				<div class="row">
					<div class="col-md-3">
						<a class="btn btn-outline-secondary myHrefBtn" href="<%=backUrl%>"><%=backNameBtn%></a>
					</div>
					<div class="col-md-9">
						<div class="row">
							<div class="col-md-11 myCol">
								<h1>Prenotazione</h1>
							</div>
							<div class="col-md-1">
								<button type="button" class="btn btn-link myDelLink" data-toggle="modal" data-target="#deletePrenotazioneModal">
									<i class="fa fa-trash-o fa-lg"></i>
								</button>
							</div>
						</div>
						<hr>
						<div class="card mb-2">
							<div class="row">
								<div class="col-md-4 myCol">
									<img class="cardImg" src="<%=prenInfo.getIconPathRif()%>">
								</div>
								<div class="col-md-8 myCol">
									<div class="card-body">
										<h4 class="card-title">Prenotazione effettuata presso:<br/> <%=prenInfo.getNomeRif()%></h4>
										<p class=" lead sub">Contatti</p>
										<hr>
										<p><span class="boldSpan">Email:</span> <%=prenInfo.getEmailRif()%></p>
										<p><span class="boldSpan">Tel:</span> <%=prenInfo.getTelRif()%></p>
										<a class="btn btn-primary" href="/rifugio/<%=prenInfo.getIdRif()%>">Visita il rifugio</a>
									</div>
								</div>
								
							</div>
						</div>
						<div class="card mb-2">
							<div class="row">
								<div class="col-md-4 myCol">
									<img class="cardImg" src="<%=prenInfo.getProfilePhotoPathUtente()%>">
								</div>
								<div class="col-md-8 myCol">
									<div class="card-body">
										<h4 class="card-title">Prenotazione effettuata da:<br/> <%=prenInfo.getNomeUtente()%> <%=prenInfo.getCognomeUtente()%></h4>
										<p class="lead sub">Contatto</p>
										<hr>
										<p><span class="boldSpan">Email:</span> <%=prenInfo.getEmailUtente()%></p>
										<a class="btn btn-primary" href="/profilo/<%=prenInfo.getIdUtente()%>">Visita il profilo</a>
									</div>
								</div>
								
							</div>
						</div>
						<div class="card mb-2">
							<div class="card-body">
								<h4 class="card-title">Informazioni</h4>
								<hr>
								<div class="row">
									<div class="col myCol">
										<p><span class="boldSpan">Check in:</span> <%=pren.getArrivo().toLocalDate().format(formatter)%></p>
									</div>
									<div class="col myCol">
										<p><span class="boldSpan">Check out:</span> <%=pren.getPartenza().toLocalDate().format(formatter)%></p>
									</div>
								</div>
								<p><span class="boldSpan">Ospiti:</span> <%=pren.getNumPersone()%></p>
								<p><span class="boldSpan">Descrizione:</span> <%=pren.getDescrizioneGruppo()%></p>
								<hr>
								<p><span class="boldSpan">Totale:</span> <%=pren.getCosto()%>€</p>
							</div>
						</div>
						<div class="card mb-2">
							<div class="card-body">
								<h4 class="card-title">Camere prenotate</h4>
								<hr>
								<% for(CameraPrenInfoDTO camInfo : camerePrenInfo) { %>
									<p><span class="boldSpan">Camera:</span> N.<%=camInfo.getNumCamera()%> <%=camInfo.getTipologiaCamera()%></p>
									<p><span class="boldSpan">Posti letto prenotati:</span> <%=camInfo.getNumPostiLettoPren()%></p>
									<hr>
								<% } %>
							</div>
						</div>
					</div>
				</div>
			</div>
		</main>
		<%@ include file="/include/footer.txt" %>
	</body>
</html>