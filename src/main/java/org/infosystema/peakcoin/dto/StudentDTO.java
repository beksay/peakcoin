package org.infosystema.peakcoin.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class StudentDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String pin;
	private String firstName;
	private String lastName;
	private String middleName;
	private Integer dictId;
	private String dictName;
	private BigDecimal mark;
	private Integer markId;
	
	public StudentDTO(Integer id,String pin,String firstName,String lastName,String middleName,Integer dictId,String dictName,BigDecimal mark,Integer markId) {
		super();
		this.id = id;
		this.pin=pin;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.dictId = dictId;
		this.dictName = dictName;
		this.mark = mark;
		this.setMarkId(markId);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getPin() {
		return pin;
	}
	
	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public Integer getDictId() {
		return dictId;
	}

	public void setDictId(Integer dictId) {
		this.dictId = dictId;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public BigDecimal getMark() {
		return mark;
	}

	public void setMark(BigDecimal mark) {
		this.mark = mark;
	}

	public Integer getMarkId() {
		return markId;
	}

	public void setMarkId(Integer markId) {
		this.markId = markId;
	}
	
	
	
	
}
