<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

Поиск, введите наименование объекта:
<form name='f' id='theForm' action='${pageContext.request.contextPath}/'
	method='POST'>
	<!--   <input type="text" class="form-control" name='formalname' placeholder="search"/>-->

	<!--   <label class="col-xs-3 control-label">Language</label> -->
	<div>
		<div>
			<input type="text" class="form-control" name='formalname'
				placeholder="Введите адрес, через пробел. Например: Москва Арбат"
				id="search" />
		</div>
				<!--   
		<div class="responseullist">
			<div class="dropdown" id="supborsh">
				      <button id="mydef" class="btn dropdown-toggle" type="button" data-toggle="dropdown" onclick="doOn(this);">  

				<ul id="def" class="dropdown-menu"
					style="display: none; margin-top: 10px; margin-left: 100px;">
					<c:forEach var="address" items="${addresses}">
						<li><a id="HTML333" onclick="mydef(this);"> <c:out
									value="${address.formalname}"></c:out>
						</a></li>
					</c:forEach>
				</ul>
				<ul id="def1" class="dropdown-menu" style="display: none"></ul>
			</div>
		</div> -->


		<div style="margin: 10px, 10px, 10px, 10px">
			<a class="btn btn-primary" role="button" data-toggle="collapse"
				href="#collapseExample" aria-expanded="false"
				aria-controls="collapseExample"> Расширенный поиск </a>
		</div>
	</div>
	<div class="collapse" id="collapseExample">
		<div class="well">
			<table>
				<tr>
					<td><div class="input-group-addon">Конечное наименование
							объекта:</div></td>
					<td><input type="text" name='formalnameexact'
						class="form-control" /></td>
				</tr>
				<tr>
					<td><div class="input-group-addon">Код КЛАДР 4.0:</div></td>
					<td><input type="text" name='code' class="form-control" /></td>
				</tr>
				<tr>
					<td><div class="input-group-addon">Индекс:</div></td>
					<td><input type="text" name='postalcode' class="form-control" /></td>
				</tr>
				<tr>
					<td><div class="input-group-addon">Код ОКАТО:</div></td>
					<td><input type="text" name='okato' class="form-control" /></td>
				</tr>
				<tr>
					<td><div class="input-group-addon">Код ОКТМО:</div></td>
					<td><input type="text" name='oktmo' class="form-control" /></td>
				</tr>
				<tr>
					<td><div class="input-group-addon">Код Региона:</div></td>
					<td><input type="text" name='regioncode' class="form-control" /></td>
				</tr>
			</table>
		</div>
	</div>
	<div class="well">
		<input id="somebutton" name="submit" type="submit" value="Запрос"
			class="btn btn-info" />
		<!-- <button id="somebutton" name="submit" value="Запрос" class="btn btn-info">3apros</button>-->
	</div>
</form>
<h3>Запрошенные адреса:</h3>

<div class="responsetable">
	<table class="table table-bordered" id="addresstable">
		<tr>
			<td class="mytd">Наименование</td>
			<td class="mytd">Тип</td>
			<td class="mytd">Код КЛАДР 4.0</td>
			<td class="mytd">Индекс</td>
			<td class="mytd">OKATO</td>
			<td class="mytd">OKTMO</td>
			<td class="mytd">Статус</td>
		</tr>
		<c:forEach var="address" items="${addresses}">
			<tr>
				<td class="mytd"><c:out value="${address.formalname}"></c:out></td>
				<td class="mytd"><c:out value="${address.shortname}"></c:out></td>
				<td class="mytd"><c:out value="${address.code}"></c:out></td>
				<td class="mytd"><c:out value="${address.postalcode}"></c:out></td>
				<td class="mytd"><c:out value="${address.okato}"></c:out></td>
				<td class="mytd"><c:out value="${address.oktmo}"></c:out></td>
				<td class="mytd"><c:out value="${address.actstatus}"></c:out></td>
			</tr>
		</c:forEach>

	</table>
</div>


<div class="responsetable2">
	<table class="table table-bordered" id="addresstable2">
		<c:forEach var="myerror" items="${myerrors}">
			<tr>
				<td class="mytd"><c:out value="${myerror.name}"></c:out></td>
				<td class="mytd"><c:out value="${myerror.type}"></c:out></td>
			</tr>
		</c:forEach>

	</table>
</div>

<script type="text/javascript">
	//do it in Javascript


	//Make fun with jQuery
	var box = $('#def');
	var box2 = $('#def1');
	$(document).click(function() {
		box.hide();
		box2.show();
	});

	var frm = $('#theForm');
	var frm2 = $('#cancel');
	var inputLength;
	var inputLengthNext;
	var calc = 1;

	frm2.submit(function(ev) {
		$.ajax({
			type : frm2.attr('method'),
			url : frm2.attr('action'),
			data : frm2.serialize(),
			success : function(responseXml) { // Execute Ajax 

			}
		});

		ev.preventDefault();
	});

	frm.submit(function(ev) {
		calc++;
		inputLength = $("#search").val();

		console.log("1" + inputLength);
		console.log("2" + inputLengthNext);
		console.log("calc" + calc);

		console.log("soeks");
		$.ajax({
			type : frm.attr('method'),
			url : frm.attr('action'),
			data : frm.serialize(),
			success : function(responseXml) { // Execute Ajax 
				console.log("gogogo");
				$("#addresstable").html(
						$(responseXml).find("div.responsetable").html());
				$("#addresstable2").html(
						$(responseXml).find("div.responsetable2").html());
				//$("#supborsh").html(
				//	    $(responseXml).find("div.responseullist").html());
			},
			complete : function(data) {
				printWithAjax();
			}

		});

		ev.preventDefault();
	});

	function printWithAjax() {
		inputLengthNext = $("#search").val();
		if (inputLengthNext != inputLength) {
			$("#somebutton").click();
		}
	};
	$('#addresstable').on('click', 'tr', function () {
	    // Show current `tr` and hide others
	    $(this).show().siblings().hide();
	});
</script>

