package org.infosystema.peakcoin.dto;

import java.util.UUID;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class AttachmentBinaryDTO extends AttachmentDTO {
	
	private static final long serialVersionUID = 405936953628090760L;
	private byte[] body;
	private String uuid;
	
	public AttachmentBinaryDTO() {
		uuid = UUID.randomUUID().toString();
	}

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}
	
	public int getBodyLength() {
		return body.length;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
