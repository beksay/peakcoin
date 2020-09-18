package org.infosystema.peakcoin.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.infosystema.peakcoin.enums.CarparkStatus;
import org.infosystema.peakcoin.enums.DocumentType;
import org.infosystema.peakcoin.enums.FormType;


/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Entity
@Table(name="carpark", uniqueConstraints=@UniqueConstraint(columnNames="pin"))
public class Carpark extends AbstractEntity<Integer>  {
	private static final long serialVersionUID = 1L;
	private String pin;
	private FormType formType;
	private Country country;
	private DocumentType documentType;
	private Attachment document;
	private BigDecimal peakcoin;
	private CarparkStatus status;

	@ManyToOne
	@JoinColumn (name="country_id")
	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	@ManyToOne
	@JoinColumn (name="document_id")
	public Attachment getDocument() {
		return document;
	}
	
	public void setDocument(Attachment document) {
		this.document = document;
	}

	public BigDecimal getPeakcoin() {
		return peakcoin;
	}

	public void setPeakcoin(BigDecimal peakcoin) {
		this.peakcoin = peakcoin;
	}

	@Enumerated(EnumType.ORDINAL)
	public CarparkStatus getStatus() {
		return status;
	}

	public void setStatus(CarparkStatus status) {
		this.status = status;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name="document_type")
	public DocumentType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name="form_type")
	public FormType getFormType() {
		return formType;
	}

	public void setFormType(FormType formType) {
		this.formType = formType;
	}

}