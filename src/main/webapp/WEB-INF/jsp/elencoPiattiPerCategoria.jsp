<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>
<%@ page import="com.student.project.TurismoDolomiti.dto.LoggedUserDTO" %>
<%@ page import="com.student.project.TurismoDolomiti.enums.CredenzialiUtente" %>
<%@ page import="com.student.project.TurismoDolomiti.dto.FotoSequenceDTO" %>
<%@ page import="com.student.project.TurismoDolomiti.dto.CommentoCardDto" %>
<%@ page import="com.student.project.TurismoDolomiti.enums.CategoriaMenu" %>
<%@ page import="com.student.project.TurismoDolomiti.entity.Piatto" %>
<%@ page import="java.util.List" %>
<%
	LoggedUserDTO loggedUser = (LoggedUserDTO) request.getAttribute("loggedUser");
	Boolean logged = (Boolean) request.getAttribute("logged");
	List<Long> gestoriRifugio = (List<Long>) request.getAttribute("gestoriRifugio");
	List<FotoSequenceDTO> fotoSequence = (List<FotoSequenceDTO>) request.getAttribute("fotoSequence");
	List<CommentoCardDto> commentiCard = (List<CommentoCardDto>) request.getAttribute("commentiCard");
	String nomeRif = (String) request.getAttribute("nomeRif");
	Long idRif = (Long) request.getAttribute("idRif");
	List<Piatto> piatti = (List<Piatto>) request.getAttribute("piatti");
	String messaggio = (String) request.getAttribute("messaggio");
	CategoriaMenu categoriaMenu = (CategoriaMenu) request.getAttribute("categoriaMenu");
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
			
			function deletePiatto() {
				//document.deleteForm.idPiatto.value = id;
				document.deleteForm.submit();
     		}
			function modifyPiatto(id) {
			 	 document.modifyForm.idPiatto.value = id;
			 	 document.modifyForm.submit();
			}
				 
   			$(document).ready(function () {
    			$('#deletePiattoModal').on('show.bs.modal', function (event) {
					var button = $(event.relatedTarget) // Button that triggered the modal
					var idPlate = button.data('id') // Extract info from data-* attributes
  					var modal = $(this)
  					modal.find('.modal-body #idPiatto').val(idPlate)
				})
   			})
     
		</script>
		<link rel="stylesheet" type="text/css" href="/webjars/bootstrap/4.1.0/css/bootstrap.min.css" />
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">	
		<style>
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
			.nav-link:hover, .myBtnLink:hover {
				transition: color 0.5s ease;
				color:#d3d3d3;
			}
			.plateCol {
				padding-right:0px;
			}
			.btnGroupPlate {
				padding-right:0px;
				padding-left:0px;
			}
			.myBtnLink {
				padding: 0rem .75rem;
				color:#5E5956;
			}
			.PlateArticle { 
				margin-bottom: 10px;
			}
			.price {
				padding-right:0px;
			}
			.myCarousel {
				width:1100px;
				height: 400px;
				margin-left: auto;
				margin-right: auto;
			}
			.sequence {
				object-fit:cover;
			}
			.fotoCommento {
				width:40px;
				height:40px;
			}
			.myComBtnLink {
				text-decoration:none;
				padding-right:7px;
				padding-left:0px;
				padding-top:0px;
			}
			.timestamp {
				font-size: 12px;
				color: #A6ACAF;
			}
			.comPhotoCol {
				padding-top:5px;
			}
			.subtitle {
				font-size:1.50rem;
			}
			.myCol {
				padding-left:0px;
			}
		</style>
	</head>
	<body>
		<%@ include file="/include/header.txt" %>
		<main>
			<div class="modal fade" id="deletePiattoModal" tabindex="-1" role="dialog" aria-labelledby="deletePiattoModalLabel" aria-hidden="true">
				  <div class="modal-dialog" role="document">
				    <div class="modal-content">
				      <div class="modal-header">
				        <h5 class="modal-title" id="deletePiattoModalLabel">Eliminazione</h5>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
				          <span aria-hidden="true">&times;</span>
				        </button>
				      </div>
				      <div class="modal-body">
				      	<p class="lead">Confermi di voler eliminare il piatto?</p>
						<form name="deleteForm" method="POST" action="/rifugio/${idRif}/menu/${categoriaMenu}/cancellaPiatto">     
					        <input type="hidden" name="idPiatto" id="idPiatto"/>      
					    </form>
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
				        <a class="btn btn-danger" href="javascript:deletePiatto()">Conferma</a>
				      </div>
				    </div>
				  </div>
			</div>
			<div class="container">
				<% if(!fotoSequence.isEmpty()){ %>
					<div class="row">
						<%@ include file="/include/photoCarousel.txt" %>
					</div>
				<% } %>
				<div class="row">
					<%@include file="/include/rifNav.txt" %>
					<div class="col-md-10">
						<h1>${nomeRif}</h1>
						<p class="lead subtitle">Menu: <%=categoriaMenu.toString()%></p>
						<hr>
						<% if(messaggio != null) { %>
							<div class="alert alert-primary" role="alert">
							  <span><i class="fa fa-info-circle fa-lg" style="color: #21618C;"></i> ${messaggio}</span>
							</div>
						<% } 
						else for(Piatto piatto : piatti) { %>
							<article class="PlateArticle">
								<div class="row">
									<div class="col-md-10 plateCol">
										<div class="row">
											<div class="col-md-9">
												<p class="lead piatto"><%=piatto.getNome()%></p>
												<p class="descrizione"><%=piatto.getDescrizione()%></p>
											</div>
											<div class="col-md-1">
												<span class="price"><%=piatto.getPrezzo()%>€</span>
											</div>
											<%if(logged && (gestoriRifugio.contains(loggedUser.getIdUtente()) || loggedUser.getCredenziali().equals(CredenzialiUtente.Admin))){%>
											<div class="col-md-2 btnGroupPlate">
												<div class="btn-group" role="group">			
													<button type="button" class="btn btn-link myBtnLink" data-toggle="modal" data-target="#deletePiattoModal" data-id="<%=piatto.getId()%>">
														<i class="fa fa-trash-o fa-lg"></i>
													</button>
													<a class="btn btn-link myBtnLink" href="javascript:modifyPiatto(<%=piatto.getId()%>)">
														<i class="fa fa-pencil fa-lg"></i>
													</a>
												</div>
											</div>
											<% } %>
										</div>
									</div>
								</div>
							</article>
						<% } %>
						<% if(!commentiCard.isEmpty()){ %>
							<%@ include file="/include/commentiNonCanc.txt" %>
						<% } %>
					</div>
				</div>
			    <form name="modifyForm" method="POST" action="/rifugio/${idRif}/menu/modificaPiatto">     
			        <input type="hidden" name="idPiatto"/>      
			    </form>
			</div>
		</main>
		<%@ include file="/include/footer.txt" %>
	</body>
</html>