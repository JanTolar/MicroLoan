package cz.microloan.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jan Tolar
 *
 * Object presents response to loan request
 */
public class LoanFormResponse implements Serializable {

	private static final long serialVersionUID = 7099971738011441840L;

	private LoanFormStateEnum status;			// General enum status to loan request
	private List<FieldError> fieldErrors = new ArrayList<FieldError>();
	private String createdUuid;

	public LoanFormStateEnum getStatus() {
		return status;
	}

	public void setStatus(final LoanFormStateEnum status) {
		this.status = status;
	}

	public List<FieldError> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(final List<FieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	public String getCreatedUuid() {
		return createdUuid;
	}

	public void setCreatedUuid(final String createdUuid) {
		this.createdUuid = createdUuid;
	}

}
