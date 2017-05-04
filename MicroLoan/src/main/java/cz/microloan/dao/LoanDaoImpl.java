package cz.microloan.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.microloan.entity.Loan;

/**
 * @author Jan Toalr
 * 
 * Repository layer for db operations with Loan entity
 * Extends AbstractDaoImpl for CRUD operations
 */
@Repository("loanDao")
@Transactional(readOnly = true)
public class LoanDaoImpl extends AbstractDaoImpl<Loan> implements LoanDao {

	/* (non-Javadoc)
	 * @see cz.microloan.dao.LoanDao#getListByIpAddress(java.lang.String)
	 */
	@Override
	public List<Loan> getListByIpAddress(final String ipAddress) {
		if (ipAddress == null) {
			return null;
		}
		final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Loan> crit = criteriaBuilder.createQuery(Loan.class);
		final Root<Loan> loans = crit.from(Loan.class);

		crit.select(loans).where(criteriaBuilder.equal(loans.get("ipAddress"), ipAddress));
		
		final List<Loan> results = findByCriteria(crit);
		return results;
	}

	/* (non-Javadoc)
	 * @see cz.microloan.dao.LoanDao#getAprovedLoansByIpAddress(java.lang.String)
	 */
	@Override
	public List<Loan> getAprovedLoansByIpAddress(final String ipAddress) {
		if (ipAddress == null) {
			return null;
		}
		final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Loan> crit = criteriaBuilder.createQuery(Loan.class);
		final Root<Loan> loans = crit.from(Loan.class);

		crit.select(loans).where(criteriaBuilder.equal(loans.get("ipAddress"), ipAddress));
		
		crit.select(loans).where(criteriaBuilder.and
				(criteriaBuilder.equal(loans.get("ipAddress"), ipAddress)),
				(criteriaBuilder.equal(loans.get("approved"), Boolean.TRUE)));
		
		final List<Loan> results = findByCriteria(crit);

		return results;
	}
	
	/* (non-Javadoc)
	 * @see cz.microloan.dao.LoanDao#findByUuid(java.lang.String)
	 */
	@Override
	public Loan findByUuid(final String uuid) {
		if (uuid == null) {
			return null;
		}
		final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Loan> crit = criteriaBuilder.createQuery(Loan.class);
		final Root<Loan> loans = crit.from(Loan.class);

		crit.select(loans).where(criteriaBuilder.equal(loans.get("uuid"), uuid));
		
		final List<Loan> results = findByCriteria(crit);
		if (results.size() != 0) {
			return results.get(0);
		}
		return null;
	}
	
}
