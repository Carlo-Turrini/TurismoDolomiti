<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>
<%@ page import="com.student.project.TurismoDolomiti.dto.LoggedUserDTO" %>
<%@ page import="com.student.project.TurismoDolomiti.enums.CredenzialiUtente" %>
<%@ page import="com.student.project.TurismoDolomiti.dto.FotoCardDto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.LinkedList" %>
<%
	LoggedUserDTO loggedUser = (LoggedUserDTO) request.getAttribute("loggedUser");
	Boolean logged = (Boolean) request.getAttribute("logged");
	String tipologia = (String) request.getAttribute("tipologia");
	String nomeEl = (String) request.getAttribute("nomeEl");
	String messaggio = (String) request.getAttribute("messaggio");
	List<FotoCardDto> foto = (List<FotoCardDto>) request.getAttribute("foto");
	String deleteUrl = null;
	String addUrl = null;
	List<Long> gestoriRifugio = new LinkedList<Long>(); 
	Long idRif = null;
	Long idEsc = null;
	if(tipologia.equals("rifugio")) {
		gestoriRifugio = (List<Long>) request.getAttribute("gestoriRifugio");
		idRif = (Long) request.getAttribute("idRif");
		deleteUrl = "/rifugio/" + idRif + "/galleria/cancella";
		addUrl= "/rifugio/" + idRif + "/galleria/aggiungi";
		
	}
	else if(tipologia.equals("escursione")) {
		idEsc = (Long) request.getAttribute("idEsc");
		deleteUrl = "/escursione/" + idEsc + "/galleria/cancella";
		addUrl= "/escursione/" + idEsc + "/galleria/aggiungi";
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
			function deleteFoto() {
				document.deleteForm.submit();
			}
			function addFoto() {
				document.addForm.submit();
				
			}
			$(document).ready(function () {
    			$('#deleteFotoModal').on('show.bs.modal', function (event) {
					var button = $(event.relatedTarget) // Button that triggered the modal
					var idPhoto = button.data('id') // Extract info from data-* attributes
  					var modal = $(this)
  					modal.find('.modal-body #idFoto').val(idPhoto)
				})
   			})
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
				width:40px;
				heigth:40px;
				
				padding-top:5px;
			}
			.col-md-2 {
				padding-left:0px;
			}
			.commUtenteRefCol {
				padding-left:0px;
			}
			.subtitle {
				font-size:1.50rem;
			}
			.deleteBtn {
				position:absolute;
				top:5px;
				right:7px;
				color:#5E5956;
			}
			<!--FOTO GALLERY CSS -->
			
			.gal-container{
				padding: 12px;
			}
			.gal-item{
				overflow: hidden;
				padding: 3px;
			}
			.gal-item .box{
				height: 350px;
				overflow: hidden;
			}
			.box img.gallery{
				height: 100%;
				width: 100%;
				object-fit:cover;
				-o-object-fit:cover;
			}
			.fotoCommento {
				width:40px;
				height:40px;
			}
			.gal-item a.gal-a:focus{
				outline: none;
			}
			.gal-item a.gal-a:after{
				content:"\f002";
				font-family: FontAwesome;
				opacity: 0;
				background-color: rgba(0, 0, 0, 0.75);
				position: absolute;
				right: 3px;
				left: 3px;
				top: 3px;
				bottom: 3px;
				text-align: center;
			    line-height: 350px;
			    font-size: 30px;
			    color: #fff;
			    -webkit-transition: all 0.5s ease-in-out 0s;
			    -moz-transition: all 0.5s ease-in-out 0s;
			    transition: all 0.5s ease-in-out 0s;
			}
			.gal-item a.gal-a:hover:after{
				opacity: 1;
			}
			.modal-open .gal-container .modal{
				background-color: rgba(0,0,0,0.4);
			}
			.modal-open .gal-item .modal-body{
				padding: 0px;
			}
			.modal-open .gal-item button.close{
			    position: absolute;
			    width: 25px;
			    height: 25px;
			    background-color: #000;
			    opacity: 1;
			    color: #fff;
			    z-index: 999;
			    right: -12px;
			    top: -12px;
			    border-radius: 50%;
			    font-size: 15px;
			    border: 2px solid #fff;
			    line-height: 25px;
			    -webkit-box-shadow: 0 0 1px 1px rgba(0,0,0,0.35);
				box-shadow: 0 0 1px 1px rgba(0,0,0,0.35);
			}
			.modal-open .gal-item button.close:focus{
				outline: none;
			}
			.modal-open .gal-item button.close span{
				position: relative;
				top: -3px;
				font-weight: lighter;
				text-shadow:none;
			}
			.gal-container .modal-dialogue{
				width: 80%;
			}
			.gal-container .description{
				position: absolute;
				bottom: 0px;
				
				padding: 10px 25px;
				background-color: rgba(0,0,0,0.5);
				color: #fff;
				text-align: left;
			}
			.gal-container .description article{
				margin:0px;
				font-size: 15px;
				font-weight: 300;
				line-height: 20px;
			}
			.gal-container .modal.fade .modal-dialog {

			    transform: scale(1);
			    top: 100px;
			    opacity: 1;
			    transition: all 0.3s;
			}
			
			.gal-container .modal-dialog {
			    width: 55%;
			    margin: 50 auto;
			}
			.labelPhoto {
				margin-bottom:0px;
			}
			.myFileInput {
				margin-bottom:0px;
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
				        <h5 class="modal-title" id="deleteFotoModalLabel">Eliminazione</h5>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
				          <span aria-hidden="true">&times;</span>
				        </button>
				      </div>
				      <div class="modal-body">
				      	<p class="lead">Confermi di voler cancellare la foto?</p>
						<form name="deleteForm" method="POST" action="<%=deleteUrl%>">     
					        <input type="hidden" name="idFoto" id="idFoto"/>      
					    </form>
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
				        <a class="btn btn-danger" href="javascript:deleteFoto()">Conferma</a>
				      </div>
				    </div>
				  </div>
			</div>
			<div class="modal fade" id="addFotoModal" tabindex="-1" role="dialog" aria-labelledby="addFotoModalLabel" aria-hidden="true">
				  <div class="modal-dialog" role="document">
				    <div class="modal-content">
				      <div class="modal-header">
				        <h5 class="modal-title" id="addFotoModalLabel">Nuova foto</h5>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
				          <span aria-hidden="true">&times;</span>
				        </button>
				      </div>
				      <div class="modal-body">
						<springForm:form cssClass="login" name="addForm" method="POST" action="<%=addUrl%>" enctype="multipart/form-data">  
							<div class="input-group mb-3">
								<label class="input-group-prepend myFileInput">
									<span class="btn btn-primary">Sfoglia&hellip; <input type="file" style="display: none;" name="foto" accept=".gif, .jpg, .png, .tiff"></span>
								</label>
								<input type="text" class="form-control" placeholder="filename" readonly >
							</div>
							<div class="form-group add">
								<label for="inputLabel" class="add">Descrizione</label>
								<input type="text"  name="label" class="form-control">
							</div>     
					    </springForm:form>
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
				        <a class="btn btn-primary" href="javascript:addFoto()">Salva</a>
				      </div>
				    </div>
				  </div>
			</div>
			<div class="container">
				<div class="row">
					<%if(tipologia.equals("rifugio")) { %>
						<%@ include file="/include/rifNav.txt" %>
					<% } else if(tipologia.equals("escursione")){ %>
						<%@ include file="/include/escNav.txt" %>
					<% } %>
					<div class="col-md-10">
						<div class="row">
							<div class="col-md-9">
								<h1>${nomeEl}</h1>
								<p class="lead subtitle">Foto:</p>
							</div>
							<div class="col-md-3">
							<% if(logged) { %>
								<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addFotoModal"><i class="fa fa-plus fa-lg"></i> Aggiungi una foto</button>
								<% } %>
							</div>
						</div>
						<hr>
						<% if(messaggio != null){ %>
							<div class="alert alert-primary" role="alert">
							  <span><i class="fa fa-info-circle fa-lg" style="color: #21618C;"></i> ${messaggio}</span>
							</div>
						<%} else { %>
							<div class="row gal-container">
							<%  for(int i=0; i<foto.size(); i++){ %>
						
							
						    <div class="col-md-4 col-sm-12 co-xs-12 gal-item">
						      <div class="box">
						        <a href="#" data-toggle="modal" data-target="#<%=i%>" class="gal-a">
						          <img class="gallery" src="<%=foto.get(i).getPhotoPath()%>">
						        </a>
						       <% if(logged && (loggedUser.getIdUtente() == foto.get(i).getIdUtente() || loggedUser.getCredenziali().equals(CredenzialiUtente.Admin))) { %>
								<button type="button" class="btn btn-link deleteBtn" data-toggle="modal" data-target="#deleteFotoModal" data-id="<%=foto.get(i).getIdFoto()%>">
									<i class="fa fa-trash-o fa-lg"></i>
								</button>
								<% } %>
						        <div class="modal fade" id="<%=i%>" tabindex="-1" role="dialog">
						          <div class="modal-dialog" role="document">
						            <div class="modal-content">
						                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
						              <div class="modal-body">
						                <img class="gallery" src="<%=foto.get(i).getPhotoPath()%>">
						              </div>
						                <div class="col-md-12 description">
							                 <span>
												<div class="row">
													<div class="comPhotoCol">
														<img src="<%=foto.get(i).getProfilePhotoPathUtente()%>" class="rounded-circle fotoCommento">
													</div>
													<div class="col-md-10">
														<a class="btn btn-link myComBtnLink" href="/profilo/<%=foto.get(i).getIdUtente()%>"><%=foto.get(i).getNomeUtente()%> <%=foto.get(i).getCognomeUtente()%></a>
														<span class="timestamp"><%=foto.get(i).getTimestamp().toString()%></span>
														<p class="labelPhoto"><%=foto.get(i).getLabel()%></p>
													</div>
												</div>
											</span>
						                </div>
						            </div>
						          </div>
						        </div>
						      </div>
						      </div>
						<% } %>
						</div>
						<% } %>
					</div>
				</div>
			</div>
		</main>
		<%@include file="/include/footer.txt" %>
	</body>
</html>