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
	
	</style>
	
	<script>
		function zwalnianie(id)
		{
			swal({ 
			    title: '<s:message code="page.stoliki.ZwalnianieStolika"/>',   
			    text: '<s:message code="page.stoliki.PytanieZwalnianie"/>',   
			    type: "question",   
			    showCancelButton: true,   
			    confirmButtonColor: "#DD6B55",   
			    confirmButtonText: '<s:message code="page.main.Tak"/>',   
			    cancelButtonText: '<s:message code="page.main.Nie"/>',   
			    closeOnConfirm: false,   
			    closeOnCancel: false }).then((result) => {
				if(result.value)
				{
					var formularze = document.getElementsByClassName("zwolnijS");
					var formularz = formularze[id-1];  
					formularz.submit();
				}
				else
				{
					swal('<s:message code="page.stoliki.Anulowane"/>'/*, "success"*/);
					return false;
				}})
		}
	</script>
</head>

<body style="background-color:#d1e1bf">
	<%@include file="/WEB-INF/menu.incl" %>
	
	<c:choose>
		<c:when test="${param.zwolniony == '1'}">
			<div class="container alert alert-success mt-2 text-center" role="alert" style="width: 70%;"><b><s:message code="page.stoliki.StolikZwolniony"/></b></div>
		</c:when>
	</c:choose>
	
	<table class="table table-bordered table-striped table-dark" id="stoliki" style="table-layout: fixed; display: none">
		<thead>
			<tr>
				<th><s:message code="page.stoliki.Nazwa"/></th>
				<th><s:message code="page.stoliki.IloscMiejsc"/></th>
				<th><s:message code="page.stoliki.czyJestZajety"/></th>
				<sec:authorize access="hasRole('MANAGER')">
					<th></th>
				</sec:authorize>
			</tr>
		</thead>
		<c:forEach items="${listaStolikow}" var="stolik">
		<tr>
			<td>
				<c:out value="${stolik.nazwa}" />
			</td>
			<td>
				<c:out value="${stolik.iloscMiejsc}" />
			</td>
			<td>
				<c:choose>
					<c:when test="${stolik.czyJestZajety}">
						<s:message code="page.stoliki.Tak"/>
					</c:when>
					<c:otherwise>
						<s:message code="page.stoliki.Nie"/>
					</c:otherwise>
				</c:choose>
			</td>
			<sec:authorize access="hasRole('MANAGER')">
			<td>
				<c:if test="${stolik.czyJestZajety}">
					<form:form id="zwolnij" class="zwolnijS" action="zwolnij?par=${stolik.idStolika}" method="POST" onsubmit="return zwalnianie(${stolik.idStolika}) ? true : false;">	
						<button class="btn btn-primary" name=dodaj value="DodajA" style="white-space: normal; min-width: 100%;"><s:message code="page.stoliki.Zwolnij"/></button>
					</form:form>				
				</c:if>
				<c:if test="${not stolik.czyJestZajety}">
					<form:form id="zwolnij" class="zwolnijS" action="zwolnij?par=${stolik.idStolika}" method="POST" onsubmit="return zwalnianie(${stolik.idStolika}) ? true : false;">	
						<button class="btn btn-primary" name=dodaj value="DodajA" style="white-space: normal; min-width: 100%;" disabled><s:message code="page.stoliki.Zwolnij"/></button>
					</form:form>	
				</c:if>
			</td>
			</sec:authorize>
		</tr>
		</c:forEach>
	</table>
	
	<div id="przyciski" style="align: center">
	
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
				btn.style.width = '50px';
				btn.style.height = '50px';
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
		 	var table = document.getElementById("stoliki");
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
		  	table = document.getElementById("stoliki");
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
			var table = document.getElementById("stoliki");
			stronicowanie();
			table.style.display = "";
		});
	</script>
		
</body>
</html>