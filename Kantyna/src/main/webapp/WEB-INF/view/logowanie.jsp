<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<html>
<head>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
		
	<style style="text/css">
		
	div#third
	{
		opacity:1;
		position:absolute;
		top: 12%;
		left:0;
	}
	div#fourth
	{
		 margin-top: 230px;
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
		top: 10%;
		left:44%;
	}
	</style>
</head>

<body style="background-color:#d1e1bf">
	<%@include file="/WEB-INF/menu.incl" %>
	
	<div id="sixth">
		<c:if test="${param.error == '1'}">
			<div class="form-group row" style="width: 100%;">
	   			<div class="container alert alert-danger mt-2 text-center" role="alert" style="width: 70%;">
	   		 		<div class="form-group row" style="width: 100%;">
	   		 			<b> <s:message code="page.logowanie.Ban"/> </b>
	   		 		</div>
	   		 		<div class="form-group row" style="width: 100%;">
	   		 			<b> <s:message code="page.Uzytkownicy.Komentarz"/>: </b><c:out value="${sessionScope.komentarz}"/>
	   		 		</div>
	   			</div>
   			</div>
		</c:if>
		
		<c:if test="${param.error == '2'}">
			<div class="form-group row" style="width: 100%;">
				<div class="container alert alert-danger mt-2 text-center" role="alert" style="width: 70%;">
			 		<b> <s:message code="page.logowanie.ZleDane"/> </b>
			 	</div>
			</div>
		</c:if>
	</div>
	
	<div id="fourth">
		<form:form action="/logowanie" method="POST">
			<div class="form-group row" style="width: 100%;">
				<label for="Login" class="col-md-1 offset-md-4 col-form-label" style="text-align: right"><strong><s:message code="page.logowanie.Login"/>:</strong></label>
				<div class="col-3">
					<input id="pole1" type="text" class="form-control" name="login" id="login" placeholder=<s:message code="page.logowanie.Login"/>>
				</div>
			</div>
		
			<div class="form-group row" style="width: 100%;">
				<label for="Haslo" class="col-md-1 offset-md-4 col-form-label" style="text-align: right"><strong><s:message code="page.logowanie.Haslo"/>:</strong></label>
				<div class="col-3">
					<input id="pole2" type="password" class="form-control" name="haslo" id="haslo" placeholder=<s:message code="page.logowanie.Haslo"/>>
				</div>
			</div>
		
			<div align="center">
				<button id="logowanko" type="submit" class="btn btn-primary" name=zaloguj value="Zaloguj"><s:message code="page.logowanie.Zaloguj"/></button>
			</div>
		</form:form>
	</div>
</body>

</html>