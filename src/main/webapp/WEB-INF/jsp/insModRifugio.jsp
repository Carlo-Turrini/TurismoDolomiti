<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>
<%@ page import="com.student.project.TurismoDolomiti.dto.LoggedUserDTO" %>
<%@ page import="com.student.project.TurismoDolomiti.enums.CredenzialiUtente" %>
<%@ page import="com.student.project.TurismoDolomiti.formValidation.RifugioForm" %>
<%@ page import="java.util.List" %>
<%
	LoggedUserDTO loggedUser = (LoggedUserDTO) request.getAttribute("loggedUser");
	Boolean logged = (Boolean) request.getAttribute("logged");
	String azione = (String) request.getAttribute("azione");
	List<String> messaggi = (List<String>) request.getAttribute("messaggi");
	RifugioForm rifForm = (RifugioForm) request.getAttribute("rifForm");
	String actionUrl = null;
	String titoloForm = null;
	if(azione.equals("inserimento")) {
		actionUrl = "/elencoRifugi/inserisciRifugio/submit";
		titoloForm = "Nuovo rifugio";
	}
	else if(azione.equals("modifica")) {
		Long idRif = (Long) request.getAttribute("idRif");
		actionUrl = "/rifugio/" + idRif + "/modifica/submit";
		titoloForm = "Modifica rifugio";
		
	}
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Turismo Dolomiti</title>

    	<script type="text/javascript" src="/webjars/jquery/3.3.1/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/js/select2.min.js"></script>
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
				var latitude = <%=rifForm.getLatitude()%>;
				var longitude = <%=rifForm.getLongitude()%>;
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
					marker = new L.marker([latitude, longitude], {icon: rifIcon}).addTo(map);
					var latLngs = [ marker.getLatLng() ];
					var markerBounds = L.latLngBounds(latLngs);
				  	map.fitBounds(markerBounds);
					
				}
    			map.on('click', function(e) {
					var latlng = map.mouseEventToLatLng(e.originalEvent);
					document.getElementById('inputLat').value = latlng.lat;
					document.getElementById('inputLon').value = latlng.lng;
					if(marker) {
						map.removeLayer(marker);
					}	
					marker = new L.Marker(e.latlng, {icon: rifIcon}).addTo(map);
				})
    		}
    		window.addEventListener("load", onLoadHandler);
    	</script>
    	<link rel="stylesheet" type="text/css" href="http://cdn.leafletjs.com/leaflet/v0.7.7/leaflet.css" />
		<link rel="stylesheet" type="text/css" href="/webjars/bootstrap/4.1.0/css/bootstrap.min.css" />
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    	<link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/css/select2.min.css" rel="stylesheet" />
		
		<style>
			.wrap{
			    width: 100%;
			    height: 100%;
			    min-height: 100%;
			    position: absolute;
			    top: 0;
			    left: 0;
			    z-index: 99;
			}
			
			
			p.form-title{
			    font-family: 'Open Sans' , sans-serif;
			    font-size: 20px;
			    font-weight: 600;
			    text-align: left;
			    color: black;
			    margin-top: 0%;
			    text-transform: uppercase;
			    letter-spacing: 4px;
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
			form.login input.my-input-group {
				width:80%
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
			    margin-top: 16px;
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
			
			.error {
				color: red;
				font-style: italic;
				font-family: 'Open Sans' , sans-serif;
			}
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
			.log {
				
				padding-right:10px;
			}
			.col-md-8 {
				padding-left:0px;
				
			}
			.col-md-2 {
				padding:0px;
			}
			.nav-link.active {
				color:black;
				font-weight: 500;
			}
			.nav-link.active:hover {
				color:black;
			}
			.nav-link{
				color:#5E5956;
				font-size: 16px; 
			}
			.nav-link:hover {
				transition: color 0.5s ease;
				color:#d3d3d3;
			}
			.nav-link.myLink {
				padding-left:0px;
			}
			.btn.nav-btn {
				margin-top:.5rem;
			}
			.lead.myLead {
				margin-bottom:0px;
				font-size: 18px;
			}
			.pageTitle {
				font-variant: small-caps;
			}
		</style>
	</head>
	<body>
		<%@include file="/include/header.txt" %>
		<main>
			<div class="container">
				<div class="row">
					<% if(azione.equals("modifica")) { %>
						<%@include file="/include/modRifNav.txt"%>
					<% } %>
					<div class="col-md-6">
						<% if(messaggi != null && !messaggi.isEmpty()) { %>
						<div class="alert alert-warning">
							<div class="row">
								<div class="col-md-1">
									<span style="font-size: 1em; color: #FFC300;"><i class="fa fa-exclamation-triangle fa-lg"></i></span>
								</div>
								<div class="col-md-11">
									<% for(String messaggio : messaggi) { %>
										<p class="lead myLead"><%=messaggio%></p>
									<% } %>
								</div>
							</div>
						</div>
						
						
						<% } %>
						<springForm:form action="<%=actionUrl%>" method="POST" modelAttribute="rifForm" cssClass="login">
							<h1 class="pageTitle"><%=titoloForm%></h1>
							<hr>
							<div class="form-group col-md-8 myCol">
								<label for="inputNome">Nome</label>
								<springForm:input type="text" cssClass="form-control" id="inputNome" path="nome" required="true" placeholder="Rifugio Lavaredo" minLength="2" maxLength="128"/>
								<springForm:errors cssClass="error" path="nome"/>
							</div>
							<div class="form-group col-md-8 myCol">
								<label for="inputMonti">Massiccio montuoso</label>
								<springForm:input type="text" cssClass="form-control" id="inputMonti" path="massiccioMontuoso" required="true" placeholder="Gruppo dei Cadini" minLength="2" maxLength="64"/>
								<springForm:errors cssClass="error" path="massiccioMontuoso"/>
							</div>
							<label for="start">Inserisci le coordinate del rifugio:</label>
							<div id="start" class="form-row">
								<div class="form-group col">
									<label for="inputLat">Latitudine</label>
									<springForm:input type="number" step="0.000000000000001" cssClass="form-control" id="inputLat"  path="latitude" required="true"/>
									<springForm:errors path="latitude" cssClass="error"/>
								</div>
								<div class="form-group col">
									<label for="inputLon">Longitudine</label>
									<springForm:input type="number" step="0.000000000000001" cssClass="form-control" id="inputLon" path="longitude" required="true"/>
									<springForm:errors path="longitude" cssClass="error"/>
								</div>
							</div>
							<div class="form-group col-md-8 myCol">
								<label for="altitudine">Altitudine</label>
								<div id="altitudine" class="input-group">
									<springForm:input type="number" step="1" cssClass="form-control my-input-group" maxLength="4" required="true" path="altitudine"/>
									<div class="input-group-append">
											<span class="input-group-text myAppend">m</span>
									</div>
									<springForm:errors path="altitudine" cssClass="error"/>
								</div>
							</div>
							<label for="contatti">Contatti:</label>
							<div id="contatti" class="form-row">
								<div class="form-group col">
							 		<label for="inputTel">Telefono</label>
							 		<springForm:input id="inputTel" type="tel" cssClass="form-control" size="11" pattern="[0-9]{10}" required="true" maxLength="10" path="tel"/>
							 		<springForm:errors cssClass="error" path="tel"/>
							 	</div>
							 	<div class="form-group col">
									      <label for="inputEmail">Email</label>
									      <springForm:input type="email" cssClass="form-control" id="inputEmail" placeholder="Email" path="email" required="true" minLength="7" maxLength="128"/>
									      <springForm:errors path="email" cssClass="error"/>
									      
								</div>
							</div>
							<div class="form-group col-md-8 myCol">
								<label for="inputPrezzo">Prezzo posto letto</label>
								<div id="inputPrezzo" class="input-group">
									<springForm:input type="number" step="1" cssClass="form-control my-input-group"  maxLength="3" required="true" path="prezzoPostoLetto"/>
									<div class="input-group-append">
											<span class="input-group-text myAppend">€</span>
									</div>
									<springForm:errors path="prezzoPostoLetto" cssClass="error"/>
								</div>
							</div>
							<div class="form-row">
								<div class="form-group col">
							 		<label for="inputDataAp">Data apertura</label>
							 		<springForm:input id="inputDataAp" type="date" cssClass="form-control" required="true" path="dataApertura"/>
							 		<springForm:errors cssClass="error" path="dataApertura"/>
							 	</div>
							 	<div class="form-group col">
								      <label for="inputDataCh">Data chiusura</label>
								      <springForm:input type="date" cssClass="form-control" id="inputDataCh"  path="dataChiusura" required="true"/>
								      <springForm:errors path="dataChiusura" cssClass="error"/> 
								</div>
							</div>
							<div class="form-group">
								<label for="inputDesc">Descrizione</label>
								<springForm:textarea cssClass="form-control" path="descrizione" id="inputDesc" maxLength="2048" rows="10" cols="100" wrap="hard"/>
								<springForm:errors cssClass="error" path="descrizione"/>
							</div>

							<div class="col-md-2">
						 		<input type="submit" class="btn btn-primary btn-sm" value="Salva"/>
						 	</div>
						 	
						</springForm:form>
					</div>
					<div class="col-md-4">
						<div id="map" style="width: 350px; height: 370px; border: 1px solid #AAA;"></div>
					</div>
				</div>
			</div>
		</main>
		<%@include file="/include/footer.txt" %>
	</body>
</html>