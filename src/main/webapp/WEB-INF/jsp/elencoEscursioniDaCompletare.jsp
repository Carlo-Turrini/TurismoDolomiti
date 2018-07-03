<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page import="com.student.project.TurismoDolomiti.dto.LoggedUserDTO" %>
<%@ page import="com.student.project.TurismoDolomiti.enums.CredenzialiUtente" %>
<%@ page import="com.student.project.TurismoDolomiti.dto.EscursioneCardDto" %>
<%@ page import="java.util.List" %>

<%
	LoggedUserDTO loggedUser = (LoggedUserDTO) request.getAttribute("loggedUser");
	Boolean logged = (Boolean) request.getAttribute("logged");
	String messaggio = (String) request.getAttribute("messaggio");
	List<EscursioneCardDto> elencoEscDaCompletare = (List<EscursioneCardDto>) request.getAttribute("elencoEscDaCompletare");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Turismo Dolomiti</title>
		
		<link rel="stylesheet" type="text/css" href="/webjars/bootstrap/4.1.0/css/bootstrap.min.css" />
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    	<link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/css/select2.min.css" rel="stylesheet" />
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
			}
			form.login input.my-input-group {
				width:70%
			}
			.myCol {
				padding-left:0px;
			}
			.fotoArticle {
				width:150px;
				height:150px;
				object-fit:cover;
			}
			.rifCard {
				max-height:400px;
			}
			.card-img-top.myCardImg {
				width:100%;
				height:200px;
				object-fit:cover;
			}
			.rifCardText {
			 margin-bottom:0px;
			}
			.rifCardSub {
				color:#5E5956;
				font-size: 1.05rem;
				margin-bottom:5px;
			}
			.pageTitle {
				font-variant: small-caps;
			}
			a.cardHref {
				text-decoration:none;
				color:black;
			}
			.escIcon {
				color:#6E2C00;
			}
		</style>
	</head>
	<body>
		<%@ include file="/include/header.txt" %>
		<main>
			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<div class="row">
								<div class="col-md-10">
									<h1 class="pageTitle">Elenco delle escursioni da completare</h1>
								</div>
								<div class="col-md-2">
								<% if(logged && loggedUser.getCredenziali().equals(CredenzialiUtente.Admin)) { %>
									<a class="btn btn-primary" href="/elencoEscursioni/inserisciEscursione" ><i class="fa fa-plus fa-lg"></i> Nuova escursione</a>
									<% } %>
								</div>
						</div>
						<hr>
						<% if(messaggio != null) { %>
							<div class="alert alert-primary" role="alert">
							  <span><i class="fa fa-info-circle fa-lg" style="color: #21618C;"></i> ${messaggio}</span>
							</div>
						<% } else { %>
							<div class="row">
							<% for(EscursioneCardDto esc : elencoEscDaCompletare){%>
							<div class="col-md-6">
								<a href="/escursione/<%=esc.getId()%>/modifica" class="cardHref">
									<div class="card rifCard mb-2">
										<img class="card-img-top myCardImg" src="<%=esc.getIconPath()%>">
										<div class="card-body">
											<div class="row">
												<div class="col-md-10 myCol">
													<h5 class="card-title rifCardText"><%=esc.getNome()%></h5>
													<p class="card-subtitle mb-2 text-muted rifCardSub"><%=esc.getLabel()%></p>
													<p class="card-text rifCardText mb-1"><%=esc.getMassiccioMontuoso()%></p>
												</div>
												<div class="col-md-2 myCol">
													<p class="card-text"><%=esc.getTipologia().toString()%></p>
												</div>
											</div>
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
									</div>
								</a>
							</div>
						<% } %>
						</div>
						<% } %>
					</div>
				</div>
			</div>
		</main>
		<%@ include file="/include/footer.txt" %>
	</body>
</html>