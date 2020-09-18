package org.infosystema.peakcoin.domain;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.infosystema.peakcoin.enums.CompanyStatus;
import org.infosystema.peakcoin.enums.FormType;


/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Entity
@Table(name="company", uniqueConstraints=@UniqueConstraint(columnNames="pin"))
public class Company extends AbstractEntity<Integer>  {
	private static final long serialVersionUID = 1L;
	private Person person;
	private String pin;
	private String companyName;
	private FormType formType;
	private Set<Dictionary> events;
	private String phone;
	private String email;
	private Attachment picture;
	private Attachment document;
	private String manager;
	private Attachment passport;
	private String instagram;
	private String facebook;
	private String website;
	private BigDecimal peakcoin;
	private CompanyStatus status;
	private Boolean requerement;

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name="form_type")
	public FormType getFormType() {
		return formType;
	}

	public void setFormType(FormType formType) {
		this.formType = formType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@ManyToOne
	@JoinColumn (name="picture_id")
	public Attachment getPicture() {
		return picture;
	}

	public void setPicture(Attachment picture) {
		this.picture = picture;
	}

	public String getInstagram() {
		return instagram;
	}

	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public BigDecimal getPeakcoin() {
		return peakcoin;
	}

	public void setPeakcoin(BigDecimal peakcoin) {
		this.peakcoin = peakcoin;
	}

	@Enumerated(EnumType.ORDINAL)
	public CompanyStatus getStatus() {
		return status;
	}

	public void setStatus(CompanyStatus status) {
		this.status = status;
	}

	@ManyToOne
	@JoinColumn (name="document_id")
	public Attachment getDocument() {
		return document;
	}

	public void setDocument(Attachment document) {
		this.document = document;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	@ManyToOne
	@JoinColumn (name="passport_id")
	public Attachment getPassport() {
		return passport;
	}

	public void setPassport(Attachment passport) {
		this.passport = passport;
	}

	@Column(name="company_name")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
	  name = "company_events", 
	  joinColumns = @JoinColumn(name = "company_id"), 
	  inverseJoinColumns = @JoinColumn(name = "dictionary_id"))	
	public Set<Dictionary> getEvents() {
		return events;
	}

	public void setEvents(Set<Dictionary> events) {
		this.events = events;
	}

	public Boolean getRequerement() {
		return requerement;
	}
	
	public void setRequerement(Boolean requerement) {
		this.requerement = requerement;
	}

	@ManyToOne
	@JoinColumn (name="person_id")
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
}