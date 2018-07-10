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
		<link rel="stylesheet" type="text/css" href="/css/turismoDolomitiCommon.css"/>
		<style>
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
					<%@include file="/include/modProfiloNav.txt" %>
					<div class="col-md-8">
						<springForm:form action="/profilo/${idUtente}/modifica/submit" modelAttribute="utenteForm" method="POST" cssClass="login">
							<h1 class="pageTitle">Modifica profilo </h1>
							<hr>
							<div class="form-group col-md-4  myCol">
								<label for="inputCred">Credenziali</label>
								<%if(loggedUser.getIdUtente() == idUtente && !loggedUser.getCredenziali().equals(CredenzialiUtente.Admin)) {%>
									<springForm:input id="inputCred" type="text" readonly="true" cssClass="form-control" path="credenziali"/>
									<springForm:errors cssClass="error" path="credenziali"/>
								<% } 
									else if(loggedUser.getCredenziali().equals(CredenzialiUtente.Admin)) {%>
										<springForm:select path="credenziali" cssClass="form-control" required="true" items="${credUt}"/>
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
						 	<div class="form-group col-md-4 myCol">
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
						 	<div class="form-group col-md-4 myCol">
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