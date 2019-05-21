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
	
					<script src="https://checkout.stripe.com/checkout.js"></script>
				<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
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
		function zatwierdzanie(id)
		{
			swal({ 
		    title: '<s:message code="page.zamowienia.Zatwierdzanie"/>',   
		    text: '<s:message code="page.zamowienia.PytanieZatwierdzanie"/>',   
		    type: "question",   
		    showCancelButton: true,   
		    confirmButtonColor: "#DD6B55",   
		    confirmButtonText: '<s:message code="page.main.Tak"/>',   
		    cancelButtonText: '<s:message code="page.main.Nie"/>',   
		    closeOnConfirm: false,   
		    closeOnCancel: false }).then((result) => {
			if(result.value)
			{
				document.getElementById("zatwierdz" + id).submit();
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
		<c:when test="${not empty param.sukces}">
			<c:if test="${param.sukces == '1'}">
				<div class="container alert alert-success mt-2 text-center" role="alert" style="width: 70%;"><b><s:message code="page.zamowienia.Zaplacone"/></b></div>
			</c:if>
		</c:when>
		<c:when test="${not empty param.blad}">
			<c:if test="${param.blad == '1'}">
				<div class="container alert alert-danger mt-2 text-center" role="alert" style="width: 70%;"><b><s:message code="page.zamowienia.Blad"/></b></div>
			</c:if>
		</c:when>
	</c:choose>
	
	<c:if test="${param.zamZatwierdzone == '1'}">
		<div class="container alert alert-success mt-2 text-center" role="alert" style="width: 70%;"><b><s:message code="page.zamowienia.ZamZatwierdzone"/></b></div>
	</c:if>

	<table class="table table-bordered table-striped table-dark" id="zamowienia" style="table-layout: fixed; display: none">
		<thead>
			<tr>
			  	<sec:authorize access="hasRole('MANAGER')">
					<th><s:message code="page.zamowienia.Uzytkownik"/></th>
					<th><s:message code="page.Uzytkownicy.Email"/></th>
				</sec:authorize>
				<th><s:message code="page.zamowienia.DataZamowienia"/></th>
				<th><s:message code="page.zamowienie.Czas"/></th>
				<th><s:message code="page.zamowienia.Stolik"/></th>
				<th><s:message code="page.zamowienia.CzyOplacone"/></th>
				<th><s:message code="page.zamowienia.CzyZrealizowane"/></th>
				
				<th style="width: 140px"><s:message code="page.zamowienia.ZamowionePotrawy"/></th>
			</tr>
		</thead>
		<c:forEach items="${listaZamowien}" var="zamowienie">
		<tr>
			<sec:authorize access="hasRole('MANAGER')">
				<td>
					<c:out value="${zamowienie.uzytkownik.imie}"/> <c:out value="${zamowienie.uzytkownik.nazwisko}"/>
				</td>
				<td style="word-break: break-all">
					<c:out value="${zamowienie.uzytkownik.login}" />
				</td>
			</sec:authorize>
			<td>
				<c:out value="${zamowienie.dataZam}" />
			</td>
			<td>
				<c:out value="${zamowienie.czasRealizacji}" />
			</td>
			<td>
				<c:out value="${zamowienie.stolik.nazwa}" />
			</td>
			<td>
				<c:if test="${not zamowienie.czyZaplacone}">
				<s:message code="page.main.Nie"/>
				<sec:authorize access="hasRole('KLIENT')">
					<form name="checkoutform" action="/zaplac?par=${zamowienie.id}" method="POST" id="checkoutform${zamowienie.id}">
						<input type="hidden" name="stripeToken" id="stripeToken${zamowienie.id}" />
						<input type="hidden" name="stripeEmail" id="stripeEmail${zamowienie.id}" />
					</form>
					<button class="btn btn-primary" style="white-space: normal; min-width: 100%;" id="customButton${zamowienie.id}">
						<s:message code="page.zamowienia.Zaplac"/>
					</button>
						<script>
						    var handler = StripeCheckout.configure
						    ({
							    key: "${stripePublicKey}",
							    locale: "${localeCode}",
							    token: function(token) 
							    {
						    	   $("#stripeToken${zamowienie.id}").val(token.id);
						           $("#stripeEmail${zamowienie.id}").val(token.email);
							       $("#checkoutform${zamowienie.id}").submit();
						    	}
						    });

						    var click_event = document.getElementById("customButton${zamowienie.id}").addEventListener('click', function(e)
						    {
							    handler.open
							    ({
							        name: "Kantyna",
							        description: "<s:message code="page.zamowienia.Oplacanie"/>",
							        amount: "${zamowienie.cenaCalkowita}",
							        currency: "${currency}",
							        allowRememberMe: false,
							        email: "${uzytkownik}"
							    });
							    e.preventDefault();
						    });

				    	    $(window).load(function()
				    	    {
				    	    	if(${sessionScope.idZamowienia} != 0)
								{
						    		document.getElementById("customButton${sessionScope.idZamowienia}").click();
								}
				    		});
				    	    
					    	$(window).on('popstate', function() 
					    	{
					    		handler.close();
					    	});
						</script>
					</sec:authorize>
				</c:if>
				<c:if test="${zamowienie.czyZaplacone}">
					<s:message code="page.main.Tak"/>
				</c:if>
			</td>
			<td>
				<c:if test="${zamowienie.czyZrealizowane}">
					<s:message code="page.main.Tak"/>
					<sec:authorize access="hasRole('MANAGER')">
						<form:form id="zatwierdz${zamowienie.id}" class="zmienStatus" action="zatwierdzZamowienie?par=${zamowienie.id}" method="POST" onsubmit="return zatwierdzanie(${zamowienie.id}) ? true : false;">	
							<button class="btn btn-primary" disabled name=dodaj value="DodajA" style="white-space: normal; min-width: 100%;"><s:message code="page.zamowienia.Zrealizuj"/></button>
						</form:form>	
					</sec:authorize>	
				</c:if>
				<c:if test="${not zamowienie.czyZrealizowane}">
					<s:message code="page.main.Nie"/>
					<sec:authorize access="hasRole('MANAGER')">
						<form:form id="zatwierdz${zamowienie.id}" class="zmienStatus" action="zatwierdzZamowienie?par=${zamowienie.id}" method="POST" onsubmit="return zatwierdzanie(${zamowienie.id}) ? true : false;">	
							<button class="btn btn-primary" name=dodaj value="DodajA" style="white-space: normal; min-width: 100%;"><s:message code="page.zamowienia.Zrealizuj"/></button>
						</form:form>	
					</sec:authorize>
				</c:if>
			</td>
			<td>
				<button id="potr${zamowienie.id}" type="button" class="btn btn-primary" data-toggle="modal" style="white-space: normal; min-width: 100%;" data-target="#potrawy" onclick="wyswietlPotrawy(${zamowienie.id})">
					<s:message code="page.zamowienia.ZamowionePotrawy"/>
				</button>
			</td>
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
			 	var table = document.getElementById("zamowienia");
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
			  	table = document.getElementById("zamowienia");
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
				var table = document.getElementById("zamowienia");
				stronicowanie();
				table.style.display = "";		
			});
			
				function wyswietlPotrawy(id) 
				{
				  var table, tr, td, i, txtValue;
				  table = document.getElementById("dania");
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
		</script>
		
		<div class="modal fade" id="potrawy" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
			  <div class="modal-dialog modal-lg" role="document">
			    <div class="modal-content">
			      <div class="modal-header">
			        <h5 class="modal-title" id="exampleModalLabel"><s:message code="page.zamowienia.ZamowionePotrawy"/></h5>
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
			          <span aria-hidden="true">&times;</span>
			        </button>
			      </div>
			      <div class="modal-body">
			      	<table class="table table-bordered table-striped table-dark" id="dania">
			        	<thead>
						<tr>
							<th><s:message code="page.main.Obrazek"/></th>
							<th><s:message code="page.main.Nazwa"/></th>
							<th><s:message code="page.zamowienie.Ilosc"/></th>
							<th><s:message code="page.main.Rodzaj"/></th>
						</tr>
						</thead>
						<c:forEach items="${listaZamowien}" var="zamowienie">
				        	<c:forEach items="${zamowienie.potrawy_Zamowienia}" var="potrawa_Zamowienie">
								<tr>
									<td hidden>
										<c:out value="${zamowienie.id}"/>
									</td>
									<td>
										<img  src="data:potrawa/jpeg;base64,${potrawa_Zamowienie.potrawa.base64}" width="200" height="200" />
									</td>
									<td>
										<c:out value="${potrawa_Zamowienie.potrawa.nazwa}"/>
									</td>
									<td>
										<c:out value="${potrawa_Zamowienie.ilosc}" />
									</td>
									<td>
										<c:out value="${potrawa_Zamowienie.potrawa.rodzajPotrawy.rodzaj}" />
									</td>
								</tr>
							</c:forEach>
						</c:forEach>
					</table>
			     </div>
			 	</div>
				</div>
			</div>	
</body>
</html>