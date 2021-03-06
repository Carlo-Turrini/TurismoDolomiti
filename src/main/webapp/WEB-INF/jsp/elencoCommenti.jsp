<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>
<%@ page import="com.student.project.TurismoDolomiti.dto.LoggedUserDTO" %>
<%@ page import="com.student.project.TurismoDolomiti.enums.CredenzialiUtente" %>
<%@ page import="com.student.project.TurismoDolomiti.dto.CommentoCardDto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.LinkedList" %>

<%
	LoggedUserDTO loggedUser = (LoggedUserDTO) request.getAttribute("loggedUser");
	Boolean logged = (Boolean) request.getAttribute("logged");
	String tipologia = (String) request.getAttribute("tipologia");
	String nomeEl = (String) request.getAttribute("nomeEl");
	List<CommentoCardDto> commentiCard = (List<CommentoCardDto>) request.getAttribute("commenti");
	String messaggio = (String) request.getAttribute("messaggio");
	String deleteUrl = null;
	List<Long> gestoriRifugio = new LinkedList<Long>(); 
	Long idRif = null;
	Long idEsc = null;
	if(tipologia.equals("Rifugio")) {
		gestoriRifugio = (List<Long>) request.getAttribute("gestoriRifugio");
		idRif = (Long) request.getAttribute("idRif");
		deleteUrl = "/rifugio/" + idRif + "/commenti/cancella";
		
	}
	else if(tipologia.equals("Escursione")) {
		idEsc = (Long) request.getAttribute("idEsc");
		deleteUrl = "/escursione/" + idEsc + "/commenti/cancella";
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
		<script language="javascript">
			function deleteCommento() {
				document.deleteForm.submit();
			}
			$(document).ready(function () {
    			$('#deleteComModal').on('show.bs.modal', function (event) {
					var button = $(event.relatedTarget) // Button that triggered the modal
					var idCom = button.data('id') // Extract info from data-* attributes
  					var modal = $(this)
  					modal.find('.modal-body #idCommento').val(idCom)
				})
   			})
		</script>
		
		<link rel="stylesheet" type="text/css" href="/webjars/bootstrap/4.1.0/css/bootstrap.min.css" />
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" type="text/css" href="/css/turismoDolomitiCommon.css"/>
		<style>

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
			.col-md-2 {
				padding-left:0px;
			}
			.commUtenteRefCol {
				padding-left:0px;
			}
			.comCol {
				padding-left:0px;
			}
		</style>
	</head>
	<body>
		<%@include file="/include/header.txt" %>
		<main>
			<div class="modal fade" id="deleteComModal" tabindex="-1" role="dialog" aria-labelledby="deleteComModalLabel" aria-hidden="true">
				  <div class="modal-dialog" role="document">
				    <div class="modal-content">
				      <div class="modal-header">
				        <h5 class="modal-title" id="deleteComModalLabel">Eliminazione</h5>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
				          <span aria-hidden="true">&times;</span>
				        </button>
				      </div>
				      <div class="modal-body">
				      	<p class="lead">Confermi di voler eliminare il commento?</p>
						<form name="deleteForm" method="POST" action="<%=deleteUrl%>">     
					        <input type="hidden" name="idCommento" id="idCommento"/>      
					    </form>
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
				        <a class="btn btn-danger" href="javascript:deleteCommento()">Conferma</a>
				      </div>
				    </div>
				  </div>
			</div>
			<div class="container">
			
				<div class="row">
					<%if(tipologia.equals("Rifugio")) { %>
						<%@ include file="/include/rifNav.txt" %>
					<% } else if(tipologia.equals("Escursione")){ %>
						<%@ include file="/include/escNav.txt" %>
					<% } %>
					<div class="col-md-10">
						<h1>${nomeEl}</h1>
						<p class="lead subtitle">Commenti</p>
						<hr>
						<% if(messaggio != null){ %>
							<div class="alert alert-primary" role="alert">
							  <span><i class="fa fa-info-circle fa-lg" style="color: #21618C;"></i> ${messaggio}</span>
							</div>
						<%} else { %>
						<%@ include file="/include/commentiCanCanc.txt" %>
						<% } %>
					</div>
				</div>
			</div>
		</main>
		<%@ include file="/include/footer.txt" %>
	</body>
</html>