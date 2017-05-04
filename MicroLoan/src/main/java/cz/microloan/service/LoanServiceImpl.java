package cz.microloan.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.microloan.component.IPAddressHelper;
import cz.microloan.component.SystemPropertyResolver;
import cz.microloan.dao.LoanDao;
import cz.microloan.entity.Loan;
import cz.microloan.model.LoanDetailView;
import cz.microloan.model.LoanExtensionResponse;
import cz.microloan.model.LoanFormRequest;
import cz.microloan.model.LoanFormStateEnum;
import cz.microloan.model.LoanHistoryGrid;
import cz.microloan.model.LoanHistoryGridRow;

/**
 * @author Jan Tolar
 *
 * Service layer implementation for Loan Entity 
 */
@Service
public class LoanServiceImpl implements LoanService {
	
	@Autowired
	LoanDao loanDao;
	
	@Autowired
	IPAddressHelper iPAddressHelper;
	
	@Autowired
	SystemPropertyResolver systemPropertyResolver;
	
	/* (non-Javadoc)
	 * @see cz.microloan.service.LoanService#create(cz.microloan.model.LoanFormRequest, javax.servlet.http.HttpServletRequest, cz.microloan.model.LoanFormStateEnum)
	 */
	@Override
	public String create(
			final LoanFormRequest loanForm, 
			final HttpServletRequest servletReq,
			final LoanFormStateEnum eligibilityStatus) {
		
		String createdUuid = "";
		if (LoanFormStateEnum.SUCCESS.equals(eligibilityStatus)) {
			createdUuid = UUID.randomUUID().toString();
			loanDao.create(mapLoanFormToEntity(loanForm, servletReq, Boolean.TRUE, createdUuid));
		} else {
			loanDao.create(mapLoanFormToEntity(loanForm, servletReq, Boolean.FALSE, createdUuid));
		}
		
		return createdUuid;
	}

	/* (non-Javadoc)
	 * @see cz.microloan.service.LoanService#update(cz.microloan.entity.Loan)
	 */
	@Override
	public void update(final Loan loan) {
		loanDao.update(loan);
	}
	
	/* (non-Javadoc)
	 * @see cz.microloan.service.LoanService#findByUuid(java.lang.String)
	 */
	@Override
	public Loan findByUuid(final String uuid) {
		return loanDao.findByUuid(uuid);
	}

	/* (non-Javadoc)
	 * @see cz.microloan.service.LoanService#getAprovedLoansByIpAddress(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public List<Loan> getAprovedLoansByIpAddress(final HttpServletRequest servletReq) {
		return loanDao.getAprovedLoansByIpAddress(iPAddressHelper.extractIpAddress(servletReq));
	}
	
	/* (non-Javadoc)
	 * @see cz.microloan.service.LoanService#oneDayAttemptsCount(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public int oneDayAttemptsCount(final HttpServletRequest servletReq) {
		
		final List<Loan> loans = loanDao.getListByIpAddress(iPAddressHelper.extractIpAddress(servletReq));

		final Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -1);
		final Date oneDayAgo = cal.getTime();
		
		int attemptsCount = 0;
		for (final Loan loan : loans) {
			if (loan.getCreateDate().after(oneDayAgo)) {
				attemptsCount++;
			}
		}		
		return attemptsCount;
	}
	
	/* (non-Javadoc)
	 * @see cz.microloan.service.LoanService#recalculateLoanAfterExtension(cz.microloan.entity.Loan, int)
	 */
	@Override
	public Loan recalculateLoanAfterExtension(final Loan loan, final int termExtension) {
		
		final int extension = loan.getExtensionDays() + termExtension;
		final int extensionInWeeks = extension / 7;		// Number of extension weeks. Neede for recalculate interest

		final BigDecimal interestExtensionFactor = systemPropertyResolver.getBigDecimalProperty("interest.extension.factor.Number");
		BigDecimal newInterest = systemPropertyResolver.getBigDecimalProperty("interest.amount.Number");

		for (int i = 0; i < extensionInWeeks; i++) {
			newInterest = newInterest.multiply(interestExtensionFactor);
		}
		
		loan.setExtensionDays(extension);
		loan.setInterest(newInterest);
				
		return loan;
	}
	
