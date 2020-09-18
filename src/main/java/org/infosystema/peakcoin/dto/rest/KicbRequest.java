package org.infosystema.peakcoin.dto.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@XmlRootElement(name="request")
public class KicbRequest {

	private Integer type;
	private String terminalId;
	private List<ItemExtra> extracts;
	private String transactionNumber;
	private ItemTo to;
	private ItemReceipt receipt;

	public KicbRequest() {}

	@XmlElement(name="request-type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@XmlElement(name="terminal-id")
	public String getTerminalId() {
		return terminalId;
	}
	
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	
	@XmlElement(name="extra")
	public List<ItemExtra> getExtracts() {
		return extracts;
	}
	
	public void setExtracts(List<ItemExtra> extracts) {
		this.extracts = extracts;
	}

	@XmlElement(name="transaction-number")
	public String getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	@XmlElement(name="to")
	public ItemTo getTo() {
		return to;
	}

	public void setTo(ItemTo to) {
		this.to = to;
	}

	@XmlElement(name="receipt")
	public ItemReceipt getReceipt() {
		return receipt;
	}

	public void setReceipt(ItemReceipt receipt) {
		this.receipt = receipt;
	}

}
