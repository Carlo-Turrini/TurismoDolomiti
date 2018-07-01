<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>
<%@ page import="com.student.project.TurismoDolomiti.dto.LoggedUserDTO" %>
<%@ page import="com.student.project.TurismoDolomiti.entity.CredenzialiUtente" %>
<%@ page import="com.student.project.TurismoDolomiti.entity.Escursione" %>
<%@ page import="java.util.List" %>

<%
	LoggedUserDTO loggedUser = (LoggedUserDTO) request.getAttribute("loggedUser");
	Boolean logged = (Boolean) request.getAttribute("logged");
	String messaggio = (String) request.getAttribute("messaggio");
	List<Escursione> elencoEsc = (List<Escursione>) request.getAttribute("elencoEsc");
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
					<div class="col-md-4">
						<p class="lead">Filtri:</p>
						<springForm:form method="POST" cssClass="login" action="/elencoEscursioni" modelAttribute="escSearch">
							<div class="form-group">
								<label for="inputNome">Nome dell'escursione</label>
								<springForm:input type="text" cssClass="form-control" path="nome" placeholder="Giro del Paterno"/>
							</div>
							<div class="form-group">
								<label for="inputMassMont">Massiccio montuoso</label>
								<springForm:input type="text" cssClass="form-control" path="massiccioMontuoso" placeholder="Tre Cime di Lavaredo"/>
							</div>
							<div class="form-group col-md-8 myCol ">
								<label for="inputTipo">Tipologia di escursione</label>
								<springForm:select cssClass="form-control" id="inputTipo" path="tipologia">
									<springForm:option value="" label="Seleziona tipologia"/>
									<springForm:option value="Trekking"/>
									<springForm:option value="Ferrata"/>
								</springForm:select>
								<springForm:errors cssClass="error" path="tipologia"/>
							</div>
							<div class="form-group col-md-8 myCol">
								<label for="inputDiff">Difficolt&aacute;</label>
								<springForm:select cssClass="form-control" id="inputDiff" path="difficolta">
									<springForm:option value="" label="Seleziona difficoltà"/>
									<springForm:option value="Facile"/>
									<springForm:option value="Medio"/>
									<springForm:option value="Difficile"/>
								</springForm:select>
								<springForm:errors cssClass="error" path="difficolta"/>
							</div>
							<div class="form-row">
								<div class="form-group col">
									<label for="inputDisSalMin">Dislivello salita min</label>
									<div id="inputDisSalMin" class="input-group mb-3">
										<springForm:input type="number" cssClass="form-control my-input-group" path="dislivelloSalitaMin"  step="1" maxLength="4"/>
										<div class="input-group-append">
											<span class="input-group-text myAppend">m</span>
										</div>
									</div>
								</div>
								<div class="form-group col">
									<label for="inputDisSalMax">Dislivello salita max</label>
									<div id="inputDisSalMax" class="input-group mb-3">
										<springForm:input type="number" cssClass="form-control my-input-group" path="dislivelloSalitaMax" step="1" maxLength="4"/>
										<div class="input-group-append">
											<span class="input-group-text myAppend">m</span>
										</div>
									</div>
								</div>
							</div>
							<div class="form-row">
								<div class="form-group col">
									<label for="inputDisDiscMin">Dislivello discesa min</label>
									<div id="inputDisDisclMin" class="input-group mb-3">
										<springForm:input type="number" cssClass="form-control my-input-group" path="dislivelloDiscesaMin"  step="1" maxLength="4"/>
										<div class="input-group-append">
											<span class="input-group-text myAppend">m</span>
										</div>
									</div>
								</div>
								<div class="form-group col">
									<label for="inputDisDiscMax">Dislivello discesa max</label>
									<div id="inputDisDiscMax" class="input-group mb-3">
										<springForm:input type="number" cssClass="form-control my-input-group" path="dislivelloDiscesaMax" step="1" maxLength="4"/>
										<div class="input-group-append">
											<span class="input-group-text myAppend">m</span>
										</div>
									</div>
								</div>
							</div>
							<div class="form-row">
								<div class="form-group col">
									<label for="inputLunMin">Lunghezza min</label>
									<div id="inputLunMin" class="input-group mb-3">
										<springForm:input type="number" cssClass="form-control my-input-group" path="lunghezzaMin"  step="1" maxLength="3"/>
										<div class="input-group-append">
											<span class="input-group-text myAppend">km</span>
										</div>
									</div>
								</div>
								<div class="form-group col">
									<label for="inputLunMax">Lunghezza max</label>
									<div id="inputLunMax" class="input-group mb-3">
										<springForm:input type="number" cssClass="form-control my-input-group" path="lunghezzaMax" step="1" maxLength="3"/>
										<div class="input-group-append">
											<span class="input-group-text myAppend">km</span>
										</div>
									</div>
								</div>
							</div>
							<div class="form-row">
								<div class="form-group col">
									<label for="inputDurMin">Durata min</label>
									<div id="inputDurMin" class="input-group mb-3">
										<springForm:input type="number" cssClass="form-control my-input-group" path="dislivelloSalitaMin"  step="0.25" maxLength="5"/>
										<div class="input-group-append">
											<span class="input-group-text myAppend">ore</span>
										</div>
									</div>
								</div>
								<div class="form-group col">
									<label for="inputDurMax">Durata max</label>
									<div id="inputDurMax" class="input-group mb-3">
										<springForm:input type="number" cssClass="form-control my-input-group" path="dislivelloDiscesaMax" step="0.25" maxLength="5"/>
										<div class="input-group-append">
											<span class="input-group-text myAppend">ore</span>
										</div>
									</div>
								</div>
							</div>
							<input type="submit" class="btn btn-outline-secondary btn-sm" value="Filtra">
						</springForm:form>
						
					</div>
					<div class="col-md-8">
						<div class="row">
								<div class="col-md-9">
									<h1 class="pageTitle">Elenco delle escursioni</h1>
								</div>
								<div class="col-md-3">
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
							<% for(Escursione esc : elencoEsc){%>
							<div class="col-md-12">
								<a href="/escursione/<%=esc.getId()%>" class="cardHref">
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