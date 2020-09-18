package org.infosystema.peakcoin.domain;

import javax.persistence.*;

/**
 * @author Kuttubek Aidaraliev
 */

@Entity
@Cacheable(true)
@Access(AccessType.PROPERTY)
@Table(name = "role", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Role extends AbstractEntity<Integer> {

    private static final long serialVersionUID = -1716718384374303808L;
    private String name;
    private String humanName;
    private String description;

    public Role() {
    }

    public String getName() {
		return name;
	}
    
    public void setName(String name) {
		this.name = name;
	}
    
    @Column(name="human_name")
    public String getHumanName() {
		return humanName;
	}
    
    public void setHumanName(String humanName) {
		this.humanName = humanName;
	}
    
    public String getDescription() {
		return description;
	}
    
    public void setDescription(String description) {
		this.description = description;
	}

}