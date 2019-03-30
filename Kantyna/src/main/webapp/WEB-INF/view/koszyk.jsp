<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
	</style>
	
	<script>
		function usuwanie(id)
		{
			swal({ 
		    title: '<s:message code="page.koszyk.UsuwaniePot"/>',   
		    text: '<s:message code="page.koszyk.PytanieUsu"/>',   
		    type: "question",   
		    showCancelButton: true,   
		    confirmButtonColor: "#DD6B55",   
		    confirmButtonText: '<s:message code="page.main.Tak"/>',   
		    cancelButtonText: '<s:message code="page.main.Nie"/>',   
		    closeOnConfirm: false,   
		    closeOnCancel: false }).then((result) => {
			if(result.value)
			{
				document.getElementById("usun" + id).submit();
			}
			else
			{
				swal('<s:message code="page.main.Anulowane"/>'/*, "success"*/);
				return false;
			}})
		}
		
		function usuwanieWszystko()
		{
			swal({ 
		    title: '<s:message code="page.koszyk.UsuwaniePotWszystko"/>',   
		    text: '<s:message code="page.koszyk.PytanieUsuWszystko"/>',   
		    type: "question",   
		    showCancelButton: true,   
		    confirmButtonColor: "#DD6B55",   
		    confirmButtonText: '<s:message code="page.main.Tak"/>',   
		    cancelButtonText: '<s:message code="page.main.Nie"/>',   
		    closeOnConfirm: false,   
		    closeOnCancel: false }).then((result) => {
			if(result.value)
			{
				document.getElementById("usunWszystko").submit();
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
	
	<c:choose>
		<c:when test="${param.potUsunieta == '1'}">
			<div class="container alert alert-success mt-2 text-center" role="alert" style="width: 70%;"><b><s:message code="page.koszyk.UsunWszystko"/></b></div>
		</c:when>
		<c:when test="${param.koszykPusty == '1'}">
			<div class="container alert alert-success mt-2 text-center" role="alert" style="width: 70%;"><b><s:message code="page.koszyk.KoszykPusty"/></b></div>
		</c:when>
	</c:choose>
	
	<form:form id="usunWszystko" class="zmienStatus" action="usunWszystko" method="POST" onsubmit="return usuwanieWszystko() ? true : false;">	
		<button type="submit" class="btn btn-danger" name=dodaj value="DodajA" style="width: 100%"><s:message code="page.koszyk.UsunWszystko"/></button>
	</form:form>
	<table class="table table-bordered table-striped table-dark" id="potrawy" style="table-layout: fixed; display: none">
		<thead>
			<tr>
				<th style="width: 220px"><s:message code="page.main.Obrazek"/></th>
				<th><s:message code="page.koszyk.Nazwa"/></th>
				<th><s:message code="page.main.CzyDostepna"/></th>
				<th><s:message code="page.main.Cena"/></th>
				<th><s:message code="page.main.CzyPromocja"/></th>
				<th><s:message code="page.main.Rodzaj"/></th>
				<th><s:message code="page.koszyk.Ilosc"/></th>
				<th></th>
			</tr>
		</thead>
		<c:forEach items="${koszyk}" var="koszyk">
		<tr>
			<td>
				<img src="data:potrawa/jpeg;base64,${koszyk.potrawa.base64}" width="200" height="200" title="<s:message code="page.main.ByPowiekszyc"/>" style="cursor: pointer" onclick="onClick('data:potrawa/jpeg;base64,${koszyk.potrawa.base64}')" data-toggle="modal" data-target="#obrazek"/>
			</td>
			<td>
				<c:out value="${koszyk.potrawa.nazwa}"/>
			</td>
			<td>
				<c:if test="${koszyk.potrawa.czyJestDostepna}">
					<s:message code="page.main.Tak"/>
				</c:if>
				<c:if test="${not koszyk.potrawa.czyJestDostepna}">
					<s:message code="page.main.Nie"/>
				</c:if>
			</td>
			<td>
				<c:if test="${koszyk.potrawa.czyPromocja}">
					<fmt:formatNumber type="number" minFractionDigits="2" value="${koszyk.potrawa.cenaPromocyjna}"/>
				</c:if>
				<c:if test="${not koszyk.potrawa.czyPromocja}">
					<fmt:formatNumber type="number" minFractionDigits="2" value="${koszyk.potrawa.cena}"/>
				</c:if>
			</td>
			<td>
				<c:if test="${koszyk.potrawa.czyPromocja}">
					<s:message code="page.main.Tak"/>
				</c:if>
				<c:if test="${not koszyk.potrawa.czyPromocja}">
					<s:message code="page.main.Nie"/>
				</c:if>
			</td>
			<td>
				<c:out value="${koszyk.potrawa.rodzajPotrawy.rodzaj}"/>
			</td>
			<td>
				<c:out value="${koszyk.ilosc}" />
			</td>
			<td>
				<form:form id="usun${koszyk.potrawa.id}" class="zmienStatus" action="usunZkoszyka?par=${koszyk.potrawa.id}" method="POST" onsubmit="return usuwanie(${koszyk.potrawa.id}) ? true : false;">	
					<button type="submit" class="btn btn-danger" name=dodaj value="DodajA" style="width: 100%"><s:message code="page.koszyk.Usun"/></button>
				</form:form>
			</td>
		</tr>
		</c:forEach>
	</table>
	
		<div id="przyciski" style="align: center">
		
		</div>
	
		<div class="modal fade" id="obrazek" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
			  <div class="modal-dialog modal-lg" role="document">
			    <div class="modal-content">
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
			          <span aria-hidden="true">&times;</span>
			        </button>
			      </div>
			      <div class="modal-body">
			      	<img class="w3-modal-content" id="wiekszyObrazek" style="width:100%">
			     </div>
			 	</div>
				</div>
		</div>	
	
	<script>
		function stronicowanie()
		{
			var td, txtValue;
			var przyc = document.getElementById("przyciski");
			przyc.innerHTML = '';
			var ileNaStrone = 1; //trzeba zmienic w funkcji ponizej jesli tu zmienie
			var ileStron = Math.ceil(${iloscRekordow}/ileNaStrone);
			var buttony = document.getElementById("przyciski");
			for(i = 0; i < ileStron; i++)
			{
				var btn = document.createElement("BUTTON");
				btn.id = i + 1;
				var t = document.createTextNode(i + 1);
				btn.appendChild(t);
				btn.className = "btn btn-primary";
				btn.onclick = function(){wyswietlRekordy()};
				buttony.appendChild(btn);
				if(i > 2 && i < ileStron - 1)
				{
					btn.style.display = "none";
				}
				if(i == 0)
				{
					btn.disabled = true;		
				}
			}
		 	var table = document.getElementById("potrawy");
		 	var tr = table.getElementsByTagName("tr");
			for (i = 0; i < tr.length; i++) 
	  		{
				if(i <= ileNaStrone)
				{
					tr[i].style.display = "";
				}
				else
				{
					tr[i].style.display = "none";
				}
	  		}
		}
	
		function wyswietlRekordy()
		{
			var ileNaStrone = 1;
			var ileStron = Math.ceil(${iloscRekordow}/ileNaStrone);
			var table, tr, td, i, txtValue;
		  	table = document.getElementById("potrawy");
		 	tr = table.getElementsByTagName("tr");
		 	var ktoryButton;
		 	var txt;
		 	window.onclick = e => 
		 	{
		 	    ktoryButton = e.srcElement.id
		 	    if(e.target.parentNode.id == "przyciski")
		 	    {
		 	    	for (i = 0; i < tr.length; i++) 
			  		{
				    	if(i == 0)
				    	{
				    		tr[i].style.display = "";
				    	}
				    	else if( (i >= (((ktoryButton - 1) * ileNaStrone) + 1)) & (i <= (((ktoryButton - 1) * ileNaStrone) + ileNaStrone)) ) //pierwszy rzad to naglowek, wiec wlasciwe rzedy zaczynaja sie od 1
					    {
				    		tr[i].style.display = "";
					    }
				        else 
				        {
				        	tr[i].style.display = "none";
				        }
				  	}
			 	    //widocznosc innych przyciskow
			 	    var ojciec = e.srcElement.parentNode;
			 	    var buttony = ojciec.children;
			 	    for(j = 0; j < buttony.length; j++)
			 	    {
			 	    	buttony[j].disabled = false;
			 	    	if(j > ileStron - 2 || j < 1)
			 	    	{
			 	    		buttony[j].style.display = "";
			 	    	}
			 	    	else if(j >= e.srcElement.id - 3 && j <= parseInt(e.srcElement.id) + 1)
		 	    		{
		 	    			buttony[j].style.display = "";
		 	    		}
			 	    	else
			 	    	{
			 	    		buttony[j].style.display = "none";
			 	    	}
		 	    	}
		 	   	 	e.srcElement.disabled = true;
				}
		 	}
		}
	
		$(document).ready(function() 
		{
		  	var table = document.getElementById("potrawy");
			stronicowanie();
			table.style.display = "";
		});
	
		function onClick(source) 
		{
			document.getElementById("wiekszyObrazek").src = source;
		}
	</script>
		
</body>
</html>