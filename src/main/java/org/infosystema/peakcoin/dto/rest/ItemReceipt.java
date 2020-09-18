package org.infosystema.peakcoin.dto.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@XmlRootElement(name="receipt")
public class ItemReceipt {
	
	private String receiptNumber;
	private String dateTime;
	
	@XmlElement(name="receipt-number")
	public String getReceiptNumber() {
		return receiptNumber;
	}
	
	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}
	
	@XmlElement(name="datetime")
	public String getDateTime() {
		return dateTime;
	}
	
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

}
