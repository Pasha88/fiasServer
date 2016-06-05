<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h3>Запрошенные адреса:</h3>

<table class="table table-bordered">
	<tr>
		<td class="mytd">Наименование</td>
		<td class="mytd">Тип</td>
		<td class="mytd">Код КЛАДР 4.0</td>
		<td class="mytd">Индекс</td>
		<td class="mytd">OKTMO</td>
		<td class="mytd">ОКАТО</td>
		<td class="mytd">Статус</td>
	</tr>
	<c:forEach var="address" items="${addresses}">
		<tr>
			<td class="mytd"><c:out value="${address.formalname}"></c:out></td>
			<td class="mytd"><c:out value="${address.shortname}"></c:out></td>
			<td class="mytd"><c:out value="${address.code}"></c:out></td>
			<td class="mytd"><c:out value="${address.postalcode}"></c:out></td>
			<td class="mytd"><c:out value="${address.oktmo}"></c:out></td>
			<td class="mytd"><c:out value="${address.okato}"></c:out></td>
			<td class="mytd"><c:out value="${address.actstatus}"></c:out></td>
		</tr>
	</c:forEach>

</table>
