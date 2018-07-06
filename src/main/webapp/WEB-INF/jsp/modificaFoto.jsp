<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>
<%@ page import="com.student.project.TurismoDolomiti.dto.LoggedUserDTO" %>
<%@ page import="com.student.project.TurismoDolomiti.enums.CredenzialiUtente" %>
<%
	LoggedUserDTO loggedUser = (LoggedUserDTO) request.getAttribute("loggedUser");
	Boolean logged = (Boolean) request.getAttribute("logged");
	String tipologia = (String) request.getAttribute("tipologia");
	String fotoPath = (String) request.getAttribute("fotoPath");
	String actionUrl = null;
	String deleteUrl = null;
	Boolean completo = null;
	if(tipologia.equals("profilo")) {
		Long idUtente = (Long) request.getAttribute("idUtente");
		actionUrl="/profilo/" + idUtente + "/modifica/foto/submit";
		deleteUrl="/profilo/" + idUtente + "/modifica/foto/cancella";
	}
	else if(tipologia.equals("escursione")) {
		completo = (Boolean) request.getAttribute("completo");
		Long idEsc = (Long) request.getAttribute("idEsc");
		actionUrl = "/escursione/" + idEsc + "/modifica/foto/submit";
		deleteUrl="/escursione/" + idEsc + "/modifica/foto/cancella";
	}
	else if(tipologia.equals("rifugio")) {
		Long idRif = (Long) request.getAttribute("idRif");
		actionUrl = "/rifugio/" + idRif + "/modifica/foto/submit";
		deleteUrl="/rifugio/" + idRif + "/modifica/foto/cancella";
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
		<script>
		$(function() {

			  // We can attach the `fileselect` event to all file inputs on the page
			  $(document).on('change', ':file', function() {
			    var input = $(this),
			        numFiles = input.get(0).files ? input.get(0).files.length : 1,
			        label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
			    input.trigger('fileselect', [numFiles, label]);
			  });

			  // We can watch for our custom `fileselect` event like this
			  $(document).ready( function() {
			      $(':file').on('fileselect', function(event, numFiles, label) {

			          var input = $(this).parents('.input-group').find(':text'),
			              log = numFiles > 1 ? numFiles + ' files selected' : label;

			          if( input.length ) {
			              input.val(log);
			          } else {
			              if( log ) alert(log);
			          }

			      });
			      
			  });
			  
			});
			
	
		</script>
		
		<link rel="stylesheet" type="text/css" href="/webjars/bootstrap/4.1.0/css/bootstrap.min.css" />
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" type="text/css" href="/css/turismoDolomitiCommon.css"/>
		<style>
			.myFileInput {
				margin-bottom:0px;
			}
			
			.rounded-circle.Photo {
				width:250px;
				height:250px;
				object-fit: cover;
				margin-left:auto;
				margin-right:auto;
				margin-bottom:5px;
			}
			img {
				display:block;
			}
			.FotoSubButton {
				
				display:block;
				margin-left:auto;
				margin-right:auto;
			}
			.col-md-2 {
				padding:0px;
			}
		</style>
	</head>
	<body>
		<%@ include file="/include/header.txt" %>
		<main>
			<div class="modal fade" id="deleteFotoModal" tabindex="-1" role="dialog" aria-labelledby="deleteFotoModalLabel" aria-hidden="true">
		  		<div class="modal-dialog" role="document">
				    <div class="modal-content">
				      <div class="modal-header">
				        <h5 class="modal-title" id="deleteFotoModalLabel">Cancellazione</h5>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
				          <span aria-hidden="true">&times;</span>
				        </button>
				      </div>
				      <div class="modal-body">
				      	<p class="lead">Confermi di voler cancellare la foto?</p>
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
				        <a class="btn btn-danger" href="<%=deleteUrl%>">Conferma</a>
				      </div>
				    </div>
		  		</div>
			</div>
			<div class="container">
				<div class="row">
					 <% if(tipologia.equals("profilo")) { %>
					 	<%@include file="/include/modProfiloNav.txt" %>
					 <% }
					 	else if(tipologia.equals("escursione")) {
					 %>
					 	<%@include file="/include/modEscNav.txt" %>
					 <% }
					 	else if(tipologia.equals("rifugio")) {
					 %>	
					 	<%@include file="/include/modRifNav.txt" %>
					 <% } %>
					 <div class="col-md-9">
					 	<h1 class="pageTitle">Modifica foto</h1>
					 	<hr>
					 	<div class="row">
					 	<div class="col-md-11">
					 	<img src="${fotoPath}" class="rounded-circle Photo"> 
					 	<springForm:form method="POST" action="<%=actionUrl%>" enctype="multipart/form-data">
					 		<div class="input-group mb-3">
								<label class="input-group-prepend myFileInput">
									<span class="btn btn-primary">Sfoglia&hellip; <input type="file" style="display: none;" name="foto" accept=".gif, .jpg, .png, .tiff"></span>
								</label>
								<input type="text" class="form-control" placeholder="filename" readonly >
							</div>
							<input type="submit" class=" col-md-3 btn btn-primary FotoSubButton" value="Salva"/>
						</springForm:form>
					 </div>
					 <div class="col-md-1">
					 	<button type="button" class="btn btn-link myDelLink" data-toggle="modal" data-target="#deleteFotoModal">
							<i class="fa fa-trash-o fa-lg"></i>
						</button>
					 </div>
					 </div>
				</div>
			</div>
		</main>
		<%@ include file="/include/footer.txt" %>
	</body>
</html>