	/* (non-Javadoc)
	 * @see cz.microloan.service.LoanService#mapEntityToDetailViewModel(cz.microloan.entity.Loan)
	 */
	@Override
	public LoanDetailView mapEntityToDetailViewModel(final Loan loan) {
		
		final LoanDetailView detailView = new LoanDetailView();
		
		final SimpleDateFormat sdf = new SimpleDateFormat("d.M.yyyy HH:mm");
		
		final BigDecimal amount = loan.getAmount();
		final BigDecimal interest = loan.getInterest();

		detailView.setUuid(loan.getUuid());
		detailView.setCreateDate(sdf.format(loan.getCreateDate()));
		detailView.setFullName(loan.getFullName());
		detailView.setStreet(loan.getStreet());
		detailView.setCity(loan.getCity());
		detailView.setEmail(loan.getEmail());
		detailView.setPhone(loan.getPhone());
		detailView.setAmount(amount.toString());
		detailView.setTerm(loan.getTerm().toString());
		detailView.setExtensionDays(String.valueOf(loan.getExtensionDays()));
		detailView.setTotalDays(String.valueOf(loan.getTerm() + loan.getExtensionDays()));
		detailView.setInterest(interest.toString());
		
		detailView.setPayBack(calculatePayBack(amount, interest).toString());

		return detailView;
	}
	
	/* (non-Javadoc)
	 * @see cz.microloan.service.LoanService#mapEntitiesToReviewGridRows(java.util.List)
	 */
	@Override
	public LoanHistoryGrid mapEntitiesToReviewGridRows(final List<Loan> loans) {
		
		final LoanHistoryGrid grid = new LoanHistoryGrid();
		final List<LoanHistoryGridRow> gridRows = new ArrayList<LoanHistoryGridRow>();
		
		final SimpleDateFormat sdf = new SimpleDateFormat("d.M.yyyy");
		for (final Loan loan : loans) {
			final LoanHistoryGridRow row = new LoanHistoryGridRow();
			
			final BigDecimal amount = loan.getAmount();
			final BigDecimal interest = loan.getInterest();
			
			row.setUuid(loan.getUuid());
			row.setCreateDate(sdf.format(loan.getCreateDate()));
			row.setAmount(amount.toString());
			row.setInterest(interest.toString());
			row.setTerm(loan.getTerm().toString());
			row.setExtensionDays(String.valueOf(loan.getExtensionDays()));
			row.setTotalDays(String.valueOf(loan.getTerm() + loan.getExtensionDays()));
			row.setPayBack(calculatePayBack(amount, interest).toString());
			
			gridRows.add(row);
		}
		grid.setGridRows(gridRows);
		
		return grid;
	}
	
	/* (non-Javadoc)
	 * @see cz.microloan.service.LoanService#mapEntityToExtensionResponse(cz.microloan.entity.Loan)
	 */
	@Override
	public LoanExtensionResponse mapEntityToExtensionResponse(final Loan loan) {
		
		final LoanExtensionResponse response = new LoanExtensionResponse();
		
		final BigDecimal amount = loan.getAmount();
		final BigDecimal interest = loan.getInterest().setScale(2, BigDecimal.ROUND_HALF_UP);
		
		response.setExtension(String.valueOf(loan.getExtensionDays()));
		response.setTotalTermDays(String.valueOf(loan.getTerm() + loan.getExtensionDays()));
		response.setInterest(interest.toString());
		
		response.setPayBack(calculatePayBack(amount, interest).toString());

		return response;
	}
	
	/**
	 * Calculate pay back based on amount of loan and interest
	 * @param BigDecimal amount
	 * @param BigDecimal interest
	 * @return BigDecimal payBack
	 */
	private BigDecimal calculatePayBack(final BigDecimal amount, final BigDecimal interest) {
		
		final BigDecimal interestSum = amount.multiply(interest).divide(new BigDecimal(100));
		final BigDecimal payBack = amount.add(interestSum).setScale(2, BigDecimal.ROUND_HALF_UP);
		
		return payBack;
	}
	
	/**
	 * Make Loan entity based on parameters
	 * @param LoanFormRequest loanForm
	 * @param HttpServletRequest servletReq
	 * @param boolean isApproved
	 * @param String createdUuid
	 * @return Loan remaped
	 */
	private Loan mapLoanFormToEntity(
			final LoanFormRequest loanForm, 
			final HttpServletRequest servletReq, 
			final boolean isApproved,
			final String createdUuid) {
		
		final Loan loan = new Loan();
		
		loan.setIpAddress(iPAddressHelper.extractIpAddress(servletReq));
		loan.setFullName(loanForm.getFullName());
		loan.setStreet(loanForm.getStreet());
		loan.setCity(loanForm.getCity());
		loan.setEmail(loanForm.getEmail());
		loan.setPhone(loanForm.getPhone());
		loan.setAmount(loanForm.getAmount());
		loan.setTerm(loanForm.getTerm());
		loan.setApproved(isApproved);
		loan.setInterest(systemPropertyResolver.getBigDecimalProperty("interest.amount.Number"));
		loan.setUuid(createdUuid);
		loan.setCreateDate(new Date());

		return loan;
	}
	
}
