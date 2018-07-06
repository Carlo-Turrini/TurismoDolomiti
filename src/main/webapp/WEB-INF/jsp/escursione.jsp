<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page import="com.student.project.TurismoDolomiti.dto.LoggedUserDTO" %>
<%@ page import="com.student.project.TurismoDolomiti.enums.CredenzialiUtente" %>
<%@ page import="com.student.project.TurismoDolomiti.dto.FotoSequenceDTO" %>
<%@ page import="com.student.project.TurismoDolomiti.dto.CommentoCardDto" %>
<%@ page import="com.student.project.TurismoDolomiti.entity.Escursione" %>
<%@ page import="com.student.project.TurismoDolomiti.dto.RifugioCartinaEscursioneCardDto" %>
<%@ page import="java.util.List" %>

<%
	LoggedUserDTO loggedUser = (LoggedUserDTO) request.getAttribute("loggedUser");
	Boolean logged = (Boolean) request.getAttribute("logged");
	List<Long> gestoriRifugio = (List<Long>) request.getAttribute("gestoriRifugio");
	List<FotoSequenceDTO> fotoSequence = (List<FotoSequenceDTO>) request.getAttribute("fotoSequence");
	List<CommentoCardDto> commentiCard = (List<CommentoCardDto>) request.getAttribute("commentiCard");
	Escursione esc = (Escursione) request.getAttribute("esc");
	List<RifugioCartinaEscursioneCardDto> rifugiEscursione = (List<RifugioCartinaEscursioneCardDto>) request.getAttribute("rifugiEscursione");
	Long idEsc = esc.getId();
	


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
				width: 372.906px; 
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
			.altimetria {
				max-width:372.906px;
				object-fit:contain;
			}
			.gal-container{
				padding: 12px;
			}
			.gal-item{
				overflow: hidden;
				padding: 3px;
			}
			.gal-item .box{
				height: 350px;
				overflow: hidden;
			}
			img.gallery{
				height: 100%;
				width: 100%;
				object-fit:cover;
				-o-object-fit:cover;
			}
			.fotoCommento {
				width:40px;
				height:40px;
			}
			.gal-item a.gal-a.alt-item:focus{
				outline: none;
			}
			.gal-item a.gal-a.alt-item:after{
				content:"\f002";
				font-family: FontAwesome;
				opacity: 0;
				background-color: rgba(0, 0, 0, 0.75);
				position: absolute;
				right: 3px;
				left: 3px;
				top: 378px;
				bottom: 3px;
				height:241px;
				text-align: center;
			    line-height: 240px;
			    font-size: 30px;
			    color: #fff;
			    -webkit-transition: all 0.5s ease-in-out 0s;
			    -moz-transition: all 0.5s ease-in-out 0s;
			    transition: all 0.5s ease-in-out 0s;
			}
			.gal-item a.gal-a.alt-item:hover:after{
				opacity: 1;
			}
			.modal-open .gal-container .modal{
				background-color: rgba(0,0,0,0.4);
			}
			.modal-open .gal-item .modal-body{
				padding: 0px;
			}
			.modal-open .gal-item button.close{
			    position: absolute;
			    width: 25px;
			    height: 25px;
			    background-color: #000;
			    opacity: 1;
			    color: #fff;
			    z-index: 999;
			    right: -12px;
			    top: -12px;
			    border-radius: 50%;
			    font-size: 15px;
			    border: 2px solid #fff;
			    line-height: 25px;
			    -webkit-box-shadow: 0 0 1px 1px rgba(0,0,0,0.35);
				box-shadow: 0 0 1px 1px rgba(0,0,0,0.35);
			}
			.modal-open .gal-item button.close:focus{
				outline: none;
			}
			.modal-open .gal-item button.close span{
				position: relative;
				top: -3px;
				font-weight: lighter;
				text-shadow:none;
			}
			.gal-container .modal-dialogue{
				width: 80%;
			}


			.gal-container .modal.fade .modal-dialog {

			    transform: scale(1);
			    top: 70px;
			    opacity: 1;
			    transition: all 0.3s;
			    max-width:760px;
			}

			
			.gal-container .modal.fade .modal-content{
		        height:460px;
		        width:760px;
			 }
 			.escIcon {
				color:#6E2C00;
			}
			.popupImg {
				max-width:100px;
				max-height:100px;
				object-fit:contain;
				margin-left:auto;
				margin-right:auto;
				
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
				var latitude = <%=esc.getLatitude()%>;
				var longitude = <%=esc.getLongitude()%>;
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
				var runLayer = omnivore.gpx('<%=esc.getGpxPath()%>') .on('ready', function() {
					marker = new L.marker([latitude, longitude]).bindPopup("<%=esc.getNome()%>: Partenza").addTo(map);
			        map.fitBounds(runLayer.getBounds());
			    }).addTo(map);
				<% for(RifugioCartinaEscursioneCardDto rif : rifugiEscursione) {%>
					if(<%=rif.getLatitude()%> != null && <%=rif.getLongitude()%> != null) {
						marker = new L.marker([<%=rif.getLatitude()%>, <%=rif.getLongitude()%>], {icon: rifIcon}).bindPopup('<center><a href="/rifugio/<%=rif.getIdRifugio()%>">' + '<%=rif.getNome()%>' +  '</a><br/><img class="popupImg" src="<%=rif.getIconPath()%>" width="180px"/></center>', { maxWidth: 400}).addTo(map);
					}
				<% } %>
    		}
    		window.addEventListener("load", onLoadHandler);
    	</script>
	</head>
	<body>
		<%@ include file="/include/header.txt" %>
		<main>

			<div class="modal fade" id="deleteEscursioneModal" tabindex="-1" role="dialog" aria-labelledby="deleteEscursioneModalLabel" aria-hidden="true">
		  		<div class="modal-dialog" role="document">
				    <div class="modal-content">
				      <div class="modal-header">
				        <h5 class="modal-title" id="deleteEscursioneModalLabel">Eliminazione</h5>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
				          <span aria-hidden="true">&times;</span>
				        </button>
				      </div>
				      <div class="modal-body">
				      	<p class="lead">Confermi di voler eliminare l'escursione?</p>
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
				        <a class="btn btn-danger" href="/escursione/<%=idEsc%>/cancella">Conferma</a>
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
					<%@ include file="/include/escNav.txt" %>
					<div class="col-md-10">
						<div class="row">
							<div class="col-md-10 myCol">
								<h1>${esc.getNome()}</h1>
								<p class="lead subtitle">${esc.getLabel()}</p>
								<p class="lead rifInfo">${esc.getMassiccioMontuoso()}</p>
								<div class="row">
									<div class="col myCol">
										<p class="mb-0"><span class="fa-stack fa-lg escIcon"><i class="fa fa-circle fa-stack-2x"></i><i class="fa fa-map-signs fa-stack-1x fa-inverse"></i></span> <%=esc.getDifficolta().toString()%></p>
									</div>
									<div class="col myCol">
										<p class="mb-0"><span class="fa-stack fa-lg escIcon"><i class="fa fa-circle fa-stack-2x"></i><i class="fa fa-arrows-h fa-stack-1x fa-inverse"></i></span> <%=esc.getLunghezza()%>km</p>
									</div>
									<div class="col myCol">
										<div class="row">
											
												<span class="fa-stack fa-lg escIcon">
													<i class="fa fa-circle fa-stack-2x"></i>
													<i class="fa fa-arrows-v fa-stack-1x fa-inverse"></i>
												</span>
												
											<div class="col myCol">
												<p class="mb-0" style="line-height:20px;"><%=esc.getDislivelloSalita()%>m</p>
												<p class="mb-0"><span class="text-muted"><%=esc.getDislivelloDiscesa()%>m</span></p>													
											</div>
										</div>
									</div>
									<div class="col myCol">
										<p class="mb-0"><span class="fa-stack fa-lg escIcon"><i class="fa fa-circle fa-stack-2x"></i><i class="fa fa-clock-o fa-stack-1x fa-inverse"></i></span> <%=esc.getDurata()%> ore</p>
									</div>
								</div>
							</div>
							<div class="col-md-2">
							<% if(logged && loggedUser.getCredenziali().equals(CredenzialiUtente.Admin)) { %>
							<div class="btn-group" role="group">
							  	<button type="button" class="btn btn-light" data-toggle="modal" data-target="#deleteEscursioneModal" title="elimina">
									<i class="fa fa-trash-o fa-lg"></i>
								</button>
							  	<div class="btn-group" role="group">
									<div class="dropdown">
					  					<button class="btn btn-light dropdown-toggle" type="button" id="modifyButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" title="modifica">
					    					<i class="fa fa-pencil"></i>
					  					</button>
					  					<div class="dropdown-menu" aria-labelledby="modifyButton">
						    				<a class="dropdown-item" href="/escursione/<%=idEsc%>/modifica">Modifica escursione</a>
						    				<a class="dropdown-item" href="/escursione/<%=idEsc%>/modifica/foto">Modifica foto</a>
						    				<a class="dropdown-item" href="/escursione/<%=idEsc%>/modifica/gpx">Modifica gpx</a>
						    				<a class="dropdown-item" href="/escursione/<%=idEsc%>/modifica/altimetria">Modifica altimetria</a>
					  					</div>
									</div>
								</div>
							</div>
								<% } %>
							</div>
						</div>
						
						<hr>
						<div class="row">
							<div class="col-md-7 myCol">
								<p class="lead subtitle">Descrizione:</p>
								<hr>
								<pre class="myPre">${esc.getDescrizione()}</pre>
								<p class="lead subtitle">Punti di Ristoro:</p>
								<hr>
								<div class="row"> 
								<% if(rifugiEscursione.isEmpty()) { %>
								<div class="alert alert-primary" role="alert">
								  	<span><i class="fa fa-info-circle fa-lg" style="color: #21618C;"></i>Nessun punto di ristoro segnalato</span>
								</div>
								<% } else for(RifugioCartinaEscursioneCardDto rif : rifugiEscursione) { %>
									<div class="col-md-8 myCol">
										<p><%=rif.getNome()%></p>
									</div>
									<div class="col-md-4 myCol">
										<a class="btn btn-primary" href="/rifugio/<%=rif.getIdRifugio()%>">Scopri di più</a>
									</div>
								<% } %>
								</div>
							</div>
							<div class="col-md-5 myCol rightCol">
								<div id="map" class="leftMap"></div>
								<div class="mt-2 gal-container gal-item">
									<div class="box">
									<a href="#" data-toggle="modal" data-target="#altimetriaModal" class="gal-a alt-item">
										<img class="altimetria" src="<%=esc.getAltimetriaPath()%>"/>
									</a>
									</div>
					        	<div class="modal fade" id="altimetriaModal" tabindex="-1" role="dialog">
						          <div class="modal-dialog" role="document">
						            <div class="modal-content">
						                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
						              <div class="modal-body">
						                <img class="gallery" src="<%=esc.getAltimetriaPath()%>">
						              </div>
						            </div>
						          </div>
						        </div>
								</div>
							</div>
						</div>
						<%@ include file="/include/commentiNonCanc.txt" %>
						<form method="POST" class="login" action="/escursione/<%=idEsc%>/aggiungiCommento">
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