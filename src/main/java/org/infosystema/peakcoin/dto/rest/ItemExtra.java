package org.infosystema.peakcoin.dto.rest;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@XmlRootElement(name="extra")
public class ItemExtra {
	
	private String name;
	private String value;
	
	@XmlAttribute
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlValue
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "InfocomExtra [name=" + name + ", value=" + value + "]";
	}

}
