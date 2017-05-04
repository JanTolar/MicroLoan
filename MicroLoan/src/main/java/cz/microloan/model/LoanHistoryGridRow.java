package cz.microloan.model;

import java.io.Serializable;

/**
 * @author Jan Tolar
 *
 * Object presents model of one row of history loan table
 */
public class LoanHistoryGridRow implements Serializable {

	private static final long serialVersionUID = 3859050821176939856L;

	private String uuid;
	private String createDate;
	private String amount;
	private String interest;
	private String term;
	private String extensionDays;
	private String totalDays;
	private String payBack;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(final String uuid) {
		this.uuid = uuid;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(final String createDate) {
		this.createDate = createDate;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(final String amount) {
		this.amount = amount;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(final String interest) {
		this.interest = interest;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(final String term) {
		this.term = term;
	}

	public String getExtensionDays() {
		return extensionDays;
	}

	public void setExtensionDays(final String extensionDays) {
		this.extensionDays = extensionDays;
	}

	public String getTotalDays() {
		return totalDays;
	}

	public void setTotalDays(final String totalDays) {
		this.totalDays = totalDays;
	}

	public String getPayBack() {
		return payBack;
	}

	public void setPayBack(final String payBack) {
		this.payBack = payBack;
	}

}
