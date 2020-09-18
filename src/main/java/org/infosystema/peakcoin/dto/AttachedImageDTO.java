package org.infosystema.peakcoin.dto;

import java.io.Serializable;


public class AttachedImageDTO implements Serializable {
	
	private static final long serialVersionUID = -8007607921616171695L;
	private String url;
	private String fileName;
	
	public AttachedImageDTO() {}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
