package org.infosystema.peakcoin.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;


/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Entity
@Table(name="person", uniqueConstraints=@UniqueConstraint(columnNames="nickname"))
public class Person extends AbstractEntity<Integer>  {
	private static final long serialVersionUID = 1L;
	private String firstname;
	private String lastname;
	private String patronymic;
	private String nickname;
	private Country country;
	private City city;
	private Attachment picture;
	private BigDecimal peakcoin;
	private String phone;
	private String account;
	private Boolean requirement;
	private Date birthDate;
	private String yourself;
	private String phoneExtra;
	private Boolean hasCompany;
	private Carpark carpark;
	private Boolean hasGuide;
	private Cook cook;
	
	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	@Transient
	public String getFullname() {
		StringBuffer buffer = new StringBuffer();
		
		if(lastname != null) buffer.append(lastname + " ");
		if(firstname != null) buffer.append(firstname + " ");
		
		return buffer.toString();
	}

	public String getPatronymic() {
		return patronymic;
	}

	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}

	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@ManyToOne
	@JoinColumn (name="country_id")
	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@ManyToOne
	@JoinColumn (name="picture_id")
	public Attachment getPicture() {
		return picture;
	}

	public void setPicture(Attachment picture) {
		this.picture = picture;
	}

	public BigDecimal getPeakcoin() {
		return peakcoin;
	}

	public void setPeakcoin(BigDecimal peakcoin) {
		this.peakcoin = peakcoin;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getAccount() {
		return account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}

	public Boolean getRequirement() {
		return requirement;
	}

	public void setRequirement(Boolean requirement) {
		this.requirement = requirement;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="birth_date")
	public Date getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getYourself() {
		return yourself;
	}

	public void setYourself(String yourself) {
		this.yourself = yourself;
	}

	@Column(name="phone_extra")
	public String getPhoneExtra() {
		return phoneExtra;
	}

	public void setPhoneExtra(String phoneExtra) {
		this.phoneExtra = phoneExtra;
	}

	@ManyToOne
	@JoinColumn (name="city_id")
	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
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
	@JoinColumn (name="cook_id")
	public Cook getCook() {
		return cook;
	}

	public void setCook(Cook cook) {
		this.cook = cook;
	}

	@Column(name="has_company")
	public Boolean getHasCompany() {
		return hasCompany;
	}

	public void setHasCompany(Boolean hasCompany) {
		this.hasCompany = hasCompany;
	}

	@Column(name="has_guide")
	public Boolean getHasGuide() {
		return hasGuide;
	}

	public void setHasGuide(Boolean hasGuide) {
		this.hasGuide = hasGuide;
	}
}