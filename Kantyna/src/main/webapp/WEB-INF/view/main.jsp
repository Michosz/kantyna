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
	
	<sec:authorize access="not isAuthenticated()">
		Hi Guest
	</sec:authorize>
	
	<sec:authorize access="isAuthenticated()">
		Hi User
	</sec:authorize>
	<img src="<c:url value='/images/du.jpg'/>"/>
	<table class="table table-bordered table-striped table-dark">
				<thead>
					<tr>
						<th><s:message code="page.main.Obrazek"/></th>
						<th><s:message code="page.main.Nazwa"/></th>
						<th><s:message code="page.main.Cena"/></th>
						<th><s:message code="page.main.Rodzaj"/></th>
					</tr>
				</thead>
				<c:forEach items = "${potrawy}" var="potrawa">
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
				</tr>
				</c:forEach>
			</table>

</body>

</html>