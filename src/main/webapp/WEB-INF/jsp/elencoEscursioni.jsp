<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>
<%@ page import="com.student.project.TurismoDolomiti.dto.LoggedUserDTO" %>
<%@ page import="com.student.project.TurismoDolomiti.enums.CredenzialiUtente" %>
<%@ page import="com.student.project.TurismoDolomiti.entity.Escursione" %>
<%@ page import="java.util.List" %>

<%
	LoggedUserDTO loggedUser = (LoggedUserDTO) request.getAttribute("loggedUser");
	Boolean logged = (Boolean) request.getAttribute("logged");
	String messaggio = (String) request.getAttribute("messaggio");
	List<Escursione> elencoEsc = (List<Escursione>) request.getAttribute("elencoEsc");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Turismo Dolomiti</title>
		
		<link rel="stylesheet" type="text/css" href="/webjars/bootstrap/4.1.0/css/bootstrap.min.css" />
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    	<link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/css/select2.min.css" rel="stylesheet" />
		<link rel="stylesheet" type="text/css" href="/css/turismoDolomitiCommon.css"/>
		<style>

			.col-md-2 {
				padding:0px;
			}
			form.login input.my-input-group {
				width:70%
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
					<div class="col-md-4">
						<p class="lead">Filtri:</p>
						<springForm:form method="POST" cssClass="login" action="/elencoEscursioni" modelAttribute="escSearch">
							<div class="form-group">
								<label for="inputNome">Nome dell'escursione</label>
								<springForm:input type="text" cssClass="form-control" path="nome" placeholder="Giro del Paterno"/>
							</div>
							<div class="form-group">
								<label for="inputMassMont">Massiccio montuoso</label>
								<springForm:input type="text" cssClass="form-control" path="massiccioMontuoso" placeholder="Tre Cime di Lavaredo"/>
							</div>
							<div class="form-group col-md-8 myCol ">
								<label for="inputTipo">Tipologia di escursione</label>
								<springForm:select cssClass="form-control" id="inputTipo" path="tipologia">
									<springForm:option value="" label="Seleziona tipologia"/>
									<springForm:option value="Trekking"/>
									<springForm:option value="Ferrata"/>
								</springForm:select>
								<springForm:errors cssClass="error" path="tipologia"/>
							</div>
							<div class="form-group col-md-8 myCol">
								<label for="inputDiff">Difficolt&aacute;</label>
								<springForm:select cssClass="form-control" id="inputDiff" path="difficolta">
									<springForm:option value="" label="Seleziona difficoltà"/>
									<springForm:option value="Facile"/>
									<springForm:option value="Medio"/>
									<springForm:option value="Difficile"/>
								</springForm:select>
								<springForm:errors cssClass="error" path="difficolta"/>
							</div>
							<div class="form-row">
								<div class="form-group col">
									<label for="inputDisSalMin">Dislivello salita min</label>
									<div id="inputDisSalMin" class="input-group mb-3">
										<springForm:input type="number" cssClass="form-control my-input-group" path="dislivelloSalitaMin"  step="1" maxLength="4"/>
										<div class="input-group-append">
											<span class="input-group-text myAppend">m</span>
										</div>
									</div>
								</div>
								<div class="form-group col">
									<label for="inputDisSalMax">Dislivello salita max</label>
									<div id="inputDisSalMax" class="input-group mb-3">
										<springForm:input type="number" cssClass="form-control my-input-group" path="dislivelloSalitaMax" step="1" maxLength="4"/>
										<div class="input-group-append">
											<span class="input-group-text myAppend">m</span>
										</div>
									</div>
								</div>
							</div>
							<div class="form-row">
								<div class="form-group col">
									<label for="inputDisDiscMin">Dislivello discesa min</label>
									<div id="inputDisDisclMin" class="input-group mb-3">
										<springForm:input type="number" cssClass="form-control my-input-group" path="dislivelloDiscesaMin"  step="1" maxLength="4"/>
										<div class="input-group-append">
											<span class="input-group-text myAppend">m</span>
										</div>
									</div>
								</div>
								<div class="form-group col">
									<label for="inputDisDiscMax">Dislivello discesa max</label>
									<div id="inputDisDiscMax" class="input-group mb-3">
										<springForm:input type="number" cssClass="form-control my-input-group" path="dislivelloDiscesaMax" step="1" maxLength="4"/>
										<div class="input-group-append">
											<span class="input-group-text myAppend">m</span>
										</div>
									</div>
								</div>
							</div>
							<div class="form-row">
								<div class="form-group col">
									<label for="inputLunMin">Lunghezza min</label>
									<div id="inputLunMin" class="input-group mb-3">
										<springForm:input type="number" cssClass="form-control my-input-group" path="lunghezzaMin"  step="1" maxLength="3"/>
										<div class="input-group-append">
											<span class="input-group-text myAppend">km</span>
										</div>
									</div>
								</div>
								<div class="form-group col">
									<label for="inputLunMax">Lunghezza max</label>
									<div id="inputLunMax" class="input-group mb-3">
										<springForm:input type="number" cssClass="form-control my-input-group" path="lunghezzaMax" step="1" maxLength="3"/>
										<div class="input-group-append">
											<span class="input-group-text myAppend">km</span>
										</div>
									</div>
								</div>
							</div>
							<div class="form-row">
								<div class="form-group col">
									<label for="inputDurMin">Durata min</label>
									<div id="inputDurMin" class="input-group mb-3">
										<springForm:input type="number" cssClass="form-control my-input-group" path="dislivelloSalitaMin"  step="0.25" maxLength="5"/>
										<div class="input-group-append">
											<span class="input-group-text myAppend">ore</span>
										</div>
									</div>
								</div>
								<div class="form-group col">
									<label for="inputDurMax">Durata max</label>
									<div id="inputDurMax" class="input-group mb-3">
										<springForm:input type="number" cssClass="form-control my-input-group" path="dislivelloDiscesaMax" step="0.25" maxLength="5"/>
										<div class="input-group-append">
											<span class="input-group-text myAppend">ore</span>
										</div>
									</div>
								</div>
							</div>
							<input type="submit" class="btn btn-outline-secondary btn-sm" value="Filtra">
						</springForm:form>
						
					</div>
					<div class="col-md-8">
						<div class="row">
								<div class="col-md-9 myCol">
									<h1 class="pageTitle">Elenco delle escursioni</h1>
								</div>
								<div class="col-md-3">
								<% if(logged && loggedUser.getCredenziali().equals(CredenzialiUtente.Admin)) { %>
									<a class="btn btn-primary" href="/elencoEscursioni/inserisciEscursione" ><i class="fa fa-plus fa-lg"></i> Nuova escursione</a>
									<% } %>
								</div>
						</div>
						<hr>
						<% if(messaggio != null) { %>
							<div class="alert alert-primary" role="alert">
							  <span><i class="fa fa-info-circle fa-lg" style="color: #21618C;"></i> ${messaggio}</span>
							</div>
						<% } else { %>
							<div class="row">
							<% for(Escursione esc : elencoEsc){%>
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