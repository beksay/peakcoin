package org.infosystema.peakcoin.dto;

import java.io.Serializable;
import java.util.Date;

import org.infosystema.peakcoin.domain.Attachment;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class AttachmentDTO implements Serializable {
	
	private static final long serialVersionUID = -8007607921616171695L;
	private String name;
	private Date dateCreated;
	private String mimeType;
	private String repositoryName;
	private Attachment attachment;
	
	public AttachmentDTO() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	
	public String getRepositoryName() {
		return repositoryName;
	}
	
	public void setRepositoryName(String repositoryName) {
		this.repositoryName = repositoryName;
	}

	public Attachment getAttachment() {
		return attachment;
	}
	
	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}
	
}
