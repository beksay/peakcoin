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

import org.infosystema.peakcoin.enums.OrderStatus;


/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Entity
@Table(name="orders")
public class Orders extends AbstractEntity<Integer>  {
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private String program;
	private Company company;
	private BigDecimal peakcoin;
	private Integer quantity;
	private Attachment picture;
	private Date date;
	private Date dateCreated;
	private OrderStatus status;
	private OrderType type;
	

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(length=2000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne
	@JoinColumn (name="company_id")
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	@ManyToOne
	@JoinColumn (name="type_id")
	public OrderType getType() {
		return type;
	}

	public void setType(OrderType type) {
		this.type = type;
	}

	@Column(length=50000)
	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_created")
	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}