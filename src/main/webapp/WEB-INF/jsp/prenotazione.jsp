<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>
<%@ page import="com.student.project.TurismoDolomiti.dto.LoggedUserDTO" %>
<%@ page import="com.student.project.TurismoDolomiti.enums.CredenzialiUtente" %>
<%@ page import="com.student.project.TurismoDolomiti.dto.PostiDisponibiliCameraRifugioDto" %>
<%@ page import="com.student.project.TurismoDolomiti.formValidation.PrenotazioneForm" %>
<%@ page import="java.time.LocalDate" %>
<%@page import="java.util.LinkedList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Date" %>
<%
	LoggedUserDTO loggedUser = (LoggedUserDTO) request.getAttribute("loggedUser");
	Boolean logged = (Boolean) request.getAttribute("logged");
	List<Long> gestoriRifugio = (List<Long>) request.getAttribute("gestoriRifugio");
	Date checkIn = (Date) request.getAttribute("checkIn");
	Date checkOut = (Date) request.getAttribute("checkOut");
	Integer numPersone = (Integer) request.getAttribute("numPersone");
	String nomeRif = (String) request.getAttribute("nomeRif");
	Long idRif = (Long) request.getAttribute("idRif");
	String messaggio = (String) request.getAttribute("messaggio");
	String prenMessage = (String) request.getAttribute("prenMessage");
	Integer prezzoNotte = (Integer) request.getAttribute("prezzoNotte");
	Date dataAperturaRif = (Date) request.getAttribute("dataAperturaRif");
	Date dataChiusuraRif = (Date) request.getAttribute("dataChiusuraRif");
	Date oggi = Date.valueOf(LocalDate.now());
	String min = null;
	if(oggi.compareTo(dataAperturaRif)>=0) {
		min = oggi.toString();
	}
	else min = dataAperturaRif.toString();
	String descGruppo = (String) request.getAttribute("descGruppo");
	PrenotazioneForm prenForm = (PrenotazioneForm) request.getAttribute("prenForm");
	List<PostiDisponibiliCameraRifugioDto> plByCamera = new LinkedList<PostiDisponibiliCameraRifugioDto>();
	if(prenForm != null) {
		plByCamera = prenForm.getPlByCamera();
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
			.sub {
				font-size:1.05rem;
			}
			</style>
		
		<script type="text/javascript" src="/webjars/jquery/3.3.1/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
		<script type="text/javascript" src="/webjars/bootstrap/4.1.0/js/bootstrap.min.js"></script>
		<script>
		function checkInChangeHandler() {
			var checkOut = document.getElementById("inputCheckOut");
			checkOut.setAttribute("min", this.value);
			
		}
		function checkOutChangeHandler() {
			var checkIn = document.getElementById("inputCheckIn");
			checkIn.setAttribute("max", this.value);
			
		}
		function onLoadHandler() {
			<% if(!gestoriRifugio.isEmpty()) { %>
				document.getElementById("inputCheckIn").addEventListener("change", checkInChangeHandler);
				document.getElementById("inputCheckOut").addEventListener("change", checkOutChangeHandler);
			<% } %>
		}
		window.addEventListener("load", onLoadHandler);
		</script>
	</head>
	<body>
		<%@ include file="/include/header.txt" %>
		<main>
			<div class="container">
				<div class="row">
					<%@ include file="/include/rifNav.txt" %>
					<div class="col-md-10 myCol">
						<h1>${nomeRif}</h1>
						<p class="lead subtitle">Prenota</p>
						<hr>
						<div class="row">
							<div class="col-md-7 myCol">
								<% if(messaggio != null){ %>
									<div class="alert alert-primary" role="alert">
								  <span><i class="fa fa-info-circle fa-lg" style="color: #21618C;"></i> ${messaggio}</span>
								</div>
								<% } else { if(prenMessage != null) { %>
								<div class="alert alert-warning" role="alert">
								  <span><i class="fa fa-exclamation-triangle fa-lg" style="color: #FFC300;"></i> ${prenMessage}</span>
								</div>
								<% } %>
								<springForm:form method="POST" action="/rifugio/${idRif}/prenotazione/submit" class="login" modelAttribute="prenForm">
									<% for(int i=0; i<plByCamera.size(); i++) { %>
										<div class="form-group">
											<label for="inputPlSel-<%=i%>" class="lead sub">N.<%=plByCamera.get(i).getNumCamera()%>  <%=plByCamera.get(i).getTipologiaCamera()%> Capienza: <%=plByCamera.get(i).getCapienza()%></label>
											<select name="plByCamera[<%=i%>].postiLettoCameraSel" id="inputPlSel-<%=i%>" class="form-control">
												<%for(int j=0; j<=plByCamera.get(i).getPostiLettoCameraDisponibili();j++) { %>
													<% if(j == plByCamera.get(i).getPostiLettoCameraSel()) { %>
													<option value="<%=j%>" label="<%=j%>" selected>
													<% } else { %>
													<option value="<%=j%>" label="<%=j%>">
													<% } %>
												<% } %>
											</select>
											<input type="hidden" name="plByCamera[<%=i%>].idCamera" value="<%=plByCamera.get(i).getIdCamera()%>"/>
											<input type="hidden" name="plByCamera[<%=i%>].capienza" value="<%=plByCamera.get(i).getCapienza()%>"/>
											<input type="hidden" name="plByCamera[<%=i%>].numCamera" value="<%=plByCamera.get(i).getNumCamera()%>"/>
											<input type="hidden" name="plByCamera[<%=i%>].tipologiaCamera" value="<%=plByCamera.get(i).getTipologiaCamera()%>"/>
											<input type="hidden" name="plByCamera[<%=i%>].postiLettoCameraDisponibili" value="<%=plByCamera.get(i).getPostiLettoCameraDisponibili()%>"/>
										</div>
									<% } %>
									<div class="form-group">
										<label for="inputDescGroup" class="lead sub">Descrizione del gruppo</label>
										<textarea name="descGruppo" value="<%=descGruppo%>" rows="7"></textarea>
									</div>
									<input type="hidden" name="checkIn" value="<%=checkIn.toString()%>"/>
									<input type="hidden" name="checkOut" value="<%=checkOut.toString()%>"/>
									<input type="hidden" name="numPersone" value="<%=numPersone%>"/>
									<div class="col-md-3 myCol">
										<input type="submit" class="btn btn-primary" value="Prenota">
									</div>
								</springForm:form>
								<% } %>
							</div>
							<div class="col-md-5">
								<div class="card">
									<div class="card-body">
									    <h5 class="card-title">Pernottamento:</h5>
									    <h6 class="card-subtitle mb-2 text-muted">Prezzo a notte: <%=prezzoNotte%>€</h6>
									    <%if(!gestoriRifugio.isEmpty()) { %>
									    <form class="login" method="POST" action="/rifugio/<%=idRif%>/prenotazione">
											<div class="form-group col-md-9 myCol">
										 		<label for="inputCheckIn">Check in</label>
										 		<input id="inputCheckIn" type="date" Class="form-control" name="checkIn" required min="<%=min%>" max="<%=dataChiusuraRif.toString()%>" value="<%=checkIn.toString()%>"/>
										 	</div>
										 	<div class="form-group col-md-9 myCol">
									      		<label for="inputCheckOut">Check out</label>
									      		<input type="date" class="form-control" id="inputCheckOut"  name="checkOut" required min="<%=min%>" max="<%=dataChiusuraRif.toString()%>" value="<%=checkOut.toString()%>"/>
											</div>
											<div class="form-group col-md-6 myCol">
												<label for="inputOspiti">Ospiti</label>
												<input type="number" step="1" maxLength="3" required id="inputOspiti" name="numPersone" class="form-control" value="<%=numPersone%>"/>						
											</div>
											<input type="submit" class="btn btn-primary" value="Cerca"/>
									    </form>
									    <% } else { %>
									    <p>Non si accettano prenotazioni online, si prega di contattare il rifugio direttamente.</p>
									    <% } %>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</main>
		<%@ include file="/include/footer.txt" %>
	</body>
</html>