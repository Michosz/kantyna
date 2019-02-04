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
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
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
	</style>
</head>

<body style="background-color:#d1e1bf">
	<%@include file="/WEB-INF/menu.incl" %>
	
	<div id="first">
	</div>
	
	<div id="fourth">
		<form:form action="rejestracja" method="POST" modelAttribute="Uzytkownik">
			
			<s:message code="page.klienci.Imie" var="mimie"/>
			<div class="form-group row" style="width: 100%;">
				<label for="Imie" class="col-md-1 offset-md-4 col-form-label" style="text-align: right"><strong><s:message code="page.klienci.Imie"/>:</strong></label>
				<div class="col-3">
					<form:input type="text" path="imie" class="form-control" name="imie" placeholder="${mimie}"/>
					<sf:errors path="Imie" class="text-danger" />
				</div>
			</div>
			
			<s:message code="page.klienci.Nazwisko" var="mnazwisko"/>
			<div class="form-group row" style="width: 100%;">
				<label for="Nazwisko" class="col-md-1 offset-md-4 col-form-label" style="text-align: right"><strong>${mnazwisko}:</strong></label>
				<div class="col-3">
					<form:input type="text" class="form-control" path="nazwisko" name="nazwisko" placeholder="${mnazwisko}"/>
					<sf:errors path="Nazwisko" class="text-danger" />
				</div>
			</div>
		
			<div class="form-group row" style="width: 100%;">
				<label for="Login" class="col-md-1 offset-md-4 col-form-label" style="text-align: right"><strong><s:message code="page.logowanie.Login"/>:</strong></label>
				<div class="col-3">
					<form:input type="text" class="form-control" path="login" name="login"  placeholder="Login"/>
					<sf:errors path="Login" class="text-danger" />
				</div>
			</div>
			
			<s:message code="page.logowanie.Haslo" var="mhaslo"/>
			<div class="form-group row" style="width: 100%;">
				<label for="Haslo" class="col-md-1 offset-md-4 col-form-label" style="text-align: right"><strong>${mhaslo}:</strong></label>
				<div class="col-3">
					<form:input type="password" class="form-control" path="haslo" name="haslo" placeholder="${mhaslo}"/>
					<sf:errors path="Haslo" class="text-danger" />
				</div>
			</div>
			
			<div class="form-group row" style="width: 100%;">
				<label for="Telefon" class="col-md-1 offset-md-4 col-form-label" style="text-align: right"><strong><s:message code="page.klienci.Telefon"/>:</strong></label>
				<div class="col-3">
					<form:input type="tel" class="form-control" path="telefon" name="telefon" placeholder="xxx-xxx-xxx"/>
					<sf:errors path="Telefon" class="text-danger" />
				</div>
			</div>
			
			<sec:authorize access="isAuthenticated() and hasRole('ROLE_MANAGER')">
			    <div class="form-group row" style="width: 100%;">
					<label for="Rola" class="col-md-1 offset-md-4 col-form-label" style="text-align: right"><strong><s:message code="page.rejestracja.Rola"/></strong></label>
					<div class="col-3">
						<sf:select items="${lista}" path="rolaa" required="required" name="rola" placeholder="rola" class="form-control"/>
					</div>
				</div>
			</sec:authorize>
		
		<div align="center">
			<button type="submit" class="btn btn-primary" name=zarejestruj value="Rejestracja"><s:message code="page.rejestracja.Zarejestruj"/></button>
		</div>
	</form:form>
	</div>
</body>

</html>