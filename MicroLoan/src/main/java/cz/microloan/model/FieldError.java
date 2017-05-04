package cz.microloan.model;

import java.io.Serializable;

/**
 * @author Jan Tolar
 * 
 * Object represents result of validation error for one field
 */
public class FieldError implements Serializable {

	private static final long serialVersionUID = -4438908179751648574L;

	private String name;		// Field name
	private String status;		// Validation error message

	public FieldError(final String name, final String status) {
		this.name = name;
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

}
