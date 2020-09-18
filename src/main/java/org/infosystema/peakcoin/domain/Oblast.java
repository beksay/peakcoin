package org.infosystema.peakcoin.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Entity
@Table(name="oblast")
public class Oblast extends AbstractEntity<Integer>  {
	private static final long serialVersionUID = 1L;
	private String name;
	private Country country;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne
	@JoinColumn (name="country_id")
	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
}