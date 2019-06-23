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
		
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.2/moment.min.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.1/js/tempusdominus-bootstrap-4.min.js"></script>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.1/css/tempusdominus-bootstrap-4.min.css" />
	
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
		margin-top: 170px;
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
	
	<div id="fourth">
	<form:form action="zamowienie" method="POST" modelAttribute="Zamowienie">
			<div align="center">
				<font size="5">
					<sf:errors path="koszyk" class="text-danger"/>
				</font>
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
					<div class="input-group date" id="timePicker" data-target-input="nearest">
                    	<form:input type="text" class="form-control" path="czasReal" name="czasReal" placeholder="xx:xx"/>
						<div class="input-group-text" data-target="#timePicker" data-toggle="datetimepicker">
							<i class="fa fa-clock-o"></i>
						</div>	
               		</div>
               		<sf:errors path="czasReal" class="text-danger" />
				</div>
			</div>
			
			<div class="form-group row" style="width: 100%;">
				<label for="Login" class="col-md-2 offset-md-3 col-form-label" style="text-align: right"><strong><s:message code="page.zamowienie.CzyPlaciOdRazu"/>:</strong></label>
				<div class="col-3">
					<span class="btn btn-success active" onclick="zaznacz('czyPlaciOdRazu', 'checkbox1')" style="height: 40px; width: 40px">
						<i id="czyPlaciOdRazu" class=""></i>
					</span>
					<c:if test="${Zamowienie.czyPlaciOdRazu}">
		 				<form:checkbox id="checkbox1" path="czyPlaciOdRazu" style="display: none" checked="${Zamowienie.czyPlaciOdRazu}"/>
		 			</c:if>
		 			<c:if test="${not Zamowienie.czyPlaciOdRazu}">
		 				<form:checkbox id="checkbox1" path="czyPlaciOdRazu" style="display: none"/>
		 			</c:if>
		 		</div>
			</div>
			
			<script>
				$(document).ready(function() 
				{
					if( ${Zamowienie.czyPlaciOdRazu} == true)
					{
						document.getElementById("czyPlaciOdRazu").className = "fas fa-check";
					}
				});
			
				function zaznacz(idIkony, idCheckboxa) 
				{
					if(document.getElementById(idIkony).className  == "fas fa-check")
					{
						document.getElementById(idIkony).className  = "";
						document.getElementById(idCheckboxa).checked = false;
					}
					else if(document.getElementById(idIkony).className  == "")
					{
						document.getElementById(idIkony).className  = "fas fa-check";
						document.getElementById(idCheckboxa).checked = true;
					}
				}
			
				$(function () 
				{
			       	$('#timePicker').datetimepicker(
			    	{
			           format: 'HH:mm',
			       	});
		      	});
			</script>
			
			<div align="center">
				<button type="submit" class="btn btn-primary btn-lg" name=szukaj value="Szukaj"><s:message code="page.zamowienie.Zloz"/></button>
			</div>
		</form:form>	
		</div>
</body>
</html>