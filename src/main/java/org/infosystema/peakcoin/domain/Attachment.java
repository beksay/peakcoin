package org.infosystema.peakcoin.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Kuttubek Aidaraliev
 */

@Entity
public class Attachment extends AbstractEntity<Integer> {

    private static final long serialVersionUID = -3583576310455965673L;
    private String fileName;
    private Boolean locked=false;
    private String repositoryName;
    private Boolean deleted=false;
    private Date dateCreated;
    private Boolean publicInfo=false;
    private byte[] data;
   

    public Attachment() {
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created")
    public Date getDateCreated() {
        return dateCreated;
    }
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
    
    @Column(name = "locked")
	public Boolean getLocked() {
        return locked;
    }
    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    @Column(name = "file_name")
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Column(name = "new_repository_name")
    public String getRepositoryName() {
        return repositoryName;
    }
    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    @Column(name = "is_deleted")
    public Boolean getDeleted() {
        return deleted;
    }
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
    
    @Column(name = "is_public_info")
	public Boolean getPublicInfo() {
		return publicInfo;
	}
	public void setPublicInfo(Boolean publicInfo) {
		this.publicInfo = publicInfo;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
}