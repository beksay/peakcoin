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
public class URLResponse extends AbstractResponse {

	private static final long serialVersionUID = 5555164924695978781L;
	private String url;
	
	public URLResponse() {}
	
	public URLResponse(Integer code){
		super(code);
	}
	
	public URLResponse(Integer code, String comment){
		super(code, comment);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
