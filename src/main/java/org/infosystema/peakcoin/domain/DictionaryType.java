package org.infosystema.peakcoin.domain;

import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Entity
@Table(name="dictionary_type")
public class DictionaryType extends AbstractEntity<Integer>  {
	private static final long serialVersionUID = 1L;
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}