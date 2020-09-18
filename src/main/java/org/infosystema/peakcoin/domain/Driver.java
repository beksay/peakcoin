package org.infosystema.peakcoin.domain;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.infosystema.peakcoin.enums.DriverStatus;


/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Entity
@Table(name="driver", uniqueConstraints=@UniqueConstraint(columnNames="pin"))
public class Driver extends AbstractEntity<Integer>  {
	private static final long serialVersionUID = 1L;
	private String pin;
	private String name;
	private Country country;
	private Date birthDate;
	private Attachment passport;
	private Attachment license;
	private String phone;
	private Set<Dictionary> languages;
	private DriverStatus status;
	private Carpark carpark;
	private Company company;
	private Date dateCreated;

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne
	@JoinColumn (name="passport_id")
	public Attachment getPassport() {
		return passport;
	}
	
	public void setPassport(Attachment passport) {
		this.passport = passport;
	}
	
	@ManyToOne
	@JoinColumn (name="license_id")
	public Attachment getLicense() {
		return license;
	}
	
	public void setLicense(Attachment license) {
		this.license = license;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Enumerated(EnumType.ORDINAL)
	public DriverStatus getStatus() {
		return status;
	}

	public void setStatus(DriverStatus status) {
		this.status = status;
	}

	@ManyToOne
	@JoinColumn (name="carpark_id")
	public Carpark getCarpark() {
		return carpark;
	}

	public void setCarpark(Carpark carpark) {
		this.carpark = carpark;
	}

	@ManyToOne
	@JoinColumn (name="company_id")
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@ManyToOne
	@JoinColumn (name="country_id")
	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="birth_date")
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
	  name = "driver_languages", 
	  joinColumns = @JoinColumn(name = "driver_id"), 
	  inverseJoinColumns = @JoinColumn(name = "dictionary_id"))	
	public Set<Dictionary> getLanguages() {
		return languages;
	}

	public void setLanguages(Set<Dictionary> languages) {
		this.languages = languages;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_created")
	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
}