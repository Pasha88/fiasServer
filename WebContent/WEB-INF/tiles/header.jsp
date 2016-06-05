<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
<a class="title" href="<c:url value='/'/>">ФИАС - Специально для Вас</a>


<sec:authorize access="!isAuthenticated()">
<a class="login" href="<c:url value='/login'/>">Вход</a>
</sec:authorize>


<sec:authorize access="isAuthenticated()">
 <a class="login" href="<c:url value='/j_spring_security_logout'/>">Выход</a>
</sec:authorize>

<b class="loginname">
<c:out value="${pageContext.request.remoteUser}"></c:out>
</b>


