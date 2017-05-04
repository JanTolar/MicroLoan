package cz.microloan.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Jan Tolar
 *
 * Object presents loan form
 */
public class LoanFormRequest implements Serializable {

	private static final long serialVersionUID = 3385758136163972861L;

	private String fullName;
	private String street;
	private String city;
	private String email;
	private String phone;
	private BigDecimal amount;
	private int term;

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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(final BigDecimal amount) {
		this.amount = amount;
	}

	public int getTerm() {
		return term;
	}

	public void setTerm(final int term) {
		this.term = term;
	}

}
