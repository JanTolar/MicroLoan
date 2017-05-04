<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="modal modal-custom fade" id="modal_loans_history" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title"><spring:message code="loans.review.modal.header" /></h4>
			</div>
			<div class="modal-body">
				<table id="reviewTable" class="table table-striped table-bordered table-hover">
					<thead>
						<tr class="bg-primary">
							<th><spring:message code="loans.review.modal.th.createDate" /></th>
							<th><spring:message code="loans.review.modal.th.amount" /></th>
							<th><spring:message code="loans.review.modal.th.interest" /></th>
							<th><spring:message code="loans.review.modal.th.term" /></th>
							<th><spring:message code="loans.review.modal.th.payBack" /></th>
							<th></th>
						</tr>
					</thead>
					<tbody id="reviewTableBody">
					</tbody>
				</table>
			</div><br />
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="loans.review.modal.btn.close" /></button>
			</div>
		</div>
	</div>
</div>