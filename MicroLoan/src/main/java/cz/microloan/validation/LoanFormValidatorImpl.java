package cz.microloan.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import cz.microloan.model.FieldError;
import cz.microloan.model.LoanFormRequest;

/**
 * @author Jan Tolar
 *
 * Validation component for form field check
 */
@Component
public class LoanFormValidatorImpl implements LoanFormValidator {
	
	@Autowired
	MessageSource messagesSource;

	private final Locale locale = LocaleContextHolder.getLocale();
	
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String ONLY_NUMBER_PATTERN = "[0-9]+";
	
	private static final String EMPTY_MSG_CODE = "form.error.empty";

	/* (non-Javadoc)
	 * @see cz.microloan.validation.LoanFormValidator#validateLoanFormRequest(cz.microloan.model.LoanFormRequest)
	 */
	@Override
	public List<FieldError> validateLoanFormRequest(final LoanFormRequest loanForm) {
		final List<FieldError> errors = new ArrayList<FieldError>();
		
		if (StringUtils.isBlank(loanForm.getFullName())) {
			errors.add(new FieldError("fullName", messagesSource.getMessage(EMPTY_MSG_CODE, null, locale)));
		}
		
		if (StringUtils.isBlank(loanForm.getStreet())) {
			errors.add(new FieldError("street", messagesSource.getMessage(EMPTY_MSG_CODE, null, locale)));
		}
		
		if (StringUtils.isBlank(loanForm.getCity())) {
			errors.add(new FieldError("city", messagesSource.getMessage(EMPTY_MSG_CODE, null, locale)));
		}
		
		if (StringUtils.isBlank(loanForm.getEmail())) {
			errors.add(new FieldError("email", messagesSource.getMessage(EMPTY_MSG_CODE, null, locale)));
		} else if (validateRegex(loanForm.getEmail(), EMAIL_PATTERN)) {
			errors.add(new FieldError("email", messagesSource.getMessage("form.error.format.email", null, locale)));
		}

		if (StringUtils.isBlank(loanForm.getPhone())) {
			errors.add(new FieldError("phone", messagesSource.getMessage(EMPTY_MSG_CODE, null, locale)));
		} else if (validateRegex(loanForm.getPhone(), ONLY_NUMBER_PATTERN)) {
			errors.add(new FieldError("phone", messagesSource.getMessage("form.error.format.phone", null, locale)));
		}

		return errors;
	}
	
	/**
	 * Check validity based on value and regex template
	 * @param String value
	 * @param String template
	 * @return boolean
	 */
	private boolean validateRegex(final String value, final String template) {
		final Pattern pattern = Pattern.compile(template);
		final Matcher matcher = pattern.matcher(StringUtils.trim(value));
		if (!matcher.matches()) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

}
