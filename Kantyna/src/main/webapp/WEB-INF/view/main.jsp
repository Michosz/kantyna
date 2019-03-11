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
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
		
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
	$(document).ready(function () 
	{
		if(${numerPotrawy} != 0)
		{
			document.getElementById("formularz" + ${numerPotrawy}).click();
		}
		if(${numerPotrawyKoszyk} != 0)
		{
			document.getElementById("formularzKoszyk" + ${numerPotrawyKoszyk}).click();
		}
		if(${numerPotrawyPromocja} != 0)
		{
			document.getElementById("formularzPromocja" + ${numerPotrawyPromocja}).click();
		}
	});
	
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
			document.getElementById("usun" + id).submit();
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
				document.getElementById("przywroc" + id).submit();
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
		<c:when test="${param.nowyRodzaj == '1'}">
			<div class="container alert alert-success mt-2 text-center" role="alert" style="width: 70%;"><b><s:message code="page.main.NowyRodzaj"/></b></div>
		</c:when>
		<c:when test="${param.UdanaRejestracja == '1'}">
			<div class="container alert alert-success mt-2 text-center" role="alert" style="width: 70%;"><b><s:message code="page.main.UdanaRejestracja"/></b></div>
		</c:when>
		<c:when test="${param.ParamZmienione == '1'}">
			<div class="container alert alert-success mt-2 text-center" role="alert" style="width: 70%;"><b><s:message code="page.main.ParamZmienione"/></b></div>
		</c:when>
		<c:when test="${param.wKoszyku == '1'}">
			<div class="container alert alert-success mt-2 text-center" role="alert" style="width: 70%;"><b><s:message code="page.main.wKoszyku"/></b></div>
		</c:when>
		<c:when test="${param.zamowienieZlozone == '1'}">
			<div class="container alert alert-success mt-2 text-center" role="alert" style="width: 70%;"><b><s:message code="page.main.ZamowienieZlozone"/></b></div>
		</c:when>
		<c:when test="${param.promocja == '1'}">
			<div class="container alert alert-success mt-2 text-center" role="alert" style="width: 70%;"><b><s:message code="page.main.PrzeniesioneNaPromocje"/></b></div>
		</c:when>
	</c:choose>
	
	<sec:authorize access="hasRole('MANAGER')">
		<c:if test="${noweZamowienia}">
			<div class="container alert alert-warning mt-2 text-center" role="alert" style="width: 70%;"><b><s:message code="page.main.NoweZamowienia"/></b></div>
		</c:if>
	</sec:authorize>
	
	<sec:authorize access="hasRole('KLIENT')">
		<c:if test="${przypomnienie}">
			<div class="container alert alert-warning mt-2 text-center" role="alert" style="width: 70%;"><b><s:message code="page.main.Przypomnienie"/></b></div>
		</c:if>
	</sec:authorize>
	
		<div class="form-group row" style="width: 100%;">
			<label for="rodzajPot" class="col-md-1 offset-md-4 col-form-label" style="text-align: right"><strong><s:message code="page.potrawa.Rodzaj"/></strong></label>
			<div class="col-3">
				<sf:select id="rodzaje" items="${lista}" path="lista" name="rodzajPot" placeholder="rodzaj" class="form-control"/>
			</div>
		</div>
		
		<div class="form-group row" style="width: 100%;">
			<label for="rodzajPot" class="col-md-1 offset-md-4 col-form-label" style="text-align: right"><strong><s:message code="page.main.Nazwa"/></strong></label>
			<div class="col-3">
				<input id="nazwaPotrawy" type="text" class="form-control" onkeydown="szukajPoNazwie(event)" name="nazwa" placeholder="nazwa"/>
			</div>
		</div>
		
		<table class="table table-bordered table-striped table-dark" id="potrawy" style="table-layout: fixed">
			<thead>
				<tr>
					<th style="width: 220px"><s:message code="page.main.Obrazek"/></th>
					<th><s:message code="page.main.Nazwa"/></th>
					<th><s:message code="page.main.CzyDostepna"/></th>
					<th><s:message code="page.main.Cena"/></th>
					<th><s:message code="page.main.CzyPromocja"/></th>
					<th><s:message code="page.main.Rodzaj"/></th>
					<th></th>
				</tr>
			</thead>
			<c:forEach items="${potrawy}" var="potrawa">
			<tr>
				<td>
					<img src="data:potrawa/jpeg;base64,${potrawa.base64}" width="200" height="200" title="<s:message code="page.main.ByPowiekszyc"/>" style="cursor: pointer" onclick="onClick('data:potrawa/jpeg;base64,${potrawa.base64}')" data-toggle="modal" data-target="#obrazek"/>
				</td>
				<td>
					<c:out value="${potrawa.nazwa}" />
				</td>
				<td>
					<c:if test="${potrawa.czyJestDostepna}">
						<s:message code="page.main.Tak"/>
					</c:if>
					<c:if test="${not potrawa.czyJestDostepna}">
						<s:message code="page.main.Nie"/>
					</c:if>
				</td>
				<td>
					<c:if test="${potrawa.czyPromocja}">
						<fmt:formatNumber type="number" minFractionDigits="2" value="${potrawa.cenaPromocyjna}"/>
					</c:if>
					<c:if test="${not potrawa.czyPromocja}">
						<fmt:formatNumber type="number" minFractionDigits="2" value="${potrawa.cena}"/>
					</c:if>
				</td>
				<td>
					<c:if test="${potrawa.czyPromocja}">
						<s:message code="page.main.Tak"/>
					</c:if>
					<c:if test="${not potrawa.czyPromocja}">
						<s:message code="page.main.Nie"/>
					</c:if>
				</td>
				<td>
					<c:out value="${potrawa.rodzajPotrawy.rodzaj}" />
				</td>
				<td>
					<sec:authorize access="hasRole('MANAGER')">
						<c:if test="${potrawa.czyJestDostepna}">
							<form:form id="usun${potrawa.id}" class="zmienStatus" action="usunZmenu?par=${potrawa.id}" method="POST" onsubmit="return usuwanie(${potrawa.id}) ? true : false;">	
								<button class="btn btn-danger" name=dodaj value="DodajA" style="width: 100%"><s:message code="page.main.UsunZmenu"/></button>
							</form:form>	
							<button id="formularzPromocja${potrawa.id}" type="button" class="btn btn-primary" style="width: 100%" data-toggle="modal" data-target="#promocja" onclick="pobierzIdPotrawyPromocja(${potrawa.id})">
								<s:message code="page.main.PrzeniesNaPromocje"/>
							</button>			
						</c:if>
					</sec:authorize>
					<sec:authorize access="hasRole('MANAGER')">
						<c:if test="${not potrawa.czyJestDostepna}">	
							<form:form id="przywroc${potrawa.id}" class="zmienStatus" action="usunZmenu?par=${potrawa.id}" method="POST" onsubmit="return przywracanie(${potrawa.id}) ? true : false;">	
								<button type="submit" class="btn btn-success" name=dodaj value="DodajA" style="width: 100%"><s:message code="page.main.PrzywrocDoMenu"/></button>
							</form:form>
						</c:if>
					</sec:authorize>
					<sec:authorize access="hasRole('KLIENT')">
						<button id="formularz${potrawa.id}" type="button" class="btn btn-primary" data-toggle="modal" style="width: 100%" data-target="#komentarz1" onclick="pobierzIdPotrawy(${potrawa.id})">
							<i class="far fa-comment-dots" style="font-size:22px"></i>  <s:message code="page.main.Skomentuj"/>
						</button>
						<c:if test="${potrawa.czyJestDostepna}">
							<button id="formularzKoszyk${potrawa.id}" type="button" class="btn btn-primary" data-toggle="modal" style="width: 100%" data-target="#koszyk1" onclick="pobierzIdPotrawyKoszyk(${potrawa.id})">
								<i class="fa fa-shopping-basket" style="font-size:22px"></i>  <s:message code="page.main.DodajDoKoszyka"/>
							</button>
						</c:if>
						<c:if test="${not potrawa.czyJestDostepna}">
							<button id="formularzKoszyk${potrawa.id}" disabled type="button" class="btn btn-primary" data-toggle="modal" style="width: 100%" data-target="#koszyk1" onclick="pobierzIdPotrawyKoszyk(${potrawa.id})">
								<i class="fa fa-shopping-basket" style="font-size:22px"></i>  <s:message code="page.main.DodajDoKoszyka"/>
							</button>
						</c:if>
					</sec:authorize>
					<c:choose>
						<c:when test="${empty potrawa.listaKomentarzy}">
							<button id="koment" type="button" class="btn btn-primary" style="width: 100%" disabled title="<s:message code="page.main.BrakKomentarzy"/>" data-toggle="modal" data-target="#komentarze" onclick="wyswietlKomentarze(${potrawa.id})">
								<s:message code="page.main.Komentarze2"/>
							</button>
						</c:when>
						<c:otherwise>
							<button id="koment" type="button" class="btn btn-primary" style="width: 100%" data-toggle="modal" data-target="#komentarze" onclick="wyswietlKomentarze(${potrawa.id})">
								<s:message code="page.main.Komentarze2"/>
							</button>
						</c:otherwise>
					</c:choose>
				</td>
				
				</tr>
			</c:forEach>
		</table>
		
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

	<div id="przyciski" style="align: center">
		
		</div>

	<script>
		function onClick(source) 
		{
			document.getElementById("wiekszyObrazek").src = source;
		}
	
		function stronicowanie(selected)
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
				td = tr[i].getElementsByTagName("td")[4];
				if(td)
				{
				    txtValue = td.textContent || td.innerText;
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
			 	    console.log(buttony.length);
			 	    for(j = 0; j < buttony.length; j++)
			 	    {
			 	    	console.log(buttony[j].id);
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
		
		function wyswietlKomentarze(id) 
		{
		  var table, tr, td, i, txtValue;
		  table = document.getElementById("komenty");
		  tr = table.getElementsByTagName("tr");
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
			stronicowanie("wszystkie");
			var url = new URL(window.location.href);
			var naz = url.searchParams.get("nazwa");
			document.getElementById("nazwaPotrawy").value = naz;
		    $("#rodzaje").change(function () 
		   	{
		    	txt = String($("#rodzaje").val());
		    	txt2 = String($('#nazwaPotrawy').val());
		    	window.location.replace('/?rodzaj=' + txt + '&nazwa=' + txt2);
		    });
		});
			
		function szukajPoNazwie(event)
		{
			stronicowanie("wszystkie");
			if(event.key == 'Enter') 
			{
				var url = new URL(window.location.href);
				var c = url.searchParams.get("nazwa");
				txt = String($("#rodzaje").val());
		    	txt2 = String($('#nazwaPotrawy').val());
		    	window.location.replace('/?rodzaj=' + txt + '&nazwa=' + txt2);
			}
		}
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
									<td style="width: 50%">
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
				function pobierzIdPotrawy(id) 
				{
					document.getElementById("numerPot").value = id;
				}
				
				function pobierzIdPotrawyKoszyk(id)
				{
					document.getElementById("numerPotKoszyk").value = id;
				}
				
				function pobierzIdPotrawyPromocja(id)
				{
					document.getElementById("numerPotPromocja").value = id;
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
					<form:form action="c" method="POST" modelAttribute="Komentarz">
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
		
			<div class="modal fade" id="koszyk1" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
			  <div class="modal-dialog" role="document">
			    <div class="modal-content">
			      <div class="modal-header">
			        <h5 class="modal-title" id="exampleModalLabel2"><s:message code="page.main.DodawanieDoKoszyka"/></h5>
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
			          <span aria-hidden="true">&times;</span>
			        </button>
			      </div>
			      <div class="modal-body">
					<form:form action="a" method="POST" modelAttribute="Koszyk">
					
						<div class="form-group row">
							<label for="Kom" class="col-3 col-form-label"><strong><s:message code="page.zamowienia.Ilosc"/>:</strong></label>
					   		<div class="col-9">
					       		<form:input type="number" path="ilosc" class="form-control" />
					       		<sf:errors path="ilosc" class="text-danger" />
							</div>
						</div>
						<div>
							<form:input id="numerPotKoszyk" type="number" path="idPotrawy" style="display: none"/>
						</div>
						<div align="center">
							<button type="submit" class="btn btn-primary" name=dodajK value="Kom"><s:message code="page.main.DodajDoKoszyka"/></button>
						</div>
					</form:form>
				</div>
			</div>
			</div>
		</div>
		
			<div class="modal fade" id="promocja" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
			  <div class="modal-dialog" role="document">
			    <div class="modal-content">
			      <div class="modal-header">
			        <h5 class="modal-title" id="exampleModalLabel2"><s:message code="page.main.PrzenoszenieNaPromocje"/></h5>
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
			          <span aria-hidden="true">&times;</span>
			        </button>
			      </div>
			      <div class="modal-body">
					<form:form action="b" method="POST" modelAttribute="Potrawa">
					
						<div class="form-group row">
							<label for="Kom" class="col-3 col-form-label"><strong><s:message code="page.main.CenaPromocyjna"/>:</strong></label>
					   		<div class="col-9">
					       		<form:input type="number" path="cenaPromocyjna" class="form-control" />
					       		<sf:errors path="cenaPromocyjna" class="text-danger" />
							</div>
						</div>
						<div>
							<form:input id="numerPotPromocja" type="number" path="id" style="display: none"/>
						</div>
						<div align="center">
							<button type="submit" class="btn btn-primary" name=dodajK value="Kom"><s:message code="page.main.PrzeniesNaPromocje"/></button>
						</div>
					</form:form>
				</div>
			</div>
			</div>
		</div>
</body>
</html>