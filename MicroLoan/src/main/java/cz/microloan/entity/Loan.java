package cz.microloan.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Jan Tolar
 */
@Entity
@Table(name = "LOAN_TABLE")
public class Loan implements Serializable {

	private static final long serialVersionUID = 8540345918145530690L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "IP_ADDRESS")
	private String ipAddress;

	@Column(name = "NAME")
	private String fullName;

	@Column(name = "STREET")
	private String street;

	@Column(name = "CITY")
	private String city;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "AMOUNT")
	private BigDecimal amount;

	@Column(name = "TERM")
	private Integer term;

	@Column(name = "INTEREST")
	private BigDecimal interest;

	@Column(name = "APPROVED")
	private boolean approved;

	@Column(name = "UUID")
	private String uuid;

	@Column(name = "CREATE_DATE")
	private Date createDate;

	@Column(name = "EXTENSION_DAYS")
	private int extensionDays = 0;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(final String ipAddress) {
		this.ipAddress = ipAddress;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(final BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(final BigDecimal interest) {
		this.interest = interest;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(final Integer term) {
		this.term = term;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(final boolean approved) {
		this.approved = approved;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(final String uuid) {
		this.uuid = uuid;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(final Date createDate) {
		this.createDate = createDate;
	}

	public int getExtensionDays() {
		return extensionDays;
	}

	public void setExtensionDays(final int extensionDays) {
		this.extensionDays = extensionDays;
	}

}
