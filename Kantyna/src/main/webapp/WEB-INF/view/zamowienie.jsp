<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<html>
<head>
	<!--  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
	-->
	<link rel='stylesheet' href='https://use.fontawesome.com/releases/v5.6.3/css/all.css' integrity='sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/' crossorigin='anonymous'>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
		
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.all.min.js"></script>
  	<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.min.css'>
	<style style="text/css">	
	div#second
	{
		opacity:1;
		position:absolute;
		top: 0;
		left:42%;
	}
	div#third
	{
		opacity:1;
		position:absolute;
		top: 12%;
		left:0;
	}
	div#fourth
	{
		opacity:1;
		position:absolute;
		top: 30%;
		left:40%;
	}
	div#fifth
	{
		opacity:1;
		position:absolute;
		top: 12%;
		left:93.5%;
	}
	div#sixth
	{
		opacity:1;
		position:absolute;
		top: 12%;
		left:44%;
	}
	
	.txt-center
	{
	    text-align: center;
	}
	
	.hide
	{
	    display: none;
	}
	</style>

</head>

<body style="background-color:#d1e1bf">
	<%@include file="/WEB-INF/menu.incl" %>
	
	<form:form action="zamowienie" method="POST" modelAttribute="Zamowienie">
		<table class="table table-bordered table-striped table-dark">
			<thead>
				<tr>
					<th><s:message code="page.main.Obrazek"/></th>
					<th><s:message code="page.main.Nazwa"/></th>
					<th><s:message code="page.main.Cena"/></th>
					<th><s:message code="page.main.Rodzaj"/></th>
					<th><s:message code="page.zamowienie.Ilosc"/></th>
				</tr>
			</thead>
			<c:forEach items="${potrawy}" var="potrawa" varStatus="licznik">
				<c:if test="${potrawa.czyJestDostepna}">
				<tr>
					<td>
						<img  src="data:potrawa/jpeg;base64,${potrawa.base64}" width="200" height="200" />
					</td>
					<td>
						<c:out value="${potrawa.nazwa}" />
					</td>
					<td>
						<c:out value="${potrawa.cena}" />
					</td>
					<td>
						<c:out value="${potrawa.rodzajPotrawy.rodzaj}" />
					</td>
					<td>
						<form:input type="number" class="form-control" path="listaIlosci" value="0" name="ilosc" placeholder="ilosc"/>
						<form:input type="number" class="form-control" path="listaPotraw" value="${potrawa.id}" name="ktoraPot" placeholder="cena" style="display: none"/>
					</td>
				</tr>
				</c:if>
			</c:forEach>
				
			</table>
			
			<div align="center">
				<sf:errors path="listaIlosci" class="text-danger" />
			</div>
			
			<div class="form-group row" style="width: 100%;">
				<label for="Login" class="col-md-2 offset-md-3 col-form-label" style="text-align: right"><strong><s:message code="page.stoliki.IloscMiejsc"/>:</strong></label>
				<div class="col-3">
					<form:input type="number" class="form-control" path="iloscMiejsc" name="iloscMiejsc" placeholder=""/>
					<sf:errors path="iloscMiejsc" class="text-danger" />
				</div>
			</div>
			
			<div class="form-group row" style="width: 100%;">
				<label for="Login" class="col-md-2 offset-md-3 col-form-label" style="text-align: right"><strong><s:message code="page.zamowienie.Czas"/>:</strong></label>
				<div class="col-3">
					<form:input type="text" class="form-control" path="czasReal" name="czasReal" placeholder="xx:xx"/>
					<sf:errors path="czasReal" class="text-danger" />
				</div>
			</div>
			
			<div align="center">
				<button type="submit" class="btn btn-primary btn-lg" name=szukaj value="Szukaj"><s:message code="page.zamowienie.Zloz"/></button>
			</div>
		</form:form>	
</body>
</html>