<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>
<%@ page import="com.student.project.TurismoDolomiti.dto.LoggedUserDTO" %>
<%@ page import="com.student.project.TurismoDolomiti.entity.CredenzialiUtente" %>
<%@ page import="com.student.project.TurismoDolomiti.dto.FotoSequenceDTO" %>
<%@ page import="com.student.project.TurismoDolomiti.dto.CommentoCardDto" %>
<%@ page import="com.student.project.TurismoDolomiti.entity.Rifugio" %>
<%@ page import="java.util.List" %>
<%
	LoggedUserDTO loggedUser = (LoggedUserDTO) request.getAttribute("loggedUser");
	Boolean logged = (Boolean) request.getAttribute("logged");
	List<Long> gestoriRifugio = (List<Long>) request.getAttribute("gestoriRifugio");
	List<FotoSequenceDTO> fotoSequence = (List<FotoSequenceDTO>) request.getAttribute("fotoSequence");
	List<CommentoCardDto> commentiCard = (List<CommentoCardDto>) request.getAttribute("commentiCard");
	Rifugio rif = (Rifugio) request.getAttribute("rif");
	Boolean aperto = (Boolean) request.getAttribute("aperto");
	Long idRif = rif.getId();
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Turismo Dolomiti</title>
		
		<link rel="stylesheet" type="text/css" href="http://cdn.leafletjs.com/leaflet/v0.7.7/leaflet.css" />
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
			.nav-link:hover, .myBtnLink:hover, .deleteBtn:hover {
				transition: color 0.5s ease;
				color:#d3d3d3;
			}
			form.login input[type="email"], form.login input[type="password"], form.login input[type="text"], form.login input[type="tel"], form.login input[type="date"], form.login input[type="number"], .input-group-text.myAppend
			{
			    width: 100%;
			    margin: 0;
			    padding: 5px 10px;
			    background: 0;
			    border: 0;
			    border-bottom: 1px solid #5E5956;
			    outline: 0;
			    font-style: italic;
			    font-size: 12px;
			    font-weight: 400;
			    letter-spacing: 1px;
			    margin-bottom: 5px;
			    color: black;
			    outline: 0;
			}
			form.login textarea {
				width: 100%;
			    margin: 0;
			    padding: 5px 10px;
			    background: 0;
			    border: 0;
			    border: 1px solid #5E5956;
			    outline: 0;
			    font-style: italic;
			    font-size: 12px;
			    font-weight: 400;
			    letter-spacing: 1px;
			    margin-bottom: 5px;
			    color: black;
			    outline: 0;
			}
			
			form.login input[type="submit"]
			{
			    width: 100%;
			    font-size: 14px;
			    text-transform: uppercase;
			    font-weight: 500;
			    outline: 0;
			    cursor: pointer;
			    letter-spacing: 1px;
			}
			
			form.login input[type="submit"]:hover
			{
			    transition: background-color 0.5s ease;
			}
			form.login label
			{
			    font-size: 15px;
			    font-weight: 400;
			    color: black;
			}
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
			.myCol {
				padding-left: 0px;
			}
			.rightCol {
				padding-right:0px;
			}
			.leftMap {
				width: 298.328px; 
				height: 370px; 
				border: 1px solid #AAA;
			}
			.subtitle {
				font-size:1.50rem;
			}
			.rifInfo {
				font-size:1.10rem;
			}
			.myPre {
				font-family:-apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif,"Apple Color Emoji","Segoe UI Emoji","Segoe UI Symbol";
				font-size:100%;
			}
		</style>
    	<script type="text/javascript" src="/webjars/jquery/3.3.1/jquery.min.js"></script>
    	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
		<script type="text/javascript" src="/webjars/bootstrap/4.1.0/js/bootstrap.min.js"></script>
    	<script type='text/javascript' src='http://cdn.leafletjs.com/leaflet/v0.7.7/leaflet.js'></script>
    	<script src='//api.tiles.mapbox.com/mapbox.js/plugins/leaflet-omnivore/v0.3.1/leaflet-omnivore.min.js'></script>
    	<script>
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
				    iconAnchor:   [23, 69], // point of the icon which will correspond to marker's location
				    shadowAnchor: [10, 65],  // the same for the shadow
				    popupAnchor:  [-3, -70] // point from which the popup should open relative to the iconAnchor
				});
				if(latitude != null && longitude != null) {
					marker = new L.marker([latitude, longitude], {icon: rifIcon}).bindPopup("<%=rif.getNome()%>").addTo(map);
					var latLngs = [ marker.getLatLng() ];
					var markerBounds = L.latLngBounds(latLngs);
				  	map.fitBounds(markerBounds);
					
				}
				
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
								<p class="lead rifInfo">${rif.getDataApertura().toString()} - ${rif.getDataChiusura().toString()}</p>
								<% if(aperto) { %>
								<span class="lead rifInfo"><i class="fa fa-circle fa-md" style="color:green;"></i>  Aperto</span>
								<%} else { %>
								<span class="lead rifInfo"><i class="fa fa-circle fa-md" style="color:red;"></i>  Chiuso</span>
								<% } %>
							</div>
							<div class="col-md-2">
							<% if(logged) { %>
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
						    				<a class="dropdown-item" href="/rifugio/<%=idRif%>/modifica/foto">Modifica l'icona del rifugio</a>
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
								<p>Email: ${rif.getEmail()}</p>
								<p>Tel: ${rif.getTel()}</p>
							</div>
							<div class="col-md-4 myCol rightCol">
								<div id="map" class="leftMap"></div>
								<div class="card mt-2">
									<div class="card-body">
									    <h5 class="card-title">Pernottamento:</h5>
									    <h6 class="card-subtitle mb-2 text-muted">Prezzo a notte: ${rif.getPrezzoPostoLetto()}€</h6>
									    <form class="login" method="POST" action="rifugio/<%=idRif%>/prenotazione">
											<div class="form-group col-md-9 myCol">
										 		<label for="inputCheckIn">Check in</label>
										 		<input id="inputCheckIn" type="date" Class="form-control" name="checkIn" required/>
										 	</div>
										 	<div class="form-group col-md-9 myCol">
									      		<label for="inputCheckOut">Check out</label>
									      		<input type="date" class="form-control" id="inputCheckOUT"  name="checkOut" required/>
											</div>
											<div class="form-group col-md-6 myCol">
												<label for="inputOspiti">Ospiti</label>
												<input type="number" step="1" maxLength="3" required id="inputOspiti" name="numPersone" class="form-control"/>						
											</div>
											<input type="submit" class="btn btn-primary" value="Cerca"/>
									    </form>
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