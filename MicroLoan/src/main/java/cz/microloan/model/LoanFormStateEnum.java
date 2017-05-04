package cz.microloan.model;

/**
 * @author Jan Tolar
 *
 * Object presents general status to loan request
 */
public enum LoanFormStateEnum {
	
	SUCCESS,
	FIELD_ERROR,
	BAD_TIME_AMOUNT_COMBINATION,
	TOO_MUCH_ATTEMPTS;

}
