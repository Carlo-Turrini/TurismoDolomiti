<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page import="com.student.project.TurismoDolomiti.dto.LoggedUserDTO" %>
<%@ page import="com.student.project.TurismoDolomiti.entity.CredenzialiUtente" %>
<%@ page import="com.student.project.TurismoDolomiti.dto.PrenotazioneClienteCardDto" %>
<%@ page import="java.util.List" %>
<%
	LoggedUserDTO loggedUser = (LoggedUserDTO) request.getAttribute("loggedUser");
	Boolean logged = (Boolean) request.getAttribute("logged");
	String messaggio = (String) request.getAttribute("messaggio");
	List<PrenotazioneClienteCardDto> prenotazioni = (List<PrenotazioneClienteCardDto>) request.getAttribute("prenotazioni");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Turismo Dolomiti</title>
		
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
			.nav-link{
				color:#5E5956;
				font-size: 16px; 
			}
			.nav-link:hover, .myBtnLink:hover, .deleteBtn:hover {
				transition: color 0.5s ease;
				color:#d3d3d3;
			}
			.boldSpan {
				font-weight:600;
			}
			.cardImg {
				height:200px;
				width:100%;
				object-fit:cover;
				
			}
			.myCol {
				padding-left:0px;
			}
			.smallCapsTitle {
				font-variant:small-caps;
			}
			</style>
	</head>
	<body>
		<%@ include file="/include/header.txt" %>
		<main>
			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<h1 class="smallCapsTitle">Le mie prenotazioni</h1>
						<hr>
						<% if(messaggio != null) { %>
							<div class="alert alert-primary" role="alert">
								  <span><i class="fa fa-info-circle fa-lg" style="color: #21618C;"></i> ${messaggio}</span>
							</div>
						<% } else for(PrenotazioneClienteCardDto pren : prenotazioni) { %>
							<div class="card mb-2">
								<div class="row">
									<div class="col-md-4 myCol">
										<img class="cardImg" src="<%=pren.getIconPathRifugio()%>">
									</div>
									<div class="col-md-8 myCol">
										<h4 class="form-title mt-2"><%=pren.getNomeRifugio()%></h4>
										<hr>
										<div class="row">
											<div class="col myCol">
												<p><span class="boldSpan">Check in:</span> <%=pren.getDataArrivo()%></p>
											</div>
											<div class="col myCol">
												<p><span class="boldSpan">Check out:</span> <%=pren.getDataPartenza()%></p>
											</div>
										</div>
										<p><span class="boldSpan">Ospiti:</span> <%=pren.getNumPersone()%></p>
										<a class="btn btn-primary" href="/leMiePrenotazioni/<%=pren.getIdPrenotazione()%>">Vedi prenotazione</a>
									</div>
								</div>
							</div>
						<% } %>
					</div>
				</div>
			</div>
		</main>
		<%@ include file="/include/footer.txt" %>
	</body>
</html>