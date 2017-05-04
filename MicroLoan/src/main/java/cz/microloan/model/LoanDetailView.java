package cz.microloan.model;

import java.io.Serializable;

/**
 * @author Jan Tolar
 *
 * Object presents detail view model of loan 
 */
public class LoanDetailView implements Serializable {

	private static final long serialVersionUID = -3653615606981952598L;

	private String uuid;
	private String createDate;
	private String fullName;
	private String street;
	private String city;
	private String email;
	private String phone;
	private String amount;
	private String term;
	private String interest;
	private String payBack;
	private String extensionDays;
	private String totalDays;

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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(final String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(final String amount) {
		this.amount = amount;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(final String term) {
		this.term = term;
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

}
