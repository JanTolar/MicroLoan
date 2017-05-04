package cz.microloan.validation;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.microloan.component.IPAddressHelper;
import cz.microloan.component.SystemPropertyResolver;
import cz.microloan.model.LoanFormRequest;
import cz.microloan.model.LoanFormStateEnum;
import cz.microloan.service.LoanService;

/**
 * @author Jan Tolar
 *
 * Component for loan risk analysis
 */
@Component
public class LoanEligibilityResolverImpl implements LoanEligibilityResolver {
	
	@Autowired
	LoanService loanService;
	
	@Autowired
	IPAddressHelper iPAddressHelper;
	
	@Autowired
	SystemPropertyResolver systemPropertyResolver;

	/* (non-Javadoc)
	 * @see cz.microloan.validation.LoanEligibilityResolver#resolveLoanEligibility(cz.microloan.model.LoanFormRequest, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public LoanFormStateEnum resolveLoanEligibility(final LoanFormRequest loanForm,	final HttpServletRequest servletReq) {
		
		LoanFormStateEnum responseState = checkMaxAttempts(servletReq);
		
		if (LoanFormStateEnum.SUCCESS.equals(responseState)) {
			responseState = checkTimeAmountCombination(loanForm);
		}
				
		return responseState;
	}
	
	/**
	 * @param servletReq
	 * @return
	 */
	private LoanFormStateEnum checkMaxAttempts(final HttpServletRequest servletReq) {
		final int maxAttempts = systemPropertyResolver.getIntProperty("max.attempts.Number");
		final int oneDayAttemptsCount = loanService.oneDayAttemptsCount(servletReq);
		
		if (oneDayAttemptsCount >= maxAttempts) {
		
			return LoanFormStateEnum.TOO_MUCH_ATTEMPTS;
			
		}
		return LoanFormStateEnum.SUCCESS;
	}
	
	/**
	 * Check loan eligibility based on combination of request time and amount
	 * @param LoanFormRequest loanForm
	 * @return LoanFormStateEnum status
	 */
	private LoanFormStateEnum checkTimeAmountCombination(final LoanFormRequest loanForm) {
		final BigDecimal maxAmountProp = systemPropertyResolver.getBigDecimalProperty("max.amount.Number");
		
		if (maxAmountProp.compareTo(loanForm.getAmount()) == 1) {
			final Calendar cal = Calendar.getInstance();
			final int actualHour = cal.get(Calendar.HOUR_OF_DAY);
			if (actualHour >= 0 && actualHour < 6) {
				
				return LoanFormStateEnum.BAD_TIME_AMOUNT_COMBINATION;
				
			}
		}		
		return LoanFormStateEnum.SUCCESS;
	}

}
