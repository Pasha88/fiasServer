<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<h2>Создание аккаунта</h2>

<sf:form id="details" method="POST"
	action="${pageContext.request.contextPath}/createaccount"
	commandName="user">

	<table>
		<tr>
			<td>Пользователь:</td>
			<td><sf:input class="form-control" path="username" name="username"
					type="text" /><br />
				<div class="error">
					<sf:errors path="username"></sf:errors>
				</div></td>
		</tr>
		<tr>
			<td>Имя:</td>
			<td><sf:input class="form-control" path="name" name="name"
					type="text" /><br />
				<div class="error">
					<sf:errors path="name"></sf:errors>
				</div></td>
		</tr>
		<tr>
			<td>Емеил:</td>
			<td><sf:input class="form-control" path="email" name="email"
					type="text" />
				<div class="error">
					<sf:errors path="email"></sf:errors>
				</div></td>
		</tr>
		<tr>
			<td>Пароль:</td>
			<td><sf:input id="password" class="form-control" path="password"
					name="password" type="password" />
				<div class="error">
					<sf:errors path="password"></sf:errors>
				</div></td>
		</tr>
		<tr>
			<td>Подтвердите пароль:</td>
			<td><input id="confirmpass" class="form-control" name="confirmpass"
				type="password" />
				<div id="matchpass"></div></td>
		</tr>
		<tr>
		    <td><input class="btn btn-info" value="Создать аккаунт" type="submit" /></td>
			<td></td>
		</tr>
	</table>

</sf:form>
