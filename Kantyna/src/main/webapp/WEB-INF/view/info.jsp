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
	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.2/moment.min.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.1/js/tempusdominus-bootstrap-4.min.js"></script>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.1/css/tempusdominus-bootstrap-4.min.css" />
	
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
	
	<div style="position:absolute; left: 40%">
		<c:choose>
			<c:when test="${wartosci.czyZamkniete}">
				<font size=8 color="red">
					<s:message code="page.main.Zamkniete"/>
				</font>
			</c:when>
		</c:choose>
	</div>
	<div id="first">
	</div>
	
	<div id=fourth class="offset-md-3" style="border-style: solid; width: 50%">
			<s:message code="page.parametry.GodzinaOtwarcia" var="godzinaOtwar"/>
			
			<div class="form-group row" style="width: 100%;">
				<label for="Imie" class="col-md-5 offset-md-4 col-form-label" style="text-align: left">
					<strong>${godzinaOtwar}:</strong>   <c:out value="${wartosci.godzinaOtwarcia}"/>
				</label>
			</div>
		
			<s:message code="page.parametry.GodzinaZamkniecia" var="godzinaZam"/>
                
			<div class="form-group row" style="width: 100%;">
				<label for="Nazwisko" class="col-md-5 offset-md-4 col-form-label" style="text-align: left">
					<strong>${godzinaZam}:</strong>  <c:out value="${wartosci.godzinaZamkniecia}"/>
				</label>
			</div>
		
			<div class="form-group row" style="width: 100%;">
				<label for="Login" class="col-md-5 offset-md-4 col-form-label" style="text-align: left">
					<strong><s:message code="page.parametry.SzukanieStolika"/>:</strong>  <c:out value="${wartosci.szukanieStolika}"/>
				</label>
			</div>
					
			<s:message code="page.parametry.coIleZwalniac" var="coIleZwal"/>
			<div class="form-group row" style="width: 100%;">
				<label for="Nazwisko" class="col-md-5 offset-md-4 col-form-label" style="text-align: left">
					<strong>${coIleZwal}:</strong>  <c:out value="${wartosci.coIleZwalniac}"/>
				</label>
			</div>
	</div>
</body>

</html>