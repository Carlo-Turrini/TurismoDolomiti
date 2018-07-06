<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>
<%@ page import="com.student.project.TurismoDolomiti.dto.LoggedUserDTO" %>
<%@ page import="com.student.project.TurismoDolomiti.dto.RifugioNomeIdDTO" %>
<%@ page import="com.student.project.TurismoDolomiti.enums.CredenzialiUtente" %>
<%@ page import="com.student.project.TurismoDolomiti.formValidation.EscursioneForm" %>
<%@page import="java.util.List" %>

<%
	LoggedUserDTO loggedUser = (LoggedUserDTO) request.getAttribute("loggedUser");
	Boolean logged = (Boolean) request.getAttribute("logged");
	String azione = (String) request.getAttribute("azione");
	List<RifugioNomeIdDTO> rifugiId = (List<RifugioNomeIdDTO>) request.getAttribute("nomiIdRif");
	EscursioneForm escForm = (EscursioneForm) request.getAttribute("escForm");
	String messaggio = (String) request.getAttribute("messaggio");
	String incompleteMessage = (String) request.getAttribute("incompleteMessage");
	Boolean completo = (Boolean) request.getAttribute("completo");
	String actionUrl = null;
	String titoloForm = null;
	String gpxPath = null;
	Double latitude = null;
	Double longitude = null;
	String actionMap = null;
	if(azione.equals("modifica")) {
		Long idEsc = (Long) request.getAttribute("idEsc");
		gpxPath = (String) request.getAttribute("gpxPath");
		if(gpxPath != null) {
			actionMap = "gpx";
			
		}
		actionUrl = "/escursione/" + idEsc + "/modifica/submit";
		titoloForm = "Modifica escursione";
	}
	else {
		actionUrl = "/elencoEscursioni/inserisciEscursione/submit";
		titoloForm = "Nuova escursione";
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
				var actionMap ="<%=actionMap%>";
				var azione = "<%=azione%>";
				var latitude = <%=escForm.getLatitude()%>;
				var longitude = <%=escForm.getLongitude()%>;
				var marker;
				if(azione == "modifica" && actionMap == "gpx") {
						var runLayer = omnivore.gpx('<%=gpxPath%>') .on('ready', function() {
							marker = new L.marker([latitude, longitude]).bindPopup('Start').addTo(map);
					        map.fitBounds(runLayer.getBounds());
					    }).addTo(map);
				}
				else if(latitude != null && longitude != null){
					marker = new L.marker([latitude, longitude]).bindPopup('Start').addTo(map);
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
					marker = new L.Marker(e.latlng).addTo(map);
				})
				$(function()
				{
					 $(".multipleCss").select2();
				});
    		}
    		window.addEventListener("load", onLoadHandler);
    	</script>
    	<link rel="stylesheet" type="text/css" href="http://cdn.leafletjs.com/leaflet/v0.7.7/leaflet.css" />
		<link rel="stylesheet" type="text/css" href="/webjars/bootstrap/4.1.0/css/bootstrap.min.css" />
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    	<link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/css/select2.min.css" rel="stylesheet" />
		<link rel="stylesheet" type="text/css" href="/css/turismoDolomitiCommon.css"/>
		<style>
		.col-md-8 {
			padding-left:0px;
		}
		.col-md-2 {
			padding:0px;
		}
		</style>
	</head>
	<body>
		<%@include file="/include/header.txt" %>
		<main>
			<div class="container">
				<div class="row">
					<% if(azione.equals("modifica")) { %>
						<%@include file="/include/modEscNav.txt"%>
					<% } %>
					<div class="col-md-6">
						<% if(messaggio != null) { %>
						<div class="alert alert-warning">
							<p class="lead myLead"><span style="font-size: 1em; color: #FFC300;"><i class="fa fa-exclamation-triangle fa-lg"></i></span>  ${messaggio}</p>
							
						</div>
						<% } %>
						<% if(incompleteMessage != null) { %>
							<div class="alert alert-warning myAlert">
								<p class="lead myLead"><span style="font-size: 1em; color: #FFC300;"><i class="fa fa-exclamation-triangle fa-lg"></i></span>  ${incompleteMessage}</p>
							</div>
						<% } %>
						<springForm:form action="<%=actionUrl%>" method="POST" modelAttribute="escForm" cssClass="login">
							<h1 class="pageTitle"><%=titoloForm%></h1>
							<hr>
							<div class="form-group col-md-8 ">
								<label for="inputNome">Nome</label>
								<springForm:input type="text" cssClass="form-control" id="inputNome" path="nome" required="true" placeholder="Giro del camoscio" minLength="2" maxLength="128"/>
								<springForm:errors cssClass="error" path="nome"/>
							</div>
							<div class="form-group col-md-8 ">
								<label for="inputLabel">Etichetta</label>
								<springForm:input type="text" cssClass="form-control" id="inputLabel" path="label" required="true" placeholder="Sentiero mozzafiato in cresta" minLength="2" maxLength="256"/>
								<springForm:errors cssClass="error" path="label"/>
							</div>
							<div class="form-group col-md-8 ">
								<label for="inputMonti">Massiccio montuoso</label>
								<springForm:input type="text" cssClass="form-control" id="inputMonti" path="massiccioMontuoso" required="true" placeholder="Gruppo dei Cadini" minLength="2" maxLength="64"/>
								<springForm:errors cssClass="error" path="massiccioMontuoso"/>
							</div>
							<div class="form-group col-md-6 myCol ">
								<label for="inputTipo">Tipologia di escursione</label>
								<springForm:select required="true" cssClass="form-control" id="inputTipo" path="tipologia">
									<springForm:option value="" label="Seleziona tipologia"/>
									<springForm:option value="Trekking"/>
									<springForm:option value="Ferrata"/>
								</springForm:select>
								<springForm:errors cssClass="error" path="tipologia"/>
							</div>
							<label for="start">Inserisci il punto di partenza dell'escursione:</label>
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
							<div class="form-group col-md-6 myCol">
								<label for="inputDiff">Difficolt&aacute;</label>
								<springForm:select required="true" cssClass="form-control" id="inputDiff" path="difficolta">
									<springForm:option value="" label="Seleziona difficoltà"/>
									<springForm:option value="Facile"/>
									<springForm:option value="Medio"/>
									<springForm:option value="Difficile"/>
								</springForm:select>
								<springForm:errors cssClass="error" path="difficolta"/>
							</div>
							<div class="form-group col-md-6 myCol">
								<label for="inputLun">Lunghezza</label>
								<div id="inputLun" class="input-group mb-3">
									<springForm:input type="number" cssClass="form-control my-input-group" path="lunghezza" required="true" step="1" maxLength="3"/>
									<div class="input-group-append">
										<span class="input-group-text myAppend">km</span>
									</div>
									<springForm:errors cssClass="error" path="lunghezza"/>
								</div>
							</div>
							<div class="form-group col-md-6 myCol ">
								<label for="inputDur">Durata</label>
								<div id="inputDur" class="input-group">
									<springForm:input type="number" cssClass="form-control my-input-group" path="durata" required="true" step="0.25" maxLength="5"/>
									<div class="input-group-append">
										<span class="input-group-text myAppend">ore</span>
									</div>
								</div>
								
								<springForm:errors cssClass="error" path="durata"/>
							</div>
							<div class="form-row">
								<div class="form-group col">
									<label for="inputDisSal">Dislivello salita</label>
									<div id="inputDisSal" class="input-group mb-3">
										<springForm:input type="number" cssClass="form-control my-input-group" path="dislivelloSalita" required="true" step="1" maxLength="4"/>
										<div class="input-group-append">
											<span class="input-group-text myAppend">m</span>
										</div>
									</div>
									<springForm:errors cssClass="error" path="dislivelloSalita"/>
								</div>
								<div class="form-group col">
									<label for="inputDisDisc">Dislivello discesa</label>
									<div id="inputDisDisc" class="input-group mb-3">
										<springForm:input type="number" cssClass="form-control my-input-group" path="dislivelloDiscesa" required="true" step="1" maxLength="4"/>
										<div class="input-group-append">
											<span class="input-group-text myAppend">m</span>
										</div>
									</div>
									<springForm:errors cssClass="error" path="dislivelloDiscesa"/>
								</div>
							</div>
							<div class="form-group col-md-11 myCol">
								<label for="inputDesc">Descrizione</label>
								<springForm:textarea cssClass="form-control" path="descrizione" id="inputDesc" maxLength="2048" rows="10" cols="100" wrap="hard"/>
								<springForm:errors cssClass="error" path="descrizione"/>
							</div>
							<div class="form-group col-md-8 ">
								<label for="inputPuntiRistoro">Punti di ristoro</label>
								<springForm:select multiple="true" path="idPuntiRistoro" id="inputPuntiRistoro" cssClass="multipleCss form-control">
									<springForm:option value="" label="Seleziona i punti di ristoro" disabled="true"/>
									<% for(RifugioNomeIdDTO punto : rifugiId ) { %>
										<springForm:option value="<%=punto.getIdRif()%>" label="<%=punto.getNomeRif()%>"/>
									<% } %>
								</springForm:select>
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