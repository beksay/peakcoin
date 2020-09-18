package org.infosystema.peakcoin.dto;

import java.util.Date;

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
public class InfoResponse extends AbstractResponse {

	private static final long serialVersionUID = 5555164924695978781L;
	private String id;
	private Date date;
	private String name;
	private String url;
	
	public InfoResponse() {}
	
	public InfoResponse(Integer code){
		super(code);
	}
	
	public InfoResponse(Integer code, String comment){
		super(code, comment);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}