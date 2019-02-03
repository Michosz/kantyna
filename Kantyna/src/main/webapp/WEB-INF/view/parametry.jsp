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
	<link rel='stylesheet' href='https://use.fontawesome.com/releases/v5.6.3/css/all.css' integrity='sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/' crossorigin='anonymous'>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
	
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.all.min.js"></script>
  	<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.min.css'>
	<style style="text/css">
	div#first
	{
		background-image: url('images.jpg');
		height: 100%;
		background-position: center;
		background-repeat: no-repeat;
		background-size: cover;
		opacity:0.6;
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
		margin-top: 170px;
	}
	</style>
</head>

<body style="background-color:#d1e1bf">
<%@include file="/WEB-INF/menu.incl" %>

	<div id="first">
	</div>
	
	<div id=fourth>
		<form:form action="parametry" method="POST" modelAttribute="Parametry">
			
			<s:message code="page.parametry.GodzinaOtwarcia" var="godzinaOtwar"/>
			
			<div class="form-group row" style="width: 100%;">
				<label for="Imie" class="col-md-2 offset-md-3 col-form-label" style="text-align: right"><strong>${godzinaOtwar}:</strong></label>
				<div class="col-3">
					<form:input type="text" path="godzinaO" class="form-control" name="godzinaOtwarcia" value="${wartosci.godzinaOtwarcia}" placeholder="${godzinaOtwar}"/>
					<sf:errors path="godzinaO" class="text-danger" />
				</div>
			</div>
			
			<s:message code="page.parametry.GodzinaZamkniecia" var="godzinaZam"/>
			
			<div class="form-group row" style="width: 100%;">
				<label for="Nazwisko" class="col-md-2 offset-md-3 col-form-label" style="text-align: right"><strong>${godzinaZam}:</strong></label>
				<div class="col-3">
					<form:input type="text" class="form-control" path="godzinaZ" name="godzinaZamkniecia" value="${wartosci.godzinaZamkniecia}" placeholder="${godzinaZam}"/>
					<sf:errors path="godzinaZ" class="text-danger" />
				</div>
			</div>
		
			<div class="form-group row" style="width: 100%;">
				<label for="Login" class="col-md-2 offset-md-3 col-form-label" style="text-align: right"><strong><s:message code="page.parametry.SzukanieStolika"/>:</strong></label>
				<div class="col-3">
					<form:input type="number" class="form-control" path="szukanieStolika" name="szukanieStolika" value="${wartosci.szukanieStolika}" placeholder=""/>
					<sf:errors path="szukanieStolika" class="text-danger" />
				</div>
			</div>
				
			<div class="form-group row" style="width: 100%;">
				<label for="Login" class="col-md-2 offset-md-3 col-form-label" style="text-align: right"><strong><s:message code="page.parametry.czyZamkniete"/>:</strong></label>
				<div class="col-3">
					<span class="btn btn-success active" onclick="zaznacz()" style="height: 40px; width: 40px">
						<i id="zaznaczony" class=""></i>
					</span>
		 			<form:checkbox id="checkbox" path="czyZamkniete" style="display: none" checked="${wartosci.czyZamkniete}"/>
			 	</div>
			</div>
		
		<div align="center">
			<button type="submit" class="btn btn-primary" name=zmien value="Zmien"><s:message code="page.parametry.Zmien"/></button>
		</div>
	</form:form>
	</div>
	
	<script>
		$(document).ready(function() 
		{
			if( ${wartosci.czyZamkniete} == true)
			{
				document.getElementById("zaznaczony").className = "fas fa-check";
			}
		});
	
		function zaznacz() 
		{
			if(document.getElementById("zaznaczony").className  == "fas fa-check")
			{
				document.getElementById("zaznaczony").className  = "";
				document.getElementById("checkbox").checked = false;
			}
			else if(document.getElementById("zaznaczony").className  == "")
			{
				document.getElementById("zaznaczony").className  = "fas fa-check";
				document.getElementById("checkbox").checked = true;
			}
		}
	</script>
</body>

</html>