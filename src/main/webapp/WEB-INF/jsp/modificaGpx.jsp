<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>
<%@ page import="com.student.project.TurismoDolomiti.dto.LoggedUserDTO" %>
<%@ page import="com.student.project.TurismoDolomiti.enums.CredenzialiUtente" %>
<%
	LoggedUserDTO loggedUser = (LoggedUserDTO) request.getAttribute("loggedUser");
	Boolean logged = (Boolean) request.getAttribute("logged");
	String gpxPath = (String) request.getAttribute("gpxPath");
	Long idEsc = (Long) request.getAttribute("idEsc");
	Boolean completo = (Boolean) request.getAttribute("completo");
	String actionMap = null;
	if(gpxPath != null) {
		actionMap = "gpx";
	}
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Turismo Dolomiti</title>
				<script type="text/javascript" src="/webjars/jquery/3.3.1/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
		<script type="text/javascript" src="/webjars/bootstrap/4.1.0/js/bootstrap.min.js"></script>
		<script type='text/javascript' src='http://cdn.leafletjs.com/leaflet/v0.7.7/leaflet.js'></script>
    	<script src='//api.tiles.mapbox.com/mapbox.js/plugins/leaflet-omnivore/v0.3.1/leaflet-omnivore.min.js'></script>
		<script>
		$(function() {

			  $(document).on('change', ':file', function() {
			    var input = $(this),
			        numFiles = input.get(0).files ? input.get(0).files.length : 1,
			        label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
			    input.trigger('fileselect', [numFiles, label]);
			  });

			  $(document).ready( function() {
			      $(':file').on('fileselect', function(event, numFiles, label) {

			          var input = $(this).parents('.input-group').find(':text'),
			              log = numFiles > 1 ? numFiles + ' files selected' : label;

			          if( input.length ) {
			              input.val(log);
			          } else {
			              if( log ) alert(log);
			          }

			      });
			      
			  });
			  
			});
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
				if(actionMap == "gpx") {
					var runLayer = omnivore.gpx('<%=gpxPath%>') .on('ready', function() {
				        map.fitBounds(runLayer.getBounds());
				    }).addTo(map);
				}
					
				
			}
			window.addEventListener('load', onLoadHandler);
	
		</script>
		<link rel="stylesheet" type="text/css" href="http://cdn.leafletjs.com/leaflet/v0.7.7/leaflet.css" />
		<link rel="stylesheet" type="text/css" href="/webjars/bootstrap/4.1.0/css/bootstrap.min.css" />
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" type="text/css" href="/css/turismoDolomitiCommon.css"/>
		<style>
			.mapCss {
				width: 450px; 
				height: 450px;
				border: 1px solid #AAA;
				margin-left:auto;
				margin-right:auto;
				margin-bottom:5px;
			}
			
			.myFileInput {
				margin-bottom:0px;
			}
			.SubButton {
				
				display:block;
				margin-left:auto;
				margin-right:auto;
			}
			.col-md-2 {
				padding:0px;
			}
		</style>
	</head>
	<body>
		<%@ include file="/include/header.txt" %>
		<main>
			<div class="container">
				<div class="row">
					 <%@include file="/include/modEscNav.txt" %>
					 <div class="col-md-9">
					 	<h1 class="pageTitle">Modifica gpx</h1>
					 	<hr>
						<div id="map" class="mapCss"></div> 
					 	<springForm:form method="POST" action="/escursione/${idEsc}/modifica/gpx/submit"  enctype="multipart/form-data">
					 		<div class="input-group mb-3">
								<label class="input-group-prepend myFileInput">
									<span class="btn btn-primary">Sfoglia&hellip; <input type="file" style="display: none;" name="gpx" accept=".gpx"></span>
								</label>
								<input type="text" class="form-control" placeholder="filename" readonly >
							</div>
							<input type="submit" class=" col-md-3 btn btn-primary SubButton" value="Salva"/>
						</springForm:form>
					 </div>
				</div>
			</div>
		</main>
		<%@ include file="/include/footer.txt" %>
	</body>
</html>