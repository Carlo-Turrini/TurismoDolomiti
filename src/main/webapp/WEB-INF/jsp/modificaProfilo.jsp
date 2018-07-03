<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>
<%@ page import="com.student.project.TurismoDolomiti.dto.LoggedUserDTO" %>
<%@ page import="com.student.project.TurismoDolomiti.enums.CredenzialiUtente" %>
<%
	LoggedUserDTO loggedUser = (LoggedUserDTO) request.getAttribute("loggedUser");
	Boolean logged = (Boolean) request.getAttribute("logged");
	Long idUtente = (Long) request.getAttribute("idUtente");
%>


<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Turismo Dolomiti</title>
		<link rel="stylesheet" type="text/css" href="/webjars/bootstrap/4.1.0/css/bootstrap.min.css" />
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<style>
			input + span {
				padding-right: 30px;
			}
			
			input:invalid+span:after {
				position: absolute; content: '✖';
				padding-left: 5px;
				color: #8b0000;
			}
			
			input:valid+span:after {
				position: absolute;
				content: '✓';
				padding-left: 5px;
				color: #009000;
			}
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
			
			form.login input[type="email"], form.login input[type="password"], form.login input[type="text"], form.login input[type="tel"], form.login input[type="date"]
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
				color: #FFFFFF;
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
			.col-md-4 {
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
		</style>
	</head>
	<body>
		<%@ include file="/include/header.txt" %>
		<main>
			<div class="container">
				<div class="row">
					<%@include file="/include/modProfiloNav.txt" %>
					<div class="col-md-8">
						<springForm:form action="/profilo/${idUtente}/modifica/submit" modelAttribute="utenteForm" method="POST" cssClass="login">
							<p class="form-title">Modifica profilo </p>
							<div class="form-group col-md-4">
								<label for="inputCred">Credenziali</label>
								<%if(loggedUser.getIdUtente() == idUtente && !loggedUser.getCredenziali().equals(CredenzialiUtente.Admin)) {%>
									<springForm:input id="inputCred" type="text" readonly="true" cssClass="form-control" path="credenziali"/>
									<springForm:errors cssClass="error" path="credenziali"/>
								<% } 
									else if(loggedUser.getCredenziali().equals(CredenzialiUtente.Admin)) {%>
										<springForm:select path="credenziali" required="true" items="${credUt}"/>
										<springForm:errors cssClass="error" path="credenziali"/>
								<% } %>
							</div>
							<div class="form-row">
								<div class="form-group col">
									<label for="inputNome">Nome</label>
									<springForm:input type="text" cssClass="form-control" id="inputNome" placeholder="Nome" path="nome" required="true" minLength="2" maxLength="48"/>
									<springForm:errors path="nome" cssClass="error"/>
								</div>
								<div class="form-group col">
									<label for="inputCognome">Cognome</label>
									<springForm:input type="text" cssClass="form-control" id="inputCognome" placeholder="Cognome" path="cognome" required="true" minLength="2" maxLength="48"/>
									<springForm:errors path="cognome" cssClass="error"/>
								</div>
							</div>
							
							<div class="form-row">
							    <div class="form-group col">
								      <label for="inputEmail">Email</label>
								      <springForm:input type="email" cssClass="form-control" id="inputEmail" placeholder="Email" path="email" required="true" minLength="7" maxLength="128"/>
								      <springForm:errors path="email" cssClass="error"/>
								      
							    </div>
							    <div class="form-group col">
								      <label for="inputPassword">Password</label>
								      <springForm:input type="password" class="form-control" id="inputPassword" placeholder="Password" path="password" required="true" minLength="8" maxLength="64"/>
								      <springForm:errors path="password" cssClass="error"/>
							    </div>
						 	</div>
						 	<div class="form-group col-md-4 mycCol">
						 		<label for="inputTel">Telefono</label>
						 		<springForm:input id="inputTel" type="tel" cssClass="form-control" size="11" pattern="[0-9]{10}" maxLength="10" path="tel"/>
						 		<springForm:errors cssClass="error" path="tel"/>
						 	</div>
						 	<label for="inputSesso">Sesso</label>
						 	<div class="form-group col-md-12" id="inputSesso">
						 		<div class="form-check form-check-inline">
							 	<springForm:radiobutton cssClass="form-check-input" name="SessoOptions" id="inline1" value="M" path="sesso"/>
							  	<label class="form-check-label" for="inline1">Uomo</label>
								</div>
								<div class="form-check form-check-inline">
							  	<springForm:radiobutton cssClass="form-check-input" name="SessoOptions" id="inline2" value="F" path="sesso"/>
							  	<label class="form-check-label" for="inline2">Donna</label>
								</div>
								<springForm:errors path="sesso" cssClass="error"/>
						 	</div>
						 	<div class="form-group col-md-4">
						 		<label for="inputBDay">Data di nascita</label>
						 		<springForm:input type="date" cssClass="form-control" id="inputBDay" path="dataNascita" required="true"/>
						 		<springForm:errors path="dataNascita" cssClass="error"/>
						 	</div>
						 	<div class="form-group">
						 		<label for="inputDesc">Descrizione</label>
						 		<springForm:textarea id="inputDesc" cssClass="form-control" path="descrizione" maxLength="2048" rows="5"/>
						 		<springForm:errors cssClass="error" path="descrizione"/>
						 	</div>
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