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

	<div id="first">
	</div>
	<div id="second">
		<font size="7" color = "blue">Kantyna</font>
		<div align="center">
			<font size="5"> <strong><s:message code="page.parametry.Tytul"/></strong> </font>
		</div>
	</div>
	
	<div id="third">
		<form action="/main" method="GET">
			<button type="submit" class="btn btn-primary"><s:message code="page.ReturnButton"/></button>
		</form>
		
		<c:set var="localeCode" value="${pageContext.response.locale}" />
		<c:choose>
			<c:when test="${localeCode == 'pl'}">
				<a class="btn btn-outline-success"
					href="?lang=en">EN</a>
			</c:when>
			<c:when test="${localeCode == 'en'}">
				<a class="btn btn-outline-success"
					href="?lang=pl">PL</a>
			</c:when>
		</c:choose>
	</div>
	
	<div id="third2">
		<div align="right">
			<sec:authorize access="isAuthenticated()">
				<form action="/logout" method="post">
					<b><s:message code="page.welcome.Zalogowany"/>${uzytkownik} </b>
					<button type="submit" class="btn btn-primary" name="${_csrf.parameterName}" value="${_csrf.token}"><s:message code="page.Wyloguj"/></button>
				</form>
			</sec:authorize>
		</div>
	</div>
	
	<div id="fourth">
		<form:form action="parametry" method="POST" modelAttribute="Parametry">
			
			<s:message code="page.parametry.GodzinaOtwarcia" var="godzinaOtwar"/>
			
			<div class="form-group row">
				<label for="Imie" class="col-4 col-form-label"><strong>${godzinaOtwar}:</strong></label>
				<div class="col-8">
					<form:input type="text" path="godzinaO" class="form-control" name="godzinaOtwarcia" value="${wartosci.godzinaOtwarcia}" placeholder="${godzinaOtwar}"/>
					<sf:errors path="godzinaO" class="text-danger" />
				</div>
			</div>
			
			<s:message code="page.parametry.GodzinaZamkniecia" var="godzinaZam"/>
			
			<div class="form-group row">
				<label for="Nazwisko" class="col-4 col-form-label"><strong>${godzinaZam}:</strong></label>
				<div class="col-8">
					<form:input type="text" class="form-control" path="godzinaZ" name="godzinaZamkniecia" value="${wartosci.godzinaZamkniecia}" placeholder="${godzinaZam}"/>
					<sf:errors path="godzinaZ" class="text-danger" />
				</div>
			</div>
		
			<div class="form-group row">
				<label for="Login" class="col-4 col-form-label"><strong><s:message code="page.parametry.SzukanieStolika"/>:</strong></label>
				<div class="col-8">
					<form:input type="number" class="form-control" path="szukanieStolika" name="szukanieStolika" value="${wartosci.szukanieStolika}" placeholder=""/>
					<sf:errors path="szukanieStolika" class="text-danger" />
				</div>
			</div>
		
		<div align="center">
			<button type="submit" class="btn btn-primary" name=zmien value="Zmien"><s:message code="page.parametry.Zmien"/></button>
		</div>
	</form:form>
	</div>
</body>

</html>