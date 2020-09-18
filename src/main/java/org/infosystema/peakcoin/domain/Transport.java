package org.infosystema.peakcoin.domain;

import java.util.Date;
import java.util.HashSet;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.infosystema.peakcoin.domain.Attachment;
import org.infosystema.peakcoin.enums.TransportStatus;


/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Entity
@Table(name="transport", uniqueConstraints=@UniqueConstraint(columnNames="number"))
public class Transport extends AbstractEntity<Integer>  {
	private static final long serialVersionUID = 1L;
	private Dictionary type;
	private String number;
	private Boolean haveDriver;
	private Driver driver;
	private Dictionary mark;
	private String model;
	private Integer year;
	private Dictionary color;
	private Integer seatNumber;
	private Boolean gear;
	private Set<Dictionary> months;
	private Boolean wifi;
	private Boolean karaoke;
	private Boolean tv;
	private TransportStatus status;
	private Set<Attachment> attachments = new HashSet<>();
	private Boolean requerements;
	private Carpark carpark;
	private Company company;
	private Date dateCreated;

	@Enumerated(EnumType.ORDINAL)
	public TransportStatus getStatus() {
		return status;
	}

	public void setStatus(TransportStatus status) {
		this.status = status;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Column(name="seat_number")
	public Integer getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(Integer seatNumber) {
		this.seatNumber = seatNumber;
	}
	
	@OneToMany
	@JoinTable(name="transport_attachment", 
		joinColumns=@JoinColumn(name="transport_id"),
		inverseJoinColumns=@JoinColumn(name="attachment_id")
	)
    public Set<Attachment> getAttachments() {
		return attachments;
	}
    
    public void setAttachments(Set<Attachment> attachments) {
		this.attachments = attachments;
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
	@JoinColumn (name="color_id")
	public Dictionary getColor() {
		return color;
	}

	public void setColor(Dictionary color) {
		this.color = color;
	}

	@ManyToOne
	@JoinColumn (name="type_id")
	public Dictionary getType() {
		return type;
	}

	public void setType(Dictionary type) {
		this.type = type;
	}

	@Column(name="have_driver")
	public Boolean getHaveDriver() {
		return haveDriver;
	}

	public void setHaveDriver(Boolean haveDriver) {
		this.haveDriver = haveDriver;
	}

	@ManyToOne
	@JoinColumn (name="driver_id")
	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	@ManyToOne
	@JoinColumn (name="mark_id")
	public Dictionary getMark() {
		return mark;
	}

	public void setMark(Dictionary mark) {
		this.mark = mark;
	}

	@ManyToOne
	@JoinColumn (name="company_id")
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Boolean getGear() {
		return gear;
	}

	public void setGear(Boolean gear) {
		this.gear = gear;
	}

	public Boolean getWifi() {
		return wifi;
	}

	public void setWifi(Boolean wifi) {
		this.wifi = wifi;
	}

	public Boolean getKaraoke() {
		return karaoke;
	}

	public void setKaraoke(Boolean karaoke) {
		this.karaoke = karaoke;
	}

	public Boolean getTv() {
		return tv;
	}

	public void setTv(Boolean tv) {
		this.tv = tv;
	}

	public Boolean getRequerements() {
		return requerements;
	}

	public void setRequerements(Boolean requerements) {
		this.requerements = requerements;
	}

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
	  name = "transport_months", 
	  joinColumns = @JoinColumn(name = "transport_id"), 
	  inverseJoinColumns = @JoinColumn(name = "dictionary_id"))	
	public Set<Dictionary> getMonths() {
		return months;
	}

	public void setMonths(Set<Dictionary> months) {
		this.months = months;
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