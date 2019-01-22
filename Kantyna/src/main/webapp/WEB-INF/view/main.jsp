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
	
	.clear 
	{
	    float: none;
	    clear: both;
	}
	
	.rating 
	{
	    width: 240px;
	    unicode-bidi: bidi-override;
	    direction: rtl;
	    text-align: center;
	    position: relative;
	}
	
	.rating > label 
	{
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
	.rating > input.radio-btn:checked ~ label
	{
	    color: transparent;
	}
	
	.rating > label:hover:before,
	.rating > label:hover ~ label:before,
	.rating > input.radio-btn:checked ~ label:before,
	.rating > input.radio-btn:checked ~ label:before 
	{
	    content: "\2605";
	    position: absolute;
	    left: 0;
	    color: #FFD700;
	}
	</style>
	
	<script>
	/*jesli bedzie w window.location.href bez https to dodaje do aktualnego adresu, w przeciwnym razie przenosi na wskazany*/
	function usuwanie(id)
	{
		swal({ 
	    title: '<s:message code="page.main.UsuwaniePot"/>',   
	    text: '<s:message code="page.main.PytanieUsu"/>',   
	    type: "question",   
	    showCancelButton: true,   
	    confirmButtonColor: "#DD6B55",   
	    confirmButtonText: '<s:message code="page.main.Tak"/>',   
	    cancelButtonText: '<s:message code="page.main.Nie"/>',   
	    closeOnConfirm: false,   
	    closeOnCancel: false }).then((result) => {
		if(result.value)
		{
			document.querySelector('#usun').submit();
		}
		else
		{
			swal('<s:message code="page.main.Anulowane"/>'/*, "success"*/);
			return false;
		}})
	}
	
	function przywracanie(id)
	{
		swal({ 
		    title: '<s:message code="page.main.DodawaniePot"/>',   
		    text: '<s:message code="page.main.PytanieDod"/>',   
		    type: "question",   
		    showCancelButton: true,   
		    confirmButtonColor: "#DD6B55",   
		    confirmButtonText: '<s:message code="page.main.Tak"/>',   
		    cancelButtonText: '<s:message code="page.main.Nie"/>',   
		    closeOnConfirm: false,   
		    closeOnCancel: false }).then((result) => {
			if(result.value)
			{
				document.querySelector('#przywroc').submit();
			}
			else
			{
				swal('<s:message code="page.main.Anulowane"/>'/*, "success"*/);
				return false;
			}})
	}
	</script>
</head>

<body style="background-color:#d1e1bf">
	<%@include file="/WEB-INF/menu.incl" %>

	<img src="<c:url value='/images/du.jpg'/>"/>
	
	<c:choose>
		<c:when test="${param.potDodana == '1'}">
			<div class="container alert alert-success mt-2 text-center" role="alert" style="width: 70%;"><b><s:message code="page.main.PotDodana"/></b></div>
		</c:when>
		<c:when test="${param.potUsunieta == '1'}">
			<div class="container alert alert-success mt-2 text-center" role="alert" style="width: 70%;"><b><s:message code="page.main.PotUsunieta"/></b></div>
		</c:when>
		<c:when test="${param.nowaPot == '1'}">
			<div class="container alert alert-success mt-2 text-center" role="alert" style="width: 70%;"><b><s:message code="page.main.NowaPotrawa"/></b></div>
		</c:when>
		<c:when test="${param.nowyKom == '1'}">
			<div class="container alert alert-success mt-2 text-center" role="alert" style="width: 70%;"><b><s:message code="page.main.NowyKom"/></b></div>
		</c:when>
		<c:when test="${param.nowyStol == '1'}">
			<div class="container alert alert-success mt-2 text-center" role="alert" style="width: 70%;"><b><s:message code="page.main.NowyStol"/></b></div>
		</c:when>
	</c:choose>
	
		<div class="form-group row" style="width: 100%;">
			<label for="rodzajPot" class="col-md-1 offset-md-4 col-form-label" style="text-align: right"><strong><s:message code="page.potrawa.Rodzaj"/></strong></label>
			<div class="col-3">
				<sf:select id="rodzaje" items="${lista}" path="lista" name="rodzajPot" placeholder="rodzaj" class="form-control"/>
			</div>
		</div>
	
		<table class="table table-bordered table-striped table-dark" id="potrawy">
			<thead>
				<tr>
					<th><s:message code="page.main.Obrazek"/></th>
					<th><s:message code="page.main.Nazwa"/></th>
					<th><s:message code="page.main.Cena"/></th>
					<th><s:message code="page.main.Rodzaj"/></th>
					<th></th>
				</tr>
			</thead>
			<c:forEach items="${potrawy}" var="potrawa">
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
					<c:if test="${potrawa.czyJestDostepna}">
						<form:form id="usun" action="usunZmenu?par=${potrawa.id}" method="POST" onsubmit="return usuwanie(${potrawa.id}) ? true : false;">	
							<button class="btn btn-primary" name=dodaj value="DodajA"><s:message code="page.main.UsunZmenu"/></button>
						</form:form>				
					</c:if>
					<c:if test="${not potrawa.czyJestDostepna}">	
						<form:form id="przywroc" action="usunZmenu?par=${potrawa.id}" method="POST" onsubmit="return przywracanie(${potrawa.id}) ? true : false;">	
							<button type="submit" class="btn btn-success" name=dodaj value="DodajA"><s:message code="page.main.PrzywrocDoMenu"/></button>
						</form:form>
					</c:if>
					<button id="formularz" type="button" class="btn btn-primary" data-toggle="modal" data-target="#komentarz1" title="<s:message code="page.main.Skomentuj"/>" onclick="myFunction2(${potrawa.id})">
						<i class="far fa-comment-dots" style="font-size:30px"></i>
					</button>
					<c:choose>
						<c:when  test="${empty potrawa.listaKomentarzy}">
							<button id="koment" type="button" class="btn btn-primary" disabled data-toggle="modal" data-target="#komentarze" onclick="myFunction(${potrawa.id})">
								<s:message code="page.main.Komentarze2"/>
							</button>
						</c:when>
						<c:otherwise>
							<button id="koment" type="button" class="btn btn-primary" data-toggle="modal" data-target="#komentarze" onclick="myFunction(${potrawa.id})">
								<s:message code="page.main.Komentarze2"/>
							</button>
						</c:otherwise>
					</c:choose>
				</td>
				</tr>
			</c:forEach>
		</table>
		
			<script>
				function myFunction(id) 
				{
				  var table, tr, td, i, txtValue;
				  table = document.getElementById("komenty");
				  tr = table.getElementsByTagName("tr");
				  // Loop through all table rows, and hide those who don't match the search query
				  for (i = 0; i < tr.length; i++) 
				  {
				    td = tr[i].getElementsByTagName("td")[0];
				    if (td)
				    {
				      txtValue = td.textContent || td.innerText;
				      if (txtValue.includes(id)) 
				      {
				        tr[i].style.display = "";
				      } 
				      else 
				      {
				        tr[i].style.display = "none";
				      }
				    }
				  }
				}
				
				$(document).ready(function() 
				{
				    $("#rodzaje").change(function () 
				   	{
				        var selectedOption = $("#rodzaje").val();
		        		var table, tr, td, i, txtValue;
					  	table = document.getElementById("potrawy");
					 	tr = table.getElementsByTagName("tr");
					  	// Loop through all table rows, and hide those who don't match the search query
					  	for (i = 0; i < tr.length; i++) 
					  	{
					    	td = tr[i].getElementsByTagName("td")[3];
						    if (td)
						    {
						      	txtValue = td.textContent || td.innerText;
							    if(selectedOption == "wszystkie")
							    {
							    	tr[i].style.display = "";
							    }
							    else if (txtValue.includes(selectedOption)) 
							    {
							    	tr[i].style.display = "";
							    } 
						        else 
						        {
						            tr[i].style.display = "none";
						        }
						   }
					  	}
				    });
				});
			</script>
		
			<div class="modal fade" id="komentarze" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
			  <div class="modal-dialog" role="document">
			    <div class="modal-content">
			      <div class="modal-header">
			        <h5 class="modal-title" id="exampleModalLabel"><s:message code="page.main.Komentarze"/></h5>
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
			          <span aria-hidden="true">&times;</span>
			        </button>
			      </div>
			      <div class="modal-body">
			      	<table class="table table-bordered table-striped table-dark" id="komenty">
			        	<thead>
						<tr>
							<th><s:message code="page.main.Komentarz"/></th>
							<th><s:message code="page.main.Ocena"/></th>
							<th><s:message code="page.main.Autor"/></th>
						</tr>
						</thead>
						<c:forEach items="${potrawy}" var="potrawa">
				        	<c:forEach items="${potrawa.listaKomentarzy}" var="komentarz">
								<tr>
									<td hidden>
										<c:out value="${potrawa.id}"/>
									</td>
									<td>
										<c:out value="${komentarz.komentarz}" />
									</td>
									<td>
										<c:forEach begin="1" end="${komentarz.ocena}" step="1"> 
											<label style="color: #FFD700; font-size: 40px;">&#9733;</label>
										</c:forEach>
										<c:forEach begin="${komentarz.ocena + 1}" end="5" step="1"> 
											<label style="font-size: 40px;">&#9733;</label>
										</c:forEach>
									</td>
									<td>
										<c:out value="${komentarz.uzytkownik.imie}"/> <c:out value="${komentarz.uzytkownik.nazwisko}"/>
									</td>
								</tr>
							</c:forEach>
						</c:forEach>
					</table>
			     </div>
			 	</div>
				</div>
			</div>	
			
			<script>
				function myFunction2(id) 
				{
					document.getElementById("numerPot").value = id;
				}
			</script>
			
			<div class="modal fade" id="komentarz1" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
			  <div class="modal-dialog" role="document">
			    <div class="modal-content">
			      <div class="modal-header">
			        <h5 class="modal-title" id="exampleModalLabel2"><s:message code="page.main.Komentarze"/></h5>
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
			          <span aria-hidden="true">&times;</span>
			        </button>
			      </div>
			      <div class="modal-body">
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
							<form:input id="numerPot" type="number" path="idPotrawy" style="display: none"/>
						</div>
						<div align="center">
							<button type="submit" class="btn btn-primary" name=dodajK value="Kom"><s:message code="page.komentarz.DodajK"/></button>
						</div>
					</form:form>
				</div>
			</div>
			</div>
		</div>
</body>
</html>