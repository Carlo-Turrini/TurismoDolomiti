<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>
<%@ page import="com.student.project.TurismoDolomiti.dto.LoggedUserDTO" %>
<%@ page import="com.student.project.TurismoDolomiti.entity.CredenzialiUtente" %>
<%@ page import="com.student.project.TurismoDolomiti.dto.RifugioCardDto" %>
<%@ page import="com.student.project.TurismoDolomiti.dto.RifugioNomeIdDTO" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Date" %>

<%
	LoggedUserDTO loggedUser = (LoggedUserDTO) request.getAttribute("loggedUser");
	Boolean logged = (Boolean) request.getAttribute("logged");
	String messaggio = (String) request.getAttribute("messaggio");
	List<RifugioCardDto> rifugiGestiti = (List<RifugioCardDto>) request.getAttribute("rifugiGestiti");
	List<RifugioNomeIdDTO> rifugiNomeId = (List<RifugioNomeIdDTO>) request.getAttribute("nomiRifugi");
	Long idUtente = (Long) request.getAttribute("idUtente");
	Date oggi = Date.valueOf(LocalDate.now());
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
			
			function deleteGestito() {
				document.deleteForm.submit();
     		}
				 
   			$(document).ready(function () {
    			$('#deleteGestitoModal').on('show.bs.modal', function (event) {
					var button = $(event.relatedTarget) // Button that triggered the modal
					var idGestito = button.data('id') // Extract info from data-* attributes
  					var modal = $(this)
  					modal.find('.modal-body #idRif').val(idGestito)
				})
   			})
     
		</script>
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
			.display-4 {
				font-size:2.5rem;
			}
			.nav-link{
				color:#5E5956;
				font-size: 16px; 
			}
			.nav-link:hover, .myCardDelBtnLink:hover {
				transition: color 0.5s ease;
				color:#d3d3d3;
			}
			.fotoArticle {
				width:150px;
				height:150px;
				object-fit:cover;
			}
			.rifCard {
				height:350px;
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
			.myCardDelBtnLink {
				position:absolute;
				top:5px;
				right:7px;
				color:#5E5956;
				z-index:99;
			}
			.myBtn {
				width:100%;
			}
			a.cardGestHref:focus{
				outline: none;
			}
			a.cardGestHref:after{
				content:".";
				opacity: 0;
				position: absolute;
				right: 3px;
				left: 3px;
				top: 3px;
				bottom: 3px;
			}
		</style>
	
	</head>
	<body>
		<%@ include file="/include/header.txt" %>
		<main>
			<div class="modal fade" id="deleteGestitoModal" tabindex="-1" role="dialog" aria-labelledby="deleteGestitoModalLabel" aria-hidden="true">
				  <div class="modal-dialog" role="document">
				    <div class="modal-content">
				      <div class="modal-header">
				        <h5 class="modal-title" id="deleteGestitoModalLabel">Cancellazione</h5>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
				          <span aria-hidden="true">&times;</span>
				        </button>
				      </div>
				      <div class="modal-body">
				      	<p class="lead">Confermi di non voler più gestire questo rifugio?</p>
						<form name="deleteForm" method="POST" action="/profilo/${idUtente}/elencoRifugiGestiti/rimuovi">     
					        <input type="hidden" name="idRif" id="idRif"/>      
					    </form>
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
				        <a class="btn btn-danger" href="javascript:deleteGestito()">Conferma</a>
				      </div>
				    </div>
				  </div>
			</div>
			<div class="container">
				<div class="row">
					<div class="col-md-3">
						<a href="/profilo/${idUtente}" class="btn btn-outline-secondary myBtn">Profilo</a>
					</div>
					<div class="col-md-9">
						<h1 class="pageTitle">I miei rifugi</h1>
						<hr>
						<% if(messaggio != null) { %>
							<div class="alert alert-primary" role="alert">
							  <span><i class="fa fa-info-circle fa-lg" style="color: #21618C;"></i> ${messaggio}</span>
							</div>
							
						<% } else { %>
							<div class="row">
							<% for(RifugioCardDto rif : rifugiGestiti){%>
							<div class="col-md-6">
								<div class="card rifCard">
									<a href="/rifugio/<%=rif.getIdRifugio()%>" class="cardGestHref"></a>
									<img class="card-img-top myCardImg" src="<%=rif.getIconPath()%>">
									<button type="button" class="btn btn-link myCardDelBtnLink" data-toggle="modal" data-target="#deleteGestitoModal" data-id="<%=rif.getIdRifugio()%>" title="cancella">
										<i class="fa fa-trash-o fa-lg"></i>
									</button>
									<div class="card-body">
										<h5 class="card-title rifCardText"><%=rif.getNome()%>, <%=rif.getAltitudine()%>m</h5>
										<p class="card-text rifCardSub"><%=rif.getMassiccioMontuoso()%></p>
										<p class="card-text rifCardText"> <%=rif.getDataApertura().toString()%> - <%=rif.getDataChiusura().toString()%></p>
										<% if(rif.getDataApertura().compareTo(oggi)<=0 && rif.getDataChiusura().compareTo(oggi)>=0) { %>
											<span class="aperto"><i class="fa fa-circle fa-md" style="color:green;"></i>  Aperto</span>
											<%} else { %>
											<span class="chiuso"><i class="fa fa-circle fa-md" style="color:red;"></i>  Chiuso</span>
										<% } %>
									</div>
								</div>
								
							</div>
						<% } %>
						</div>
						<% } %>
						<%if(logged && loggedUser.getCredenziali().equals(CredenzialiUtente.Admin)) { %>
							<p class="lead mt-2">Aggiungi rifugio gestito</p>
							<hr>
							<springForm:form method="POST" action="/profilo/${idUtente}/elencoRifugiGestiti/aggiungi">
								<select name="idRif" class="form-control col-md-6" required="true">
										<option value="" label="Seleziona il rifugio da far gestire"/>
									<% for(RifugioNomeIdDTO rif : rifugiNomeId ) { %>
										<option value="<%=rif.getIdRif()%>" label="<%=rif.getNomeRif()%>"/>
									<% } %>
								</select>
								<input type="submit" class="btn btn-primary mt-2" value="Aggiungi">
							</springForm:form>
						<% } %>
					</div>
				</div>
			</div>
		</main>
		<%@ include file="/include/footer.txt" %>
	</body>
</html>