package cz.microloan.validation;

import java.util.List;

import cz.microloan.model.FieldError;
import cz.microloan.model.LoanFormRequest;

/**
 * @author Jan Tolar
 *
 * Interface for form field validation
 */
public interface LoanFormValidator {
	
	/**
	 * Check form fields and return list of FieldError object 
	 * @param LoanFormRequest loanForm
	 * @return List<FieldError>
	 */
	public List<FieldError> validateLoanFormRequest(LoanFormRequest loanForm);

}
