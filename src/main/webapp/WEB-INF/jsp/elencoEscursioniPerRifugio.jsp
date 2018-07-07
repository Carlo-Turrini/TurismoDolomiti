<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page import="com.student.project.TurismoDolomiti.dto.LoggedUserDTO" %>
<%@ page import="com.student.project.TurismoDolomiti.enums.CredenzialiUtente" %>
<%@ page import="com.student.project.TurismoDolomiti.dto.EscursioneCardDto" %>
<%@ page import="java.util.List" %>

<%
	LoggedUserDTO loggedUser = (LoggedUserDTO) request.getAttribute("loggedUser");
	Boolean logged = (Boolean) request.getAttribute("logged");
	String messaggio = (String) request.getAttribute("messaggio");
	Long idRif = (Long) request.getAttribute("idRif");
	String nomeRif = (String) request.getAttribute("nomeRif");
	List<Long> gestoriRifugio = (List<Long>) request.getAttribute("gestoriRifugio");
	List<EscursioneCardDto> escursioniPerRifugio = (List<EscursioneCardDto>) request.getAttribute("escursioniPerRifugio");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Turismo Dolomiti</title>
		
		<script type="text/javascript" src="/webjars/jquery/3.3.1/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
		<script type="text/javascript" src="/webjars/bootstrap/4.1.0/js/bootstrap.min.js"></script>
		
		<link rel="stylesheet" type="text/css" href="/webjars/bootstrap/4.1.0/css/bootstrap.min.css" />
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    	<link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/css/select2.min.css" rel="stylesheet" />
		<link rel="stylesheet" type="text/css" href="/css/turismoDolomitiCommon.css"/>
		<style>
			.col-md-2 {
				padding:0px;
			}
			.fotoArticle {
				width:150px;
				height:150px;
				object-fit:cover;
			}
			.rifCard {
				max-height:400px;
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
			a.cardHref {
				text-decoration:none;
				color:black;
			}
			.escIcon {
				color:#6E2C00;
			}
		</style>
	</head>
	<body>
		<%@ include file="/include/header.txt" %>
		<main>
			<div class="container">
				<div class="row">
					<%@ include file="/include/rifNav.txt" %>
					<div class="col-md-10 myCol">
						<h1><%=nomeRif%></h1>
						<p class="lead subtitle">Escursioni</p>
						<hr>
						<% if(messaggio != null) { %>
							<div class="alert alert-primary" role="alert">
							  <span><i class="fa fa-info-circle fa-lg" style="color: #21618C;"></i> ${messaggio}</span>
							</div>
						<% } else { %>
							<div class="row">
							<% for(EscursioneCardDto esc : escursioniPerRifugio){%>
							<div class="col-md-12">
								<a href="/escursione/<%=esc.getId()%>" class="cardHref">
									<div class="card rifCard mb-2">
										<img class="card-img-top myCardImg" src="<%=esc.getIconPath()%>">
										<div class="card-body">
											<div class="row">
												<div class="col-md-10 myCol">
													<h5 class="card-title rifCardText"><%=esc.getNome()%></h5>
													<p class="card-subtitle mb-2 text-muted rifCardSub"><%=esc.getLabel()%></p>
													<p class="card-text rifCardText mb-1"><%=esc.getMassiccioMontuoso()%></p>
												</div>
												<div class="col-md-2 myCol">
													<p class="card-text"><%=esc.getTipologia().toString()%></p>
												</div>
											</div>
											<div class="row">
												<div class="col myCol">
													<p class="mb-0"><span class="fa-stack fa-lg escIcon"><i class="fa fa-circle fa-stack-2x"></i><i class="fa fa-map-signs fa-stack-1x fa-inverse"></i></span> <%=esc.getDifficolta().toString()%></p>
												</div>
												<div class="col myCol">
													<p class="mb-0"><span class="fa-stack fa-lg escIcon"><i class="fa fa-circle fa-stack-2x"></i><i class="fa fa-arrows-h fa-stack-1x fa-inverse"></i></span> <%=esc.getLunghezza()%>km</p>
												</div>
												<div class="col myCol">
													<div class="row">
														
															<span class="fa-stack fa-lg escIcon">
																<i class="fa fa-circle fa-stack-2x"></i>
																<i class="fa fa-arrows-v fa-stack-1x fa-inverse"></i>
															</span>
															
														<div class="col myCol">
															<p class="mb-0" style="line-height:20px;"><%=esc.getDislivelloSalita()%>m</p>
															<p class="mb-0"><span class="text-muted"><%=esc.getDislivelloDiscesa()%>m</span></p>													
														</div>
													</div>
												</div>
												<div class="col myCol">
													<p class="mb-0"><span class="fa-stack fa-lg escIcon"><i class="fa fa-circle fa-stack-2x"></i><i class="fa fa-clock-o fa-stack-1x fa-inverse"></i></span> <%=esc.getDurata()%> ore</p>
												</div>
											</div>
										</div>
									</div>
								</a>
							</div>
						<% } %>
						</div>
						<% } %>
					</div>
				</div>
			</div>
		</main>
		<%@ include file="/include/footer.txt" %>
	</body>
</html>