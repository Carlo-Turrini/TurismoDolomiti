<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>
<%@ page import="com.student.project.TurismoDolomiti.dto.LoggedUserDTO" %>
<%@ page import="com.student.project.TurismoDolomiti.enums.CredenzialiUtente" %>
<%@ page import="com.student.project.TurismoDolomiti.entity.Rifugio" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
	LoggedUserDTO loggedUser = (LoggedUserDTO) request.getAttribute("loggedUser");
	Boolean logged = (Boolean) request.getAttribute("logged");
	String messaggio = (String) request.getAttribute("messaggio");
	List<Rifugio> rifugi = (List<Rifugio>) request.getAttribute("rifugi");
	Date oggi = Date.valueOf(LocalDate.now());
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Turismo Dolomiti</title>
		
		
		<link rel="stylesheet" type="text/css" href="/webjars/bootstrap/4.1.0/css/bootstrap.min.css" />
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">	
		<link rel="stylesheet" type="text/css" href="/css/turismoDolomitiCommon.css"/>
		<style>
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
			a.cardHref {
				text-decoration:none;
				color:black;
			}
		</style>
	</head>
	<body>
		<%@ include file="/include/header.txt" %>
		<main>
			<div class="container">
				<div class="row">
					<div class="col-md-3">
						<p class="lead">Filtri:</p>
						<springForm:form method="POST" cssClass="login" action="/elencoRifugi" modelAttribute="rifSearch">
							<div class="form-group">
								<label for="inputNome">Nome del rifugio</label>
								<springForm:input type="text" cssClass="form-control" path="nome" placeholder="Rifugio Comici"/>
							</div>
							<div class="form-group">
								<label for="inputMassMont">Massiccio montuoso</label>
								<springForm:input type="text" cssClass="form-control" path="massiccioMontuoso" placeholder="Tre Cime di Lavaredo"/>
							</div>
							<div class="form-group">
								<label for="inputStato">Stato dei rifugi</label>
								<springForm:checkbox label="Aperto" path="aperto"/>
							</div>
							<input type="submit" class="btn btn-outline-secondary btn-sm" value="Filtra">
						</springForm:form>
					</div>
					<div class="col-md-9">
						<div class="row">
								<div class="col-md-9 myCol">
									<h1 class="pageTitle">Elenco dei rifugi</h1>
								</div>
								<div class="col-md-3">
								<% if(logged && loggedUser.getCredenziali().equals(CredenzialiUtente.Admin)) { %>
									<a class="btn btn-primary" href="/elencoRifugi/inserisciRifugio" ><i class="fa fa-plus fa-lg"></i> Nuovo rifugio</a>
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
							<% for(Rifugio rif : rifugi){%>
							<div class="col-md-6">
								<a href="/rifugio/<%=rif.getId()%>" class="cardHref">
								<div class="card rifCard mb-2">
									<img class="card-img-top myCardImg" src="<%=rif.getIconPath()%>">
									<div class="card-body">
										<h5 class="card-title rifCardText"><%=rif.getNome()%>, <%=rif.getAltitudine()%>m</h5>
										<p class="card-text rifCardSub"><%=rif.getMassiccioMontuoso()%></p>
										<p class="card-text rifCardText"> <%=rif.getDataApertura().toLocalDate().format(formatter)%> - <%=rif.getDataChiusura().toLocalDate().format(formatter)%></p>
										<% if(rif.getDataApertura().compareTo(oggi)<=0 && rif.getDataChiusura().compareTo(oggi)>=0) { %>
											<span class="aperto"><i class="fa fa-circle fa-md" style="color:green;"></i>  Aperto</span>
											<%} else { %>
											<span class="chiuso"><i class="fa fa-circle fa-md" style="color:red;"></i>  Chiuso</span>
										<% } %>
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