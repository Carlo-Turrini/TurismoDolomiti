<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %> 
<%@ page import="com.student.project.TurismoDolomiti.formValidation.LoginForm" %> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>
<% 
	String messaggio = (String) request.getAttribute("messaggio");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="/webjars/bootstrap/4.1.0/css/bootstrap.min.css" />
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">	
		<style>
			::placeholder {
				color: white;
			}
			.row.reg {
				margin-top: 50px;
			}
			* {
			  margin: 0;
			  padding: 0;
			}
			body {
				background-image: url("/sfondoBody.jpg");
				background-repeat: no-repeat;
				background-attachment: fixed;
				background-size: cover;
				position: relative;
			}

			.col-md-8.reg {
				margin-left:auto;
				margin-right:auto;
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
			    text-align: center;
			    color: #FFFFFF;
			    margin-top: 5%;
			    text-transform: uppercase;
			    letter-spacing: 4px;
			}
			
			form.login input[type="email"], form.login input[type="password"], form.login input[type="text"]
			{
			    width: 100%;
			    margin: 0;
			    padding: 5px 10px;
			    background: 0;
			    border: 0;
			    border-bottom: 1px solid #FFFFFF;
			    outline: 0;
			    font-style: italic;
			    font-size: 12px;
			    font-weight: 400;
			    letter-spacing: 1px;
			    margin-bottom: 5px;
			    color: #FFFFFF;
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
			    font-size: 12px;
			    font-weight: 400;
			    color: #FFFFFF;
			}
			
			.error {
				color: #FFFFFF;
				font-style: italic;
				font-family: 'Open Sans' , sans-serif;
			}
			.myAlert {
				width:210px;
				margin: 0 auto;
			}
			.myLead {
				margin-bottom:0px;
				text-align:center;
			}
		</style>
		
		<title>Turismo Dolomiti</title>
	</head>
	<body>
	<main>
		<div class="container">
			<div class="row reg">
				<div class="col-md-8 reg">
					<% if(messaggio != null) { %>
							<div class="alert alert-warning myAlert">
								<p class="lead myLead" style="font-size:19px;"><span style="font-size: 1em; color: #FFC300;"><i class="fa fa-exclamation-triangle fa-lg"></i></span>  ${messaggio}</p>
							</div>
					<% } %>
					<springForm:form action="/registrazione/submit" modelAttribute="regForm" method="POST" cssClass="login">
						<p class="form-title">Registrazione </p>
						<div class="form-row">
							<div class="form-group col-md-6">
								<label for="inputNome">Nome</label>
								<springForm:input type="text" cssClass="form-control" id="inputNome" placeholder="Nome" path="nome" required="true" minLength="2" maxLength="48"/>
								<springForm:errors path="nome" cssClass="error"/>
							</div>
							<div class="form-group col-md-6">
								<label for="inputCognome">Cognome</label>
								<springForm:input type="text" cssClass="form-control" id="inputCognome" placeholder="Cognome" path="cognome" required="true" minLength="2" maxLength="48" />
								<springForm:errors path="cognome" cssClass="error"/>
							</div>
						</div>
						
						<div class="form-row">
						    <div class="form-group col-md-6">
							      <label for="inputEmail">Email</label>
							      <springForm:input type="email" cssClass="form-control" id="inputEmail" placeholder="Email" path="email" required="true" minLength="7" maxLength="128"/>
							      <springForm:errors path="email" cssClass="error"/>
						    </div>
						    <div class="form-group col-md-6">
							      <label for="inputPassword">Password</label>
							      <springForm:input type="password" class="form-control" id="inputPassword" placeholder="Password" path="password" required="true" minLength="8" maxLength="64"/>
							      <springForm:errors path="password" cssClass="error"/>
						    </div>
					 	</div>
					 	<label for="inputSesso">Sesso</label>
					 	<div class="form-group col-md-16" id="inputSesso">
					 		
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
					 	<div class="form-group col-md-16">
					 		<label for="inputBDay">Data di nascita</label>
					 		<springForm:input type="date" cssClass="form-control" id="inputBDay" path="dataNascita"/>
					 		<springForm:errors path="dataNascita" cssClass="error"/>
					 	</div>
					 	<input type="submit" class="btn btn-primary btn-sm"/>
					</springForm:form>
				</div>
			</div>
		</div>
	</main>
	</body>
</html>