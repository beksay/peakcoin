package org.infosystema.peakcoin.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Entity
@Table(name="location")
public class Location extends AbstractEntity<Integer>  {
	private static final long serialVersionUID = 1L;
	private Dictionary category;
	private String name;
	private String info;
	private Set<Attachment> attachments = new HashSet<>();
	private Oblast oblast;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne
	@JoinColumn (name="oblast_id")
	public Oblast getOblast() {
		return oblast;
	}

	public void setOblast(Oblast oblast) {
		this.oblast = oblast;
	}

	@Column(length=5000)
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	@OneToMany
	@JoinTable(name="location_attachment", 
		joinColumns=@JoinColumn(name="location_id"),
		inverseJoinColumns=@JoinColumn(name="attachment_id")
	)
	public Set<Attachment> getAttachments() {
		return attachments;
	}
	
	public void setAttachments(Set<Attachment> attachments) {
		this.attachments = attachments;
	}

	@ManyToOne
	@JoinColumn (name="category_id")
	public Dictionary getCategory() {
		return category;
	}

	public void setCategory(Dictionary category) {
		this.category = category;
	}
}