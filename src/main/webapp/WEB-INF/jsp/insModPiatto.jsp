<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>
<%@ page import="com.student.project.TurismoDolomiti.dto.LoggedUserDTO" %>
<%@ page import="com.student.project.TurismoDolomiti.enums.CredenzialiUtente" %>
<%@ page import="java.util.List" %>

<%
	LoggedUserDTO loggedUser = (LoggedUserDTO) request.getAttribute("loggedUser");
	Boolean logged = (Boolean) request.getAttribute("logged");
	List<Long> gestoriRifugio = (List<Long>) request.getAttribute("gestoriRifugio");
	String nomeRif = (String) request.getAttribute("nomeRif");
	Long idRif = (Long) request.getAttribute("idRif");
	String messaggio = (String) request.getAttribute("messaggio");
	String azione = (String) request.getAttribute("azione");
	String actionUrl = null;
	if(azione.equals("modifica")) {
		Long idPiatto = (Long) request.getAttribute("idPiatto");
		actionUrl = "/rifugio/" + idRif + "/menu/modificaPiatto/submit";
	}
	else {
		actionUrl = "/rifugio/" + idRif + "/menu/aggiungiPiatto/submit";
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
		<link rel="stylesheet" type="text/css" href="/webjars/bootstrap/4.1.0/css/bootstrap.min.css" />
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<style>
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
			.col-md-6, .myCol {
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
				width:80%
			}
			.subtitle {
				font-size:1.50rem;
			}
		</style>
	</head>
	<body>
		<%@ include file="/include/header.txt" %>
		<main>
			<div class="container">
				<div class="row">
						<%@include file="/include/rifNav.txt" %>
					<div class="col-md-10">
						<h1>${nomeRif}</h1>
						<p class="lead subtitle">Menu: <%if(azione.equals("modifica")) { %>modifica piatto <%} else { %>nuovo piatto<% } %></p>
						<hr>
						<% if(messaggio != null) { %>
							<div class="alert alert-warning" role="alert">
							  <span><i class="fa fa-exclamation-triangle fa-lg" style="color: #FFC300;"></i> ${messaggio}</span>
							</div>
						<% } %>
						<springForm:form action="<%=actionUrl%>" modelAttribute="piattoForm" method="POST" cssClass="login">
							<div class="form-group col-md-6">
								<label for="inputNome">Nome</label>
								<springForm:input type="text" required="true" minLength="2" maxLength="64" cssClass="form-control" path="nome"/>
								<springForm:errors cssClass="error" path="nome"/>
							</div>
							<div class="form-group col-md-3 myCol">
								<label for="inputPrezzo">Prezzo</label>
								<div id="inputPrezzo" class="input-group">
									<springForm:input type="number" cssClass="form-control my-input-group" path="prezzo" required="true" step="0.01" maxLength="5"/>
									<div class="input-group-append">
										<span class="input-group-text myAppend">€</span>
									</div>
								</div>
								<springForm:errors cssClass="error" path="prezzo"/>
							</div>
							<div class="form-group col-md-6">
								<label for="inputCat">Categoria</label>
								<springForm:select cssClass="form-control" required="true" path="categoria" id="inputCat">
									<springForm:option value="">Selezione categoria</springForm:option>
									<springForm:option value="Primi"/>
									<springForm:option value="Secondi"/>
									<springForm:option value="Contorni"/>
									<springForm:option value="Dessert"/>
								</springForm:select>
							</div>
							<div class="form-group col-md-6">
								<label for="inputDesc">Descrizione</label>
								<springForm:textarea rows="5" required="true" minLength="2" maxLength="516" cssClass="form-control" path="descrizione"/>
								<springForm:errors cssClass="error" path="descrizione"/>
							</div>

							<%if(azione.equals("modifica")) { %>
							<input type="hidden" name="idPiatto" value="${idPiatto}">
							<% } %>
							<div class="col-md-2">
								<input type="submit" class="btn btn-primary btn-sm" value="Salva"/>
							</div>
						</springForm:form>
					</div>
				</div>
			</div>
		</main>
		<%@ include file="/include/footer.txt" %>
	</body>
</html>