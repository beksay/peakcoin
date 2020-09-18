package org.infosystema.peakcoin.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@XmlRootElement(name="response")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class IdentifyResponse extends AbstractResponse {

	private static final long serialVersionUID = 5555164924695978781L;
	private String checksum;
	
	public IdentifyResponse() {}
	
	public IdentifyResponse(Integer code){
		super(code);
	}
	
	public IdentifyResponse(Integer code, String comment){
		super(code, comment);
	}

	public String getChecksum() {
		return checksum;
	}
	
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

}
