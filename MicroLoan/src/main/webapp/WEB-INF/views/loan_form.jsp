<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
	<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link id="link" rel="stylesheet" href="css/stylesheet.css" type="text/css" />
 	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
 	<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/general.js"></script>

<script type="text/javascript" class="init">
$(document).ready(function() {
	$('#loanForm')[0].reset();
	$('.form-group').removeClass('has-error');
	$('.help-block').empty();
} );

function sendLoanRequest() {
	$('#loanStatus').empty();
	$('#loanStatus').hide();
	$('#btnSave').text('<spring:message code="form.btn.submit" />');
	$('#btnSave').attr('disabled',true);
	$('.form-group').removeClass('has-error');
	$('.help-block').empty();

	$.ajax({
		dataType : "JSON",
		contentType: "application/json; charset=utf-8",
		url : 'processLoanRequest',
		type: "POST",
		data: getFormJSON('loanForm'),
		success: function(data) {
			if (data.status == 'SUCCESS') {
				$('#loanStatus').append('<div class="alert alert-success">' +
											'<spring:message code="form.response.status.SUCCESS" /><br />' +
											'<a href="loan_detail?uuid=' + data.createdUuid + '">' +
												' <spring:message code="form.response.detail.link" />' +
											'</a>' +
										'</div>');
				$('#loanForm')[0].reset();
				$('#loanStatus').show();
			}
			else if (data.status == 'FIELD_ERROR') {
				for (var i = 0; i < data.fieldErrors.length; i++) {
					$('[name="' + data.fieldErrors[i].name + '"]').parent().parent().addClass('has-error');
					$('[name="' + data.fieldErrors[i].name + '"]').next().text(data.fieldErrors[i].status);
				}
			}
			else {
				switch (data.status) {
				case 'TOO_MUCH_ATTEMPTS':
					$('#loanStatus').append('<div class="alert alert-danger"><spring:message code="form.response.status.TOO_MUCH_ATTEMPTS" /></div>');
					break;
				case 'BAD_TIME_AMOUNT_COMBINATION':
					$('#loanStatus').append('<div class="alert alert-danger"><spring:message code="form.response.status.BAD_TIME_AMOUNT_COMBINATION" /></div>');
					break;
				default:
					break;
				}
				$('#loanStatus').show();
			}
			$('#btnSave').text('<spring:message code="form.btn.submit" />');
			$('#btnSave').attr('disabled',false);
		},
		error: function (jqXHR, textStatus, errorThrown) {
			$('#btnSave').text('<spring:message code="form.btn.submit" />');
			$('#btnSave').attr('disabled',false);
			alert('<spring:message code="alert.general.ajax" />');
		}
	});
}

function openHistoryModal(e) {
	e.preventDefault();
	$.ajax({
		url : 'loadLoansHistory',
		type: "GET",
		dataType: "JSON",
		success: function(data) {
			var rows = data.gridRows;
			var html = '';
			for (var i=0; i < rows.length; i++) {
				html += '<tr>' +
   							'<td>' + rows[i].createDate + '</td>' +
   							'<td>' + rows[i].amount + ' <spring:message code="measuring.unit.currency" /></td>' +
   							'<td>' + rows[i].interest + '%</td>' +
   							getTermRow(rows[i]) +
   							'<td>' + rows[i].payBack + ' <spring:message code="measuring.unit.currency" /></td>' +
   							'<td><a href="loan_detail?uuid=' + rows[i].uuid + '"><spring:message code="loans.review.modal.th.datailLink" /></td>' +
						'</tr>';
				getTermRow(rows[i]);	
			}

			$('#reviewTableBody').empty().append(html);
			$('#modal_loans_history').modal('show');
		},
		error: function (jqXHR, textStatus, errorThrown) {
			alert('<spring:message code="alert.general.ajax" />');
		}
	});
}

function getTermRow(rowData) {
	var row ='';
	if (rowData.extensionDays == 0) {
		return '<td>' + rowData.term + ' <spring:message code="measuring.unit.days" /></td>';
	} else {
		return '<td>' +
					rowData.term + ' <spring:message code="measuring.unit.days" />' +
					' (<spring:message code="loans.review.modal.th.extensionDays" /> ' + rowData.extensionDays + ' <spring:message code="measuring.unit.days" />, ' +
					'<spring:message code="loans.review.modal.th.totalDays" /> ' + rowData.totalDays + ' <spring:message code="measuring.unit.days" />)' +
				'</td>';
	}
}
</script>
</head>
<body>
<div id="mainWrapper">
	<div class="loan-container">
		<div class="loan-inner">
			<h2><spring:message code="main.header" /></h2>
			<div class="loan-form">
			<div id="loanStatus"></div>
				<form action="#" id="loanForm" class="form-horizontal">
					<div class="form-body">
						<div class="form-group input-sm">
							<label class="control-label col-md-4"><spring:message code="form.fullname.label" /></label>
							<div class="col-md-8">
								<input name="fullName" class="form-control" type="text">
								<span class="help-block"></span>
							</div>
						</div>
						<div class="form-group input-sm">
							<label class="control-label col-md-4"><spring:message code="form.street.label" /></label>
							<div class="col-md-8">
								<input name="street" class="form-control" type="text">
								<span class="help-block"></span>
							</div>
						</div>
						<div class="form-group input-sm">
							<label class="control-label col-md-4"><spring:message code="form.city.label" /></label>
							<div class="col-md-8">
								<input name="city" class="form-control" type="text">
								<span class="help-block"></span>
							</div>
						</div>
						<div class="form-group input-sm">
							<label class="control-label col-md-4"><spring:message code="form.email.label" /></label>
							<div class="col-md-8">
								<input name="email" class="form-control" type="text">
								<span class="help-block"></span>
							</div>
						</div>
						<div class="form-group input-sm">
							<label class="control-label col-md-4"><spring:message code="form.phone.label" /></label>
							<div class="col-md-8">
								<input name="phone" class="form-control" type="text">
								<span class="help-block"></span>
							</div>
						</div>
						<div class="form-group input-sm">
							<label class="control-label col-md-4"><spring:message code="form.amount.label" /></label>
							<div class="col-md-8">
								<select name="amount" class="form-control">
									<c:forEach items="${amountSelectValues}" var="amountValue">
										<option value="${amountValue}"><c:out value="${amountValue}"/> <spring:message code="measuring.unit.currency" /></option>
									</c:forEach>
								</select>
								<span class="help-block"></span>
							</div>
						</div>
						<div class="form-group input-sm">
							<label class="control-label col-md-4"><spring:message code="form.term.label" /></label>
							<div class="col-md-8">
								<select name="term" class="form-control">
									<c:forEach begin="${minimumTermDays}" end="${maximumTermDays}" varStatus="loop">
										<option value="${loop.current}"><c:out value="${loop.current}"/> <spring:message code="measuring.unit.days" /></option>
									</c:forEach>
								</select>
								<span class="help-block"></span>
							</div>
						</div>
						<br />
						<div class="form-actions text-center">
							<button type="button" id="btnSave" onclick="sendLoanRequest()" class="btn btn-primary btn-default">
								<spring:message code="form.btn.submit" />
							</button>
						</div>
					</div>
				</form>
			</div>
			<a href="#" onclick="openHistoryModal(event);"><spring:message code="loan.showHistory" /></a><br /><br />
		</div>
	</div>
</div>

<jsp:include page="loans_review_modal.jsp"></jsp:include>

<div class="ajaxLoadingGif" ></div>

</body>
</html>