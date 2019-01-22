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
	
	.txt-center {
    text-align: center;
}

.hide {
    display: none;
}

.clear {
    float: none;
    clear: both;
}

.rating {
    width: 240px;
    unicode-bidi: bidi-override;
    direction: rtl;
    text-align: center;
    position: relative;
}

.rating > label {
    float: right;
    display: inline;
    padding: 0;
    margin: 0;
    position: relative;
    width: 1.1em;
    cursor: pointer;
    color: #000;
	font-size: 40px;
}

.rating > label:hover,
.rating > label:hover ~ label,
.rating > input.radio-btn:checked ~ label {
    color: transparent;
}

.rating > label:hover:before,
.rating > label:hover ~ label:before,
.rating > input.radio-btn:checked ~ label:before,
.rating > input.radio-btn:checked ~ label:before {
    content: "\2605";
    position: absolute;
    left: 0;
    color: #FFD700;
}
	</style>
</head>

<body style="background-color:#d1e1bf">
	<%@include file="/WEB-INF/menu.incl" %>

	<div id="first">
	</div>
	<div id="second">
		<font size="7" color = "blue">Kantyna</font>
		<div align="center">
			<font size="5"> <strong><s:message code="page.rejestracja.Tytul"/></strong> </font>
		</div>
	</div>
	
	<div id="third2">
		<div align="right">
			<sec:authorize access="isAuthenticated()">
				<form action="/logout" method="post">
					<b><s:message code="page.welcome.Zalogowany"/>${Uzytkownik} </b>
					<button type="submit" class="btn btn-primary" name="${_csrf.parameterName}" value="${_csrf.token}"><s:message code="page.Wyloguj"/></button>
				</form>
			</sec:authorize>
		</div>
	</div>
	
	<div id="fourth">
		<form:form action="dodajKom" method="POST" modelAttribute="Komentarz">
			
			<div class="form-group row">
				<label for="Ocena" class="col-3 col-form-label"><strong><s:message code="page.komentarz.Ocena"/>:</strong></label>
				<div class="col-9">
					<div class="txt-center">
						 <div class="rating">
				            <form:radiobutton path="jakaOcena" id="star5" name="star" value="5" class="radio-btn hide" />
				            <label for="star5">&#9733;</label>
				            <form:radiobutton path="jakaOcena" id="star4" name="star" value="4" class="radio-btn hide" />
				            <label for="star4">&#9733;</label>
				            <form:radiobutton path="jakaOcena" id="star3" name="star" value="3" class="radio-btn hide" />
				            <label for="star3">&#9733;</label>
				            <form:radiobutton path="jakaOcena" id="star2" name="star" value="2" class="radio-btn hide" />
				            <label for="star2">&#9733;</label>
				            <form:radiobutton path="jakaOcena" id="star1" name="star" value="1" class="radio-btn hide" />
				            <label for="star1">&#9733;</label>
				            <div class="clear"></div>
				        </div>
				        <sf:errors path="Ocena" class="text-danger" />
			       </div>
				</div>
				</div>
				
				<div class="form-group row">
					<label for="Kom" class="col-3 col-form-label"><strong><s:message code="page.komentarz.Tresc"/>:</strong></label>
			   		<div class="col-9">
			       		<form:textarea path="komentarz" rows="6" cols="40" class="form-control" />
			       		<sf:errors path="Komentarz" class="text-danger" />
					</div>
				</div>
				
				<div>
					<form:input type="number" path="idPotrawy" value="${potrawa}" style="display: none"/>
				</div>
		
			
		<div align="center">
			<button type="submit" class="btn btn-primary" name=dodajK value="Kom"><s:message code="page.komentarz.DodajK"/></button>
		</div>
	</form:form>
	</div>
</body>

</html>