<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>
<%@ page import="com.student.project.TurismoDolomiti.dto.LoggedUserDTO" %>
<%@ page import="com.student.project.TurismoDolomiti.enums.CredenzialiUtente" %>
<%@ page import="com.student.project.TurismoDolomiti.dto.FotoSequenceDTO" %>
<%@ page import="com.student.project.TurismoDolomiti.dto.CommentoCardDto" %>
<%@ page import="com.student.project.TurismoDolomiti.entity.Rifugio" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.sql.Date"%>
<%
	LoggedUserDTO loggedUser = (LoggedUserDTO) request.getAttribute("loggedUser");
	Boolean logged = (Boolean) request.getAttribute("logged");
	List<Long> gestoriRifugio = (List<Long>) request.getAttribute("gestoriRifugio");
	List<FotoSequenceDTO> fotoSequence = (List<FotoSequenceDTO>) request.getAttribute("fotoSequence");
	List<CommentoCardDto> commentiCard = (List<CommentoCardDto>) request.getAttribute("commentiCard");
	Rifugio rif = (Rifugio) request.getAttribute("rif");
	Boolean aperto = (Boolean) request.getAttribute("aperto");
	Long idRif = rif.getId();
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	String apertura = rif.getDataApertura().toLocalDate().format(formatter);
	String chiusura = rif.getDataChiusura().toLocalDate().format(formatter);
	Date oggi = Date.valueOf(LocalDate.now());
	String min = null;
	if(oggi.compareTo(rif.getDataApertura())>=0) {
		min = oggi.toString();
	}
	else min = rif.getDataApertura().toString();
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Turismo Dolomiti</title>
		
		<link rel="stylesheet" type="text/css" href="http://cdn.leafletjs.com/leaflet/v0.7.7/leaflet.css" />
		<link rel="stylesheet" type="text/css" href="/webjars/bootstrap/4.1.0/css/bootstrap.min.css" />
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" type="text/css" href="/css/turismoDolomitiCommon.css"/>
		<style>
			.myCarousel {
				width:1100px;
				height: 400px;
				margin-left: auto;
				margin-right: auto;
			}
			.sequence {
				object-fit:cover;
			}
			.fotoCommento {
				width:40px;
				height:40px;
			}
			.myComBtnLink {
				text-decoration:none;
				padding-right:7px;
				padding-left:0px;
				padding-top:0px;
			}
			.timestamp {
				font-size: 12px;
				color: #A6ACAF;
			}
			.comPhotoCol {
				padding-top:5px;
			}
			.rightCol {
				padding-right:0px;
			}
			.leftMap {
				width: 298.328px; 
				height: 370px; 
				border: 1px solid #AAA;
			}
			.rifInfo {
				font-size:1.10rem;
			}
			.myPre {
				font-family:-apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif,"Apple Color Emoji","Segoe UI Emoji","Segoe UI Symbol";
				font-size:100%;
			}
			.boldSpan {
				font-weight:600;
			}
		</style>
    	<script type="text/javascript" src="/webjars/jquery/3.3.1/jquery.min.js"></script>
    	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
		<script type="text/javascript" src="/webjars/bootstrap/4.1.0/js/bootstrap.min.js"></script>
    	<script type='text/javascript' src='http://cdn.leafletjs.com/leaflet/v0.7.7/leaflet.js'></script>
    	<script src='//api.tiles.mapbox.com/mapbox.js/plugins/leaflet-omnivore/v0.3.1/leaflet-omnivore.min.js'></script>
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
    			var map = L.map( 'map', {
   					center: [46.62303384721474, 12.009429931640625],
    				minZoom: 7,
    				zoom: 9
   				})
   				var layer1 = L.tileLayer( 'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
					attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>',
					subdomains: ['a', 'b', 'c']
				}).addTo( map )
				var latitude = <%=rif.getLatitude()%>;
				var longitude = <%=rif.getLongitude()%>;
				var marker;
				var rifIcon = L.icon({
				    iconUrl: '/rifMarker.png',
				    shadowUrl: '/pin-shadow.png',

				    iconSize:     [70, 70], // size of the icon
				    shadowSize:   [70, 70], // size of the shadow
				    iconAnchor:   [33, 69], // point of the icon which will correspond to marker's location
				    shadowAnchor: [23, 65],  // the same for the shadow
				    popupAnchor:  [0, -50] // point from which the popup should open relative to the iconAnchor
				});
				if(latitude != null && longitude != null) {
					marker = new L.marker([latitude, longitude], {icon: rifIcon}).bindPopup("<%=rif.getNome()%>").addTo(map);
					var latLngs = [ marker.getLatLng() ];
					var markerBounds = L.latLngBounds(latLngs);
				  	map.fitBounds(markerBounds);
					
				}
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
			<div class="modal fade" id="deleteRifugioModal" tabindex="-1" role="dialog" aria-labelledby="deleteRifugioModalLabel" aria-hidden="true">
		  		<div class="modal-dialog" role="document">
				    <div class="modal-content">
				      <div class="modal-header">
				        <h5 class="modal-title" id="deleteRifugioModalLabel">Eliminazione</h5>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
				          <span aria-hidden="true">&times;</span>
				        </button>
				      </div>
				      <div class="modal-body">
				      	<p class="lead">Confermi di voler eliminare il rifugio?</p>
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
				        <a class="btn btn-danger" href="/rifugio/<%=idRif%>/cancella">Conferma</a>
				      </div>
				    </div>
		  		</div>
			</div>
			<div class="container">
				<% if(!fotoSequence.isEmpty()){ %>
				<div class="row">
					<%@ include file="/include/photoCarousel.txt" %>
				</div>
				<% } %>
				<div class="row">
					<%@ include file="/include/rifNav.txt" %>
					<div class="col-md-10">
						<div class="row">
							<div class="col-md-10 myCol">
								<h1>${rif.getNome()}, ${rif.getAltitudine()}m</h1>
								<p class="lead subtitle">${rif.getMassiccioMontuoso()}</p>
								<p class="lead rifInfo"><%=apertura%> - <%=chiusura%></p>
								<% if(aperto) { %>
								<span class="lead rifInfo"><i class="fa fa-circle fa-md" style="color:green;"></i>  Aperto</span>
								<%} else { %>
								<span class="lead rifInfo"><i class="fa fa-circle fa-md" style="color:red;"></i>  Chiuso</span>
								<% } %>
							</div>
							<div class="col-md-2">
							<% if(logged && (gestoriRifugio.contains(loggedUser.getIdUtente()) || loggedUser.getCredenziali().equals(CredenzialiUtente.Admin))) { %>
							<div class="btn-group" role="group">
							  	<button type="button" class="btn btn-light" data-toggle="modal" data-target="#deleteRifugioModal" title="elimina">
									<i class="fa fa-trash-o fa-lg"></i>
								</button>
							  	<div class="btn-group" role="group">
									<div class="dropdown">
					  					<button class="btn btn-light dropdown-toggle" type="button" id="modifyButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" title="modifica">
					    					<i class="fa fa-pencil"></i>
					  					</button>
					  					<div class="dropdown-menu" aria-labelledby="modifyButton">
						    				<a class="dropdown-item" href="/rifugio/<%=idRif%>/modifica">Modifica rifugio</a>
						    				<a class="dropdown-item" href="/rifugio/<%=idRif%>/modifica/foto">Modifica foto</a>
					  					</div>
									</div>
								</div>
							</div>
								<% } %>
							</div>
						</div>
						
						<hr>
						<div class="row">
							<div class="col-md-8 myCol">
								<p class="lead subtitle">Descrizione:</p>
								<hr>
								<pre class="myPre">${rif.getDescrizione()}</pre>
								<p class="lead subtitle">Contatti:</p>
								<hr>
								<p><span class="boldSpan">Email:</span> ${rif.getEmail()}</p>
								<p><span class="boldSpan">Tel:</span> ${rif.getTel()}</p>
							</div>
							<div class="col-md-4 myCol rightCol">
								<div id="map" class="leftMap"></div>
								<div class="card mt-2">
									<div class="card-body">
									    <h5 class="card-title">Pernottamento:</h5>
									    <h6 class="card-subtitle mb-2 text-muted">Prezzo a notte: ${rif.getPrezzoPostoLetto()}€</h6>
									    <%if(!gestoriRifugio.isEmpty()) { 
									    	if(logged) {
									    %>
									    <form class="login" method="POST" action="/rifugio/<%=idRif%>/prenotazione">
											<div class="form-group col-md-9 myCol">
										 		<label for="inputCheckIn">Check in</label>
										 		<input id="inputCheckIn" type="date" Class="form-control" name="checkIn" required min="<%=min%>" max="<%=rif.getDataChiusura().toString()%>"/>
										 	</div>
										 	<div class="form-group col-md-9 myCol">
									      		<label for="inputCheckOut">Check out</label>
									      		<input type="date" class="form-control" id="inputCheckOut"  name="checkOut" required min="<%=min%>" max="<%=rif.getDataChiusura().toString()%>"/>
											</div>
											<div class="form-group col-md-6 myCol">
												<label for="inputOspiti">Ospiti</label>
												<input type="number" step="1" maxLength="3" required id="inputOspiti" name="numPersone" class="form-control"/>						
											</div>
											<input type="submit" class="btn btn-primary" value="Cerca"/>
									    </form>
									    <% } else { %>
									    <div class="alert alert-primary" role="alert">
							  				<span><i class="fa fa-info-circle fa-md" style="color: #21618C;"></i> Se vuoi prenotare devi effettuare il login</span>
										</div>
									   
									    <% } } else { %>
									    <div class="alert alert-primary" role="alert">
							  				<span><i class="fa fa-info-circle fa-md" style="color: #21618C;"></i> Non si accettano prenotazioni online, si prega di contattare il rifugio direttamente.</span>
										</div>
									    <% } %>
									</div>
								</div>
							</div>
						</div>
						<%@ include file="/include/commentiNonCanc.txt" %>
						<form method="POST" class="login" action="/rifugio/<%=idRif%>/aggiungiCommento">
							<div class="form-group col-md-8 myCol">
								<label for="inputCommento">Commenta</label>
								<textarea name="commento" id="inputCommento" required maxLength="516" rows="4" class="form-control"></textarea>
							</div>
							<div class="col-md-2 myCol">
							<input type="submit" class="btn btn-primary" value="Pubblica"/>
							</div>
						</form>
						
					</div>
				</div>
			</div>
		</main>
		<%@ include file="/include/footer.txt" %>
	</body>
</html>