package org.infosystema.peakcoin.dto.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@XmlRootElement(name = "response")
public class KicbResponse {

	private Integer type;
	private String protocolVersion;
	private String terminalId;
	private Integer resultCode;
	private Integer operatorId;
	private List<ItemExtra> extracts;
	private Integer statusId;
	private String transactionNumber;
	private String txnId;
	private ItemTo to;

	public KicbResponse() {
	}

	@XmlElement(name = "request-type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@XmlElement(name = "operator-id")
	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	@XmlElement(name = "transaction-number")
	public String getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	@XmlElement(name = "to")
	public ItemTo getTo() {
		return to;
	}

	public void setTo(ItemTo to) {
		this.to = to;
	}

	@XmlElement(name = "protocol-version")
	public String getProtocolVersion() {
		return protocolVersion;
	}

	public void setProtocolVersion(String protocolVersion) {
		this.protocolVersion = protocolVersion;
	}

	@XmlElement(name = "result-code")
	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	@XmlTransient
	public Integer getCorrectStatus() {
		if (resultCode == null)
			return null;
		return resultCode.intValue() == 251 ? 424 : resultCode;
	}

	@XmlElement(name = "extra")
	public List<ItemExtra> getExtracts() {
		return extracts;
	}

	public void setExtracts(List<ItemExtra> extracts) {
		this.extracts = extracts;
	}

	@XmlElement(name = "status-id")
	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	@XmlElement(name = "terminal-id")
	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	@XmlElement(name = "txn-id")
	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
}
