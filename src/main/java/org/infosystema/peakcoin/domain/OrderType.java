package org.infosystema.peakcoin.domain;

import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Entity
@Table(name="order_type")
public class OrderType extends AbstractEntity<Integer>  {
	private static final long serialVersionUID = 1L;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}