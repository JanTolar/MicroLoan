package cz.microloan.model;

import java.io.Serializable;

/**
 * @author Jan Tolar
 *
 * Object presents part of loan detail model returned after loan extension request processed
 */
public class LoanExtensionResponse implements Serializable {

	private static final long serialVersionUID = 8470429209528093187L;

	private String extension;
	private String interest;
	private String payBack;
	private String totalTermDays;

	public String getExtension() {
		return extension;
	}

	public void setExtension(final String extension) {
		this.extension = extension;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(final String interest) {
		this.interest = interest;
	}

	public String getPayBack() {
		return payBack;
	}

	public void setPayBack(final String payBack) {
		this.payBack = payBack;
	}

	public String getTotalTermDays() {
		return totalTermDays;
	}

	public void setTotalTermDays(final String totalTermDays) {
		this.totalTermDays = totalTermDays;
	}

}
