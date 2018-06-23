<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
<%@ page session="false" %> 
<%@ page import="com.student.project.TurismoDolomiti.formValidation.LoginForm" %> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>


<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="/webjars/bootstrap/4.1.0/css/bootstrap.min.css" />
		<style>
			::placeholder {
				color: white;
			}
			.container {
				position: absolute;
				top: 200px;
				left: 200px;
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
			form
			{
			    width: 250px;
			    margin: 0 auto;
			}
			
			form.login input[type="email"], form.login input[type="password"]
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
			form.login label, form.login a
			{
			    font-size: 12px;
			    font-weight: 400;
			    color: #FFFFFF;
			}
			
			form.login a
			{
			    transition: color 0.5s ease;
			}
			
			form.login a:hover
			{
			    color: #2ecc71;
			}
			.error {
				color: #FFFFFF;
				font-style: italic;
				font-family: 'Open Sans' , sans-serif;
			}
			
			
			
		</style>
		<title>Insert title here</title>
	</head>
	<body>
		<div class="container">
			<div class="row">
				<div class="col-md-15">
					<div class="wrap">
						<springForm:form method="POST" modelAttribute="logForm" cssClass="login" action="/login/submit">
							<p class="form-title">Sign In </p>
							
							<div class="form-group">
								<label for="emailInput">Email</label>
								<springForm:input cssClass="form-control" type="email" require="true" id="emailInput" path="email" placeholder="Email" />
								<span><springForm:errors path="email" cssClass="error"/></span>
							</div>
							<div class="form-group">
								<label for="passInput">Password</label>
								<springForm:input cssClass="form-control" type="password" required="true" id="passInput" path="password" placeholder="Password"/>
								<span><springForm:errors path="password" cssClass="error"/></span>
							</div>
							<input type="submit" class="btn btn-primary btn-sm"/>
							<div class="row">
								<div class="col-md-15">
									<p>Non hai ancora un account? <a href="/registrazione">Registrati</a> </p>
								</div>
							</div>
						</springForm:form>
					</div>
				</div>
			</div>	
		</div>
	</body>
</html>