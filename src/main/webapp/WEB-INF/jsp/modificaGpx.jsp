<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>
<%@ page import="com.student.project.TurismoDolomiti.dto.LoggedUserDTO" %>
<%@ page import="com.student.project.TurismoDolomiti.entity.CredenzialiUtente" %>
<%
	LoggedUserDTO loggedUser = (LoggedUserDTO) request.getAttribute("loggedUser");
	Boolean logged = (Boolean) request.getAttribute("logged");
	String gpxPath = (String) request.getAttribute("gpxPath");
	Long idEsc = (Long) request.getAttribute("idEsc");
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

			  // We can attach the `fileselect` event to all file inputs on the page
			  $(document).on('change', ':file', function() {
			    var input = $(this),
			        numFiles = input.get(0).files ? input.get(0).files.length : 1,
			        label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
			    input.trigger('fileselect', [numFiles, label]);
			  });

			  // We can watch for our custom `fileselect` event like this
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
					 	<h1>Modifica gpx</h1>
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