package org.infosystema.peakcoin.dto.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.infosystema.peakcoin.adapter.AmountAdapter;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@XmlRootElement(name = "to")
public class ItemTo {

	private String accountNumber;
	private Double amount;
	private String serviceId;

	@XmlElement(name = "account-number")
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	@XmlJavaTypeAdapter(AmountAdapter.class)
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@XmlElement(name = "service-id")
	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	@Override
	public String toString() {
		return "InfocomTo [accountNumber=" + accountNumber + ", amount=" + amount + ", serviceId=" + serviceId + "]";
	}

}
