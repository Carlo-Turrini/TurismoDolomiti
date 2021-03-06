<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page import="com.student.project.TurismoDolomiti.dto.LoggedUserDTO" %>
<%@ page import="com.student.project.TurismoDolomiti.enums.CredenzialiUtente" %>
<%@ page import="com.student.project.TurismoDolomiti.dto.PrenotazioneClienteCardDto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
	LoggedUserDTO loggedUser = (LoggedUserDTO) request.getAttribute("loggedUser");
	Boolean logged = (Boolean) request.getAttribute("logged");
	String messaggio = (String) request.getAttribute("messaggio");
	List<PrenotazioneClienteCardDto> prenotazioni = (List<PrenotazioneClienteCardDto>) request.getAttribute("prenotazioni");
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
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
			.cardImg {
				height:200px;
				width:100%;
				object-fit:cover;
				
			}
			</style>
	</head>
	<body>
		<%@ include file="/include/header.txt" %>
		<main>
			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<h1 class="pageTitle">Le mie prenotazioni</h1>
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
												<p><span class="boldSpan">Check in:</span> <%=pren.getDataArrivo().toLocalDate().format(formatter)%></p>
											</div>
											<div class="col myCol">
												<p><span class="boldSpan">Check out:</span> <%=pren.getDataPartenza().toLocalDate().format(formatter)%></p>
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