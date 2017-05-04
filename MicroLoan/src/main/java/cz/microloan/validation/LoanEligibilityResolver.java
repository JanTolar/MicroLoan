package cz.microloan.validation;

import javax.servlet.http.HttpServletRequest;

import cz.microloan.model.LoanFormRequest;
import cz.microloan.model.LoanFormStateEnum;

/**
 * @author Jan Tolar
 * 
 * Interface for loan risk analysis
 */
public interface LoanEligibilityResolver {
	
	/**
	 * Resolve eligibility of loan request based on client data and his requirements
	 * @param LoanFormRequest loanForm
	 * @param HttpServletRequest servletReq
	 * @return LoanFormStateEnum status
	 */
	LoanFormStateEnum resolveLoanEligibility(LoanFormRequest loanForm, HttpServletRequest servletReq);

}
