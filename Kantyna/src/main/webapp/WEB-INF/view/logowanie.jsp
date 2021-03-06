<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<html>
<head>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
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
	</style>
</head>

<body style="background-color:#d1e1bf">

	<div id="second">
		<font size="18" color = "blue">World of Music</font>
		<div align="center">
			<font size="5"> <strong><s:message code="page.logowanie.Tytul"/></strong> </font>
		</div>
	</div>
	
	<div id="sixth">
		<c:if test="${param.error == '1'}">
			<c:if test="${SPRING_SECURITY_LAST_EXCEPTION.message == 'User is disabled'}">
      			<div class="container alert alert-danger mt-2 text-center" role="alert" style="width: 70%;">
      		 		<b> <s:message code="page.logowanie.Ban"/> </b>
      			</div>
			</c:if>
		
			<c:if test="${SPRING_SECURITY_LAST_EXCEPTION.message == 'Bad credentials'}">
      			<div class="container alert alert-danger mt-2 text-center" role="alert" style="width: 70%;">
      		 		<b> <s:message code="page.logowanie.ZleDane"/> </b>
      			</div>
			</c:if>
		</c:if>
	</div>
	
	<div id="third">
		<form action="/welcome" method="GET">
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
	
	<div id="fourth">
		<form:form action="/logowanie" method="POST">
			<div class="form-group row">
				<label for="Login" class="col-3 col-form-label"><strong><s:message code="page.logowanie.Login"/>:</strong></label>
				<div class="col-9">
					<input type="text" class="form-control" name="login" id="login" placeholder=<s:message code="page.logowanie.Login"/>>
				</div>
			</div>
		
			<div class="form-group row">
				<label for="Haslo" class="col-3 col-form-label"><strong><s:message code="page.logowanie.Haslo"/>:</strong></label>
				<div class="col-9">
					<input type="password" class="form-control" name="haslo" id="haslo" placeholder=<s:message code="page.logowanie.Haslo"/>>
				</div>
			</div>
		
			<div align="center">
				<button type="submit" class="btn btn-primary" name=zaloguj value="Zaloguj"><s:message code="page.logowanie.Zaloguj"/></button>
			</div>
		</form:form>
	</div>
</body>

</html>