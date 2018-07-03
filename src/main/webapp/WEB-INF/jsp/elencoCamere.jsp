<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>
<%@ page import="com.student.project.TurismoDolomiti.dto.LoggedUserDTO" %>
<%@ page import="com.student.project.TurismoDolomiti.enums.CredenzialiUtente" %>
<%@ page import="com.student.project.TurismoDolomiti.entity.Camera" %>
<%@ page import="java.util.List" %>
<%
	LoggedUserDTO loggedUser = (LoggedUserDTO) request.getAttribute("loggedUser");
	Boolean logged = (Boolean) request.getAttribute("logged");
	List<Long> gestoriRifugio = (List<Long>) request.getAttribute("gestoriRifugio");
	String nomeRif = (String) request.getAttribute("nomeRif");
	Long idRif = (Long) request.getAttribute("idRif");
	String messaggio = (String) request.getAttribute("messaggio");
	String insertMessage = (String) request.getAttribute("insertMessage");
	List<Camera> camere = (List<Camera>) request.getAttribute("camere");
	
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Turismo Dolomiti</title>
		<script type="text/javascript" src="/webjars/jquery/3.3.1/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
		<script type="text/javascript" src="/webjars/bootstrap/4.1.0/js/bootstrap.min.js"></script>
		<script language="javascript">
			function deleteCamera() {
				document.deleteForm.submit();
			}	 
   			$(document).ready(function () {
    			$('#deleteCameraModal').on('show.bs.modal', function (event) {
					var button = $(event.relatedTarget) // Button that triggered the modal
					var idCam = button.data('id') // Extract info from data-* attributes
  					var modal = $(this)
  					modal.find('.modal-body #idCamera').val(idCam)
				})
   			})
     
		</script>
		
		<link rel="stylesheet" type="text/css" href="/webjars/bootstrap/4.1.0/css/bootstrap.min.css" />
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
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
			.display-4 {
				font-size:2.5rem;
			}
			.nav-link{
				color:#5E5956;
				font-size: 16px; 
			}
			.nav-link:hover, .myBtnLink:hover, .myDelLink:hover {
				transition: color 0.5s ease;
				color:#d3d3d3;
			}
			.myBtnLink {
				padding: 0rem .75rem;
				color:#5E5956;
			}
			.myDelLink {
				padding:0px;
				color:#5E5956;
			}
			.col-md-2 {
				padding-left:0px;
			}
			.formEl {
				padding-left:0px;
			}
			.artCol {
				padding-left:0px;
			}
			.firstRow {
				margin-bottom:0px;
				font-size:1.10rem;
			}
			.camArtCol {
				border-radius: 5px;
				margin-bottom:7px;
				padding-bottom:0px;
				background-color: #F7F9F9;
			}
			.subtitle {
				font-size:1.50rem;
			}
			
		</style>
	</head>
	<body>
		<%@ include file="/include/header.txt" %>
		<main>
			<div class="modal fade" id="deleteCameraModal" tabindex="-1" role="dialog" aria-labelledby="deleteCameraModalLabel" aria-hidden="true">
				  <div class="modal-dialog" role="document">
				    <div class="modal-content">
				      <div class="modal-header">
				        <h5 class="modal-title" id="deleteCameraModalLabel">Eliminazione</h5>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
				          <span aria-hidden="true">&times;</span>
				        </button>
				      </div>
				      <div class="modal-body">
				      	<p class="lead">Confermi di voler rimuovere la camera anche se potrebbero esserci delle prenotazioni annesse?</p>
						<form name="deleteForm" method="POST" action="/rifugio/${idRif}/elencoCamere/rimuovi">     
					        <input type="hidden" name="idCamera" id="idCamera"/>      
					    </form>
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
				        <a class="btn btn-danger" href="javascript:deleteCamera()">Conferma</a>
				      </div>
				    </div>
				  </div>
			</div>
			<div class="container">
				<div class="row">
					<%@include file="/include/rifNav.txt" %>
					<div class="col-md-10">
						<h1>${nomeRif}</h1>
						<p class="lead subtitle">Camere:</p>
						<hr>
						<% if(messaggio != null) { %>
							<div class="alert alert-primary" role="alert">
							  <span><i class="fa fa-info-circle fa-lg" style="color: #21618C;"></i> ${messaggio}</span>
							</div>
						<% } else for(Camera cam : camere) {%>
							<article>
								<div class="col-md-6 camArtCol">
								<div class="row">
									<div class="col-md-11 artCol">
										<p class="lead firstRow"> N°<%=cam.getNumCamera()%>   <%=cam.getTipologia()%> </p>
									</div>
									<div class="col-md-1">
										<% if( logged && (gestoriRifugio.contains(loggedUser.getIdUtente()) || loggedUser.getCredenziali().equals(CredenzialiUtente.Admin))) { %>
										<button type="button" class="btn btn-link myDelLink" data-toggle="modal" data-target="#deleteCameraModal" data-id="<%=cam.getId()%>">
											<i class="fa fa-trash-o fa-lg"></i>
										</button>
										<% } %>
									</div>
								</div>
								<p class="pb-2">Capienza: <%=cam.getCapienza()%></p>
								</div>
								
							</article>
						<% } %>
						<springForm:form action="/rifugio/${idRif}/elencoCamere/aggiungi" method="POST" modelAttribute="camForm" cssClass="login">
							<p class="lead subtitle">Nuova camera</p>
							<hr>
							<% if(insertMessage != null) { %>
								<div class="alert alert-warning" role="alert">
								  <span><i class="fa fa-exclamation-triangle fa-lg" style="color: #FFC300;"></i> ${insertMessage}</span>
								</div>
							<% } %>
							<div class="form-group col-md-3 formEl">
								<label for="inputNum">Numero di camera</label>
								<springForm:input type="number" id="inputNum" step="1" maxLength="2" required="true" cssClass="form-control" path="numCamera"/>
								<springForm:errors cssClass="error" path="numCamera"/>
							</div>
							<div class="form-group col-md-7 formEl">
								<label for="inputTipo">Tipologia</label>
								<springForm:input type="text" required="true" minLength="2" maxLength="128" id="inputTipo" cssClass="form-control" path="tipologia"/>
								<springForm:errors cssClass="error" path="tipologia"/>
							</div>
							<div class="form-group col-md-3 formEl">
								<label for="inputCap">Capienza</label>
								<springForm:input type="number" id="inputCap" step="1" maxLength="3" required="true" cssClass="form-control" path="capienza"/>
								<springForm:errors cssClass="error" path="capienza"/>
							</div>
							<div class="col-md-2">
								<input type="submit" class="btn btn-primary" value="Salva"/>
							</div>
						</springForm:form>
					</div>
				</div>
			</div>
		</main>
		<%@include file="/include/footer.txt" %>
		
	</body>
</html>