<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
		div#third2
		{
			opacity:1;
			position:absolute;
			top: 10%;
			left:93.5%;
		}
		.input-container 
		{
	  		display: flex;
	 		width: 100%;
		}
	</style>
</head>

<body style="background-color: #d1e1bf">
	<%@include file="/WEB-INF/menu.incl" %>

	<div id="first">
	</div>
	
	<div id="fourth">
		<sf:form action="nowa-potrawa" method="POST" modelAttribute="Potrawa" enctype="multipart/form-data">
			
			<div class="form-group row" style="width: 100%;">
				<label for="nazwa" class="col-md-1 offset-md-4 col-form-label" style="text-align: right"><strong><s:message code="page.potrawa.Nazwa"/>:</strong></label>
				<div class="col-3">
					<sf:input type="text" class="form-control" path="nazwa" name="nazwa" value="${potrawa.nazwa}" placeholder="nazwa"/>
					<sf:errors path="Nazwa" class="text-danger" />
				</div>
			</div>	
			
			<div class="form-group row" style="width: 100%;">
				<label for="cena" class="col-md-1 offset-md-4 col-form-label" style="text-align: right"><strong><s:message code="page.potrawa.Cena"/></strong></label>
				<div class="col-3">
					<div class="input-group">
						<sf:input type="number" step="0.01" class="form-control" path="cena" name="cena" value="${potrawa.cena}" placeholder="cena"/>
					</div>
					<sf:errors path="Cena" class="text-danger"/>
				</div>
			</div>
			
			<div class="form-group row" style="width: 100%;">
				<label for="rodzajPot" class="col-md-1 offset-md-4 col-form-label" style="text-align: right"><strong><s:message code="page.potrawa.Rodzaj"/></strong></label>
				<div class="col-3">
					<sf:select items="${lista}" path="rodzajPot" name="rodzajPot" value="${potrawa.rodzajPot}" placeholder="rodzaj" class="form-control"/>
					<sf:errors path="RodzajPot" class="text-danger" />
				</div>
			</div>
			
			<div class="form-group row" style="width: 100%;">
				<label for="Login" class="col-md-1 offset-md-4 col-form-label" style="text-align: right"><strong><s:message code="page.potrawa.Obrazek"/></strong></label>
				<div class="col-3">
					<label class="btn btn-info btn-lg btn-block" style="min-width: 250px">
					<span id="nazwaPliku"><i class="fa fa-upload"></i> <strong><s:message code="page.potrawa.Plik"/></strong></span>
			   			<sf:input type="file" class="inputfile" id="plik" path="obrazek" name="obrazek" style="display: none"/>	
			  		</label>
			  		<div class="form-group row" style="margin-left: 3px">
					<sf:errors path="Obrazek" class="text-danger" />
					</div>
				</div>
			</div>
		
			<script>
			    $(document).ready(function()
			    {
		    		  $("#plik").change(function(e)
		  			  {
		  					$("#nazwaPliku").text(e.target.files[0].name)
		  			  });
			    });
			</script>
		
		<div align="center">
			<button type="submit" class="btn btn-primary btn-lg" name=szukaj value="Szukaj"><s:message code="page.potrawa.Wyslij"/></button>
		</div>
	</sf:form>
	</div>
</body>

</html>