package org.infosystema.peakcoin.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.infosystema.peakcoin.enums.CookStatus;


/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Entity
@Table(name="cook")
public class Cook extends AbstractEntity<Integer>  {
	private static final long serialVersionUID = 1L;
	private Set<Dictionary> foods;
	private CookStatus status;

	@Enumerated(EnumType.ORDINAL)
	public CookStatus getStatus() {
		return status;
	}

	public void setStatus(CookStatus status) {
		this.status = status;
	}

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
	  name = "cook_foods", 
	  joinColumns = @JoinColumn(name = "cook_id"), 
	  inverseJoinColumns = @JoinColumn(name = "dictionary_id"))	
	public Set<Dictionary> getFoods() {
		return foods;
	}
	
	public void setFoods(Set<Dictionary> foods) {
		this.foods = foods;
	}

}