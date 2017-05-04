package cz.microloan.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cz.microloan.component.SystemPropertyResolver;
import cz.microloan.entity.Loan;
import cz.microloan.model.FieldError;
import cz.microloan.model.LoanExtensionRequest;
import cz.microloan.model.LoanExtensionResponse;
import cz.microloan.model.LoanFormRequest;
import cz.microloan.model.LoanFormResponse;
import cz.microloan.model.LoanFormStateEnum;
import cz.microloan.model.LoanHistoryGrid;
import cz.microloan.service.LoanService;
import cz.microloan.validation.LoanEligibilityResolver;
import cz.microloan.validation.LoanFormValidator;

/**
 * @author Jan Tolar
 */
@RestController
public class LoanApi {
	
	@Autowired
	LoanService loanService;
	
	@Autowired
	LoanFormValidator validator;
	
	@Autowired
	LoanEligibilityResolver eligibilityResolver;
	
	@Autowired
	SystemPropertyResolver systemPropertyResolver;

	/**
	 * Process request for loan form sent
	 * @param LoanFormRequest loanForm contains client personal data and loan claim
	 * @param HttpServletRequest servletReq
	 * @return LoanFormResponse contains general status of validation results and set behavior for frontend page
	 */
	@RequestMapping(value = "/processLoanRequest", method = RequestMethod.POST)
	public LoanFormResponse processLoanRequest(
			@RequestBody final LoanFormRequest loanForm,
			final HttpServletRequest servletReq) {

		final LoanFormResponse response = new LoanFormResponse();
		final List<FieldError> fieldErrors = validator.validateLoanFormRequest(loanForm);
		if (fieldErrors.isEmpty()) {
			final LoanFormStateEnum eligibilityStatus = eligibilityResolver.resolveLoanEligibility(loanForm, servletReq);
			
			final String createdLoanUuid = loanService.create(loanForm, servletReq, eligibilityStatus);
			
			response.setCreatedUuid(createdLoanUuid);
			response.setStatus(eligibilityStatus);

		} else {
			response.setFieldErrors(fieldErrors);
			response.setStatus(LoanFormStateEnum.FIELD_ERROR);
		}

		return response;
	}

	/**
	 * Process request for loan term extension
	 * @param LoanExtensionRequest extensionFormReq contains days requirement and loan uuid identifier
	 * @param HttpServletRequest servletReq
	 * @return LoanExtensionResponse contains updated loan data
	 */
	@RequestMapping(value = "/processLoanExtensionRequest", method = RequestMethod.POST)
	public LoanExtensionResponse processLoanExtensionRequest(
			@RequestBody final LoanExtensionRequest extensionFormReq,
			final HttpServletRequest servletReq) {

		LoanExtensionResponse response = new LoanExtensionResponse();
		Loan loan = loanService.findByUuid(extensionFormReq.getUuid());
		
		loan = loanService.recalculateLoanAfterExtension(loan, extensionFormReq.getTermExtension());
		loanService.update(loan);
		
		response = loanService.mapEntityToExtensionResponse(loan);

		return response;
	}

	/**
	 * @param HttpServletRequest servletReq
	 * @return LoanHistoryGrid contains data for construct table
	 */
	@RequestMapping(value = "/loadLoansHistory", method = RequestMethod.GET)
	public LoanHistoryGrid loadLoansHistory(final HttpServletRequest servletReq) {

		final List<Loan> loans = loanService.getAprovedLoansByIpAddress(servletReq);
		final LoanHistoryGrid responseGrid = loanService.mapEntitiesToReviewGridRows(loans);
		
		return responseGrid;
	}

}
