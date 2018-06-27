<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>
<%@ page import="com.student.project.TurismoDolomiti.dto.LoggedUserDTO" %>
<%@ page import="com.student.project.TurismoDolomiti.entity.CredenzialiUtente" %>
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
	if(tipologia.equals("Rifugio")) {
		gestoriRifugio = (List<Long>) request.getAttribute("gestoriRifugio");
		Long idRif = (Long) request.getAttribute("idRif");
		deleteUrl = "/rifugio/" + idRif + "/commenti/cancella";
		
	}
	else if(tipologia.equals("Escursione")) {
		Long idEsc = (Long) request.getAttribute("idEsc");
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
			function deleteCommento(id) {
				document.deleteForm.idCom.value = id;
				document.deleteForm.submit();
				
			}
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
			.nav-link:hover, .myBtnLink:hover {
				transition: color 0.5s ease;
				color:#d3d3d3;
			}
			.myBtnLink {
				padding: 0rem .75rem;
				color:#5E5956;
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
			.col-md-2 {
				padding-left:0px;
			}
		</style>
	</head>
	<body>
		<%@include file="/include/header.txt" %>
		<main>
			<div class="container">
			
				<div class="row">
					<%if(tipologia.equals("Rifugio")) { %>
						<%@ include file="/include/rifNav.txt" %>
					<% } else if(tipologia.equals("Escursione")){ %>
						<%@ include file="/include/escNav.txt" %>
					<% } %>
					<div class="col-md-10">
						<h1>${nomeEl}</h1>
						<p class="lead">Commenti:</p>
						<% if(messaggio != null){ %>
							<div class="alert alert-primary" role="alert">
							  <span><i class="fa fa-info-circle fa-lg" style="color: #21618C;"></i> ${messaggio}</span>
							</div>
						<%} else { %>
						<%@ include file="/include/commentiCanCanc.txt" %>
						<% } %>
					</div>
				</div>
				<form name="deleteForm" method="POST" action="<%=deleteUrl%>">     
			        <input type="hidden" name="idCom"/>      
			    </form>
			</div>
		</main>
		<%@ include file="/include/footer.txt" %>
	</body>
</html>