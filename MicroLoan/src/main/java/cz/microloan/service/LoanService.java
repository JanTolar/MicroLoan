package cz.microloan.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cz.microloan.entity.Loan;
import cz.microloan.model.LoanDetailView;
import cz.microloan.model.LoanExtensionResponse;
import cz.microloan.model.LoanFormRequest;
import cz.microloan.model.LoanFormStateEnum;
import cz.microloan.model.LoanHistoryGrid;

/**
 * @author Jan Tolar
 *
 * Service layer interface for Loan Entity 
 */
public interface LoanService {

	/**
	 * Create and save Loan entity based on params
	 * @param LoanFormRequest loanForm
	 * @param HttpServletRequest servletReq
	 * @param LoanFormStateEnum eligibilityStatus
	 * @return generated String uuid
	 */
	String create(LoanFormRequest loanForm, HttpServletRequest servletReq, LoanFormStateEnum eligibilityStatus);

	/**
	 * Update the Loan
	 * @param Loan loan
	 */
	void update(Loan loan);

	/**
	 * Find Loan based on uuid
	 * @param String uuid
	 * @return found Loan
	 */
	Loan findByUuid(String uuid);

	/**
	 * Find all approved loan based on IP address from HttpServletRequest
	 * @param HttpServletRequest servletReq
	 * @return List of all found loans
	 */
	List<Loan> getAprovedLoansByIpAddress(HttpServletRequest servletReq);

	/**
	 * Recalculate extensionDays and interest attributes after loan extension request sent
	 * @param Loan loan
	 * @param int termExtension
	 * @return Loan with recalculated attributes
	 */
	Loan recalculateLoanAfterExtension(Loan loan, int termExtension);

	/**
	 * Count client loan request attempts
	 * @param HttpServletRequest servletReq
	 * @return count of client attempts
	 */
	int oneDayAttemptsCount(HttpServletRequest servletReq);

	/**
	 * Map Loan entity attributes to LoanDetailView object
	 * @param Loan loan
	 * @return LoanDetailView
	 */
	LoanDetailView mapEntityToDetailViewModel(Loan loan);

	/**
	 * Map Loan entity attributes to LoanHistoryGrid object
	 * @param List<Loan> loans
	 * @return LoanHistoryGrid
	 */
	LoanHistoryGrid mapEntitiesToReviewGridRows(List<Loan> loans);
	
	/**
	 * Map Loan entity attributes to LoanExtensionResponse object
	 * @param Loan loan
	 * @return LoanExtensionResponse
	 */
	LoanExtensionResponse mapEntityToExtensionResponse(Loan loan);

}
