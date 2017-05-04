package cz.microloan.dao;

import java.util.List;

import cz.microloan.entity.Loan;

/**
 * @author Jan Tolar
 * 
 * Interface for db operations with Loan entity
 */
public interface LoanDao extends AbstractDao<Loan> {

	/**
	 * @param String ipAddress
	 * @return List of all loans based on ipAddress param
	 */
	List<Loan> getListByIpAddress(String ipAddress);

	/**
	 * @param String ipAddress
	 * @return List of all approved loans based on ipAddress param
	 */
	List<Loan> getAprovedLoansByIpAddress(String ipAddress);

	/**
	 * @param String uuid
	 * @return Loan based on uuid param
	 */
	Loan findByUuid(String uuid);

}
