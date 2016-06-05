<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<h3>Админпанель</h3>

<div id="container">
	<div style="float: left; margin-left: 10px">
		<form name='w' id="theUpdate"
			action='${pageContext.request.contextPath}/adminloadupdate'
			method='POST'>

			<input name="updatedatabase" id="loaddatabase" type="submit"
				class="btn btn-info" value="Загрузить обновления" />
		</form>
	</div>

	<div style="float: left; margin-left: 10px">
		<form name='h' id="theFullUpdate"
			action='${pageContext.request.contextPath}/adminloadfullupdate'
			method='POST'>
			<input name="loadfulldatabase" id="loadfulldatabase" type="submit"
				class="btn btn-info" value="Перезагрузить всю базу" />
		</form>
	</div>
	
	<div style="float: left; margin-left: 10px">
		<form name='z' id="getConfig"
			action='${pageContext.request.contextPath}/getconfig'
			method='GET'>
			<input name="pushConfig" id="pushConfig" type="submit" style="display:none;"
				class="btn btn-info" value="Получить конфиг" />
		</form>
	</div>
</div>

<div style="margin-left: 10px; margin-bottom: 10px; clear: both;">
	<p>&nbsp</p>
	<div style="margin-top: 10px">
		<form name='f' id='theForm' id="updateAddress"
			action='${pageContext.request.contextPath}/changeaddress'
			method='GET'>
			<div>
				<input type="text" class="form-control" name='httpaddress'
					placeholder="Изменить адрес обновления, например: http://fias.nalog.ru/Public/Downloads/Actual/"
					id="newaddress" /> <input id="somebutton" name="submit"
					type="submit" value="Подтвердить адрес" class="btn btn-info"
					style="margin-top: 10px" />
			</div>
		</form>
	</div>

	<form name='fa' id='theForm' id="refreshTime"
		action='${pageContext.request.contextPath}/refreshtime' method='GET'>
		<div class="well">
			<table>
				<tr>
					<td><div class="input-group-addon">Периодичность времени
							обновления, введите минуты:</div></td>
					<td><input type="text" id='updatetime' name='updatetime' class="form-control"
						placeholder="Например: 3600"  onkeypress='return event.charCode >= 48 && event.charCode <= 57'/></td>
					<td><input id="somebutton" name="submit" type="submit"
						value="Подтвердить время" class="btn btn-info" /></td>
				</tr>
			</table>
		</div>
	</form>
	<p></p>


	<div class="databaseupdatetable" id="dbconfig">
		<table class="table table-bordered" id="addresstable2">
			<c:forEach var="databaseconfig" items="${databaseconfig}">
						    <tr>
					<td>Статус Загрузки:</td>
					<td class="mytd" id="updatestatusconfig"><c:out
							value="${databaseconfig.updatestatus}"></c:out></td>
				</tr>
				<tr>
					<td>Дата последнего обновления базы:</td>
					<td class="mytd"><c:out
							value="${databaseconfig.databasedeltaupdate}"></c:out></td>
				</tr>
				<tr>
					<td>Полная загрузка всей базы (0-Нет, 1-Да):</td>
					<td class="mytd"><c:out
							value="${databaseconfig.databasefullupdate}"></c:out></td>
				</tr>
				<tr>
					<td>Адрес сервера обновления базы ФИАС:</td>
					<td class="mytd"><c:out value="${databaseconfig.httpaddress}"></c:out></td>
				</tr>
				<tr>
					<td>Переодичность запроса обновления в минутах:</td>
					<td class="mytd"><c:out
							value="${databaseconfig.checkupdatetime}"></c:out></td>

				</tr>
			</c:forEach>

		</table>
	</div>
</div>

<script type="text/javascript">

var frm5 = $('#theUpdate');
var frm7 = $('#theFullUpdate');
var frm9 = $('#getConfig');

frm5.submit(function(ev) {
    $("#loaddatabase").prop('disabled',true);
	var refreshIntervalId = setInterval(function(){ 
		   $("#pushConfig").click();
		},1000);
	console.log("soeks2");
	$.ajax({
		type : frm5.attr('method'),
		url : frm5.attr('action'),
		data : frm5.serialize(),
		success : function(responseXml) { // Execute Ajax 
			console.log("gogogo");
		},
		complete : function(data) {
			$("#loaddatabase").prop('disabled',false);
			clearInterval(refreshIntervalId);
			$("#updatestatusconfig").text("Обновление завершено");			  
			printWithAjax();
		}

	});

	ev.preventDefault();
});


function printWithAjax() {
	console.log("finish");
};

frm7.submit(function(ev) {
	$("#loadfulldatabase").prop('disabled',true);
// 	var refreshIntervalId2 = setInterval(function(){ 
// 		   $("#pushConfig").click();
// 		},1000);
	console.log("soeks3");
	$.ajax({
		type : frm7.attr('method'),
		url : frm7.attr('action'),
		data : frm7.serialize(),
		success : function(responseXml) { // Execute Ajax 
			console.log("gogogo");
		},
		complete : function(data) {
			$("#loadfulldatabase").prop('disabled',false);
			$("#updatestatusconfig").text("Полная загрузка завершена");		
			clearInterval(refreshIntervalId2);
			printWithAjax2();
		}

	});
	$(location).attr('href', window.location.href);
	ev.preventDefault();
});

function printWithAjax2() {
	console.log("finish");
};


frm9.submit(function(ev) {

	$.ajax({
		type : frm9.attr('method'),
		url : frm9.attr('action'),
		data : frm9.serialize(),
		success : function(responseXml) { // Execute Ajax 
			console.log("Response Config");
			$("#addresstable2").html(
					$(responseXml).find("div.databaseupdatetable").html());
		},
		complete : function(data) {
			printWithAjax3();
		}

	});

	ev.preventDefault();
});

function printWithAjax3() {
	console.log("Config Finish");
};

$(document).ready(function() {
    $("#updatetime").keydown(function (e) {
        // Allow: backspace, delete, tab, escape, enter and .
        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
             // Allow: Ctrl+A
            (e.keyCode == 65 && e.ctrlKey === true) ||
             // Allow: Ctrl+C
            (e.keyCode == 67 && e.ctrlKey === true) ||
             // Allow: Ctrl+X
            (e.keyCode == 88 && e.ctrlKey === true) ||
             // Allow: home, end, left, right
            (e.keyCode >= 35 && e.keyCode <= 39)) {
                 // let it happen, don't do anything
                 return;
        }
        // Sure that it is a number and stop the keypress
           if ((e.shiftKey || ((e.keyCode < 48) || (e.keyCode > 57))) && ((e.keyCode < 96) || (e.keyCode > 105))) {
            e.preventDefault();
        }
    });
});

</script>


