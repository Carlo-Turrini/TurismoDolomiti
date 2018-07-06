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
	Long idEsc = (Long) request.getAttribute("idEsc");
	String altimetriaPath = (String) request.getAttribute("altimetriaPath");
	Boolean completo = (Boolean) request.getAttribute("completo");
	if(altimetriaPath == null) {
		altimetriaPath = "/noImage.png";
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
			.altimetria {
				width:550px;
				height:333px;
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
			<div class="container">
				<div class="row">
				 	<%@include file="/include/modEscNav.txt" %>
					 <div class="col-md-9">
					 	<h1 class="pageTitle">Modifica altimetria</h1>
					 	<hr>
					 	<img src="<%=altimetriaPath%>" class="altimetria"> 
					 	<springForm:form method="POST" action="/escursione/${idEsc}/modifica/altimetria/submit" enctype="multipart/form-data">
					 		<div class="input-group mb-3">
								<label class="input-group-prepend myFileInput">
									<span class="btn btn-primary">Sfoglia&hellip; <input type="file" style="display: none;" name="altimetria" accept=".gif, .jpg, .png, .tiff"></span>
								</label>
								<input type="text" class="form-control" placeholder="filename" readonly >
							</div>
							<input type="submit" class=" col-md-3 btn btn-primary FotoSubButton" value="Salva"/>
						</springForm:form>
					 </div>

				</div>
			</div>
		</main>
		<%@ include file="/include/footer.txt" %>
	</body>
</html>