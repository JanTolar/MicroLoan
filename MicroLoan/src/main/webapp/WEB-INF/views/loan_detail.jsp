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

<script type="text/javascript">
$(document).ready(function() {
	if ( "${detailModel.extensionDays}" > 0 ) {
		$('#extensionRow').removeClass('hidden');
	}
} );

function switchExtensionFormVisibility() {
	if ( $('#termExtensionBtn').hasClass('hidden') ) {
		$('#termExtensionSelect').addClass('hidden');
		$('#termExtensionBtn').removeClass('hidden');
	} else {
		$('#termExtensionBtn').addClass('hidden');
		$('#termExtensionSelect').removeClass('hidden');
	}
}

function sendLoanExtensionRequest() {
	$.ajax({
		dataType : "JSON",
		contentType: "application/json; charset=utf-8",
		url : 'processLoanExtensionRequest',
		type: "POST",
		data: getFormJSON('termExtensionForm'),
		success: function(data) {
			$('#extensionDays').html(data.extension);
			$('#payBackRow').html(data.payBack);
			$('#interestRow').html(data.interest);
			$('#totalDays').html(data.totalTermDays);
			
			$('#extensionRow').removeClass('hidden');

			switchExtensionFormVisibility();
		},
		error: function (jqXHR, textStatus, errorThrown) {
			alert('<spring:message code="alert.general.ajax" />');
		}
	});
}
</script>
<style type="text/css">
.termExtension {
	margin-left: 40px;
}
</style>
</head>
<body>
<div id="mainWrapper">
	<div class="loan-container">
		<div class="loan-inner">
			<h2><spring:message code="loan.detail.header" /></h2>
			<div class="loan-form">
				<form action="#" id="termExtensionForm" class="form-horizontal">
					<input type="hidden" name="uuid" value="${detailModel.uuid}">
					<table class="table" style="width:100%;">
						<tr>
							<td width="40%"><strong><spring:message code="loan.detail.createDate" /></strong></td>
							<td><c:out value="${detailModel.createDate}" /></td>
						</tr>
						<tr>
							<td><strong><spring:message code="loan.detail.fullName" /></strong></td>
							<td><c:out value="${detailModel.fullName}" /></td>
						</tr>
						<tr>
							<td><strong><spring:message code="loan.detail.street" /></strong></td>
							<td><c:out value="${detailModel.street}" /></td>
						</tr>
						<tr>
							<td><strong><spring:message code="loan.detail.city" /></strong></td>
							<td><c:out value="${detailModel.city}" /></td>
						</tr>
						<tr>
							<td><strong><spring:message code="loan.detail.email" /></strong></td>
							<td><c:out value="${detailModel.email}" /></td>
						</tr>
						<tr>
							<td><strong><spring:message code="loan.detail.phone" /></strong></td>
							<td><c:out value="${detailModel.phone}" /></td>
						</tr>
						<tr>
							<td><strong><spring:message code="loan.detail.amount" /></strong></td>
							<td><c:out value="${detailModel.amount}" /> <spring:message code="measuring.unit.currency" /></td>
						</tr>
						<tr>
							<td><strong><spring:message code="loan.detail.term" /></strong></td>
							<td>
								<c:out value="${detailModel.term}" /> <spring:message code="measuring.unit.days" />
								<button type="button" id="termExtensionBtn" onclick="switchExtensionFormVisibility()" class="btn btn-sm btn-default termExtension">
									<spring:message code="loans.term.extension.link.extend" />
								</button>
								
								<span id="termExtensionSelect" class="hidden">
									<select name="termExtension" class="selectpicker input-sm termExtension" data-width="50px">
										<c:forEach begin="${minimumExtensionDays}" end="${maximumExtensionDays}" varStatus="loop">
											<option value="${loop.current}"><c:out value="${loop.current}"/> 
												<spring:message code="measuring.unit.days" />
											</option>
										</c:forEach>
									</select>
									<button type="button" id="btnSave" onclick="sendLoanExtensionRequest()" class="btn btn-sm btn-default">
										<spring:message code="loans.term.extension.btn.ok" />
									</button>
									<button type="button" id="btnCancel" onclick="switchExtensionFormVisibility()" class="btn btn-sm btn-default">
										<spring:message code="loans.term.extension.btn.cancel" />
									</button>
								</span>
							</td>
						</tr>
						<tr id="extensionRow" class="hidden">
							<td><strong><spring:message code="loan.detail.extensionDays" /></strong></td>
							<td>
								<span id="extensionDays"><c:out value="${detailModel.extensionDays}" /></span> 
								<spring:message code="measuring.unit.days" /> (
								<spring:message code="loan.detail.totalDays" /> 
								<span id="totalDays"><c:out value="${detailModel.totalDays}" /></span> 
								<spring:message code="measuring.unit.days" /> )
							</td>
						</tr>
						<tr>
							<td><strong><spring:message code="loan.detail.payBack" /></strong></td>
							<td>
								<span id="payBackRow"><c:out value="${detailModel.payBack}" /></span> 
								<spring:message code="measuring.unit.currency" />
							</td>
						</tr>
						<tr>
							<td><strong><spring:message code="loan.detail.interest" /></strong></td>
							<td>
								<span id="interestRow"><c:out value="${detailModel.interest}" /></span>%</td>
						</tr>
						<tr>
						<td></td><td></td></tr>	<!-- Blank row for last row underline -->
					</table>
				</form>
			</div>
			<a href="loan_form"><spring:message code="loan.showHistory" /></a><br /><br />
		</div>
	</div>
</div>

<div class="ajaxLoadingGif" ></div>

</body>
</html>